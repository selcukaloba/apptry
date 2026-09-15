package com.selcukaloba.apptry.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest
        (
        @NotBlank(message = "{user.username.required}")
        @Size(min=3, max=50,message = "{user.username.size}")
        String username,

        @NotBlank(message = "{user.password.required}")
        @Size(min=6, max=50,message = "{user.password.size}")
        String password
){}
