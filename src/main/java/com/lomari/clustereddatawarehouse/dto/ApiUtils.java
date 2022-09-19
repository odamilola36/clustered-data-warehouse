package com.lomari.clustereddatawarehouse.dto;

import com.lomari.clustereddatawarehouse.service.validator.DealError;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ApiUtils {

    public static ApiResponse buildApiResponse(Object d){
        return ApiResponse.builder()
                .message("successful")
                .data(d)
                .status(HttpStatus.CREATED)
                .build();
    }

    public static ApiResponse buildErrorResponse(List<DealError> errors, String message) {
        return ApiResponse.builder()
                .message("error")
                .status(HttpStatus.BAD_REQUEST)
                .errorList(errors)
                .error(message)
                .build();
    }
}
