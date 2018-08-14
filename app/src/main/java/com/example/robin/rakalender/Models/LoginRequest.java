package com.example.robin.rakalender.Models;

public class LoginRequest {

    String e_mail;
    String password;

    public LoginRequest(String name, String password) {
        this.e_mail = name;
        this.password = password;
    }
}