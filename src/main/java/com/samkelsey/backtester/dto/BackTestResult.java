package com.samkelsey.backtester.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BackTestResult {

    private float startingEquity;
    private float finalEquity;

    public float getPercentageChange() {
        return ((finalEquity - startingEquity) / startingEquity) * 100;
    }

}
