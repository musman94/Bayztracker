package com.bayzat.bayztracker.helper;

import com.bayzat.bayztracker.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseHelper {

    public <T> ResponseEntity okResponse(T data) {
        Response response = new Response(data, null, false);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    public <T> ResponseEntity okResponse(T data, String message) {
        Response response = new Response(data, message, false);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
