package com.selcukaloba.apptry.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentCreateRequest(
        @NotBlank(message = "{comment.context.required}")
        @Size(min=5, max=5000 , message = "{comment.title.size}")
        String context,

        @NotNull(message = "{comment.postId.required}")
        Long postId
) {
}
