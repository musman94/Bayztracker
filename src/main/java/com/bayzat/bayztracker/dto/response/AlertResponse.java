package com.bayzat.bayztracker.dto.response;

import com.bayzat.bayztracker.enumeration.AlertStatus;
import com.bayzat.bayztracker.model.Alert;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class AlertResponse {

    private Long id;

    private Long userId;

    private Long currencyId;

    private Double targetValue;

    private AlertStatus alertStatus;

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