package com.example.ctsbe.exception;

import org.springframework.web.client.RestClientException;

public class DeleteStaffException extends RestClientException {
    public DeleteStaffException(int staffId) {
        super(String.format("Xoá ảnh của nhân viên có Id %s thất bại.", staffId));
    }
}
