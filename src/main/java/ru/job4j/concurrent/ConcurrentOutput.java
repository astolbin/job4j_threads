package ru.job4j.concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread two = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        two.start();

        Thread three = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        three.start();

        System.out.println(Thread.currentThread().getName());
    }
}