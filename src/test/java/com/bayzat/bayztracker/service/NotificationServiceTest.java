package com.bayzat.bayztracker.service;

import com.bayzat.bayztracker.enumeration.NotificationStatus;
import com.bayzat.bayztracker.model.Notification;
import com.bayzat.bayztracker.repository.NotificationRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private Notification notification;

    private List<Notification> notificationList;

    private PrintStream standardOut;

    private ByteArrayOutputStream outputStreamCaptor;

    @Before
    public void setup() {
        notification = new Notification();
        notification.setId(0L);
        notification.setUserId("1");
        notification.setCurrencyId("2");
        notification.setStatus(NotificationStatus.NEW);

        notificationList = new ArrayList<>();
        notificationList.add(notification);

        standardOut = System.out;
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testSendNotifications() {
        String expectedOutput = "Notification sent to user with id: " + notification.getUserId() + " for currency with id: " + notification.getCurrencyId();
        when(notificationRepository.findAllByStatusEquals(NotificationStatus.NEW)).thenReturn(java.util.Optional.ofNullable(notificationList));

        notificationService.sendNotifications();
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @After
    public void tearDown() {
        System.setOut(standardOut);
    }
}
