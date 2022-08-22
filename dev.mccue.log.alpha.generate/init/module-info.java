import dev.mccue.log.alpha.generate.AnnotationProcessor;

module dev.mccue.log.alpha.generate {
    exports dev.mccue.log.alpha.generate;

    requires java.compiler;

    provides javax.annotation.processing.Processor
            with AnnotationProcessor;
}