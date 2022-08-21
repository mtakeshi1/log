module dev.mccue.log.alpha.slf4j {
    requires org.slf4j;
    requires dev.mccue.log.alpha;

    provides org.slf4j.spi.SLF4JServiceProvider
            with dev.mccue.log.alpha.slf4j.SLF4JAdapter;
}