package com.bayzat.bayztracker.service;

import com.bayzat.bayztracker.dto.request.AddUserRequestDto;
import com.bayzat.bayztracker.dto.request.LoginRequestDto;
import com.bayzat.bayztracker.dto.response.JwtUser;
import com.bayzat.bayztracker.dto.response.UserResponse;
import com.bayzat.bayztracker.model.User;

public interface UserService {
    JwtUser login(LoginRequestDto requestDto);

    UserResponse signUp(AddUserRequestDto requestDto);

    User getUserById(Long id);
}
