package ru.job4j.res;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserCacheTest {
    @Test
    public void whenFindAllReturnCopies() {
        UserCache cache = new UserCache();

        cache.add(User.of("user", 12));
        List<User> cachedUsers = cache.findAll();
        User userFromCache = cachedUsers.get(0);
        userFromCache.setName("user 2");

        assertEquals(User.of("user", 12), cache.findAll().get(0));
    }

    @Test
    public void whenFindByIdReturnCopy() {
        UserCache cache = new UserCache();

        cache.add(User.of("user", 12));
        User userFromCache = cache.findById(1);
        userFromCache.setName("user 2");

        assertEquals(User.of("user", 12), cache.findById(1));
    }
}