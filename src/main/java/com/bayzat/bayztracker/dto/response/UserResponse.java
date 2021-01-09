package com.bayzat.bayztracker.dto.response;

import com.bayzat.bayztracker.enumeration.UserType;
import com.bayzat.bayztracker.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class UserResponse {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private UserType type;

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