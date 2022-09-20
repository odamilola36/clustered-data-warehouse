package com.lomari.clustereddatawarehouse.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lomari.clustereddatawarehouse.service.validator.DealError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private String message;
    private HttpStatus status;
//    private LocalDateTime dateTime; // TODO
    private Object data;
    private List<DealError> errorList;
    private String error;
}
