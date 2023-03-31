package com.example.ctsbe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel<T> {
    private Integer code = 0;
    private T data;
}
