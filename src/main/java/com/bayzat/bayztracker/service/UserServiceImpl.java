package com.bayzat.bayztracker.service;

import com.bayzat.bayztracker.config.PasswordHelper;
import com.bayzat.bayztracker.config.jwt.JwtProvider;
import com.bayzat.bayztracker.config.jwt.JwtUserDetailsService;
import com.bayzat.bayztracker.dto.request.AddUserRequestDto;
import com.bayzat.bayztracker.dto.response.JwtResponse;
import com.bayzat.bayztracker.dto.response.JwtUser;
import com.bayzat.bayztracker.dto.response.UserResponse;
import com.bayzat.bayztracker.exception.InvalidParameterException;
import com.bayzat.bayztracker.model.User;
import com.bayzat.bayztracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.bayzat.bayztracker.constant.ExceptionMessageConstants.USER_ALREADY_EXISTS;

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
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    @Transactional
    public JwtUser login(String email, String pass) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, pass)
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

        throw new InvalidParameterException(USER_ALREADY_EXISTS);
    }

    private boolean checkUserExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }
}
