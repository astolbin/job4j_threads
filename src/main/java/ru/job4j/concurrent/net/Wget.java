package ru.job4j.concurrent.net;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")
        ) {
            byte[] dataBuffer = new byte[speed];
            int bytesRead;
            int bytesSum = 0;
            boolean run = true;

            while (run) {
                long start = System.currentTimeMillis();
                if ((bytesRead = in.read(dataBuffer, 0, speed)) == -1) {
                    run = false;
                } else {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    long time = System.currentTimeMillis() - start;
                    if (time < 1000) {
                        Thread.sleep(1000 - time);
                    }
                    bytesSum += bytesRead;
                }
                System.out.print("\rload: " + bytesSum + " bytes");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        System.out.println("Load start");
        wget.start();
        wget.join();
    }
}
