package com.dragonsky.newspeed.service;

import com.dragonsky.newspeed.dto.member.CreateMemberDto;
import com.dragonsky.newspeed.entity.Gender;
import com.dragonsky.newspeed.entity.Member;
import com.dragonsky.newspeed.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberService memberService;
    @Mock
    private SecurityContextLogoutHandler logoutHandler;

    @BeforeEach
    public void setUp() {
        //memberService = new MemberService(memberRepository, passwordEncoder);
    }

    @Test
    @DisplayName("맴버 생성")
    void testCreateMember(){
        // given
        CreateMemberDto createMemberDto = new CreateMemberDto();
        createMemberDto.setUsername("박용민");
        createMemberDto.setEmail("qkrdydals327@naver.com");
        createMemberDto.setPassword("235711113");
        createMemberDto.setNickname("dragonsky");
        createMemberDto.setGender(Gender.MALE);



        // when
        memberService.createMember(createMemberDto);
        Member findMemeber = memberRepository.findByEmail("qkrdydals327@naver.com")
                .orElse(null);

        // then
        assertNotNull(findMemeber);
        assertEquals("박용민",findMemeber.getUsername());
        assertEquals("qkrdydals327@naver.com",findMemeber.getEmail());
        assertEquals("dragonsky",findMemeber.getNickname());
    }

    @Test
    @DisplayName("로그아웃")
    void testLogout(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        memberService.logoutMember(request,response);

        verify(logoutHandler, times(1)).logout(request, response, authentication);
    }

}