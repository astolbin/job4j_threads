package ru.job4j.res;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName(), user.getId()));
    }

    public User findById(int id) {
        return User.of(users.get(id).getName(), users.get(id).getId());
    }

    public List<User> findAll() {
        return users.values().stream()
                .map(user -> User.of(user.getName(), user.getId()))
                .collect(Collectors.toList());
    }
}
