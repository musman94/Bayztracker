package com.bayzat.bayztracker.service;

import com.bayzat.bayztracker.enumeration.NotificationStatus;
import com.bayzat.bayztracker.model.Notification;
import com.bayzat.bayztracker.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    public void sendNotifications() {
        Optional<List<Notification>> unsentNotifications = notificationRepository.findAllByStatusEquals(NotificationStatus.NEW);

        if(unsentNotifications.isPresent()) {
            for(Notification unsentNotification : unsentNotifications.get()) {
                sendNotification(unsentNotification);

                unsentNotification.setStatus(NotificationStatus.SENT);

                notificationRepository.save(unsentNotification);
            }
        }
    }

    private void sendNotification(Notification notification) {
        String userId = notification.getUserId();

        String currencyId = notification.getCurrencyId();

        System.out.println("Notification sent to user with id: " + userId + " for currency with id: " + currencyId);
    }
}
