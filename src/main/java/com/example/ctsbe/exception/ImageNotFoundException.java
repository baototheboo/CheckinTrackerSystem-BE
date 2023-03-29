package com.example.ctsbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Search parameter is invalid")
public class ImageNotFoundException extends RestClientException {
    public ImageNotFoundException(String msg) {
        super(msg);
    }
}
