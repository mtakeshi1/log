package dev.mccue.log.alpha.publisher;
import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A Logger which will fan out logs in batches to publishers.
 */
public final class GlobalFanOutLogger {
    private static final List<PublisherWiring> WIRINGS = new ArrayList<>();

    private GlobalFanOutLogger() {
    }

    public static void registerPublisher(Publisher publisher, BlockingQueue<Log> mailbox, Duration publishDelay) {
        var wakeupThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(publishDelay.toMillis());
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        wakeupThread.setDaemon(true);
        wakeupThread.start();
        synchronized (WIRINGS) {
            WIRINGS.add(new PublisherWiring(
                    wakeupThread,
                    mailbox,
                    publisher
            ));
        }
    }

    public static void deregisterPublisher(Publisher publisher) {
        synchronized (WIRINGS) {
            WIRINGS.removeIf(wiring -> Objects.equals(wiring.publisher, publisher));
        }
    }

    public static void deregisterAllPublishers() {
        synchronized (WIRINGS) {
            for (var wiring : WIRINGS) {
                wiring.wakeupThread.interrupt();
            }
            WIRINGS.clear();
        }
    }

    public static void log(Log log) {
        synchronized (WIRINGS) {
            for (var wiring : WIRINGS) {
                synchronized (wiring.mailbox) {
                    wiring.mailbox.add(log);
                }
            }
        }
    }

    public static LoggerFactory provider() {
        return () -> GlobalFanOutLogger::log;
    }

    private record PublisherWiring(
            Thread wakeupThread,
            BlockingQueue<Log> mailbox,
            Publisher publisher
    ) {}

    @Override
    public String toString() {
        synchronized (WIRINGS) {
            return "GlobalFanOutLogger[publishers=" +
                    WIRINGS.stream().map(PublisherWiring::publisher).toList() +
                    "]";
        }
    }
}
