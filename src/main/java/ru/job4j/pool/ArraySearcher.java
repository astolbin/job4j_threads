package ru.job4j.pool;

import java.util.concurrent.RecursiveTask;

public class ArraySearcher<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T item;
    private int from;
    private int to;

    public ArraySearcher(T[] array, T item) {
        this.array = array;
        this.item = item;
        this.from = 0;
        this.to = array.length;
    }

    public ArraySearcher(T[] array, T item, int from, int to) {
        this(array, item);
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return searchItemIndex();
        }

        int middle = (from + to) / 2;

        ArraySearcher<T> searcherLeft = new ArraySearcher<>(array, item, from, middle);
        ArraySearcher<T> searcherRight = new ArraySearcher<>(array, item, middle + 1, to);

        searcherLeft.fork();
        searcherRight.fork();

        int leftResult = searcherLeft.join();
        int rightResult = searcherRight.join();

        return leftResult != -1 ? leftResult : rightResult;
    }

    private int searchItemIndex() {
        for (int i = from; i < to; i++) {
            if (array[i].equals(item)) {
                return i;
            }
        }

        return -1;
    }
}
