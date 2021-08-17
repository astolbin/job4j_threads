package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(1000000);

    public ThreadPool() {
        int poolSize = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < poolSize; i++) {
            Thread worker = new Worker();
            threads.add(worker);
            worker.start();
        }
    }

    public void work(Runnable job) {
        if (Thread.currentThread().isInterrupted()) {
            return;
        }
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    tasks.poll().run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}