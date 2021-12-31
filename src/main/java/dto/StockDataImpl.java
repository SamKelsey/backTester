package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockDataImpl implements StockData {

    private String ticker;
    private float stockPrice;

}
