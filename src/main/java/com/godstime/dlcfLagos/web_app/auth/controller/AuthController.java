package com.godstime.dlcfLagos.web_app.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.godstime.dlcfLagos.web_app.auth.service.SecurityUserService;
import com.godstime.dlcfLagos.web_app.dto.Request;

@RestController
@RequestMapping
public class AuthController {


    private final SecurityUserService userService;


    public AuthController(SecurityUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Request> register(@RequestBody Request registerRequest){
        return  ResponseEntity.ok(userService.register(registerRequest));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Request> login(@RequestBody Request loginRequest){
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @PostMapping("/auth/refreshToken")
    public ResponseEntity<Request> refreshToken(@RequestBody Request refreshTokenRequest){
        return ResponseEntity.ok(userService.refreshToken(refreshTokenRequest));
    }


}
