package ru.job4j.pool;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmailNotificationTest {
    @Test
    public void whenSendWithThreadPool() {
        EmailNotification notificationSender = new EmailNotification();

        for (int i = 0; i < 5; i++ ) {
            notificationSender.emailTo(new User("Name " + i, "Email " + i));
        }

        notificationSender.close();

        assertEquals(5, notificationSender.getSentCount());
    }
}