package com.bayzat.bayztracker.service;

import com.bayzat.bayztracker.dto.request.AddAlertRequestDto;
import com.bayzat.bayztracker.dto.request.UpdateAlertRequestDto;
import com.bayzat.bayztracker.dto.response.AlertResponse;
import com.bayzat.bayztracker.enumeration.AlertStatus;
import com.bayzat.bayztracker.exception.InvalidParameterException;
import com.bayzat.bayztracker.exception.NotFoundException;
import com.bayzat.bayztracker.model.Alert;
import com.bayzat.bayztracker.model.Currency;
import com.bayzat.bayztracker.model.User;
import com.bayzat.bayztracker.repository.AlertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.bayzat.bayztracker.constant.ExceptionMessageConstants.ALERT_ALREADY_EXISTS;
import static com.bayzat.bayztracker.constant.ExceptionMessageConstants.INVALID_PARAMETER_MESSAGE;

@Service
@Slf4j
public class AlertServiceImpl implements AlertService {
    @Autowired
    UserService userService;

    @Autowired
    CurrencyService currencyService;

    @Autowired
    AlertRepository alertRepository;

    @Override
    @Transactional
    public AlertResponse addAlert(AddAlertRequestDto request) {
        User user = userService.getUserById(request.getUserId());
        Currency currency = currencyService.getCurrencyById(request.getCurrencyId());

        if(!checkAlertExists(user, currency, request.getTargetValue())) {
            Alert alert = Alert.builder()
                    .user(user)
                    .currency(currency)
                    .targetValue(request.getTargetValue())
                    .build();

            alert = alertRepository.save(alert);

            return AlertResponse.of(alert);
        }

        throw new InvalidParameterException(ALERT_ALREADY_EXISTS);
    }

    @Override
    @Transactional
    public AlertResponse ackAlert(Long id) {
        Alert alert = getAlertById(id);

        if(alert.getAlertStatus() != AlertStatus.TRIGGERED) {
            throw new InvalidParameterException("Only a triggered alert can be acknowledged");
        }

        alert.setAlertStatus(AlertStatus.ACKED);

        alertRepository.save(alert);

        return AlertResponse.of(alert);

    }

    @Override
    @Transactional
    public AlertResponse cancelAlert(Long id) {
        Alert alert = getAlertById(id);

        if(alert.getAlertStatus() != AlertStatus.NEW) {
            throw new InvalidParameterException("The alert cannot be cancelled after it has been triggered");
        }

        alert.setAlertStatus(AlertStatus.CANCELLED);

        alertRepository.save(alert);

        return AlertResponse.of(alert);

    }

    @Override
    @Transactional
    public AlertResponse updateAlert(UpdateAlertRequestDto request) {
        Alert alert = getAlertById(request.getAlertId());

        Currency currency = currencyService.getCurrencyById(request.getCurrencyId());

        alert.setCurrency(currency);
        alert.setTargetValue(request.getTargetValue());

        alertRepository.save(alert);

        return AlertResponse.of(alert);

    }

    @Override
    @Transactional
    public AlertResponse deleteAlert(Long id) {
        Alert alert = getAlertById(id);

        alertRepository.delete(alert);

        return AlertResponse.of(alert);

    }

    private boolean checkAlertExists(User user, Currency currency, Double targetValue) {
        Optional<Alert> alert = alertRepository.findByUserAndCurrencyAndTargetValue(user, currency, targetValue);

        return alert.isPresent();
    }

    private Alert getAlertById(Long id) {
        Optional<Alert> alert = alertRepository.findById(id);

        if(alert.isEmpty()) {
            throw new NotFoundException();
        }

        return alert.get();
    }
}
