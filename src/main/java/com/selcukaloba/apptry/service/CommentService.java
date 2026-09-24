package com.selcukaloba.apptry.service;

import com.selcukaloba.apptry.dto.comment.CommentCreateRequest;
import com.selcukaloba.apptry.dto.comment.CommentResponse;
import com.selcukaloba.apptry.dto.comment.CommentUpdateRequest;
import com.selcukaloba.apptry.entity.Comment;
import com.selcukaloba.apptry.entity.Post;
import com.selcukaloba.apptry.entity.User;
import com.selcukaloba.apptry.exception.BaseException;
import com.selcukaloba.apptry.exception.ErrorMessage;
import com.selcukaloba.apptry.exception.MessageType;
import com.selcukaloba.apptry.repository.CommentRepository;
import com.selcukaloba.apptry.repository.PostRepository;
import com.selcukaloba.apptry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public CommentResponse createComment(CommentCreateRequest request, String username)
    {
        User user = userRepository.findByUsername(username).orElseThrow(()->new BaseException(new ErrorMessage(username, MessageType.USERNAME_NOT_FOUND)));
        Post post = postRepository.findById(request.postId()).orElseThrow(()->new BaseException(new ErrorMessage(null, MessageType.POST_NOT_FOUND)));

        Comment comment = new Comment();
        comment.setContext(request.context());
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);
        return CommentResponse.fromEntity(comment);
    }

    @Transactional
    public CommentResponse updateComment(CommentUpdateRequest request, Long id, String username)
    {
        Comment comment = commentRepository.findById(id).orElseThrow(()->new BaseException(new ErrorMessage(null, MessageType.COMMENT_NOT_FOUND)));
        if(!username.equals(comment.getUser().getUsername()))
        {
            throw new BaseException(new ErrorMessage(username, MessageType.COMMENT_NOT_OWNER));
        }
        comment.setContext(request.context());
        commentRepository.save(comment);
        return CommentResponse.fromEntity(comment);
    }

    @Transactional
    public void deleteComment(Long id, String username)
    {
        Comment comment = commentRepository.findById(id).orElseThrow(()->new BaseException(new ErrorMessage(null, MessageType.COMMENT_NOT_FOUND)));
        if(!username.equals(comment.getUser().getUsername()))
        {
            throw new BaseException(new ErrorMessage(username, MessageType.COMMENT_NOT_OWNER));
        }
        commentRepository.delete(comment);
    }

    public CommentResponse getCommentById(Long id)
    {
        Comment comment = commentRepository.findById(id).orElseThrow(()->new BaseException(new ErrorMessage(null, MessageType.COMMENT_NOT_FOUND)));
        return CommentResponse.fromEntity(comment);
    }

    public List<CommentResponse> getCommentsByPostId(Long postId)
    {
        List<Comment> commentList = commentRepository.findByPostId(postId);
        if(!postRepository.existsById(postId))
        {
            throw new BaseException(new ErrorMessage(null, MessageType.POST_NOT_FOUND));
        }
        return commentList.stream()
                .map(comment->CommentResponse.fromEntity(comment))
                .collect(Collectors.toList());
    }
}
