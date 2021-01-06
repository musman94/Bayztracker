package com.bayzat.bayztracker.repository;

import com.bayzat.bayztracker.model.Alert;
import com.bayzat.bayztracker.model.Currency;
import com.bayzat.bayztracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long>, JpaSpecificationExecutor<Alert> {
    Optional<Alert> findByUserAndCurrencyAndTargetValue(User user, Currency currency, Double targetValue);
}