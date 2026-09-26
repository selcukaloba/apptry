package com.selcukaloba.apptry.dto.like;

import com.selcukaloba.apptry.entity.Like;

public record LikeResponse(
        Long id,
        Long postId,
        Long userId,
        String username
) {
    public static LikeResponse fromEntity(Like like)
    {
        return new LikeResponse(
                like.getId(),
                like.getPost().getId(),
                like.getUser().getId(),
                like.getUser().getUsername()
        );
    }
}
