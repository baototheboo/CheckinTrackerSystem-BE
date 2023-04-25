package com.example.ctsbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Không thể lưu bản ghi trên database.")
public class ConnectionErrorException extends RestClientException {
    public ConnectionErrorException() {
        super("Không thể lưu bản ghi trên database.");
    }
}
