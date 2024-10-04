package com.ra.projectmd5.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutOfStockException extends Exception{
    private String field;
    public OutOfStockException(String message,String field) {
        super(message);
        this.field = field;

    }
}
