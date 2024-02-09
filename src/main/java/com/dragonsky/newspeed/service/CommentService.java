package com.dragonsky.newspeed.service;

import com.dragonsky.newspeed.dto.comment.CreateCommentDto;
import com.dragonsky.newspeed.dto.comment.UpdateCommentDto;
import com.dragonsky.newspeed.entity.Comment;
import com.dragonsky.newspeed.entity.Member;
import com.dragonsky.newspeed.entity.Post;
import com.dragonsky.newspeed.repository.CommentRepository;
import com.dragonsky.newspeed.repository.PostRepository;
import com.sun.nio.sctp.IllegalReceiveException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {
    private final PostService postService;
    private final CommentRepository commentRepository;

    @Transactional
    public void createComment(Long postId, CreateCommentDto createCommentDto, Member member) {
        Post post =  postService.findByPostId(postId);

        Comment comment = new Comment(createCommentDto,post,member);
        commentRepository.save(comment);
    }

    public List<Comment> getComments(Long postId) {
        Post post =  postService.findByPostId(postId);

        List<Comment> comments =  commentRepository.findByPostId(postId);
        return comments;
    }

    public Comment getComment(Long postId, Long commentId) {
        Post post =  postService.findByPostId(postId);

        Comment comment =  commentRepository.findById(commentId)
                .orElseThrow(()-> new IllegalArgumentException("존재 하지 않은 댓글 입니다."));
        return comment;
    }

    @Transactional
    public void updateComment(Long postId, Long commentId, UpdateCommentDto updateCommentDto, Member member) throws AuthenticationException {
        Post post = postService.findByPostId(postId);
        Comment comment =  commentRepository.findById(commentId)
                .orElseThrow(()-> new IllegalArgumentException("존재 하지 않은 댓글 입니다."));

        if(isPosterOwner(comment,member)){
            throw new AuthenticationException("수정 권한이 없습니다.");
        }

        comment.updateComment(updateCommentDto);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, Member member) throws AuthenticationException {
        Post post = postService.findByPostId(postId);
        Comment comment =  commentRepository.findById(commentId)
                .orElseThrow(()-> new IllegalArgumentException("존재 하지 않은 댓글 입니다."));

        if(isPosterOwner(comment,member)){
            throw new AuthenticationException("삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }

    private boolean isPosterOwner(Comment comment, Member member){
        return comment.getMember().getId() == member.getId();
    }

}
