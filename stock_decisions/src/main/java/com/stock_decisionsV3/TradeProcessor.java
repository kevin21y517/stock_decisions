package com.stock_decisionsV3;

public class TradeProcessor {
    private TradeStrategy strategy;
    private DataMediator mediator;

    public TradeProcessor(DataMediator mediator) {
        this.mediator = mediator;
    }

    public void setStrategy(TradeStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean processTrade(TradeParameters params, StockData stockData, boolean SystemState) {
        if (strategy == null) {
            throw new IllegalStateException("TradeStrategy is not set.");
        }

        // 執行交易邏輯
        boolean shouldStop = strategy.execute(params, stockData, mediator, SystemState);

        // 返回是否需要停止
        return shouldStop;
    }
}