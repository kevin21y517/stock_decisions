package com.stock_decisionsV3;

public class StockDataProviderFactory {
    public static StockDataProvider getDataProvider(String type) {
        switch (type.toLowerCase()) {
            case "local":
                return new LocalStockFetcher("stock_data.csv");
            case "realtime":
                return new RealTimeStockFetcher("stock_data_provider.py");
            case "external":
                ExternalStockAPI api = new ExternalStockAPI("api_key");
                return new ExternalStockAdapter(api);
            default:
                throw new IllegalArgumentException("Unknown data provider type: " + type);
        }
    }
}