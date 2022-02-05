package io.github.samkelsey.backtester.datasource.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * A class to represent the data that is returned from the DataSource class.
 * This is fed to algorithms to help with their decision making process.
 */
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class StockData {

    @NonNull
    private float stockPrice;

    private String ticker;

}
