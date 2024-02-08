package com.dragonsky.newspeed.entity;

import com.dragonsky.newspeed.dto.member.CreateMemberDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true,nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private String introduction;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    public Member(CreateMemberDto createMemberDto, String passwordEncryption){
        this.username = createMemberDto.getUsername();
        this.email = createMemberDto.getEmail();
        this.password = passwordEncryption;
        this.nickname = createMemberDto.getNickname();
        this.gender = createMemberDto.getGender();
        this.introduction = createMemberDto.getIntroduction();
    }
}
