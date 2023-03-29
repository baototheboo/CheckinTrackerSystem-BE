package com.example.ctsbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Account not found")
public class StaffDoesNotExistException extends RestClientException{

    public StaffDoesNotExistException(int staffId)  {
        super(String.format("Staff '%s' does not exist.", staffId));
    }
}
