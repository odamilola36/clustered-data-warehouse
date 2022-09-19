package com.lomari.clustereddatawarehouse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lomari.clustereddatawarehouse.models.Deal;
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
public class ApiResponse {
    private String message;
    private HttpStatus status;
    private Object data;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime localDateTime = LocalDateTime.now();
    private List<DealError> errorList;
    private String error;
}
