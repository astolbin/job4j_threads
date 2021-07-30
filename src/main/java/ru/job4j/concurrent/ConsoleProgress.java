package ru.job4j.concurrent;

import java.util.List;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        int count = 0;
        List<String> process = List.of("\\", "|", "/");
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: " + process.get(count++ % 3));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}
