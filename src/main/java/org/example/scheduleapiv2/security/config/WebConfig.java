package org.example.scheduleapiv2.security.config;

import jakarta.servlet.Filter;
import org.example.scheduleapiv2.security.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 웹 관련 설정 클래스.
 */
@Configuration
public class WebConfig {

    /**
     * 로그인 상태를 검증하는 {@link LoginFilter}를 모든 요청 경로에 등록합니다.
     *
     * @return 필터 등록 정보를 담은 {@link FilterRegistrationBean}
     */
    @Bean
    public FilterRegistrationBean loginFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter());
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
