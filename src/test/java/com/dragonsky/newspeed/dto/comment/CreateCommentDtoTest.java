package com.dragonsky.newspeed.dto.comment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateCommentDtoTest {
    @Test
    public void testCreateCommentDto(){
        CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setComment("안녕하세요");

        assertEquals("안녕하세요",createCommentDto.getComment());
    }

    @Test
    public void testCreateCommentDtoWithInvalidData(){
        CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setComment("반가워요");

        assertNotEquals("안녕하세요",createCommentDto.getComment());
    }
}