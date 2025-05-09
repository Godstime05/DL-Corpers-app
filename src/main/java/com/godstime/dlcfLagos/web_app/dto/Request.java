package com.godstime.dlcfLagos.web_app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.godstime.dlcfLagos.web_app.models.User;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Request{
    private int statusCode;
    private String message;
    private String error;

    private String token;
    private String refreshToken;
    private String expirationTime;

    private User user;
    private List<User> UsersList;

    private String username;
    private String email;
    private String password;
    private String role;
    private String phoneNumber;

    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;

}