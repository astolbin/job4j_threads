package ru.job4j.atomic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public Base get(int id) {
        return memory.get(id);
    }

    public boolean update(Base model) {
        BiFunction<Integer, Base, Base> updateFunction = (key, stored) -> {
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }

            Base updated = new Base(model.getId(), model.getVersion() + 1);
            updated.setName(model.getName());

            return updated;
        };

        return memory.computeIfPresent(model.getId(), updateFunction) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }
}
