package com.rule.engine.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ExceptionHandler<T> {

    public ResponseEntity<T> handle(Object data, HttpStatus status) {
        return new ResponseEntity(data, status);
    }
}