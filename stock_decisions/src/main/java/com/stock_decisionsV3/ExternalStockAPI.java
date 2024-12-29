package com.stock_decisionsV3;

// 模擬的第三方 API 類
public class ExternalStockAPI {
    private String apiKey;

    public ExternalStockAPI(String apiKey) {
        this.apiKey = apiKey;
    }

    // 模擬獲取股票數據的方法
    public ExternalStockData getStockData(String stockCode) {
        // 假設返回的數據格式如下：
        return new ExternalStockData(stockCode, "2024-12-28", 150.25);
    }
}

// 第三方返回的數據格式
class ExternalStockData {
    private String symbol;
    private String date;
    private double price;

    public ExternalStockData(String symbol, String date, double price) {
        this.symbol = symbol;
        this.date = date;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }
}
