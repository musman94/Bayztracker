package com.bayzat.bayztracker.dto.request;

import com.bayzat.bayztracker.enumeration.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddUserRequestDto {
    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    private String email;

    private UserType userType;
}

