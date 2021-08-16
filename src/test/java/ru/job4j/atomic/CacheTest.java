package ru.job4j.atomic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {
    Cache cache;
    Base base;

    @Before
    public void setUp() {
        cache = new Cache();
        base = new Base(1, 0);
    }

    @Test
    public void whenAdd() {
        assertTrue(cache.add(base));
        assertEquals(base, cache.get(base.getId()));
    }

    @Test
    public void whenAddFail() {
        cache.add(base);
        assertFalse(cache.add(base));
    }

    @Test
    public void whenDelete() {
        cache.add(base);
        cache.delete(base);

        assertNull(cache.get(base.getId()));
    }

    @Test
    public void whenUpdateNotFound() {
        assertFalse(cache.update(base));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateFail() {
        cache.add(base);
        cache.update(new Base(1, 1));
    }

    @Test
    public void whenUpdateSuccess() {
        cache.add(base);
        base.setName("123");

        assertTrue(cache.update(base));
        assertEquals(base, cache.get(base.getId()));
    }
}