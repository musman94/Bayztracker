package com.bayzat.bayztracker.service;

import com.bayzat.bayztracker.config.PasswordHelper;
import com.bayzat.bayztracker.config.jwt.JwtProvider;
import com.bayzat.bayztracker.dto.request.AddUserRequestDto;
import com.bayzat.bayztracker.dto.request.LoginRequestDto;
import com.bayzat.bayztracker.dto.response.JwtResponse;
import com.bayzat.bayztracker.dto.response.JwtUser;
import com.bayzat.bayztracker.dto.response.UserResponse;
import com.bayzat.bayztracker.exception.InvalidParameterException;
import com.bayzat.bayztracker.exception.NotFoundException;
import com.bayzat.bayztracker.model.User;
import com.bayzat.bayztracker.repository.UserRepository;
import com.bayzat.bayztracker.constant.ExceptionMessageConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    @Transactional
    public JwtUser login(LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwt = jwtProvider.generateJwtToken(authentication);
        return JwtUser.from(user, new JwtResponse(jwt));
    }

    @Override
    @Transactional
    public UserResponse signUp(AddUserRequestDto request) {
        if (!checkUserExist(request.getEmail())) {
            User user = User.builder()
                    .name(request.getName())
                    .cipher(passwordHelper.encode(request.getPassword()))
                    .email(request.getEmail())
                    .type(request.getUserType())
                    .build();

            user = userRepository.save(user);

            return UserResponse.of(user);
        }

        throw new InvalidParameterException(ExceptionMessageConstants.USER_ALREADY_EXISTS_MESSAGE);
    }

    @Override
    @Transactional
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) {
            throw new NotFoundException(ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE);
        }

        return user.get();
    }

    private boolean checkUserExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return user.isPresent();
    }

}
