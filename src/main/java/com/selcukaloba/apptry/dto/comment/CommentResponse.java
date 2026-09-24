package com.selcukaloba.apptry.dto.comment;
import com.selcukaloba.apptry.entity.Comment;

public record CommentResponse(
        Long id,
        Long postId,
        Long userId,
        String username,
        String context
) {
    public static CommentResponse fromEntity(Comment comment)
    {
        return new CommentResponse(
                comment.getId(),
                comment.getPost().getId(),
                comment.getUser().getId(),
                comment.getUser().getUsername(),
                comment.getContext()
        );
    }
}
