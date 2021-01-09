package com.bayzat.bayztracker.service;

import com.bayzat.bayztracker.dto.request.AddCurrencyRequestDto;
import com.bayzat.bayztracker.dto.response.CurrencyResponse;
import com.bayzat.bayztracker.model.Currency;

import java.util.Collection;

public interface CurrencyService {
    CurrencyResponse addCurrency(AddCurrencyRequestDto request);

    CurrencyResponse removeCurrency(String name);

    CurrencyResponse getCurrencyByName(String name);

    Currency getCurrencyById(Long Id);

    Collection<CurrencyResponse> getCurrencyAll();
}
