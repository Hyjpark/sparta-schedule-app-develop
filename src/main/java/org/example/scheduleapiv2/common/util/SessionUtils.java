package org.example.scheduleapiv2.common.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;

public class SessionUtils {
    public static Long getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new IllegalStateException("HttpSession is null");
        }

        Long userId = (Long) session.getAttribute("LOGIN_USER");
        if (userId == null) {
            throw new IllegalStateException("UserId is null");
        }

        return userId;
    }

    public static void assertUserIsOwner(Long sesssionUserId, Long resourceId) {
        if (!ObjectUtils.nullSafeEquals(sesssionUserId, resourceId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission.");
        }
    }
}
