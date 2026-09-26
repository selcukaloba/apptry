package com.selcukaloba.apptry.repository;

import com.selcukaloba.apptry.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByPostIdAndUserId(Long postId, Long userId);
    Long countByPostId(Long postId);
    Optional<Like> findByPostIdAndUserId(Long postId, Long userId);
}
