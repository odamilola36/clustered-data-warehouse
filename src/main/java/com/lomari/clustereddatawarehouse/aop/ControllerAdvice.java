package com.lomari.clustereddatawarehouse.aop;

import com.lomari.clustereddatawarehouse.dto.ApiResponse;
import com.lomari.clustereddatawarehouse.exceptions.InvalidRequestFieldException;
import com.lomari.clustereddatawarehouse.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.lomari.clustereddatawarehouse.dto.ApiUtils.buildErrorResponse;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(InvalidRequestFieldException.class)
    public ResponseEntity<ApiResponse> handleException(InvalidRequestFieldException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildErrorResponse(e.getErrors(), e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleException(ResourceNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildErrorResponse(List.of(), e.getMessage(), HttpStatus.NOT_FOUND));
    }
}
