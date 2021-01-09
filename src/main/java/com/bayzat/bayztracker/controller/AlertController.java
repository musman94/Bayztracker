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

import static com.bayzat.bayztracker.constant.MessageConstants.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/alert")
public class AlertController {

    private final ResponseHelper responseHelper;

    private final AlertService alertService;

    @Autowired
    public AlertController(ResponseHelper responseHelper, AlertService alertService) {
        this.responseHelper = responseHelper;
        this.alertService = alertService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addAlert(@Valid @RequestBody AddAlertRequestDto request) {
        AlertResponse alert = alertService.addAlert(request);
        return responseHelper.okResponse(alert, ADD_ALERT_SUCCESSFUL_MESSAGE);
    }

    @RequestMapping(value = "/ack", method = RequestMethod.POST)
    public ResponseEntity ackAlert(@Valid @RequestParam Long id) {
        AlertResponse alert = alertService.ackAlert(id);
        return responseHelper.okResponse(alert, ACK_ALERT_SUCCESSFUL_MESSAGE);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ResponseEntity cancelAlert(@Valid @RequestParam Long id) {
        AlertResponse alert = alertService.cancelAlert(id);
        return responseHelper.okResponse(alert, CANCEL_ALERT_SUCCESSFUL_MESSAGE);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity updateAlert(@Valid @RequestBody UpdateAlertRequestDto request) {
        AlertResponse alert = alertService.updateAlert(request);
        return responseHelper.okResponse(alert, UPDATE_ALERT_SUCCESSFUL_MESSAGE);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteAlert(@Valid @RequestParam Long id) {
        AlertResponse alert = alertService.deleteAlert(id);
        return responseHelper.okResponse(alert, DELETE_ALERT_SUCCESSFUL_MESSAGE);
    }
}
