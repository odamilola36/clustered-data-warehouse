package com.lomari.clustereddatawarehouse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lomari.clustereddatawarehouse.dto.DealRequestDto;
import com.lomari.clustereddatawarehouse.dto.DealResponseDto;
import com.lomari.clustereddatawarehouse.exceptions.InvalidRequestFieldException;
import com.lomari.clustereddatawarehouse.exceptions.ResourceNotFoundException;
import com.lomari.clustereddatawarehouse.service.DealsService;
import com.lomari.clustereddatawarehouse.service.validator.DealError;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(DealsController.class)
class DealsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DealsService dealsService;

    @Test
    public void testSuccessfullySaveDeal() throws Exception {

        DealRequestDto deal = DealRequestDto.builder()
                .dealUniqueId("id")
                .toCurrencyISO("NGN")
                .orderingCurrencyISO("EUR")
                .amountInOrderingCurrency(new BigDecimal("10000.0"))
                .build();
        DealResponseDto responseDto = DealResponseDto.builder()
                .dealUniqueId("id")
                .toCurrencyISO("NGN")
                .orderingCurrencyISO("EUR")
                .amountInOrderingCurrency(new BigDecimal("10000.0"))
                .build();
        when(dealsService.saveDeal(deal))
                .thenReturn(responseDto);
        mvc.perform(post("/api/deals/create-deal")
                        .content(asJsonString(deal))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errorList").doesNotExist())
                .andExpect(jsonPath("$.data.dealUniqueId").exists())
        ;
    }

    @Test
    public void testBadRequestDataSaveDeal() throws Exception {

        DealRequestDto deal = DealRequestDto.builder()
                .dealUniqueId("id")
                .orderingCurrencyISO("EUR")
                .amountInOrderingCurrency(new BigDecimal("10000.0"))
                .build();
        DealError error = new DealError();
        error.setErrorMessage("to currency can't be null");
        error.setRejectedField("toCurrency");
        when(dealsService.saveDeal(deal))
                .thenThrow(new InvalidRequestFieldException("invalid request field", List.of(error)));
        mvc.perform(post("/api/deals/create-deal")
                        .content(asJsonString(deal))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorList").exists())
                .andExpect(jsonPath("$.errorList").isArray())
                .andExpect(jsonPath("$.errorList.[0].errorMessage")
                        .value("to currency can't be null"));
    }
    @Test
    public void testGetOneDeal() throws Exception {
        String ID_TO_FIND = "kau8-2879-99ij";
        DealResponseDto responseDto = DealResponseDto.builder()
                .dealUniqueId(ID_TO_FIND)
                .toCurrencyISO("NGN")
                .orderingCurrencyISO("EUR")
                .amountInOrderingCurrency(new BigDecimal("10000.0"))
                .build();
        when(dealsService.getDeal(ID_TO_FIND))
                .thenReturn(responseDto);
        mvc.perform(get("/api/deals/"+ID_TO_FIND))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorList").doesNotExist()) // no errors on fetch
                .andExpect(jsonPath("$.data.dealUniqueId").value(ID_TO_FIND));
    }

    @Test
    public void testNotFoundDeal() throws Exception {
        String ID_TO_FIND = "kau8-2879-99ij";
        when(dealsService.getDeal(ID_TO_FIND))
                .thenThrow(new ResourceNotFoundException("resource not found"));
        mvc.perform(get("/api/deals/"+ID_TO_FIND))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorList").exists())
                .andExpect(jsonPath("$.errorList").isArray()) // no errors on fetch
                .andExpect(jsonPath("$.error").value("resource not found"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
    private String asJsonString(DealRequestDto deal) {
        try {
            return new ObjectMapper().writeValueAsString(deal);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}