package com.orkhan.task1.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SportEventException extends RuntimeException{

    private final HttpStatus status;
    public SportEventException(String message) {
        super(message);
        status = HttpStatus.BAD_REQUEST;
    }

    public SportEventException(HttpStatus status, String msg) {
        super(msg);
        this.status = status;
    }
}
