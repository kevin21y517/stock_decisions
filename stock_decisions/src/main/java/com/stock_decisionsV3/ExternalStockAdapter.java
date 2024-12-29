package com.stock_decisionsV3;

public class ExternalStockAdapter implements StockDataProvider {
    private final ExternalStockAPI externalApi;

    public ExternalStockAdapter(ExternalStockAPI externalApi) {
        this.externalApi = externalApi;
    }

    @Override
    public StockData fetchStockData(String stockCode) {
        // 從第三方 API 獲取數據
        ExternalStockData externalData = externalApi.getStockData(stockCode);

        // 將 ExternalStockData 轉換為 StockData
        return new StockData(
            externalData.getSymbol(),
            externalData.getDate(),
            externalData.getPrice()
        );
    }
}
