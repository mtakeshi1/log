module dev.mccue.log.alpha.record {
    requires java.compiler;

    requires dev.mccue.log.alpha;

    exports dev.mccue.log.alpha.record;
    provides javax.annotation.processing.Processor
            with dev.mccue.log.alpha.record.AnnotationProcessor;
}