package com.lomari.clustereddatawarehouse.service;

import com.lomari.clustereddatawarehouse.dto.DealRequestDto;
import com.lomari.clustereddatawarehouse.dto.DealResponseDto;
import com.lomari.clustereddatawarehouse.exceptions.InvalidRequestFieldException;
import com.lomari.clustereddatawarehouse.models.Deal;

public interface DealsService {
    DealResponseDto saveDeal(DealRequestDto dealRequestDto) throws InvalidRequestFieldException;

    DealResponseDto getDeal(String dealUniqueId);

}
