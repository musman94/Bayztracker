package com.bayzat.bayztracker.dto.response;

import com.bayzat.bayztracker.enumeration.AlertStatus;
import com.bayzat.bayztracker.enumeration.UserType;
import com.bayzat.bayztracker.model.Alert;
import com.bayzat.bayztracker.model.Currency;
import com.bayzat.bayztracker.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class AlertResponse {

    public final Long id;

    public final Long userId;

    public final Long currencyId;

    public final Double targetValue;

    public final AlertStatus alertStatus;

    public static AlertResponse of(Alert alert) {
        AlertResponseBuilder builder = AlertResponse.builder()
                .id(alert.getId())
                .userId(alert.getUser().getId())
                .currencyId(alert.getCurrency().getId())
                .targetValue(alert.getTargetValue())
                .alertStatus(alert.getAlertStatus());

        return builder.build();
    }

    public static Collection<AlertResponse> of(Collection<Alert> u) {
        return u.stream().map(AlertResponse::of).collect(Collectors.toList());
    }
}