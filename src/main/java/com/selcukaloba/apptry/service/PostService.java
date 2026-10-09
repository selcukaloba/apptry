package com.selcukaloba.apptry.service;

import com.selcukaloba.apptry.dto.post.PostCreateRequest;
import com.selcukaloba.apptry.dto.post.PostResponse;
import com.selcukaloba.apptry.dto.post.PostUpdateRequest;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    public List<PostResponse>  getAllPosts(String username)
    {
        User user = userRepository.findByUsername(username).orElseThrow(()->new BaseException(new ErrorMessage(username, MessageType.USERNAME_NOT_FOUND)));
        List<Post> postList = postRepository.findAll();

        return postList.stream()
                .map(post -> {
                    Long likeCount = likeRepository.countByPostId(post.getId());
                    boolean isLiked = likeRepository.existsByPostIdAndUserId(post.getId(), user.getId());
                    return PostResponse.fromEntity(post, likeCount, isLiked);
                }).collect(Collectors.toList());
    }

    public PostResponse getPostById(Long id, String username)
    {
        User user = userRepository.findByUsername(username).orElseThrow(()->new BaseException(new ErrorMessage(username, MessageType.USERNAME_NOT_FOUND)));
        Post post = postRepository.findById(id).orElseThrow(()->new BaseException(new ErrorMessage(null, MessageType.POST_NOT_FOUND)));
        Long likeCount = likeRepository.countByPostId(id);
        boolean isLiked = likeRepository.existsByPostIdAndUserId(id, user.getId());
        return PostResponse.fromEntity(post, likeCount, isLiked);
    }

    @Transactional
    public PostResponse createPost(PostCreateRequest request, String username)
    {
        User user = userRepository.findByUsername(username).orElseThrow(()->new BaseException(new ErrorMessage(username, MessageType.USERNAME_NOT_FOUND)));
        Post post = new Post();
        post.setTitle(request.title());
        post.setText(request.text());
        post.setUser(user);
        postRepository.save(post);
        Long likeCount = 0L;
        boolean isLiked = false;
        return PostResponse.fromEntity(post, likeCount, isLiked);
    }

    @Transactional
    public PostResponse updatePost(PostUpdateRequest request, String username, Long id)
    {
        Post post = postRepository.findById(id).orElseThrow(()->new BaseException(new ErrorMessage(null, MessageType.POST_NOT_FOUND)));
        if(!username.equals(post.getUser().getUsername()))
        {
            throw new BaseException(new ErrorMessage(username, MessageType.POST_NOT_OWNER));
        }
        post.setTitle(request.title());
        post.setText(request.text());
        postRepository.save(post);
        Long likeCount = likeRepository.countByPostId(id);
        User user = userRepository.findByUsername(username).orElseThrow(()->new BaseException(new ErrorMessage(username, MessageType.USERNAME_NOT_FOUND)));
        boolean isLiked = likeRepository.existsByPostIdAndUserId(post.getId(), user.getId());
        return PostResponse.fromEntity(post, likeCount, isLiked);
    }

    @Transactional
    public void deletePost(Long id, String username)
    {
        Post post = postRepository.findById(id).orElseThrow(()->new BaseException(new ErrorMessage(null, MessageType.POST_NOT_FOUND)));
        if (!username.equals(post.getUser().getUsername()))
        {
            throw new BaseException(new ErrorMessage(username, MessageType.POST_NOT_OWNER));
        }
        postRepository.delete(post);
    }
}
