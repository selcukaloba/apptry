package com.selcukaloba.apptry.dto.like;

import jakarta.validation.constraints.NotNull;

public record LikeCreateRequest(
        @NotNull(message = "{like.postId.required}")
        Long postId
) {
}
