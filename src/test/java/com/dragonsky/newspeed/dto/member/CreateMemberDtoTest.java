package com.dragonsky.newspeed.dto.member;

import com.dragonsky.newspeed.entity.Gender;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class CreateMemberDtoTest {
    @Test
    public void testCreateMemberDtoValidation(){
        CreateMemberDto createMemberDto = new CreateMemberDto();
        createMemberDto.setUsername("박용민");
        createMemberDto.setEmail("qkrdydals327@anver.com");
        createMemberDto.setPassword("23571113");
        createMemberDto.setNickname("dragonsky");
        createMemberDto.setGender(Gender.MALE);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<CreateMemberDto>> violations  = validator.validate(createMemberDto);

        assertEquals(0,violations.size(),"검증 통과");
    }

    @Test
    public void testCreateMemberDtoValidationWithInvalidData(){
        CreateMemberDto createMemberDto = new CreateMemberDto();
        createMemberDto.setUsername("박");
        createMemberDto.setEmail("qkrdydals327 anver");
        createMemberDto.setPassword("235");
        createMemberDto.setNickname("");
        createMemberDto.setGender(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<CreateMemberDto>> violations = validator.validate(createMemberDto);

        // nickname 부분에서 NotBlank과 Size값이 2개로 나오는게 정상?
        assertEquals(6,violations.size(),"검증 오류 발생");
    }
}