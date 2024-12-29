package com.stock_decisionsV3;

public class StockTradingApp {
    public static void main(String[] args) {
        DataMediator mediator = new DataMediatorImpl();
        StockTradingUI ui = new StockTradingUI(mediator);
        StockTradingServer tradingServer = StockTradingServer.getInstance(mediator);

        // 根據需求切換數據提供器
        StockDataProvider provider = StockDataProviderFactory.getDataProvider("local");
        tradingServer.setStockDataProvider(provider);

        TradeStrategy strategy = TradeStrategyFactory.createStrategy("buy_high");
        tradingServer.setTradeStrategy(strategy);
        // 啟動 UI

        ui.bindToServer(tradingServer, mediator);
        LogManagers.getInstance().log("Application started. Using provider: " + provider.getClass().getSimpleName());
        LogManagers.getInstance().log("Current strategy: " + strategy.getClass().getSimpleName());

    }
}
