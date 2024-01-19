package com.kpekala.meetingplanner.domain.auth;

import com.kpekala.meetingplanner.domain.auth.dto.AuthResponse;
import com.kpekala.meetingplanner.domain.auth.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("signin")
    public AuthResponse authenticateUser(@RequestBody LoginRequest loginRequest){
        return authService.signin(loginRequest.getEmailAddress(), loginRequest.getPassword());
    }
}
