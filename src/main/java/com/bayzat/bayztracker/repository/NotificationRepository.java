package com.bayzat.bayztracker.repository;

import com.bayzat.bayztracker.enumeration.NotificationStatus;
import com.bayzat.bayztracker.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository  extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {
    Optional<List<Notification>> findAllByStatusEquals(NotificationStatus status);
}
