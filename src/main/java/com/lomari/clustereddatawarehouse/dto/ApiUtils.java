package com.lomari.clustereddatawarehouse.dto;

import com.lomari.clustereddatawarehouse.service.validator.DealError;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ApiUtils {

    public static ApiResponse buildApiResponse(Object d, HttpStatus status){
        return ApiResponse.builder()
                .message("successful")
                .data(d)
                .status(status)
                .build();
    }

    public static ApiResponse buildErrorResponse(List<DealError> errors, String message, HttpStatus status) {
        return ApiResponse.builder()
                .message("error")
                .status(status)
                .errorList(errors)
                .error(message)
                .build();
    }
}
