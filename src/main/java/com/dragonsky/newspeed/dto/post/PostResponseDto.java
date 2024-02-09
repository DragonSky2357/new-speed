package com.dragonsky.newspeed.dto.post;

import com.dragonsky.newspeed.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private String title;
    private String content;
    private LocalDateTime createAt;
    private String nickname;

    public PostResponseDto(Post post){
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createAt = post.getCreateAt();
        this.nickname = post.getMember().getNickname();
    }
}
