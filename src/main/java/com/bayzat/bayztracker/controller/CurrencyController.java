package com.bayzat.bayztracker.controller;

import com.bayzat.bayztracker.constant.MessageConstants;
import com.bayzat.bayztracker.dto.request.AddCurrencyRequestDto;
import com.bayzat.bayztracker.dto.request.RemoveCurrencyRequestDto;
import com.bayzat.bayztracker.dto.response.CurrencyResponse;
import com.bayzat.bayztracker.helper.ResponseHelper;
import com.bayzat.bayztracker.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/currency")
public class CurrencyController {
    @Autowired
    private ResponseHelper responseHelper;

    @Autowired
    private CurrencyService currencyService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addCurrency(@Valid @RequestBody AddCurrencyRequestDto request) {

        CurrencyResponse currency = currencyService.addCurrency(request);
        return responseHelper.okResponse(currency, MessageConstants.ADD_CURRENCY_SUCCESSFUL_MESSAGE);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResponseEntity removeCurrency(@Valid @RequestBody RemoveCurrencyRequestDto request) {

        currencyService.removeCurrency(request);
        return responseHelper.okResponse();
    }

}
