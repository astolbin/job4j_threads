package ru.job4j.synch.user;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class MemoryStore implements Store {
    @GuardedBy("this")
    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    @Override
    public synchronized boolean add(User user) {
        if (users.containsKey(user.getId())) {
            return false;
        }

        users.put(user.getId(), user);
        return true;
    }

    @Override
    public synchronized boolean update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new IllegalStateException("User not found");
        }

        users.put(user.getId(), user);

        return true;
    }

    @Override
    public synchronized boolean delete(User user) {
        if (!users.containsKey(user.getId())) {
            throw new IllegalStateException("User not found");
        }

        users.remove(user.getId());

        return true;
    }

    @Override
    public synchronized void transfer(int fromId, int toId, int amount) {
        User userFrom = users.get(fromId);
        User userTo = users.get(toId);

        if (userFrom == null || userTo == null) {
            throw new IllegalStateException("User not found");
        }

        if (userFrom.getAmount() - amount < 0) {
            throw new IllegalArgumentException("Wrong amount to transfer");
        }

        userFrom.setAmount(userFrom.getAmount() - amount);
        userTo.setAmount(userTo.getAmount() + amount);
    }
}
