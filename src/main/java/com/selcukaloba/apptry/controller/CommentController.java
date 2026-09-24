package com.selcukaloba.apptry.controller;

import com.selcukaloba.apptry.dto.comment.CommentCreateRequest;
import com.selcukaloba.apptry.dto.comment.CommentResponse;
import com.selcukaloba.apptry.dto.comment.CommentUpdateRequest;
import com.selcukaloba.apptry.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping(path = "/createComment")
    public CommentResponse createComment(@Valid @RequestBody CommentCreateRequest request, Principal principal)
    {
        return commentService.createComment(request, principal.getName());
    }

    @PutMapping(path = "/updateComment/{id}")
    public CommentResponse updateComment(@Valid @RequestBody CommentUpdateRequest request, @PathVariable Long id, Principal principal)
    {
        return commentService.updateComment(request, id, principal.getName());
    }

    @DeleteMapping(path = "/deleteComment/{id}")
    public void deleteComment(@PathVariable Long id, Principal principal)
    {
        commentService.deleteComment(id, principal.getName());
    }

    @GetMapping(path = "/getCommentBy/{id}")
    public CommentResponse getCommentById(@PathVariable  Long id)
    {
        return commentService.getCommentById(id);
    }

    @GetMapping(path = "/getCommentsBy/{postId}")
    public List<CommentResponse> getCommentsByPostId(@PathVariable Long postId)
    {
        return commentService.getCommentsByPostId(postId);
    }

}
