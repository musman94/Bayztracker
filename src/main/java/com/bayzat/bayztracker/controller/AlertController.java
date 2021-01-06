package com.bayzat.bayztracker.controller;

import com.bayzat.bayztracker.dto.request.AddAlertRequestDto;
import com.bayzat.bayztracker.dto.request.UpdateAlertRequestDto;
import com.bayzat.bayztracker.dto.response.AlertResponse;
import com.bayzat.bayztracker.helper.ResponseHelper;
import com.bayzat.bayztracker.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bayzat.bayztracker.constant.MessageConstants.ADD_ALERT_SUCCESSFUL_MESSAGE;

@RestController
@CrossOrigin("*")
@RequestMapping("/alert")
public class AlertController {
    @Autowired
    private ResponseHelper responseHelper;

    @Autowired
    private AlertService alertService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addAlert(@Valid @RequestBody AddAlertRequestDto request) {
        AlertResponse alert = alertService.addAlert(request);
        return responseHelper.okResponse(alert, ADD_ALERT_SUCCESSFUL_MESSAGE);
    }

    @RequestMapping(value = "/ack", method = RequestMethod.POST)
    public ResponseEntity ackAlert(@Valid @RequestParam Long id) {
        AlertResponse alert = alertService.ackAlert(id);
        return responseHelper.okResponse(alert, ADD_ALERT_SUCCESSFUL_MESSAGE);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ResponseEntity cancelAlert(@Valid @RequestParam Long id) {
        AlertResponse alert = alertService.cancelAlert(id);
        return responseHelper.okResponse(alert, ADD_ALERT_SUCCESSFUL_MESSAGE);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity updateAlert(@Valid @RequestBody UpdateAlertRequestDto request) {
        AlertResponse alert = alertService.updateAlert(request);
        return responseHelper.okResponse(alert, ADD_ALERT_SUCCESSFUL_MESSAGE);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteAlert(@Valid @RequestParam Long id) {
        AlertResponse alert = alertService.deleteAlert(id);
        return responseHelper.okResponse(alert, ADD_ALERT_SUCCESSFUL_MESSAGE);
    }
}
