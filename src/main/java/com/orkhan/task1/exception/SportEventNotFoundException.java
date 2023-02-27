package com.orkhan.task1.exception;

import org.springframework.http.HttpStatus;

public class SportEventNotFoundException extends SportEventException {

    public SportEventNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND,message);
    }
}
