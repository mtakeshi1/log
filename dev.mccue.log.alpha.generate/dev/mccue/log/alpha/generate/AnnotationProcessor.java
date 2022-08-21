package dev.mccue.log.alpha.generate;

import java.io.IOException;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;

/**
 * Annotation processor that handles MagicBean annotations.
 */
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
        var messager = this.processingEnv.getMessager();
        var elementUtils = this.processingEnv.getElementUtils();

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(DeriveLogger.class);
        for (var element : elements) {
            if (!(element instanceof TypeElement typeElement)) {
                messager.printMessage(
                        Diagnostic.Kind.ERROR,
                        "Log annotation should only be placeable on classes.",
                        element
                );
            } else {
                var className = typeElement.getSimpleName();
                var enclosingElement = typeElement.getEnclosingElement();
                if (!(enclosingElement instanceof PackageElement packageElement)) {
                    messager.printMessage(
                            Diagnostic.Kind.ERROR,
                            "Currently only top level classes supported.",
                            element
                    );
                    return true;
                }

                String packageName;
                if (packageElement.isUnnamed()) {
                    packageName = null;
                }
                else {
                    packageName = packageElement.toString();
                }


                String selfExpr;
                if (typeElement.getAnnotation(DeriveLogger.class).useTypeSafeCast()) {
                    selfExpr = "(switch (this) { case %s __ -> __; })".formatted(className);
                } else {
                    selfExpr = "((%s) this)".formatted(className);
                }



                var packageDecl = packageName == null ? "" : "package " + packageName + ";\n\n";


                var classDeclStart = "sealed interface %s permits %s {\n\n".formatted(
                        className + "Log", className
                );

                var classDeclEnd = "}";

                var classDecl = new StringBuilder();
                classDecl.append(packageDecl);
                classDecl.append(classDeclStart);

                classDecl.append("    static final dev.mccue.log.alpha.Logger.Namespaced log = dev.mccue.log.alpha.LoggerFactory.getLogger(%s.class);\n".formatted(className));

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
                    throw new RuntimeException(e);
                }
            }
        }

        return true;
    }
}