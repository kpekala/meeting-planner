package com.kpekala.meetingplanner.domain.auth;

import com.kpekala.meetingplanner.domain.auth.dto.AuthResponse;
import com.kpekala.meetingplanner.domain.auth.dto.SignupRequest;

public interface AuthService {
    AuthResponse signin(String email, String password);
}
