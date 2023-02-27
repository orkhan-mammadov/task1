package com.orkhan.task1.handler;

import com.orkhan.task1.exception.SportEventException;
import com.orkhan.task1.exception.SportEventNotFoundException;
import com.orkhan.task1.model.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {SportEventException.class, SportEventNotFoundException.class})
    protected ResponseEntity<ResponseModel> handleConflict(SportEventException ex) {
        log.trace(ex.getLocalizedMessage());
        return ResponseEntity.status(ex.getStatus())
                .body(ResponseModel.builder()
                        .response(ex.getLocalizedMessage())
                        .build());
    }

}