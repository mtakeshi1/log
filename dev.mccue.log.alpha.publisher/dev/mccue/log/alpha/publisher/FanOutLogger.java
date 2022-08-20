package dev.mccue.log.alpha.publisher;

import dev.mccue.log.alpha.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A Logger which will fan out logs in batches to publishers.
 *
 * Each publisher
 */
public final class FanOutLogger implements Logger {
    private final List<Publisher> publishers;
    private final List<PublisherWiring> wirings;

    public FanOutLogger(List<Publisher> publishers) {
        this.wirings = new ArrayList<>();
        for (var publisher : publishers) {
            var mailbox = new ArrayList<Log>();
            wirings.add(new PublisherWiring(
                    new Thread(() -> {
                        while (true) {
                            try {
                                Thread.sleep(500);
                                synchronized (mailbox) {
                                    publisher.publish(new ArrayList<>(mailbox));
                                    mailbox.clear();
                                }
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }),
                    mailbox,
                    publisher
            ));
        }
        this.publishers = List.copyOf(publishers);
    }

    @Override
    public void log(Log log) {
        System.out.println(log);
    }

    private record PublisherWiring(
            Thread wakeupThread,
            List<Log> mailbox,
            Publisher publisher
    ) {}
}
