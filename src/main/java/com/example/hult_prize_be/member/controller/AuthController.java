//package com.example.hult_prize_be.member.controller;
//
//
//import com.example.hult_prize_be.member.model.dto.AuthDto;
//import com.example.hult_prize_be.member.service.AuthService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final AuthService authService;
//
//    @PostMapping("/login")
//    public String login(@RequestBody AuthDto.LoginRequest request) {
//        AuthDto.LoginResponse response = authService.login(request);
//        return response.toString();
//    }
//}
