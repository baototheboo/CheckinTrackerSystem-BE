package com.example.ctsbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Nhân viên đã được setup ảnh nhận diện.")
public class SetupEmployeeFacialRecognitionException extends RestClientException {

    public SetupEmployeeFacialRecognitionException(String staffId) {
        super(String.format("Nhân viên với ID '%s' đã được setup ảnh nhận diện.", staffId));
    }
}
