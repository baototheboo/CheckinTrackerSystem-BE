package com.example.ctsbe.response;

import com.example.ctsbe.dto.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private AccountDTO accountDTO;
    private String accessToken;

}
