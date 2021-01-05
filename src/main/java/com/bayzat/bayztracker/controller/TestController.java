package com.bayzat.bayztracker.controller;

import com.bayzat.bayztracker.dto.request.LoginRequestDto;
import com.bayzat.bayztracker.dto.response.JwtUser;
import com.bayzat.bayztracker.helper.ResponseHelper;
import com.bayzat.bayztracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
public class TestController {
    @Autowired
    private ResponseHelper responseHelper;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void login(@Valid @RequestBody LoginRequestDto requestDto) {
        System.out.println("testing");
    }
}
