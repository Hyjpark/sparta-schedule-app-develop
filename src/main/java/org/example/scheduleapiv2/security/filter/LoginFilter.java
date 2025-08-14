package org.example.scheduleapiv2.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.scheduleapiv2.common.error.GlobalErrorCode;
import org.example.scheduleapiv2.common.exception.ApiException;
import org.example.scheduleapiv2.common.exception.ErrorResponse;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

/**
 * 로그인 상태를 검증하는 로그인 필터 클래스
 *
 * 화이트리스트에 포함되지 않은 요청에 대해 세션에서 로그인 정보를 확인합니다.
 * 로그인하지 않은 경우 에외를 발생시키고,
 * JSON 형태의 에러 응답을 클라이언트에 반환합니다.
 */
@Slf4j
public class LoginFilter implements Filter {
    private static final String[] WHITE_LIST = {"/", "/users/signup", "/users/login"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String requestURI = httpRequest.getRequestURI();

        try {
            if (!isWhiteList(requestURI)) {
                HttpSession session = httpRequest.getSession(false);

                if (session == null || session.getAttribute("LOGIN_USER") == null) {
                    throw new ApiException(GlobalErrorCode.LOGIN_REQUIRED);
                }
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ApiException e) {
            httpResponse.setStatus(e.getErrorCode().getHttpStatus().value());
            httpResponse.setContentType("application/json;charset=UTF-8");
            String json = new ObjectMapper().writeValueAsString(
                    ErrorResponse.builder()
                            .code(e.getErrorCode().name())
                            .message(e.getErrorCode().getMessage())
                            .build()
            );
            httpResponse.getWriter().write(json);
        }
    }

    /**
     * 요청 URI가 화이트리스트에 포함되는지 확인합니다.
     *
     * @param requestURI 요청 URI
     * @return 화이트리스트에 포함되면 true, 아니면 false
     */
    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
