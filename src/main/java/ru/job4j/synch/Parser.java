package ru.job4j.synch;

import java.util.function.Predicate;

public interface Parser {
    String getContent(Predicate<Character> filter);
}
