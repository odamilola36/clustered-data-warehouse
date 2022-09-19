package com.lomari.clustereddatawarehouse.service.validator;

import lombok.Data;

@Data
public class DealError {
    private String errorMessage;
    private Object[] argProvided;
    private String rejectedField;
}
