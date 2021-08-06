package ru.job4j.synch;

import java.io.*;
import java.util.Arrays;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() {
        StringBuilder builder = new StringBuilder();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            byte[] dataBuffer = new byte[1024];
            while (in.read(dataBuffer, 0, 1024) != -1) {
                builder.append(Arrays.toString(dataBuffer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public String getContentWithoutUnicode() throws IOException {


        InputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            if (data < 0x80) {
                output += (char) data;
            }
        }
        return output;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            out.write(content.getBytes());
        }
    }
}
