package com.dragonsky.newspeed.entity;

import com.dragonsky.newspeed.dto.post.CreatePostDto;
import com.dragonsky.newspeed.dto.post.UpdatePostDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<PostCategory> categories = new ArrayList<>();

    public Post(CreatePostDto createPostDto,Member member){
        this.title = createPostDto.getTitle();
        this.content = createPostDto.getContent();
        this.member = member;
    }

    public void updatePost(UpdatePostDto updatePostDto) {
        if(checkContainDto(updatePostDto.getTitle())) this.title = updatePostDto.getTitle();
        if(checkContainDto(updatePostDto.getContent())) this.content = updatePostDto.getContent();
    }

    private boolean checkContainDto(String data){
        return ((data != null) && (!data.isEmpty()));
    }
}
