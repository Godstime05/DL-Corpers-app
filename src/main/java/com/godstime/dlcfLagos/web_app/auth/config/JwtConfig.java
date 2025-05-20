package com.godstime.dlcfLagos.web_app.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class JwtConfig {
    
    private static final String SECRET_KEY = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
    
    @Bean
    public SecretKey jwtSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }
}
