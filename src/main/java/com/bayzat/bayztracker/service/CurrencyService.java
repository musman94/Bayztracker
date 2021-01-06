package com.bayzat.bayztracker.service;

import com.bayzat.bayztracker.dto.request.AddCurrencyRequestDto;
import com.bayzat.bayztracker.dto.request.RemoveCurrencyRequestDto;
import com.bayzat.bayztracker.dto.response.CurrencyResponse;
import com.bayzat.bayztracker.model.Currency;

import java.util.Collection;
import java.util.List;

public interface CurrencyService {
    CurrencyResponse addCurrency(AddCurrencyRequestDto request);

    void removeCurrency(String name);

    CurrencyResponse getCurrency(String name);

    Currency getCurrencyById(Long Id);

    Collection<CurrencyResponse> getCurrencyAll();
}
