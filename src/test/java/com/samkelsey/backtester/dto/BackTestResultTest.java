package com.samkelsey.backtester.dto;

import io.github.samkelsey.backtester.dto.BackTestResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BackTestResultTest {

    @Test
    void shouldReturnFloat_whenGetPercentageChange() {
        BackTestResult result = new BackTestResult(
                1000,
                1200
        );

        assertEquals(20f, result.getPercentageChange());
    }
}
