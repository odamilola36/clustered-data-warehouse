package com.lomari.clustereddatawarehouse.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String dealUniqueId;

    private Currency orderingCurrencyISO;

    private Currency toCurrencyISO;

    private Instant dealTimestamp;

    private BigDecimal amountInOrderingCurrency;
}
