package com.lomari.clustereddatawarehouse.service.validator;

import com.lomari.clustereddatawarehouse.dto.DealRequestDto;
import com.lomari.clustereddatawarehouse.models.Deal;
import com.lomari.clustereddatawarehouse.repository.DealsRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
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
            validateOrderingCurrency(dealRequestDto.getOrderingCurrencyISO());
            validateToCurrency(dealRequestDto.getToCurrencyISO());
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
                amountInOrderingCurrency.compareTo(BigDecimal.ZERO) == 0 ||
                amountInOrderingCurrency.compareTo(BigDecimal.ZERO) < 0
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

    private void validateToCurrency(String toCurrencyISO) {
        if (Objects.isNull(toCurrencyISO) || notValidCurrencyCode(toCurrencyISO)){
            addError("Invalid currency ISO", "orderingCurrencyISO");
        }
    }

    private void validateOrderingCurrency(String orderingCurrencyISO) {
        if (Objects.isNull(orderingCurrencyISO) || notValidCurrencyCode(orderingCurrencyISO)){
            addError("Invalid currency ISO", "orderingCurrencyISO");
        }
    }

    private boolean notValidCurrencyCode(String orderingCurrencyISO) {
        return Currency.getAvailableCurrencies().stream()
                .noneMatch(v -> v.getCurrencyCode().equals(orderingCurrencyISO));
    }

    private void validateUniqueId(String dealUniqueId) {
        if (Objects.isNull(dealUniqueId) || dealUniqueId.isEmpty()){
            addError("deal unique id cannot be null", "dealUniqueId");
        }
    }

    private void addError(String message, String field){
        DealError error = new DealError();
        error.setErrorMessage(message);
        error.setRejectedField(field);
        errors.add(error);
    }
}
