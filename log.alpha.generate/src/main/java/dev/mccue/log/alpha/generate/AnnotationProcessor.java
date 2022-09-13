package dev.mccue.log.alpha.generate;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Set;

public final class AnnotationProcessor extends AbstractProcessor {
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of("dev.mccue.log.alpha.generate.DeriveLogger");
    }

    @Override
    public boolean process(
            Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv
    ) {
        var filer = this.processingEnv.getFiler();

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(DeriveLogger.class);
        for (var element : elements) {
            var typeElement = (TypeElement) element;
            var className = typeElement.getSimpleName();

            var annotation = typeElement.getAnnotation(DeriveLogger.class);

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
                    className + "Log", typeElement.getQualifiedName()
            );

            var classDeclEnd = "}";

            var classDecl = new StringBuilder();
            classDecl.append(packageDecl);
            classDecl.append(classDeclStart);
            classDecl.append("    static dev.mccue.log.alpha.Logger.Namespaced %s =".formatted(annotation.fieldName()));
            classDecl.append("\n         dev.mccue.log.alpha.LoggerFactory.getLogger(%s.class);\n".formatted(typeElement.getQualifiedName()));
            classDecl.append(classDeclEnd);

            try {
                var file = filer.createSourceFile(
                        (packageName == null ? "" : packageName + ".") + className + "Log",
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