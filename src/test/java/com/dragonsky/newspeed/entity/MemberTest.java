package com.dragonsky.newspeed.entity;

import com.dragonsky.newspeed.config.WebSecurityConfig;
import com.dragonsky.newspeed.dto.member.CreateMemberDto;
import com.dragonsky.newspeed.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(WebSecurityConfig.class)
class MemberTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void createMember(){
        createMember("박용민", "qkrdydals327@naver.com", "23571113", "dragonsky", Gender.MALE);
        createMember("김철수", "kim111@naver.com", "111222", "kim철수", Gender.MALE);
        createMember("신짱구", "shin@gmail.com", "3333300000", "부리부리", Gender.FEMALE);
    }
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

    @Test
    public void testSaveMember(){
        Member member = createMember("이훈이", "hooni1234@naver.com", "12345678", "hoon", Gender.FEMALE);

       Member findMember = memberRepository.findByEmail("hooni1234@naver.com")
                .orElse(null);

        assertNotNull(member);
        assertEquals(member.getEmail(),findMember.getEmail());
        assertEquals(member.getNickname(),findMember.getNickname());
    }

    @Test
    public void testFindAllMembers(){
        List<Member> members =  memberRepository.findAll();
        assertEquals(members.size(),3);
    }


    @Test
    public void testFindMember(){
        Member member = memberRepository.findByEmail("shin@gmail.com").orElseThrow(null);

        assertEquals(member.getUsername(),"신짱구");
        assertEquals(member.getNickname(),"부리부리");
    }

    @Test
    public void testDeleteMember(){
        Member member = memberRepository.findByEmail("shin@gmail.com").orElseThrow(null);

        memberRepository.delete(member);

        assertFalse(memberRepository.findById(member.getId()).isPresent());
    }
}