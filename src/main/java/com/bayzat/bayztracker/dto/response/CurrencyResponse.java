package com.bayzat.bayztracker.dto.response;

import com.bayzat.bayztracker.model.Currency;
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
public class CurrencyResponse {

    private Long id;

    private String name;

    private String symbol;

    private Double currentPrice;

    public static CurrencyResponse of(Currency currency) {
        CurrencyResponseBuilder builder = CurrencyResponse.builder()
                .id(currency.getId())
                .name(currency.getName())
                .symbol(currency.getSymbol())
                .currentPrice(currency.getCurrentPrice());

        return builder.build();
    }

    public static Collection<CurrencyResponse> of(Collection<Currency> currency) {
        return currency.stream().map(CurrencyResponse::of).collect(Collectors.toList());
    }

}
