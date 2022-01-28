package io.github.samkelsey.backtester;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BackTestResult {

    private float startingEquity;
    private float finalEquity;
//    private Map<String, float> percentageChanges;

    public float getTotalPercentageChange() {
        return ((finalEquity - startingEquity) / startingEquity) * 100;
    }

    // Returns the percentage change for the requested ticker
    public float getPercentageChange(String ticker) {
        return 1f;
    }

}
