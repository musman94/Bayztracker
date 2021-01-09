package com.bayzat.bayztracker.service;

import com.bayzat.bayztracker.dto.request.AddAlertRequestDto;
import com.bayzat.bayztracker.dto.request.UpdateAlertRequestDto;
import com.bayzat.bayztracker.dto.response.AlertResponse;

public interface AlertService {
    AlertResponse addAlert(AddAlertRequestDto request);

    AlertResponse ackAlert(Long id);

    AlertResponse cancelAlert(Long id);

    AlertResponse updateAlert(UpdateAlertRequestDto requestDto);

    AlertResponse deleteAlert(Long id);
}
