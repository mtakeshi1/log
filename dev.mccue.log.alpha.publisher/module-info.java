import dev.mccue.log.alpha.LoggerFactory;
import dev.mccue.log.alpha.publisher.GlobalFanOutLogger;
import dev.mccue.log.alpha.publisher.Publisher;

module dev.mccue.log.alpha.publisher {
    uses Publisher;
    requires com.lmax.disruptor;
    requires transitive dev.mccue.log.alpha;
    exports dev.mccue.log.alpha.publisher;

    provides LoggerFactory with GlobalFanOutLogger;
}