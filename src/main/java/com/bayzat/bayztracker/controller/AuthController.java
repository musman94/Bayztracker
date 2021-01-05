package com.bayzat.bayztracker.controller;

import com.bayzat.bayztracker.dto.request.AddUserRequestDto;
import com.bayzat.bayztracker.dto.request.LoginRequestDto;
import com.bayzat.bayztracker.dto.response.JwtUser;
import com.bayzat.bayztracker.dto.response.UserResponse;
import com.bayzat.bayztracker.helper.ResponseHelper;
import com.bayzat.bayztracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private ResponseHelper responseHelper;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@Valid @RequestBody LoginRequestDto requestDto) {
        JwtUser user = userService.login(requestDto.getEmail(), requestDto.getPassword());
        return responseHelper.okResponse(user);
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity signUp(@Valid @RequestBody AddUserRequestDto requestDto) {

        UserResponse user = userService.signUp(requestDto);
        return responseHelper.okResponse(user);
    }
}

