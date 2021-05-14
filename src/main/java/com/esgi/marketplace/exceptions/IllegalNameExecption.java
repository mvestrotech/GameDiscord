package com.esgi.marketplace.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class IllegalNameExecption extends Throwable {
    public IllegalNameExecption(String message) {
        super(message);
    }
}
