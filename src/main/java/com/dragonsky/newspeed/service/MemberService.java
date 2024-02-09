package com.dragonsky.newspeed.service;

import com.dragonsky.newspeed.dto.member.CreateMemberDto;
import com.dragonsky.newspeed.dto.member.UpdateMemberDto;
import com.dragonsky.newspeed.entity.Member;
import com.dragonsky.newspeed.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public void createMember(CreateMemberDto createMemberDto) {
        String passwordEncryption = passwordEncoder.encode(createMemberDto.getPassword());

        Optional<Member> findMember = memberRepository.findByEmail(createMemberDto.getEmail());

        if(findMember.isPresent()){
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        Member member = new Member(createMemberDto, passwordEncryption);
        memberRepository.save(member);
    }

    public void logoutMember(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }

    public void updateMember(UpdateMemberDto updateMemberDto) {

    }
}
