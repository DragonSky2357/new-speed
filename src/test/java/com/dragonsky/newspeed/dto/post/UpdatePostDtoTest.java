package com.dragonsky.newspeed.dto.post;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdatePostDtoTest {
    @Test
    public void testUpdatePostDtoValidation(){
        UpdatePostDto updatePostDto = new UpdatePostDto();
        updatePostDto.setTitle("오늘 점심 비빔냉면");
        updatePostDto.setContent("점심으로 비빔냉면이랑 양념갈비");

        assertEquals("오늘 점심 비빔냉면",updatePostDto.getTitle());
        assertEquals("점심으로 비빔냉면이랑 양념갈비",updatePostDto.getContent());
    }

    @Test
    public void testUpdatePostDtoValidationWithInvalidData(){
        UpdatePostDto updatePostDto = new UpdatePostDto();
        updatePostDto.setTitle("오늘 점심 물냉면");
        updatePostDto.setContent("");

        assertNotEquals("오늘 점심 비빔냉면",updatePostDto.getTitle());
        assertNotEquals("점심으로 비빔냉면이랑 양념갈비",updatePostDto.getContent());
    }
}