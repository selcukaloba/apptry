package com.selcukaloba.apptry.dto.post;
import com.selcukaloba.apptry.entity.Post;

public record PostResponse(
        String text,
        String title,
        Long id,
        Long userId,
        String username,
        Long likeCount,
        boolean isLiked
) {
    public static PostResponse fromEntity(Post post, Long likeCount, boolean isLiked)
    {
        return new PostResponse(
                post.getText(),
                post.getTitle(),
                post.getId(),
                post.getUser().getId(),
                post.getUser().getUsername(),
                likeCount,
                isLiked
        );
    }
}
