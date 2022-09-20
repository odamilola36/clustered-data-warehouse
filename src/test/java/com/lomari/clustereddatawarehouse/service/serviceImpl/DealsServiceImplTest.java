package com.lomari.clustereddatawarehouse.service.serviceImpl;

import com.lomari.clustereddatawarehouse.dto.DealRequestDto;
import com.lomari.clustereddatawarehouse.models.Deal;
import com.lomari.clustereddatawarehouse.repository.DealsRepository;
import com.lomari.clustereddatawarehouse.service.DealsService;
import com.lomari.clustereddatawarehouse.service.mapper.DealMapper;
import com.lomari.clustereddatawarehouse.service.validator.DealValidator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class DealsServiceImplTest {

    @MockBean
    DealsRepository dealsRepository;

    @MockBean
    DealMapper dealMapper;

    @MockBean
    DealValidator validator;

    @InjectMocks
    DealsServiceImpl dealsService;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveDealWhenNoDealExist() {
        DealRequestDto requestDto = createDealRequest();
        Deal deal = createDeal(requestDto);
        when(dealsRepository.findByDealUniqueId(anyString()))
                .thenReturn(Optional.empty());
        when(dealsRepository.save(any(Deal.class))).
                thenReturn(deal);
        dealsService.saveDeal(requestDto);
        verify(validator, times(1)).validateRequestFields(any(DealRequestDto.class));
        verify(validator, times(1)).validateDealExistence(requestDto.getDealUniqueId());
        verify(dealsRepository, times(1)).save(any(Deal.class));
        verifyNoInteractions(dealsRepository);
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
        Currency toCurrency = Currency.getInstance(requestDto.getToCurrencyISO());
        Currency fromCurrency = Currency.getInstance(requestDto.getOrderingCurrencyISO());

        return Deal.builder()
                .dealTimestamp(requestDto.getDealTimestamp())
                .dealUniqueId(requestDto.getDealUniqueId())
                .toCurrencyISO(toCurrency)
                .orderingCurrencyISO(fromCurrency)
                .amountInOrderingCurrency(requestDto.getAmountInOrderingCurrency())
                .id(1L)
                .build();
    }
}