package com.lomari.clustereddatawarehouse.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "deals")
@Builder
@Entity
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, name = "deal_id")
    private String dealUniqueId;

    @Column(nullable = false, name = "ordering_currency")
    private Currency orderingCurrencyISO;

    @Column(nullable = false, name = "to_currency")
    private Currency toCurrencyISO;

    @Column(nullable = false, name = "deal_timestamp")
    private Instant dealTimestamp;

    @Column(nullable = false, name = "amount_in_ordering_currency")
    private BigDecimal amountInOrderingCurrency;
}
