package com.bayzat.bayztracker.dto.response;

import com.bayzat.bayztracker.enumeration.UserType;
import com.bayzat.bayztracker.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class UserResponse {

    public final Long id;

    public final String name;

    public final String surname;

    public final String email;

    public final UserType type;

    public static UserResponse of(User user) {
        UserResponseBuilder builder = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .type(user.getType());

        return builder.build();
    }

    public static Collection<UserResponse> of(Collection<User> u) {
        return u.stream().map(UserResponse::of).collect(Collectors.toList());
    }
}