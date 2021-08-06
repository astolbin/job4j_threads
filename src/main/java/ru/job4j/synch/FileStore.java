package ru.job4j.synch;

import java.io.*;

public class FileStore implements Store {
    private final File file;

    public FileStore(File file) {
        this.file = file;
    }

    @Override
    public void save(String content) {
        try(BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            out.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
