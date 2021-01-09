package com.bayzat.bayztracker.service;

import com.bayzat.bayztracker.dto.request.AddAlertRequestDto;
import com.bayzat.bayztracker.dto.request.UpdateAlertRequestDto;
import com.bayzat.bayztracker.dto.response.AlertResponse;
import com.bayzat.bayztracker.enumeration.AlertStatus;
import com.bayzat.bayztracker.enumeration.UserType;
import com.bayzat.bayztracker.exception.InvalidParameterException;
import com.bayzat.bayztracker.exception.NotFoundException;
import com.bayzat.bayztracker.model.Alert;
import com.bayzat.bayztracker.model.Currency;
import com.bayzat.bayztracker.model.User;
import com.bayzat.bayztracker.repository.AlertRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AlertServiceTest {

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private CurrencyServiceImpl currencyService;

    @InjectMocks
    private AlertServiceImpl alertService;

    private User user;

    private Currency currency;

    private Alert alert;

    private AlertResponse alertResponse;

    private AddAlertRequestDto addAlertRequestDto;

    private UpdateAlertRequestDto updateAlertRequestDto;

    @Before
    public void setup() {
        user = new User();
        user.setId(1L);
        user.setName("adminUser1");
        user.setEmail("adminUser1@gmail.com");
        user.setType(UserType.NORMAL);

        currency = new Currency();
        currency.setId(2L);
        currency.setName("testCurrency");
        currency.setSymbol("TC");
        currency.setCurrentPrice(10.5);

        alert = new Alert();
        alert.setId(0L);
        alert.setUser(user);
        alert.setCurrency(currency);
        alert.setTargetValue(16.0);
        alert.setAlertStatus(AlertStatus.NEW);

        alertResponse = new AlertResponse();
        alertResponse.setId(0L);
        alertResponse.setUserId(user.getId());
        alertResponse.setCurrencyId(currency.getId());
        alertResponse.setTargetValue(16.0);
        alertResponse.setAlertStatus(AlertStatus.NEW);

        addAlertRequestDto = new AddAlertRequestDto();
        addAlertRequestDto.setUserId(1L);
        addAlertRequestDto.setCurrencyId(2L);
        addAlertRequestDto.setTargetValue(16.0);

        updateAlertRequestDto = new UpdateAlertRequestDto();
        updateAlertRequestDto.setAlertId(0L);
        updateAlertRequestDto.setCurrencyId(2L);
        updateAlertRequestDto.setTargetValue(18.0);

    }

    @Test
    public void testAddAlert() {
        when(userService.getUserById(1L)).thenReturn(user);
        when(currencyService.getCurrencyById(2L)).thenReturn(currency);

        when(alertRepository.save(any())).thenAnswer(new Answer<Alert>() {
            public Alert answer(InvocationOnMock invocation) throws Throwable {
                return alert;
            }
        });

        Assert.assertEquals(alertResponse, alertService.addAlert(addAlertRequestDto));
    }

    @Test(expected = InvalidParameterException.class)
    public void testAddAlertThrowsException() {
        when(userService.getUserById(1L)).thenReturn(user);
        when(currencyService.getCurrencyById(2L)).thenReturn(currency);
        when(alertRepository.findByUserAndCurrencyAndTargetValue(user, currency, alert.getTargetValue())).thenReturn(java.util.Optional.ofNullable(alert));

        alertService.addAlert(addAlertRequestDto);
    }


    @Test
    public void testAckAlert() {
        alert.setAlertStatus(AlertStatus.TRIGGERED);

        when(alertRepository.findById(alert.getId())).thenReturn(java.util.Optional.ofNullable(alert));
        when(alertRepository.save(alert)).thenReturn(alert);

        alertResponse.setAlertStatus(AlertStatus.ACKED);
        Assert.assertEquals(alertResponse, alertService.ackAlert(alert.getId()));

    }

    @Test(expected = InvalidParameterException.class)
    public void testAckAlertThrowsException() {
        alert.setAlertStatus(AlertStatus.NEW);

        when(alertRepository.findById(alert.getId())).thenReturn(java.util.Optional.ofNullable(alert));

        alertService.ackAlert(alert.getId());
    }

    @Test
    public void testCancelAlert() {
        alert.setAlertStatus(AlertStatus.NEW);

        when(alertRepository.findById(alert.getId())).thenReturn(java.util.Optional.ofNullable(alert));
        when(alertRepository.save(alert)).thenReturn(alert);

        alertResponse.setAlertStatus(AlertStatus.CANCELLED);
        Assert.assertEquals(alertResponse, alertService.cancelAlert(alert.getId()));
    }

    @Test(expected = InvalidParameterException.class)
    public void testCancelAlertThrowsException() {
        alert.setAlertStatus(AlertStatus.TRIGGERED);

        when(alertRepository.findById(alert.getId())).thenReturn(java.util.Optional.ofNullable(alert));

        alertService.cancelAlert(alert.getId());
    }

    @Test
    public void testUpdateAlert() {
        alert.setTargetValue(18.0);
        alert.setAlertStatus(AlertStatus.NEW);

        alertResponse.setTargetValue(18.0);

        when(currencyService.getCurrencyById(2L)).thenReturn(currency);

        when(alertRepository.findById(alert.getId())).thenReturn(java.util.Optional.ofNullable(alert));

        Assert.assertEquals(alertResponse, alertService.updateAlert(updateAlertRequestDto));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateAlertThrowsException() {
        when(alertRepository.findById(alert.getId())).thenReturn(java.util.Optional.ofNullable(null));

        alertService.updateAlert(updateAlertRequestDto);
    }

    @Test
    public void testDeleteAlert() {
        when(alertRepository.findById(alert.getId())).thenReturn(java.util.Optional.ofNullable(alert));

        Assert.assertEquals(alertResponse, alertService.deleteAlert(alert.getId()));
    }
}
