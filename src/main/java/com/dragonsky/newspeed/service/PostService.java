package com.dragonsky.newspeed.service;

import com.dragonsky.newspeed.dto.post.CreatePostDto;
import com.dragonsky.newspeed.dto.post.CreatePostResponseDto;
import com.dragonsky.newspeed.entity.Member;
import com.dragonsky.newspeed.entity.Post;
import com.dragonsky.newspeed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public CreatePostResponseDto createPost(CreatePostDto createPostDto, Member member){
        Post post = new Post(createPostDto,member);
        Post savePost = postRepository.save(post);

        return new CreatePostResponseDto(savePost);
    }
}
