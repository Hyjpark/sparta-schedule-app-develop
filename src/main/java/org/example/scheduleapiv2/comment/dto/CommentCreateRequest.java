package org.example.scheduleapiv2.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentCreateRequest {
    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;
}
