package com.lomari.clustereddatawarehouse.service.mapper;

import com.lomari.clustereddatawarehouse.dto.DealRequestDto;
import com.lomari.clustereddatawarehouse.dto.DealResponseDto;
import com.lomari.clustereddatawarehouse.models.Deal;

public interface DealMapper {
    DealResponseDto dealToDealResponseDto(Deal deal);

    Deal dealDtoToEntity(DealRequestDto dealRequestDto);
}
