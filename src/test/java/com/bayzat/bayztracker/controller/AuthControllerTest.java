package com.bayzat.bayztracker.controller;

import com.bayzat.bayztracker.dto.request.AddUserRequestDto;
import com.bayzat.bayztracker.dto.request.LoginRequestDto;
import com.bayzat.bayztracker.dto.response.JwtResponse;
import com.bayzat.bayztracker.dto.response.JwtUser;
import com.bayzat.bayztracker.dto.response.UserResponse;
import com.bayzat.bayztracker.enumeration.UserType;
import com.bayzat.bayztracker.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@IfProfileValue(name = "spring.profiles.active", values = {"dev"})
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private AddUserRequestDto addUserRequestDto;

    private UserResponse userResponse;

    private JwtUser jwtUser;

    private JwtResponse jwtResponse;

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

        jwtResponse = new JwtResponse("token");

        jwtUser = new JwtUser();
        jwtUser.setJwt(jwtResponse);
        jwtUser.setUserDetails(userResponse);

        loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("testUser@gmail.com");
        loginRequestDto.setPassword("password");
    }

    @Test
    public void testSignUpUser() throws Exception {
        given(userService.signUp(addUserRequestDto)).willReturn(userResponse);

        this.mockMvc.perform(post("/api/auth/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addUserRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(userResponse.getId().intValue())))
                .andExpect(jsonPath("$.data.name", is(userResponse.getName())))
                .andExpect( jsonPath("$.data.email", is(userResponse.getEmail())))
                .andExpect(jsonPath("$.data.type", is(userResponse.getType().toString())))
                .andExpect(jsonPath("$.error", is(false)));
    }

    @Test
    public void testLoginUser() throws Exception {
        given(userService.login(loginRequestDto)).willReturn(jwtUser);

        this.mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userDetails.id", is(userResponse.getId().intValue())))
                .andExpect(jsonPath("$.data.userDetails.name", is(userResponse.getName())))
                .andExpect( jsonPath("$.data.userDetails.email", is(userResponse.getEmail())))
                .andExpect(jsonPath("$.data.userDetails.type", is(userResponse.getType().toString())))
                .andExpect(jsonPath("$.data.jwt.token", is(jwtResponse.getToken())))
                .andExpect(jsonPath("$.data.jwt.type", is(jwtResponse.getType())))
                .andExpect(jsonPath("$.error", is(false)));
    }

}
