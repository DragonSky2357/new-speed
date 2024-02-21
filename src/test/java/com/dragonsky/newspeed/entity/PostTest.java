package com.dragonsky.newspeed.entity;

import com.dragonsky.newspeed.config.WebSecurityConfig;
import com.dragonsky.newspeed.dto.member.CreateMemberDto;
import com.dragonsky.newspeed.dto.post.CreatePostDto;
import com.dragonsky.newspeed.dto.post.UpdatePostDto;
import com.dragonsky.newspeed.repository.MemberRepository;
import com.dragonsky.newspeed.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(WebSecurityConfig.class)
class PostTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup(){
        Member member =  createMember("박용민", "qkrdydals327@naver.com", "23571113", "dragonsky", Gender.MALE);
        createPost("오늘 아침 누릉지","어제 먹다 남은 누릉지",member);
        createPost("오늘 점심 비빔냉면","오늘 점심 비빔냉면 그리고 물냉면",member);
        createPost("오늘 저녁 초밥","오늘은 초밥 뷔페다",member);
    }

//    @AfterEach
//    public void cleanup(){
//        memberRepository.deleteAll();
//        postRepository.deleteAll();
//    }

    private Member createMember(String username, String email, String password, String nickname, Gender gender) {
        CreateMemberDto createMemberDto = new CreateMemberDto();
        createMemberDto.setUsername(username);
        createMemberDto.setEmail(email);
        createMemberDto.setPassword(password);
        createMemberDto.setNickname(nickname);
        createMemberDto.setGender(gender);

        Member member = new Member(createMemberDto, passwordEncoder.encode(createMemberDto.getPassword()));
        return memberRepository.save(member);
    }

    private Post createPost(String title, String content, Member member){
        CreatePostDto createPostDto = new CreatePostDto();
        createPostDto.setTitle(title);
        createPostDto.setContent(content);

        Post post = new Post(createPostDto,member);
        return postRepository.save(post);
    }

    @Test
    public void testSavePost(){
        Member member = memberRepository.findByEmail("qkrdydals327@naver.com")
                .orElse(null);

        Post post = createPost("내일은 베이글빵","빵에다가 단백질 쉐이크",member);
        postRepository.save(post);

        Post findPost =  postRepository.findById(4L)
                .orElse(null);

        assertNotNull(post);
        assertEquals(post.getTitle(),findPost.getTitle());
        assertEquals(post.getContent(),findPost.getContent());
        assertEquals(post.getMember().getId(),findPost.getMember().getId());
    }

    @Test
    public void testFindAllPosts(){
        List<Post> posts = postRepository.findAll();

        assertEquals(posts.size(),3);
    }

    @Test
    public void testFindPost(){
        Post post = postRepository.findById(1L)
                .orElse(null);

        assertEquals(post.getTitle(),"오늘 아침 누릉지");
        assertEquals(post.getContent(),"어제 먹다 남은 누릉지");
    }

    @Test
    public void testUpdatePost(){
        Post post = postRepository.findById(1L)
                .orElse(null);

        UpdatePostDto updatePostDto = new UpdatePostDto();
        updatePostDto.setTitle("오늘 아침은 단식");
        updatePostDto.setContent("어제 너무 많이 먹었다.");

        post.updatePost(updatePostDto);


        assertEquals(post.getTitle(),"오늘 아침은 단식");
        assertEquals(post.getContent(),"어제 너무 많이 먹었다.");
    }

    @Test
    public void testDeletePost(){
        Post post = postRepository.findById(1L)
                .orElse(null);

        postRepository.delete(post);

        assertFalse(postRepository.findById(post.getId()).isPresent());
    }
}