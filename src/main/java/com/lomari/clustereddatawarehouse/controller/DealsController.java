package com.lomari.clustereddatawarehouse.controller;

import com.lomari.clustereddatawarehouse.dto.ApiResponse;
import com.lomari.clustereddatawarehouse.dto.DealRequestDto;
import com.lomari.clustereddatawarehouse.dto.DealResponseDto;
import com.lomari.clustereddatawarehouse.service.DealsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.lomari.clustereddatawarehouse.dto.ApiUtils.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/api/deals")
@RestController
public class DealsController {

    private final Logger log = LoggerFactory.getLogger(DealsController.class);
    private final DealsService dealsService;

    public DealsController(DealsService dealsService) {
        this.dealsService = dealsService;
    }

    @PostMapping("/create-deal")
    public ResponseEntity<ApiResponse> saveDeal(@RequestBody DealRequestDto requestDto, HttpServletRequest req){
        log.info("request {} from {}", requestDto, req.getRemoteAddr());
        DealResponseDto dealResponseDto = dealsService.saveDeal(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(buildApiResponse(dealResponseDto, HttpStatus.CREATED));
    }

    @GetMapping("/{dealId}")
    public ResponseEntity<ApiResponse> getDeal(@PathVariable String dealId){
        log.info("get request from {}", dealId);
        DealResponseDto deal = dealsService.getDeal(dealId);
        return ResponseEntity.status(HttpStatus.OK).body(buildApiResponse(deal, HttpStatus.OK));
    }
}
