package com.bayzat.bayztracker.service;

import com.bayzat.bayztracker.dto.request.AddCurrencyRequestDto;
import com.bayzat.bayztracker.dto.request.RemoveCurrencyRequestDto;
import com.bayzat.bayztracker.dto.response.CurrencyResponse;

public interface CurrencyService {
    CurrencyResponse addCurrency(AddCurrencyRequestDto request);

    void removeCurrency(RemoveCurrencyRequestDto request);
}
