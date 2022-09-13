module dev.mccue.log.alpha.json {
    requires dev.mccue.log.alpha;
    requires transitive com.fasterxml.jackson.core;
    requires transitive com.fasterxml.jackson.databind;
    exports dev.mccue.log.alpha.jackson;
}