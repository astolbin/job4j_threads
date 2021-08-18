package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class EmailNotification {
    private final AtomicInteger sentCounter = new AtomicInteger(0);
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        pool.submit(() -> {
            String subject = String.format(
                    "Notification %s to email %s", user.getName(), user.getEmail());
            String body = String.format("Add a new event to %s", user.getName());
            EmailSender.send(subject, body, user.getEmail());
            sentCounter.incrementAndGet();
        });
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getSentCount() {
        return sentCounter.get();
    }
}
