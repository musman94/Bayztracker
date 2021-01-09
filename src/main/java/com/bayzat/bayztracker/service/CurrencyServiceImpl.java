package com.bayzat.bayztracker.service;

import com.bayzat.bayztracker.dto.request.AddCurrencyRequestDto;
import com.bayzat.bayztracker.dto.response.CurrencyResponse;
import com.bayzat.bayztracker.enumeration.UnsupportedCurrencyType;
import com.bayzat.bayztracker.exception.NotFoundException;
import com.bayzat.bayztracker.exception.UnsupportedCurrencyCreationException;
import com.bayzat.bayztracker.model.Currency;
import com.bayzat.bayztracker.repository.CurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.bayzat.bayztracker.constant.ExceptionMessageConstants.CURRENCY_NOT_FOUND_MESSAGE;
import static com.bayzat.bayztracker.constant.ExceptionMessageConstants.UNSUPPORTED_CURRENCY_MESSAGE;

@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    @Transactional
    public CurrencyResponse addCurrency(AddCurrencyRequestDto request) {
        if(!checkValidCurrencyType(request.getSymbol())) {
            throw new UnsupportedCurrencyCreationException(UNSUPPORTED_CURRENCY_MESSAGE);
        }

        Currency currency = Currency.builder()
                .name(request.getName())
                .symbol(request.getSymbol())
                .currentPrice(request.getCurrentPrice())
                .build();

        currency = currencyRepository.save(currency);

        return CurrencyResponse.of(currency);
    }

    @Override
    @Transactional
    public CurrencyResponse removeCurrency(String name) {
        Currency currency = checkCurrencyExistsByName(name);

        currencyRepository.delete(currency);

        return CurrencyResponse.of(currency);
    }

    @Override
    @Transactional(readOnly = true)
    public CurrencyResponse getCurrencyByName(String name) {
        Currency currency = checkCurrencyExistsByName(name);

        return CurrencyResponse.of(currency);
    }

    @Override
    @Transactional(readOnly = true)
    public Currency getCurrencyById(Long id) {
        Optional<Currency> currency = currencyRepository.findById(id);

        if(currency.isEmpty()) {
            throw new NotFoundException(CURRENCY_NOT_FOUND_MESSAGE);
        }

        return currency.get();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<CurrencyResponse> getCurrencyAll() {
        List<Currency> currencyList = currencyRepository.findAll();

        return CurrencyResponse.of(currencyList);
    }

    private Currency checkCurrencyExistsByName(String name) {
        Optional<Currency> currency = currencyRepository.findByName(name);

        if(currency.isEmpty()) {
            throw new NotFoundException(CURRENCY_NOT_FOUND_MESSAGE);
        }

        return currency.get();
    }

    private boolean checkValidCurrencyType(String currencySymbol) {
        for(UnsupportedCurrencyType type: UnsupportedCurrencyType.values()) {
            if(currencySymbol.equals(type.name())) {
                return false;
            }
        }

        return true;
    }

}
