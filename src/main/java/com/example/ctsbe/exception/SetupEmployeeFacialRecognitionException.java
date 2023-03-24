package com.example.ctsbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Setup employee facial recognition failed.")
public class SetupEmployeeFacialRecognitionException extends RestClientException {

    public SetupEmployeeFacialRecognitionException(String staffId) {
        super(String.format("Employee id '%s' had been setup facial recognition.", staffId));
    }
}
