package com.godstime.dlcfLagos.web_app.auth.config;

import com.godstime.dlcfLagos.web_app.auth.service.CustomUserService;
import com.godstime.dlcfLagos.web_app.auth.service.JwtAuthFilter;
import com.godstime.dlcfLagos.web_app.auth.service.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private CustomUserService customUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        // Auth endpoints - public
                        .requestMatchers("/api/auth/**").permitAll()
                        
                        // Public endpoints
                        .requestMatchers(
                            "/api/fellowship-centers/{id}",
                            "/api/fellowship-centers",
                            "/api/fellowship-centers/active",
                            "/api/fellowship-centers/city/{city}",
                            "/api/fellowship-centers/state/{state}",
                            "/api/fellowship-centers/count/active",
                            "/api/fellowship-centers/count/city/{city}",
                            "/api/fellowship-centers/count/state/{state}",
                            "/api/announcements/{id}",
                            "/api/announcements",
                            "/api/announcements/active",
                            "/api/announcements/priority/{priority}",
                            "/api/announcements/target",
                            "/api/events/{id}",
                            "/api/events",
                            "/api/events/active",
                            "/api/events/upcoming",
                            "/api/events/ongoing",
                            "/api/events/category/{category}",
                            "/api/events/status/{status}",
                            "/api/events/date-range",
                            "/api/events/target",
                            "/api/communes",
                            "/api/communes/{id}",
                            "/api/communes/{communeId}/members",
                            "/api/prayer-requests/{id}",
                            "/api/prayer-requests",
                            "/api/prayer-requests/category/{category}",
                            "/api/prayer-requests/status/{status}",
                            "/api/prayer-responses/{id}",
                            "/api/testimonies/{id}",
                            "/api/testimonies",
                            "/api/evangelism-records/{id}",
                            "/api/evangelism-records",
                            "/api/evangelism-records/fellowship-center/{centerId}"
                        ).permitAll()
                        
                        // Admin/Moderator only endpoints
                        .requestMatchers(
                            "/api/announcements/**",
                            "/api/events/**",
                            "/api/announcements/creator/{userId}",
                            "/api/events/creator/{userId}"
                        ).hasAnyRole("ADMIN", "MODERATOR")
                        
                        // Authenticated user endpoints
                        .requestMatchers(
                            "/api/fellowship-centers/**",
                            "/api/prayer-requests/**",
                            "/api/prayer-responses/**",
                            "/api/testimonies/**",
                            "/api/evangelism-records/**",
                            "/api/feedback/**",
                            "/api/discussions/**",
                            "/api/communes/**"
                        ).authenticated()
                        
                        // All other requests need authentication
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtUtils jwtUtils, CustomUserService userService) {
        return new JwtAuthFilter(jwtUtils, userService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Frontend URL
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); // 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}