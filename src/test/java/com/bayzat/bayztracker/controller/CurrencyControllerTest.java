package com.bayzat.bayztracker.controller;

import com.bayzat.bayztracker.dto.request.AddCurrencyRequestDto;
import com.bayzat.bayztracker.dto.response.CurrencyResponse;
import com.bayzat.bayztracker.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.bayzat.bayztracker.constant.MessageConstants.*;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@IfProfileValue(name = "spring.profiles.active", values = {"dev"})
public class CurrencyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CurrencyService currencyService;

    private AddCurrencyRequestDto addCurrencyRequestDto;

    private CurrencyResponse currencyResponse;

    private CurrencyResponse currencyResponse2;

    @Before
    public void setup() {
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
        currencyResponse2.setCurrentPrice(16.0);

    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    public void testAddCurrency() throws Exception {
        given(currencyService.addCurrency(addCurrencyRequestDto)).willReturn(currencyResponse);

        this.mockMvc.perform(post("/api/currency/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addCurrencyRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(currencyResponse.getId().intValue())))
                .andExpect(jsonPath("$.data.name", is(currencyResponse.getName())))
                .andExpect( jsonPath("$.data.symbol", is(currencyResponse.getSymbol())))
                .andExpect(jsonPath("$.data.currentPrice", is(currencyResponse.getCurrentPrice())))
                .andExpect(jsonPath("$.message", is(ADD_CURRENCY_SUCCESSFUL_MESSAGE)))
                .andExpect(jsonPath("$.error", is(false)));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    public void testRemoveCurrency() throws Exception {
        String currencyName = "testCurrency";

        given(currencyService.removeCurrency(currencyName)).willReturn(currencyResponse);

        this.mockMvc.perform(delete("/api/currency/remove").param("name", currencyName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(currencyResponse.getId().intValue())))
                .andExpect(jsonPath("$.data.name", is(currencyResponse.getName())))
                .andExpect( jsonPath("$.data.symbol", is(currencyResponse.getSymbol())))
                .andExpect(jsonPath("$.data.currentPrice", is(currencyResponse.getCurrentPrice())))
                .andExpect(jsonPath("$.message", is(REMOVE_CURRENCY_SUCCESSFUL_MESSAGE)))
                .andExpect(jsonPath("$.error", is(false)));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    public void testGetCurrency() throws Exception {
        String currencyName = "testCurrency";

        given(currencyService.getCurrencyByName(currencyName)).willReturn(currencyResponse);

        this.mockMvc.perform(get("/api/currency/get").param("name", currencyName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(currencyResponse.getId().intValue())))
                .andExpect(jsonPath("$.data.name", is(currencyResponse.getName())))
                .andExpect( jsonPath("$.data.symbol", is(currencyResponse.getSymbol())))
                .andExpect(jsonPath("$.data.currentPrice", is(currencyResponse.getCurrentPrice())));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    public void testGetCurrencyAll() throws Exception {
        List<CurrencyResponse> currencyResponseList = new ArrayList<>();

        currencyResponseList.add(currencyResponse);
        currencyResponseList.add(currencyResponse2);

        given(currencyService.getCurrencyAll()).willReturn(currencyResponseList);

        this.mockMvc.perform(get("/api/currency/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", is(currencyResponse.getId().intValue())))
                .andExpect(jsonPath("$.data[0].name", is(currencyResponse.getName())))
                .andExpect( jsonPath("$.data[0].symbol", is(currencyResponse.getSymbol())))
                .andExpect(jsonPath("$.data[0].currentPrice", is(currencyResponse.getCurrentPrice())))
                .andExpect(jsonPath("$.data[1].id", is(currencyResponse2.getId().intValue())))
                .andExpect(jsonPath("$.data[1].name", is(currencyResponse2.getName())))
                .andExpect( jsonPath("$.data[1].symbol", is(currencyResponse2.getSymbol())))
                .andExpect(jsonPath("$.data[1].currentPrice", is(currencyResponse2.getCurrentPrice())));
    }

}
