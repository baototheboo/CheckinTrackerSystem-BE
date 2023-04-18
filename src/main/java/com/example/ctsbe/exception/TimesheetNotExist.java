package com.example.ctsbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Không tìm thấy thông tin của ngày.")
public class TimesheetNotExist extends RestClientException {
    public TimesheetNotExist(String msg) {
        super(msg);
    }
}
