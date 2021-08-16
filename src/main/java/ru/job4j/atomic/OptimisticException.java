package ru.job4j.atomic;

public class OptimisticException extends RuntimeException {

    public OptimisticException(String message) {
        super(message);
    }
}