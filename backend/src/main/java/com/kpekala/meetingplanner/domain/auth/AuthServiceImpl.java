package com.kpekala.meetingplanner.domain.auth;

import com.kpekala.meetingplanner.domain.auth.dto.AuthResponse;
import com.kpekala.meetingplanner.domain.user.RoleRepository;
import com.kpekala.meetingplanner.domain.user.UserRepository;
import com.kpekala.meetingplanner.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse signin(String email, String password) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var expirationDate = jwtService.tokenExpirationDate(jwt);

        return AuthResponse.builder().token(jwt).tokenExpirationDate(expirationDate).build();
    }
}
