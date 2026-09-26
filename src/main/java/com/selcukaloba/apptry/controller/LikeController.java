package com.selcukaloba.apptry.controller;

import com.selcukaloba.apptry.dto.like.LikeCreateRequest;
import com.selcukaloba.apptry.dto.like.LikeResponse;
import com.selcukaloba.apptry.service.LikeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/api/likes")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping(path = "/createLike")
    public LikeResponse createLike(@Valid @RequestBody LikeCreateRequest request, Principal principal)
    {
        return likeService.createLike(request, principal.getName());
    }

    @DeleteMapping(path = "/deleteLike")
    public void deleteLike(@RequestParam Long postId, Principal principal)
    {
        likeService.deleteLike(postId, principal.getName());
    }

    @GetMapping(path = "/getLikeCount")
    Long getLikeCount(@RequestParam Long postId)
    {
        return likeService.getLikeCount(postId);
    }

    @GetMapping(path ="/isLiked")
    public boolean isLiked(@RequestParam Long postId, Principal principal)
    {
        return likeService.isLiked(postId, principal.getName());
    }
}
