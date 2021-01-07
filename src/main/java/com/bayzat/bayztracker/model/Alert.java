package com.bayzat.bayztracker.model;

import com.bayzat.bayztracker.enumeration.AlertStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "alert")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public final class Alert extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @Column(name = "target_value")
    private Double targetValue;

    @Enumerated(EnumType.STRING)
    private AlertStatus alertStatus;

    @PrePersist
    void preInsert() {
        if (this.alertStatus == null)
            this.alertStatus = AlertStatus.NEW;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                ", user=" + user +
                ", currency='" + currency + '\'' +
                ", targetValue=" + targetValue +
                ", status='" + alertStatus + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updateAt=" + updatedAt +
                '}';
    }
}