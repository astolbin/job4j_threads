package ru.job4j.synch;

import java.io.*;
import java.util.function.Predicate;

public class FileParser implements Parser {
    private final File file;

    public FileParser(File file) {
        this.file = file;
    }

    public String getContent() {
        return getContent(character -> true);
    }

    @Override
    public String getContent(Predicate<Character> filter) {
        StringBuilder text = new StringBuilder();
        try(BufferedReader in = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = in.read()) > 0) {
                if(filter.test((char)data)) {
                    text.append((char)data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }
}
