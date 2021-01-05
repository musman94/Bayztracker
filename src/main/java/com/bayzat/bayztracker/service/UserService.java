package com.bayzat.bayztracker.service;

import com.bayzat.bayztracker.dto.request.AddUserRequestDto;
import com.bayzat.bayztracker.dto.response.JwtUser;
import com.bayzat.bayztracker.dto.response.UserResponse;

public interface UserService {
    JwtUser login(String email, String pass);

    UserResponse signUp(AddUserRequestDto requestDto);
}
