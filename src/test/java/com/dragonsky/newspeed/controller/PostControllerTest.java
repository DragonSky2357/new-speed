package com.dragonsky.newspeed.controller;

import com.dragonsky.newspeed.dto.member.CreateMemberDto;
import com.dragonsky.newspeed.dto.post.CreatePostDto;
import com.dragonsky.newspeed.entity.Gender;
import com.dragonsky.newspeed.entity.Member;
import com.dragonsky.newspeed.entity.Post;
import com.dragonsky.newspeed.security.UserDetailsImpl;
import com.dragonsky.newspeed.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(PostController.class)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Gson gson = new Gson();

    @Test
    @WithMockUser(username = "qkrdydals327@naver.com")
    void testCreatePost() throws Exception {
        // Given
        CreatePostDto createPostDto = new CreatePostDto();
        createPostDto.setTitle("테스트 게시물");
        createPostDto.setContent("테스트 게시물 내용");

        String requestBody = gson.toJson(createPostDto);

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

}