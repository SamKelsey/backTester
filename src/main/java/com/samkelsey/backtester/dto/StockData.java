package com.samkelsey.backtester.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * A class to represent the data that is returned from the DataSource class.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StockData {

    @NonNull
    private float stockPrice;

    private String ticker;

}
