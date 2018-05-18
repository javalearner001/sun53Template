package com.sun.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse implements Serializable {
    private  int code;
    private  String message;

    public BaseResponse() {
        this.code = 0;
        this.message = "SUCCESS";
    }
}