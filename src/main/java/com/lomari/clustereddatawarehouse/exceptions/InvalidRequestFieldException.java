package com.lomari.clustereddatawarehouse.exceptions;


import com.lomari.clustereddatawarehouse.service.validator.DealError;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InvalidRequestFieldException extends RuntimeException{
    private List<DealError> errors = new ArrayList<>();
    public InvalidRequestFieldException(String message, List<DealError> requestFields) {
        super(message);
        errors = requestFields;
    }
}
