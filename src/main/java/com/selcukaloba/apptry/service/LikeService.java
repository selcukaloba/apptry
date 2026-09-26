package com.selcukaloba.apptry.service;

import com.selcukaloba.apptry.dto.like.LikeCreateRequest;
import com.selcukaloba.apptry.dto.like.LikeResponse;
import com.selcukaloba.apptry.entity.Like;
import com.selcukaloba.apptry.entity.Post;
import com.selcukaloba.apptry.entity.User;
import com.selcukaloba.apptry.exception.BaseException;
import com.selcukaloba.apptry.exception.ErrorMessage;
import com.selcukaloba.apptry.exception.MessageType;
import com.selcukaloba.apptry.repository.LikeRepository;
import com.selcukaloba.apptry.repository.PostRepository;
import com.selcukaloba.apptry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LikeService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Transactional
    public LikeResponse createLike(LikeCreateRequest request, String username)
    {
        User user = userRepository.findByUsername(username).orElseThrow(()->new BaseException(new ErrorMessage(username, MessageType.USERNAME_NOT_FOUND)));
        Post post = postRepository.findById(request.postId()).orElseThrow(()->new BaseException(new ErrorMessage(null, MessageType.POST_NOT_FOUND)));
        if(!likeRepository.existsByPostIdAndUserId(request.postId(), user.getId()))
        {
            Like like = new Like();
            like.setUser(user);
            like.setPost(post);
            likeRepository.save(like);
            return LikeResponse.fromEntity(like);
        }
        throw new BaseException(new ErrorMessage(null, MessageType.ALREADY_LIKED));
    }

    @Transactional
    public void deleteLike(Long postId, String username)
    {
        User user = userRepository.findByUsername(username).orElseThrow(()->new BaseException(new ErrorMessage(username, MessageType.USERNAME_NOT_FOUND)));
        Optional<Like> like = likeRepository.findByPostIdAndUserId(postId, user.getId());
        if(like.isEmpty())throw new BaseException(new ErrorMessage(null, MessageType.LIKE_NOT_FOUND));
        Like entityLike = like.get();
        likeRepository.delete(entityLike);
    }

    public Long getLikeCount(Long postId)
    {
        return likeRepository.countByPostId(postId);
    }

    public boolean isLiked(Long postId, String username)
    {
        User user = userRepository.findByUsername(username).orElseThrow(()->new BaseException(new ErrorMessage(username, MessageType.USERNAME_NOT_FOUND)));
        if(likeRepository.existsByPostIdAndUserId(postId, user.getId())) return true;
        return false;
    }
}
