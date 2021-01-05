package com.bayzat.bayztracker.dto.response;

import com.bayzat.bayztracker.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtUser {
    private final UserResponse userDetails;
    private final JwtResponse jwt;

    public static JwtUser from(User user, JwtResponse jwt) {
        return new JwtUser(UserResponse.of(user), jwt);
    }
}
