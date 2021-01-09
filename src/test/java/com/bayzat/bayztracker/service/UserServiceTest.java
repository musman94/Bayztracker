package com.bayzat.bayztracker.service;

import com.bayzat.bayztracker.config.PasswordHelper;
import com.bayzat.bayztracker.config.jwt.JwtProvider;
import com.bayzat.bayztracker.dto.request.AddUserRequestDto;
import com.bayzat.bayztracker.dto.request.LoginRequestDto;
import com.bayzat.bayztracker.dto.response.JwtResponse;
import com.bayzat.bayztracker.dto.response.UserResponse;
import com.bayzat.bayztracker.enumeration.UserType;
import com.bayzat.bayztracker.exception.NotFoundException;
import com.bayzat.bayztracker.model.User;
import com.bayzat.bayztracker.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHelper passwordHelper;

    @InjectMocks
    private UserServiceImpl userService;

    private AddUserRequestDto addUserRequestDto;

    private UserResponse userResponse;

    private User user;

    private LoginRequestDto loginRequestDto;

    @Before
    public void setup() {
        addUserRequestDto = new AddUserRequestDto();
        addUserRequestDto.setName("testUser");
        addUserRequestDto.setEmail("testUser@gmail.com");
        addUserRequestDto.setPassword("password");
        addUserRequestDto.setType(UserType.ADMIN);

        userResponse = new UserResponse();
        userResponse.setId(0L);
        userResponse.setName("testUser");
        userResponse.setEmail("testUser@gmail.com");
        userResponse.setType(UserType.ADMIN);

        user = new User();
        user.setId(0L);
        user.setName("testUser");
        user.setEmail("testUser@gmail.com");
        user.setType(UserType.ADMIN);

        loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("testUser@gmail.com");
        loginRequestDto.setPassword("password");

    }

    @Test
    public void testSignUp() {
        when(userRepository.findByEmail(addUserRequestDto.getEmail())).thenReturn(java.util.Optional.ofNullable(null));

        when(passwordHelper.encode(addUserRequestDto.getPassword())).thenReturn("password");

        when(userRepository.save(any())).thenAnswer(new Answer<User>() {
            public User answer(InvocationOnMock invocation) throws Throwable {
                return user;
            }
        });

        Assert.assertEquals(userResponse, userService.signUp(addUserRequestDto));

    }

    @Test
    public void testGetUserById() {
        Long id = 0L;

        when(userRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(user));

        Assert.assertEquals(user, userService.getUserById(id));
    }

    @Test(expected = NotFoundException.class)
    public void testGetUserByIdThrowsException() {
        Long id = 0L;

        when(userRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(null));

        userService.getUserById(id);
    }

}
