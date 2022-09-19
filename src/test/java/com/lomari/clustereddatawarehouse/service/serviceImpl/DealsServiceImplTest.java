package com.lomari.clustereddatawarehouse.service.serviceImpl;

import com.lomari.clustereddatawarehouse.dto.DealRequestDto;
import com.lomari.clustereddatawarehouse.models.Deal;
import com.lomari.clustereddatawarehouse.repository.DealsRepository;
import com.lomari.clustereddatawarehouse.service.DealsService;
import org.hibernate.id.GUIDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class DealsServiceImplTest {

    @Mock
    DealsRepository dealsRepository;

    @InjectMocks
    DealsService dealsService;


    @Test
    void saveDealWhenNoDealExist() {
        DealRequestDto requestDto = createDealRequest();
        when(dealsRepository.findByDealUniqueId(requestDto.getDealUniqueId()))
                .thenReturn(Optional.empty());
        Deal deal = createDeal(requestDto);
        verify(dealsRepository, times(1)).save(deal);
    }

    private DealRequestDto createDealRequest() {
        return DealRequestDto.builder()
                .dealUniqueId("id")
                .toCurrencyISO("NGN")
                .orderingCurrencyISO("EUR")
                .amountInOrderingCurrency(new BigDecimal("10000.0"))
                .build();
    }

    private Deal createDeal(DealRequestDto requestDto){
        return Deal.builder()
                .dealTimestamp(requestDto.getDealTimestamp())
                .dealUniqueId(requestDto.getDealUniqueId())
                .toCurrencyISO(requestDto.getToCurrencyISO())
                .orderingCurrencyISO(requestDto.getOrderingCurrencyISO())
                .amountInOrderingCurrency(requestDto.getAmountInOrderingCurrency())
                .id(1L)
                .build();
    }
}