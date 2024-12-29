package com.stock_decisionsV3;

import java.math.BigDecimal;
import java.math.RoundingMode;

class StockData {
    private final String stockCode;
    private final String timestamp;
    private final Double price;

    public StockData(String stockCode, String timestamp, Double price) {
        this.stockCode = stockCode;
        this.timestamp = timestamp;
        this.price = price != null ? BigDecimal.valueOf(price)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue() : null;
    }

    public String getStockCode() {
        return stockCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("Stock Code: %s, Timestamp: %s, Price: %s",
                stockCode, timestamp, price == null ? "No Price" : String.format("%.2f", price));
    }
}

