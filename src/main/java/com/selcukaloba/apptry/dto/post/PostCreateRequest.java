package com.selcukaloba.apptry.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostCreateRequest(
        @NotBlank(message = "{post.title.required}")
        @Size(min=5, max=50 , message = "{post.title.size}")
        String title,

        @NotBlank(message = "{post.text.required}")
        @Size(min=5, max=1000, message = "{post.text.size}")
        String text
) {
}
