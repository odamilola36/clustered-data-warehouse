package com.lomari.clustereddatawarehouse.aop;

import com.lomari.clustereddatawarehouse.dto.ApiResponse;
import com.lomari.clustereddatawarehouse.exceptions.InvalidRequestFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.lomari.clustereddatawarehouse.dto.ApiUtils.buildErrorResponse;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(InvalidRequestFieldException.class)
    public ResponseEntity<ApiResponse> handleException(InvalidRequestFieldException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(e.getErrors(), e.getMessage()));
    }
}
