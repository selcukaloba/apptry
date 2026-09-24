package com.selcukaloba.apptry.repository;

import com.selcukaloba.apptry.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>
{
    List<Comment> findByPostId(Long postId);
    List<Comment> findByUserId(Long userId);
}
