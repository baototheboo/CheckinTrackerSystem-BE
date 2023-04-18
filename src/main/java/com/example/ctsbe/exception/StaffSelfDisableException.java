package com.example.ctsbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Không thể khoá tài khoản của chính bản thân!")
public class StaffSelfDisableException extends RestClientException {
    public StaffSelfDisableException(String msg) {
        super(msg);
    }
}
