package com.selcukaloba.apptry.controller;

import com.selcukaloba.apptry.dto.post.PostCreateRequest;
import com.selcukaloba.apptry.dto.post.PostResponse;
import com.selcukaloba.apptry.dto.post.PostUpdateRequest;
import com.selcukaloba.apptry.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping(path = "/getAll")
    public List<PostResponse> getAllPosts(Principal principal)
    {
        return postService.getAllPosts(principal.getName());
    }

    @GetMapping(path = "/getById/{id}")
    public PostResponse getPostById(@PathVariable Long id, Principal principal)
    {
        return postService.getPostById(id, principal.getName());
    }

    @PostMapping(path = "/createPost")
    public PostResponse createPost(@Valid @RequestBody PostCreateRequest request, Principal principal)
    {
        return postService.createPost(request, principal.getName());
    }

    @PutMapping(path = "/updatePost/{id}")
    public PostResponse updatePost(@Valid @RequestBody PostUpdateRequest request, Principal principal, @PathVariable Long id)
    {
        return postService.updatePost(request, principal.getName(), id);
    }

    @DeleteMapping(path = "/deletePost/{id}")
    public void deletePost(@PathVariable Long id, Principal principal)
    {
         postService.deletePost(id, principal.getName());
    }
}
