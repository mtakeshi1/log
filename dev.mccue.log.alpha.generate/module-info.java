module dev.mccue.log.alpha.generate {
    exports dev.mccue.log.alpha.generate;
    exports dev.mccue.log.alpha.generate.processor;

    requires java.compiler;

    provides javax.annotation.processing.Processor
            with dev.mccue.log.alpha.generate.processor.AnnotationProcessor;
}