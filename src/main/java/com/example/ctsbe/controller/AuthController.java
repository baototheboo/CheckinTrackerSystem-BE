package com.example.ctsbe.controller;

import com.example.ctsbe.dto.account.AccountDTO;
import com.example.ctsbe.dto.LoginDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.exception.ExceptionObject;
import com.example.ctsbe.mapper.AccountMapper;
import com.example.ctsbe.response.AuthResponse;
import com.example.ctsbe.service.AccountService;
import com.example.ctsbe.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtTokenUtil jwtUtil;
    @Autowired
    private AccountService accountService;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        try {
            ExceptionObject exceptionObject = new ExceptionObject();
            Map<String,String> errorMap = new HashMap<>();
            int errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            exceptionObject.setCode(errorCode);
            if(accountService.getAccountByUsername(loginDTO.getUsername()) == null) {
                errorMap.put("username", "Tên tài khoản không đúng!");
                exceptionObject.setError(errorMap);
                return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
            }
            else{
                String dbPassword = accountService.getAccountByUsername(loginDTO.getUsername()).getPassword();
                if(!passwordEncoder.matches(loginDTO.getPassword(), dbPassword)){
                    errorMap.put("password","Mật khẩu không đúng. Vui lòng thử lại!");
                    exceptionObject.setError(errorMap);
                    return new ResponseEntity<>(exceptionObject,HttpStatus.BAD_REQUEST);
                }
                else {
                    Authentication authentication = authManager.authenticate(
                            new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword())
                    );
                    Account user = (Account) authentication.getPrincipal();
                    AccountDTO dto = AccountMapper.convertEntityToDTO(user);
                    String accessToken = jwtUtil.generateAccessToken(user);
                    AuthResponse response = new AuthResponse(dto, accessToken);
                    return ResponseEntity.ok().body(response);
                }
            }
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
