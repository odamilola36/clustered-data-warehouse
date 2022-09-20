package com.lomari.clustereddatawarehouse.service.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealError {
    private String errorMessage;
    private String rejectedField;
}
