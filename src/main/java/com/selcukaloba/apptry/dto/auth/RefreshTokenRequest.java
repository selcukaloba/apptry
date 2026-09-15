package com.selcukaloba.apptry.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RefreshTokenRequest(
        @NotBlank(message = "{refresh_token.required}")
        String refreshToken) {
}
