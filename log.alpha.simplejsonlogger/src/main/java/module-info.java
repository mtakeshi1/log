module dev.mccue.log.alpha.simplejsonlogger {
    requires dev.mccue.log.alpha;
    requires dev.mccue.log.alpha.json;
    provides dev.mccue.log.alpha.LoggerFactory with dev.mccue.log.alpha.simplejsonlogger.SimpleJsonLoggerFactory;

}