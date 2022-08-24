module dev.mccue.log.alpha.main {
    requires dev.mccue.log.alpha;
    requires dev.mccue.log.alpha.generate;
    requires dev.mccue.log.alpha.publisher;
    requires dev.mccue.log.alpha.console;
    requires dev.mccue.log.alpha.sentry;
    requires dev.mccue.log.alpha.record;

    requires org.slf4j;

    requires dev.mccue.log.alpha.slf4j;
    requires java.desktop;
}