import dev.mccue.log.alpha.LoggerFactory;
import dev.mccue.log.alpha.publisher.Publisher;
import dev.mccue.log.alpha.publisher.FanOutLoggerFactory;

module dev.mccue.log.alpha.publisher {
    uses Publisher;
    requires dev.mccue.log.alpha;

    provides LoggerFactory with FanOutLoggerFactory;
}