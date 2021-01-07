package com.bayzat.bayztracker.model;

import com.bayzat.bayztracker.enumeration.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "notification")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public final class Notification extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "currency_id")
    private String currencyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NotificationStatus status;

    @PrePersist
    void preInsert() {
        if (this.status == null)
            this.status = status.NEW;
    }

    @Override
    public String toString() {
        return " Notification{" +
                "id=" + id +
                ", userId=" + userId +
                ", currency='" + currencyId + '\'' +
                ", status=" + status +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}