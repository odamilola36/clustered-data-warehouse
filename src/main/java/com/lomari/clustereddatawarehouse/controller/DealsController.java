package com.lomari.clustereddatawarehouse.controller;

import com.lomari.clustereddatawarehouse.dto.ApiResponse;
import com.lomari.clustereddatawarehouse.dto.DealRequestDto;
import com.lomari.clustereddatawarehouse.dto.DealResponseDto;
import com.lomari.clustereddatawarehouse.service.DealsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.lomari.clustereddatawarehouse.dto.ApiUtils.*;


@RequestMapping("/api/deals")
@RestController
@Slf4j
public class DealsController {

    private final DealsService dealsService;

    public DealsController(DealsService dealsService) {
        this.dealsService = dealsService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> saveDeal(@RequestBody DealRequestDto requestDto){
        DealResponseDto dealResponseDto = dealsService.saveDeal(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(buildApiResponse(dealResponseDto, HttpStatus.CREATED));
    }

    @GetMapping("/{dealId}")
    public ResponseEntity<ApiResponse> getDeal(@PathVariable String dealId){
        DealResponseDto deal = dealsService.getDeal(dealId);
        return ResponseEntity.status(HttpStatus.OK).body(buildApiResponse(deal, HttpStatus.OK));
    }
}
