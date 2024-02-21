package com.dragonsky.newspeed.dto.member;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginMemberDtoTest {
    @Test
    public void testLoginMemberDtoValidation(){
        LoginMemberDto loginMemberDto = new LoginMemberDto();
        loginMemberDto.setEmail("qkrdydals327@naver.com");
        loginMemberDto.setPassword("235711");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<LoginMemberDto>> violations  = validator.validate(loginMemberDto);

        assertEquals(0,violations.size(),"검증 오류가 발생");

    }

    @Test
    public void testLoginMemberDtoValidationInvalidData(){
        LoginMemberDto loginMemberDto = new LoginMemberDto();
        loginMemberDto.setEmail("qkrdydals3 com");
        loginMemberDto.setPassword("2");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<LoginMemberDto>> violations  = validator.validate(loginMemberDto);

        assertEquals(1,violations.size(),"검증 오류가 발생");
    }
}