package com.example.ctsbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Staff không khả dụng hoặc không tồn tại.")
public class StaffNotAvailableException extends RestClientException {
    public StaffNotAvailableException(String msg) {
        super(msg);
    }
}
