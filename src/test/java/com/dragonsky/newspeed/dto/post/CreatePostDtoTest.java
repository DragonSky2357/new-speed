package com.dragonsky.newspeed.dto.post;

import com.dragonsky.newspeed.dto.member.CreateMemberDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CreatePostDtoTest {
    @Test
    public void testCreatePostDtoValidation(){
        CreatePostDto createPostDto = new CreatePostDto();
        createPostDto.setTitle("아침 누룽지");
        createPostDto.setContent("먹다 남은 누룽지");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<CreatePostDto>> violations = validator.validate(createPostDto);

        assertEquals(0,violations.size(),"검증 통과");
    }

    @Test
    public void testCreatePostDtoValidationWithInvalidData(){
        CreatePostDto createPostDto = new CreatePostDto();
        createPostDto.setTitle("");
        createPostDto.setContent("");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<CreatePostDto>> violations = validator.validate(createPostDto);

        assertEquals(2,violations.size(),"검증 오류가 발생");
    }
}