package com.orkhan.task1.handler;

import com.orkhan.task1.exception.SportEventException;
import com.orkhan.task1.exception.SportEventNotFoundException;
import com.orkhan.task1.model.ResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {SportEventException.class, SportEventNotFoundException.class})
    protected ResponseEntity<ResponseModel> handleConflict(SportEventException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(ResponseModel.builder()
                        .response(ex.getLocalizedMessage())
                        .build());
    }
}