package com.dragonsky.newspeed.service;

import com.dragonsky.newspeed.dto.member.CreateMemberDto;
import com.dragonsky.newspeed.dto.post.CreatePostDto;
import com.dragonsky.newspeed.dto.post.PostResponseDto;
import com.dragonsky.newspeed.dto.post.UpdatePostDto;
import com.dragonsky.newspeed.entity.Gender;
import com.dragonsky.newspeed.entity.Member;
import com.dragonsky.newspeed.entity.Post;
import com.dragonsky.newspeed.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach()
    void setup(){
        //MockitoAnnotations.initMocks(this);
    }

    private Member createMember() {
        CreateMemberDto createMemberDto = new CreateMemberDto();
        createMemberDto.setUsername("박용민");
        createMemberDto.setEmail("qkrdydals327@naver.com");
        createMemberDto.setPassword("235711");
        createMemberDto.setNickname("dragonsky");
        createMemberDto.setGender(Gender.MALE);
        return new Member(createMemberDto, passwordEncoder.encode(createMemberDto.getPassword()));
    }

    @Test
    @DisplayName("게시물 생성")
    void testCreatePost(){
        // given
        //Member member = createMember(); // 적절한 createMember() 메서드 호출로 회원을 생성

        Member member = mock(Member.class);
        given(member.getId()).willReturn(1L);

        CreatePostDto createPostDto = new CreatePostDto();
        createPostDto.setTitle("오늘 아침 누릉지");
        createPostDto.setContent("어제 먹다 남은 누릉지");

        // when
        postService.createPost(createPostDto, member);

        // then
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("게시물 전체 조회")
    void testGetAllPosts(){
        // given
        Member member = createMember();

        CreatePostDto createPostDto1 = new CreatePostDto();
        createPostDto1.setTitle("오늘 아침 누릉지");
        createPostDto1.setContent("어제 먹다 남은 누릉지");

        CreatePostDto createPostDto2 = new CreatePostDto();
        createPostDto2.setTitle("오늘 점심 비빔냉면");
        createPostDto2.setContent("오늘 점심 비빔냉면 그리고 물냉면");

        List<Post> postList = Arrays.asList(
                new Post(createPostDto1, member),
                new Post(createPostDto2, member)
        );

        when(postRepository.findAll()).thenReturn(postList);

        // when
        List<PostResponseDto> postResponseDtoList = postService.getPosts();

        // then
        assertEquals(postResponseDtoList.size(),2);

        assertEquals("오늘 아침 누릉지", postResponseDtoList.get(0).getTitle());
        assertEquals("어제 먹다 남은 누릉지", postResponseDtoList.get(0).getContent());

        assertEquals("오늘 점심 비빔냉면", postResponseDtoList.get(1).getTitle());
        assertEquals("오늘 점심 비빔냉면 그리고 물냉면", postResponseDtoList.get(1).getContent());
    }

    @Test
    @DisplayName("특정 게시물 조회")
    void testGetPost(){
        // given
        Member member = createMember();

        CreatePostDto createPostDto = new CreatePostDto();
        createPostDto.setTitle("오늘 아침 누릉지");
        createPostDto.setContent("어제 먹다 남은 누릉지");


        Post post = new Post(createPostDto, member);

        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        // when
        PostResponseDto findPost1 = postService.getPost(post.getId());

        // then
        assertEquals(post.getTitle(), findPost1.getTitle());
        assertEquals(post.getContent(), findPost1.getContent());
    }

    @Test
    @DisplayName("존재 하지 않은 포스터 조회")
    void testGetNonExistingPost(){
        // given
        Long postId = 1000L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(IllegalArgumentException.class,()-> postService.getPost(postId));
    }

    @Test
    @DisplayName("게시물 업데이트")
    void testUpdatePost(){
        // given
        Member member = mock(Member.class);
        when(member.getId()).thenReturn(1L);

        CreatePostDto createPostDto = new CreatePostDto();
        createPostDto.setTitle("오늘 아침 누릉지");
        createPostDto.setContent("어제 먹다 남은 누릉지");

        Post post = new Post(createPostDto,member);

        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        UpdatePostDto updatePostDto = new UpdatePostDto();
        updatePostDto.setTitle("수정된 제목");
        updatePostDto.setContent("수정된 내용");

        // when
        postService.updatePost(post.getId(),updatePostDto,member);

        // then
        assertEquals("수정된 제목",post.getTitle());
        assertEquals("수정된 내용",post.getContent());
    }

    @Test
    @DisplayName("게시글 작성자가 아닌 경우 업데이트 실패")
    void testUpdatePostWithNonOwner() {
        // given
        Member member = mock(Member.class);
        when(member.getId()).thenReturn(1L);

        Member member2 = mock(Member.class);
        when(member2.getId()).thenReturn(2L);

        CreatePostDto createPostDto = new CreatePostDto();
        createPostDto.setTitle("오늘 아침 누릉지");
        createPostDto.setContent("어제 먹다 남은 누릉지");

        Post post = new Post(createPostDto,member);

        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        // when
        UpdatePostDto updatePostDto = new UpdatePostDto();
        updatePostDto.setTitle("수정된 제목");
        updatePostDto.setContent("수정된 내용");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            postService.updatePost(post.getId(), updatePostDto, member2);
        });

        // then
        assertEquals(exception.getMessage(),"게시글은 작성자만 접근할 수 있습니다.");
    }

    @Test
    @DisplayName("게시물 삭제")
    void testDeletePost(){
        Member member = mock(Member.class);
        when(member.getId()).thenReturn(1L);

        CreatePostDto createPostDto = new CreatePostDto();
        createPostDto.setTitle("오늘 아침 누릉지");
        createPostDto.setContent("어제 먹다 남은 누릉지");

        Post post = new Post(createPostDto,member);

        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        postService.deletePost(post.getId(), member);

        verify(postRepository,times(1)).delete(post);
    }

    @Test
    @DisplayName("게시글 작성자가 아닌 경우 삭제 실패")
    void testDeletePostWithNonOwner(){
        Member member = mock(Member.class);
        when(member.getId()).thenReturn(1L);

        Member member2 = mock(Member.class);
        when(member.getId()).thenReturn(2L);

        CreatePostDto createPostDto = new CreatePostDto();
        createPostDto.setTitle("오늘 아침 누릉지");
        createPostDto.setContent("어제 먹다 남은 누릉지");

        Post post = new Post(createPostDto,member);

        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            postService.deletePost(post.getId(), member2);
        });

        // then
        assertEquals(exception.getMessage(),"게시글은 작성자만 접근할 수 있습니다.");

    }
}