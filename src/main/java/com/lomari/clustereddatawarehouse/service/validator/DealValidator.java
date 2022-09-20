package com.lomari.clustereddatawarehouse.service.validator;

import com.lomari.clustereddatawarehouse.dto.DealRequestDto;
import com.lomari.clustereddatawarehouse.models.Deal;
import com.lomari.clustereddatawarehouse.repository.DealsRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Component
@RequestScope
public class DealValidator {

    private final DealsRepository dealsRepository;
    List<DealError> errors = new ArrayList<>();

    public DealValidator(DealsRepository dealsRepository) {
        this.dealsRepository = dealsRepository;
    }

    public List<DealError> validateRequestFields(DealRequestDto dealRequestDto){
        if(Objects.isNull(dealRequestDto)){
            DealError error = new DealError();
            error.setErrorMessage("Deal request should not be null");
            errors.add(error);
        } else {
            validateUniqueId(dealRequestDto.getDealUniqueId());
            validateCurrency(dealRequestDto.getOrderingCurrencyISO(), "orderingCurrencyISO");
            validateCurrency(dealRequestDto.getToCurrencyISO(), "toCurrencyISO");
            validateDealTimestamp(dealRequestDto.getDealTimestamp());
            validateAmountInOrderingCurrency(dealRequestDto.getAmountInOrderingCurrency());
            validateOrderingAndToCurrency(dealRequestDto.getOrderingCurrencyISO(), dealRequestDto.getToCurrencyISO());
        }
        return errors;
    }

    public List<DealError> validateDealExistence(String dealUniqueId){
        if (Objects.isNull(dealUniqueId)){
            addError("deal unique id should not be null", "dealUniqueId");
        } else {
            Optional<Deal> existingDeal = dealsRepository.findByDealUniqueId(dealUniqueId);
            if(existingDeal.isPresent()) {
                addError("deal with id " + dealUniqueId + " already exists", "");
            }
        }
        return errors;
    }

    private void validateOrderingAndToCurrency(String orderingCurrencyISO, String toCurrencyISO) {
        if (orderingCurrencyISO.equals(toCurrencyISO)){
            addError("Ordering currency should not be the same as to currency", "");
        }
    }

    private void validateAmountInOrderingCurrency(BigDecimal amountInOrderingCurrency) {
        if (Objects.isNull(amountInOrderingCurrency) ||
                amountInOrderingCurrency.compareTo(BigDecimal.ZERO) <= 0
        ){
            addError("Invalid amount, amount should be greater than zero", "amountInOrderingCurrency");
        }
    }

    private void validateDealTimestamp(Instant dealTimestamp) {
        if (Objects.isNull(dealTimestamp) || dealTimestamp.isAfter(Instant.now())){
            addError("Invalid deal timestamp, deal timestamp should not be null or after current date",
                    "dealTimestamp");
        }
    }

    private void validateCurrency(String currency, String fieldName){
        if (Objects.isNull(currency) || isValidCurrencyCode(currency)){
            addError("Invalid currency ISO", fieldName);
        }
    }

    private boolean isValidCurrencyCode(String orderingCurrencyISO) {
        return Currency.getAvailableCurrencies().stream()
                .noneMatch(v -> v.getCurrencyCode().equals(orderingCurrencyISO));
    }

    private void validateUniqueId(String dealUniqueId) {
        if (Objects.isNull(dealUniqueId) || dealUniqueId.isBlank()){
            addError("deal unique id cannot be null", "dealUniqueId");
        }
    }

    private void addError(String message, String field){
        DealError error = new DealError(message, field);
        errors.add(error);
    }
}
