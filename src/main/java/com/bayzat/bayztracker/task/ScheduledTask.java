package com.bayzat.bayztracker.task;

import com.bayzat.bayztracker.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {
    private static final int ONE_SECOND = 1000;
    private static final int THIRTY_SECONDS = 30 * ONE_SECOND;

    private final NotificationService notificationService;

    @Autowired
    public ScheduledTask(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Scheduled(fixedRate = THIRTY_SECONDS)
    public void sendAlertNotifications() {
        notificationService.sendNotifications();
    }
}
