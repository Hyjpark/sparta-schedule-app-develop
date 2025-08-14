package org.example.scheduleapiv2.security.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * 비밀번호 암호화 및 검증을 담당하는 클래스.
 */
@Component
public class PasswordEncoder {

    /**
     * 비밀번호를 BCrypt로 암호화합니다.
     *
     * @param rawPassword 비밀번호
     * @return 암호화된 비밀번호
     */
    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    /**
     * 입력 비밀번호가 암호화된 비밀번호와 일치하는지 검증합니다.
     *
     * @param rawPassword 입력 비밀번호
     * @param encodedPassword 저장된 암호화 비밀번호
     * @return 일치하면 true, 일치하지 않으면 false
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}
