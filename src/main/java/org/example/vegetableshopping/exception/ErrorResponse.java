package org.example.vegetableshopping.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ErrorResponse {
    private Date timestamp;
    private String message;

    public ErrorResponse(String message) {
        this.timestamp = new Date();
        this.message = message;
    }

}
