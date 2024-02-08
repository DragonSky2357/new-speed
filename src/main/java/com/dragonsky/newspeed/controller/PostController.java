package com.dragonsky.newspeed.controller;

import com.dragonsky.newspeed.dto.post.CreatePostDto;
import com.dragonsky.newspeed.security.UserDetailsImpl;
import com.dragonsky.newspeed.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping()
    public ResponseEntity createPost(@RequestBody @Valid CreatePostDto createPostDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails){

        postService.createPost(createPostDto,userDetails.getUser());
        return null;
    }


}
