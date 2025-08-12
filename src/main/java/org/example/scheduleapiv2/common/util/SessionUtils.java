package org.example.scheduleapiv2.common.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.scheduleapiv2.common.error.GlobalErrorCode;
import org.example.scheduleapiv2.common.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;

public class SessionUtils {
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

    public static void assertUserIsOwner(Long sesssionUserId, Long resourceId) {
        if (!ObjectUtils.nullSafeEquals(sesssionUserId, resourceId)) {
            throw new ApiException(GlobalErrorCode.ACCESS_DENIED);
        }
    }
}
