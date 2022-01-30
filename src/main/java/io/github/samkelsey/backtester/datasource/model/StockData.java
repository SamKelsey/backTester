package io.github.samkelsey.backtester.datasource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * A class to represent the data that is returned from the DataSource class.
 * This is fed to algorithms to help with their decision making process.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StockData {

    @NonNull
    private float stockPrice;

    private String ticker;

}
