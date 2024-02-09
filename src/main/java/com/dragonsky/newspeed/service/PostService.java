package com.dragonsky.newspeed.service;

import com.dragonsky.newspeed.dto.post.CreatePostDto;
import com.dragonsky.newspeed.dto.post.PostResponseDto;
import com.dragonsky.newspeed.dto.post.UpdatePostDto;
import com.dragonsky.newspeed.entity.Member;
import com.dragonsky.newspeed.entity.Post;
import com.dragonsky.newspeed.repository.PostRepository;
import com.sun.nio.sctp.IllegalReceiveException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void createPost(CreatePostDto createPostDto, Member member){
        Post post = new Post(createPostDto,member);
        postRepository.save(post);
    }

    public List<PostResponseDto> getPosts(){
        return postRepository.findAll()
                .stream()
                .map(post -> new PostResponseDto(post))
                .toList();
    }

    public PostResponseDto getPost(Long id) {
        return postRepository.findById(id)
                .map(post -> new PostResponseDto(post))
                .orElseThrow(()-> new IllegalReceiveException("존재 하지 않은 포스트입니다."));
    }

    @Transactional
    public void updatePost(Long id, UpdatePostDto updatePostDto, Member member) {
        Post post =  findByPostId(id);

        if(!isPosterOwner(post,member)){
            throw new IllegalArgumentException("게시글은 작성자만 접근할 수 있습니다.");
        }

        post.updatePost(updatePostDto);
    }

    @Transactional
    public void deletePost(Long id, Member member) {
        Post post =  findByPostId(id);

        if(!isPosterOwner(post,member)){
            throw new IllegalArgumentException("게시글은 작성자만 접근할 수 있습니다.");
        }

        postRepository.delete(post);
    }

    public Post findByPostId(Long postId){
        return postRepository.findById(postId)
                .orElseThrow(()-> new IllegalReceiveException("존재 하지 않은 포스트입니다."));
    }

    private boolean isPosterOwner(Post post, Member member){
        return post.getMember().getId() == member.getId();
    }



}
