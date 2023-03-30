package com.example.ctsbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Xác thực bằng khuôn mặt thất bại. Hãy thử lại!")
public class FacialRecognitionIdentifyException extends RestClientException {

    public FacialRecognitionIdentifyException() {
        super(String.format("Xác thực bằng khuôn mặt thất bại. Hãy thử lại!"));
    }
}
