package com.dragonsky.newspeed.entity;

import com.dragonsky.newspeed.dto.comment.CreateCommentDto;
import com.dragonsky.newspeed.dto.comment.UpdateCommentDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="COMMENT_ID")
    private Long id;

    @Column(nullable = false)
    private String comment;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="POST_ID")
    private Post post;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    public Comment(CreateCommentDto createPostCommentDto, Post post, Member member){
        this.comment = createPostCommentDto.getComment();
        this.post = post;
        this.member = member;
    }

    public void updateComment(UpdateCommentDto updateCommentDto){
         if(checkContainDto(updateCommentDto.getComment())) this.comment = updateCommentDto.getComment();
    }

    private boolean checkContainDto(String data){
        return ((data != null) && (!data.isEmpty()));
    }
}
