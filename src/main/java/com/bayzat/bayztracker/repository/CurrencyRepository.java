package com.bayzat.bayztracker.repository;

import com.bayzat.bayztracker.model.Currency;
import com.bayzat.bayztracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long>, JpaSpecificationExecutor<Currency> {
    Optional<Currency> findByName(String name);
}
