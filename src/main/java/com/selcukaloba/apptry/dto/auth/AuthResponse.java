package com.selcukaloba.apptry.dto.auth;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String username
) {
}
