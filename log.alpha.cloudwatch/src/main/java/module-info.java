@SuppressWarnings({ "requires-automatic", "requires-transitive-automatic" })
module dev.mccue.log.alpha.cloudwatch {
    requires transitive dev.mccue.log.alpha;
    requires transitive dev.mccue.log.alpha.publisher;
    requires transitive software.amazon.awssdk.services.cloudwatchlogs;
    requires dev.mccue.log.alpha.json;

    exports dev.mccue.log.alpha.cloudwatch;
}