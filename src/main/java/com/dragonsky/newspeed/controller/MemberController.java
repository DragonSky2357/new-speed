package com.dragonsky.newspeed.controller;

import com.dragonsky.newspeed.common.dto.ResponseDto;
import com.dragonsky.newspeed.dto.member.UpdateMemberDto;
import com.dragonsky.newspeed.dto.member.CreateMemberDto;
import com.dragonsky.newspeed.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signUp(@RequestBody @Valid CreateMemberDto createMemberDto) {
        memberService.createMember(createMemberDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout(HttpServletRequest request, HttpServletResponse response) {
        memberService.logoutMember(request,response);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("")
    public ResponseEntity<ResponseDto> updateMember(@RequestBody @Valid UpdateMemberDto updateMemberDto){
        memberService.updateMember(updateMemberDto);
        return null;
    }
}
