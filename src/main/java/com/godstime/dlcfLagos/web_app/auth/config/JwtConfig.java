package com.godstime.dlcfLagos.web_app.auth.config;

import org.springframework.context.annotation.Bean;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class JwtConfig {

    @Bean
    public SecretKey secretKey() {
        String secretString = "84357912345678901234567890123456789012345678901234567890";
        byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }
    
}
