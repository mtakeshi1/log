import dev.mccue.log.alpha.publisher.Publisher;

module dev.mccue.log.alpha.console {
    requires dev.mccue.log.alpha.publisher;
    exports dev.mccue.log.alpha.console;
    provides Publisher with dev.mccue.log.alpha.console.ConsolePublisher;
}