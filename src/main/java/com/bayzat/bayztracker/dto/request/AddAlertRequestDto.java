package com.bayzat.bayztracker.dto.request;

import com.bayzat.bayztracker.enumeration.AlertStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddAlertRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long currencyId;

    @NotNull
    private Double targetValue;

    private AlertStatus status;
}
