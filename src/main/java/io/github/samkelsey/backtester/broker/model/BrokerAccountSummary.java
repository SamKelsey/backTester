package io.github.samkelsey.backtester.broker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * A data class to summarise the current state of the broker account.
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class BrokerAccountSummary {

    @NonNull
    private Map<String, BrokerStockData> portfolio;

    @NonNull
    private float cash;

    @NonNull
    private float startingBalance;

    private float totalEquity;
}
