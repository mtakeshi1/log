package dev.mccue.log.alpha.record;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Set;
import java.util.stream.Collectors;

public final class AnnotationProcessor extends AbstractProcessor {
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of("dev.mccue.log.alpha.record.LoggableRecord");
    }

    @Override
    public boolean process(
            Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv
    ) {
        var filer = this.processingEnv.getFiler();

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(LoggableRecord.class);
        for (var element : elements) {
            var typeElement = (TypeElement) element;
            if (typeElement.getKind() != ElementKind.RECORD) {
                throw new RuntimeException("Needs to be a record");
            }
            var annotation = typeElement.getAnnotation(LoggableRecord.class);



            var className = typeElement.getSimpleName();

            String selfExpr;
            if (annotation.useTypeSafeCast()) {
                selfExpr = "(switch (this) { case %s __ -> __; })".formatted(className);
            } else {
                selfExpr = "((%s) this)".formatted(className);
            }

            var topElement = typeElement.getEnclosingElement();
            while (!(topElement instanceof PackageElement packageElement)) {
                topElement = topElement.getEnclosingElement();
            }

            String packageName;
            if (packageElement.isUnnamed()) {
                packageName = null;
            } else {
                packageName = packageElement.toString();
            }


            var packageDecl = packageName == null ? "" : "package " + packageName + ";\n\n";


            var classDeclStart = "sealed interface %s permits %s {\n".formatted(
                    className + "Loggable", typeElement.getQualifiedName()
            );

            var classDeclEnd = "}";

            var classDecl = new StringBuilder();
            classDecl.append(packageDecl);
            classDecl.append(classDeclStart);
            String logMethod = """
                        default void log(dev.mccue.log.alpha.Logger logger, dev.mccue.log.alpha.Log.Category category) {
                            logger.%s(
                                category,
                    %s
                            );
                        }
                        
                        default void log(dev.mccue.log.alpha.Logger.Namespaced logger, String name) {
                            logger.%s(
                                name,
                    %s
                            );
                        }
                    """;
            var methodName = switch (annotation.defaultLogLevel()) {
                case UNSPECIFIED -> "log";
                case TRACE -> "trace";
                case DEBUG -> "debug";
                case INFO -> "info";
                case WARN -> "warn";
                case ERROR -> "error";
                case FATAL -> "fatal";
            };
            var params =                     typeElement.getRecordComponents()
                    .stream()
                    .map(recordComponent -> "            dev.mccue.log.alpha.Log.Entry.of(\"%s\", %s.%s())"
                            .formatted(
                                    recordComponent.getSimpleName(),
                                    selfExpr,
                                    recordComponent.getSimpleName()
                            ))
                    .collect(Collectors.joining(",\n"));

            logMethod = logMethod.formatted(
                    methodName, params,
                    methodName, params
            );

            classDecl.append(logMethod);
            classDecl.append(classDeclEnd);

            try {
                var file = filer.createSourceFile(
                        (packageName == null ? "" : packageName + ".") + className + "Loggable",
                        element
                );
                try (var writer = file.openWriter()) {
                    writer.append(classDecl.toString());
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        return true;
    }
}
