package org.example.scheduleapiv2.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 댓글 수정 요청 데이터를 담는 DTO 클래스.
 *
 * 클라이언트에서 전달한 댓글 내용을 검증(@NotBlank)합니다.
 */
@Getter
public class CommentUpdateRequest {

    /** 댓글 내용 (필수 입력) */
    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;
}
