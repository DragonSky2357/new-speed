package com.dragonsky.newspeed.controller;

import com.dragonsky.newspeed.common.dto.ResponseDto;
import com.dragonsky.newspeed.dto.member.CreateMemberDto;
import com.dragonsky.newspeed.entity.Gender;
import com.dragonsky.newspeed.service.CommentService;
import com.dragonsky.newspeed.service.EmailService;
import com.dragonsky.newspeed.service.MemberService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(MemberController.class)
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    private Gson gson = new Gson();

    @Test
    @WithMockUser
    public void testSignUp() throws Exception{
        CreateMemberDto createMemberDto = new CreateMemberDto();
        createMemberDto.setUsername("박용민");
        createMemberDto.setEmail("qkrdydals327@naver.com");
        createMemberDto.setNickname("dragonsky");
        createMemberDto.setPassword("23571113");
        createMemberDto.setGender(Gender.MALE);

        String requestBody = gson.toJson(createMemberDto);

        // memberService.createMember() 메서드가 호출되었음을 확인하기 위해 doNothing()을 사용합니다.
        doNothing().when(memberService).createMember(any(CreateMemberDto.class));

        mockMvc.perform(post("/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isCreated());

        // memberService.createMember() 메서드가 1번 호출되었는지 확인합니다.
        verify(memberService, times(1)).createMember(any(CreateMemberDto.class));
    }

    @Test
    @WithMockUser
    void testLogout() throws Exception {
        // Given
        doNothing().when(memberService).logoutMember(any(HttpServletRequest.class), any(HttpServletResponse.class));

        // When & Then
        mockMvc.perform(post("/members/logout")
                .with(csrf()))
                .andExpect(status().isNoContent());

        // Verify that the logoutMember method of the memberService is called once
        verify(memberService, times(1)).logoutMember(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }
}