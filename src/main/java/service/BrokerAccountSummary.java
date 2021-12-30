package service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * A data class to summarise the current state of the broker account.
 */
@Data
@AllArgsConstructor
public class BrokerAccountSummary {

    private Map<String, Integer> portfolio;
    private float cash;

}
