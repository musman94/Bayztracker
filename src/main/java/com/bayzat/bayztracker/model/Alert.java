package com.bayzat.bayztracker.model;

import com.bayzat.bayztracker.enumeration.AlertStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "alert")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public final class Alert extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private AlertStatus alertStatus;

    @Column(name = "taget_value")
    private Double targetValue;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                ", currency='" + currency + '\'' +
                ", status='" + alertStatus + '\'' +
                ", targetValue=" + targetValue +
                ", user=" + user +
                ", createdAt='" + createdAt + '\'' +
                ", updateAt=" + updatedAt +
                '}';
    }
}