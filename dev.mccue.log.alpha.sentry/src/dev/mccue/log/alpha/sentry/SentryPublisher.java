package dev.mccue.log.alpha.sentry;

import dev.mccue.log.alpha.publisher.Publisher;

import java.util.List;

public final class SentryPublisher implements Publisher {
    public SentryPublisher(HasSentryConfig hasSentryConfig) {

    }

    @Override
    public void publish(List<dev.mccue.log.alpha.Log> logs) {
        System.out.println("[SENTRY]: " + logs);
    }
}
