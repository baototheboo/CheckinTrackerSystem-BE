package com.example.ctsbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Facial recognition identify failed. Please try again or use PIN.")
public class FacialRecognitionIdentifyException extends RestClientException {

    public FacialRecognitionIdentifyException() {
        super(String.format("Facial recognition identify failed. Please try again or use PIN."));
    }
}
