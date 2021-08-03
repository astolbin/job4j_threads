package ru.job4j.res;

public class Count {
    private static Cache cache;

    public synchronized static Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }

    private static class Cache {
    }
}
