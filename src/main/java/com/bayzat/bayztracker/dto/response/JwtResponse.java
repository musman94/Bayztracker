package com.bayzat.bayztracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class JwtResponse {

    private final String token;
    private final String type = "Bearer";
}
