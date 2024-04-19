package com.example.cnpm.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
}
