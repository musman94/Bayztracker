package com.bayzat.bayztracker.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateAlertRequestDto {
    @NotNull
    private Long alertId;

    @NotNull
    private Long currencyId;

    @NotNull
    private Double targetValue;

}
