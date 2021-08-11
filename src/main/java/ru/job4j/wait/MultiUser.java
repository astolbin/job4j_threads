package ru.job4j.wait;

public class MultiUser {
    public static void main(String[] args) {
        var countBarrier = new CountBarrier(5);
        Thread master = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    for (int i = 1; i <= 5; i++) {
                        try {
                            Thread.sleep(1000);
                            System.out.print("\rcount " + i);
                            countBarrier.count();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                },
                "Master"
        );
        Thread slave = new Thread(
                () -> {
                    countBarrier.await();
                    System.out.println(
                            System.lineSeparator() + Thread.currentThread().getName() + " started"
                    );
                },
                "Slave"
        );
        master.start();
        slave.start();
    }
}
