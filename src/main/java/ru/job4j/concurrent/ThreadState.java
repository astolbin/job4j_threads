package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Runnable treadInfo = () -> showThreadInfo(Thread.currentThread());

        Thread first = new Thread(treadInfo);
        Thread second = new Thread(treadInfo);

        showThreadInfo(first);
        showThreadInfo(second);

        first.start();
        second.start();

        while (first.getState() != Thread.State.TERMINATED
              || second.getState() != Thread.State.TERMINATED
        ) {
            showThreadInfo(first);
            showThreadInfo(second);
        }

        showThreadInfo(first);
        showThreadInfo(second);
    }

    private static void showThreadInfo(Thread thread) {
        System.out.format("tread: %s, state: %s%n", thread.getName(), thread.getState());
    }
}
