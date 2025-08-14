package org.example.scheduleapiv2.common.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.scheduleapiv2.common.error.GlobalErrorCode;
import org.example.scheduleapiv2.common.exception.ApiException;
import org.springframework.util.ObjectUtils;

/**
 * 세션 관련 유틸리티 클래스
 *
 * 로그인 사용자 정보 확인 및 권한 검증 등의 공통 기능을 제공합니다.
 */
public class SessionUtils {

    /**
     * 세션에서 로그인한 사용자 ID를 조회합니다.
     *
     * @param request HTTP 요청 객체
     * @return 로그인한 사용자 ID
     * @throws ApiException 세션 또는 로그인 정보가 없을 경우
     */
    public static Long getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new ApiException(GlobalErrorCode.SESSION_NOT_FOUND);
        }

        Long userId = (Long) session.getAttribute("LOGIN_USER");
        if (userId == null) {
            throw new ApiException(GlobalErrorCode.SESSION_ATTRIBUTE_NOT_FOUND);
        }

        return userId;
    }

    /**
     * 로그인 사용자 ID가 리소스 소유자 ID와 일치하는지 확인합니다.
     * 일치하지 않으면 권한이 없다고 판단하여 예외를 던집니다.
     *
     * @param sesssionUserId 세션에서 조회한 로그인 사용자 ID
     * @param resourceId 리소스 소유자 ID
     * @throws ApiException 접근 권한이 없는 경우
     */
    public static void assertUserIsOwner(Long sesssionUserId, Long resourceId) {
        if (!ObjectUtils.nullSafeEquals(sesssionUserId, resourceId)) {
            throw new ApiException(GlobalErrorCode.ACCESS_DENIED);
        }
    }
}
