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
public class AddCurrencyRequestDto {

    @NotNull
    private String name;

    @NotNull
    private String symbol;

    @NotNull
    private Double currentPrice;

}
