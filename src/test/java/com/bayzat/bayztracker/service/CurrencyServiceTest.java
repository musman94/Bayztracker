package com.bayzat.bayztracker.service;

import com.bayzat.bayztracker.dto.request.AddCurrencyRequestDto;
import com.bayzat.bayztracker.dto.response.CurrencyResponse;
import com.bayzat.bayztracker.enumeration.UnsupportedCurrencyType;
import com.bayzat.bayztracker.exception.NotFoundException;
import com.bayzat.bayztracker.exception.UnsupportedCurrencyCreationException;
import com.bayzat.bayztracker.model.Currency;
import com.bayzat.bayztracker.repository.CurrencyRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceTest {
    @Mock
    CurrencyRepository currencyRepository;

    @InjectMocks
    CurrencyServiceImpl currencyService;

    private Currency currency;

    private Currency currency2;

    private AddCurrencyRequestDto addCurrencyRequestDto;

    private CurrencyResponse currencyResponse;

    private CurrencyResponse currencyResponse2;


    @Before
    public void setup() {
        currency = new Currency();
        currency.setId(0L);
        currency.setName("testCurrency");
        currency.setSymbol("TC");
        currency.setCurrentPrice(10.5);

        currency2 = new Currency();
        currency2.setId(1L);
        currency2.setName("testCurrency2");
        currency2.setSymbol("TC2");
        currency2.setCurrentPrice(11.0);

        addCurrencyRequestDto = new AddCurrencyRequestDto();
        addCurrencyRequestDto.setName("testCurrency");
        addCurrencyRequestDto.setSymbol("TC");
        addCurrencyRequestDto.setCurrentPrice(10.5);

        currencyResponse = new CurrencyResponse();
        currencyResponse.setId(0L);
        currencyResponse.setName("testCurrency");
        currencyResponse.setSymbol("TC");
        currencyResponse.setCurrentPrice(10.5);

        currencyResponse2 = new CurrencyResponse();
        currencyResponse2.setId(1L);
        currencyResponse2.setName("testCurrency2");
        currencyResponse2.setSymbol("TC2");
        currencyResponse2.setCurrentPrice(11.0);
    }

    @Test
    public void testAddCurrency() {
        when(currencyRepository.save(any())).thenAnswer(new Answer<Currency>() {
            public Currency answer(InvocationOnMock invocation) throws Throwable {
                return currency;
            }
        });

        Assert.assertEquals(currencyResponse, currencyService.addCurrency(addCurrencyRequestDto));
    }

    @Test(expected = UnsupportedCurrencyCreationException.class)
    public void testAddCurrencyThrowsException() {
        addCurrencyRequestDto.setSymbol(UnsupportedCurrencyType.ETH.toString());

        currencyService.addCurrency(addCurrencyRequestDto);
    }

    @Test
    public void testRemoveCurrency() {
        when(currencyRepository.findByName(currency.getName())).thenReturn(java.util.Optional.ofNullable(currency));

        Assert.assertEquals(currencyResponse, currencyService.removeCurrency(currency.getName()));

    }

    @Test(expected = NotFoundException.class)
    public void testRemoveCurrencyThrowsException() {
        when(currencyRepository.findByName(currency.getName())).thenReturn(java.util.Optional.ofNullable(null));

        currencyService.removeCurrency(currency.getName());
    }

    @Test
    public void testGetCurrencyByName() {
        when(currencyRepository.findByName(currency.getName())).thenReturn(java.util.Optional.ofNullable(currency));

        Assert.assertEquals(currencyResponse, currencyService.getCurrencyByName(currency.getName()));

    }

    @Test
    public void testGetCurrencyById() {
        when(currencyRepository.findById(currency.getId())).thenReturn(java.util.Optional.ofNullable(currency));

        Assert.assertEquals(currency, currencyService.getCurrencyById(currency.getId()));

    }

    @Test(expected = NotFoundException.class)
    public void testGetCurrencyByIdThrowsException() {
        when(currencyRepository.findById(currency.getId())).thenReturn(java.util.Optional.ofNullable(null));

        currencyService.getCurrencyById(currency.getId());
    }

    @Test
    public void testGetCurrencyAll() {
        List<Currency> currencyList = new ArrayList<>();

        List<CurrencyResponse> currencyResponseList = new ArrayList<>();

        currencyList.add(currency);
        currencyList.add(currency2);

        currencyResponseList.add(currencyResponse);
        currencyResponseList.add(currencyResponse2);

        when(currencyRepository.findAll()).thenReturn(currencyList);

        Assert.assertEquals(currencyResponseList, currencyService.getCurrencyAll());
    }

}
