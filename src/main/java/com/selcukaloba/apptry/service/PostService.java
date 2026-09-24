package com.selcukaloba.apptry.service;

import com.selcukaloba.apptry.dto.post.PostCreateRequest;
import com.selcukaloba.apptry.dto.post.PostResponse;
import com.selcukaloba.apptry.dto.post.PostUpdateRequest;
import com.selcukaloba.apptry.entity.Post;
import com.selcukaloba.apptry.entity.User;
import com.selcukaloba.apptry.exception.BaseException;
import com.selcukaloba.apptry.exception.ErrorMessage;
import com.selcukaloba.apptry.exception.MessageType;
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

    public List<PostResponse>  getAllPosts()
    {
        List<Post> postList = postRepository.findAll();

        return postList.stream()
                .map(post -> PostResponse.fromEntity(post))
                .collect(Collectors.toList());
    }

    public PostResponse getPostById(Long id)
    {
        Post post = postRepository.findById(id).orElseThrow(()->new BaseException(new ErrorMessage(null, MessageType.POST_NOT_FOUND)));

        return PostResponse.fromEntity(post);
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
        return PostResponse.fromEntity(post);
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
        return PostResponse.fromEntity(post);
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
