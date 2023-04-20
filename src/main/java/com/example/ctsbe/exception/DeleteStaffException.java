package com.example.ctsbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Không có ảnh nhận diện của nhân viên.")
public class DeleteStaffException extends RestClientException {
    public DeleteStaffException(int staffId) {
        super(String.format("Không có ảnh nhận diện của nhân viên."));
    }
}
