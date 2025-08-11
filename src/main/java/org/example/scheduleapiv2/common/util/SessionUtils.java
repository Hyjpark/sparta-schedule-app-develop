package org.example.scheduleapiv2.common.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
}
