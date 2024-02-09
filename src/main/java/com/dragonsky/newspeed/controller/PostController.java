package com.dragonsky.newspeed.controller;

import com.dragonsky.newspeed.common.dto.ResponseDto;
import com.dragonsky.newspeed.dto.comment.CreateCommentDto;
import com.dragonsky.newspeed.dto.comment.UpdateCommentDto;
import com.dragonsky.newspeed.dto.post.CreatePostDto;
import com.dragonsky.newspeed.dto.post.PostResponseDto;
import com.dragonsky.newspeed.dto.post.UpdatePostDto;
import com.dragonsky.newspeed.entity.Comment;
import com.dragonsky.newspeed.security.UserDetailsImpl;
import com.dragonsky.newspeed.service.CommentService;
import com.dragonsky.newspeed.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @PostMapping()
    public ResponseEntity createPost(@RequestBody @Valid CreatePostDto createPostDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.createPost(createPostDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @GetMapping()
    public ResponseEntity getPosts() {
        List<PostResponseDto> result = postService.getPosts();
        return ResponseEntity.status(HttpStatus.OK.value()).body(ResponseDto.success(HttpStatus.OK.value(), result));
    }

    @GetMapping("/{id}")
    public ResponseEntity getPost(@PathVariable Long id) {
        PostResponseDto result = postService.getPost(id);
        return ResponseEntity.status(HttpStatus.OK.value()).body(ResponseDto.success(HttpStatus.OK.value(), result));
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePost(@PathVariable Long id,
                                     @RequestBody UpdatePostDto updatePostDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.updatePost(id, updatePostDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable Long id,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity createPostComment(@PathVariable Long postId,
                                            @RequestBody CreateCommentDto createCommentDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.createComment(postId, createCommentDto, userDetails.getUser());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity getPostComments(@PathVariable Long postId) {
        List<Comment> result = commentService.getComments(postId);
        return ResponseEntity.ok().body(ResponseDto.success(HttpStatus.OK.value(), result));
    }

    @GetMapping("/{postId}/comment/{commentId}")
    public ResponseEntity getPostComment(@PathVariable Long postId,
                                         @PathVariable Long commentId) {
        Comment result = commentService.getComment(postId, commentId);
        return ResponseEntity.ok().body(ResponseDto.success(HttpStatus.OK.value(), result));
    }

    @PutMapping("/{postId}/comment/{commentId}")
    public ResponseEntity updatePostComment(@PathVariable Long postId,
                                            @PathVariable Long commentId,
                                            @RequestBody UpdateCommentDto updateCommentDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) throws AuthenticationException {
        commentService.updateComment(postId, commentId, updateCommentDto,userDetails.getUser());
        return ResponseEntity.ok().body(ResponseDto.success(HttpStatus.NO_CONTENT.value()));
    }

    @DeleteMapping("/{postId}/comment/{commentId}")
    public ResponseEntity deletePostComment(@PathVariable Long postId,
                                            @PathVariable Long commentId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) throws AuthenticationException {
        commentService.deleteComment(postId, commentId, userDetails.getUser());
        return ResponseEntity.ok().body(ResponseDto.success(HttpStatus.OK.value()));
    }
}
