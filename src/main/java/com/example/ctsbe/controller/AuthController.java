package com.example.ctsbe.controller;

import com.example.ctsbe.dto.account.AccountDTO;
import com.example.ctsbe.dto.LoginDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.mapper.AccountMapper;
import com.example.ctsbe.response.AuthResponse;
import com.example.ctsbe.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
            AccountDTO dto = AccountMapper.convertEntityToDTO(user);
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(dto, accessToken);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
