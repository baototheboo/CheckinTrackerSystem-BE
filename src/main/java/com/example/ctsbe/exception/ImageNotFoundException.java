package com.example.ctsbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Không tìm thấy ảnh theo yêu cầu.")
public class ImageNotFoundException extends RestClientException {
    public ImageNotFoundException(String msg) {
        super(msg);
    }
}
