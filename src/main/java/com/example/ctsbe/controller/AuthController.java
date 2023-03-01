package com.example.ctsbe.controller;

import com.example.ctsbe.dto.LoginDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtTokenUtil jwtUtil;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword())
            );

            Account user = (Account) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            //AuthResponse response = new AuthResponse(user.getEmail(), accessToken);

            return ResponseEntity.ok().body(accessToken);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
