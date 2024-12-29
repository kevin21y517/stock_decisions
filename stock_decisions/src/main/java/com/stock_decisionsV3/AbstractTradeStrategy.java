package com.stock_decisionsV3;

public abstract class AbstractTradeStrategy implements TradeStrategy {
    @Override
    public final boolean execute(TradeParameters params, StockData stockData, DataMediator mediator, boolean systemState) {
        if (stockData == null) {
            System.out.println("No valid stock data available.");
            return false;
        }



        beforeTrade(stockData, params, mediator, systemState);

        if (shouldBuy(stockData, params, mediator)) {
            performBuy(stockData, params, mediator);
        } else if (shouldSell(stockData, params, mediator, systemState)) {
            performSell(stockData, params, mediator);
        }
        performroi(stockData, params, mediator);

        afterTrade(stockData, params, mediator);

        return shouldStopTrading(mediator, params);
    }

    // 可選擇性覆蓋的方法
    protected void beforeTrade(StockData stockData, TradeParameters params, DataMediator mediator, boolean systemState) {
        // 預留供具體策略實現額外操作
    }

    protected void afterTrade(StockData stockData, TradeParameters params, DataMediator mediator) {
        // 預留供具體策略實現額外操作
    }

    protected abstract boolean shouldBuy(StockData stockData, TradeParameters params, DataMediator mediator);
    protected abstract boolean shouldSell(StockData stockData, TradeParameters params, DataMediator mediator, boolean systemState);
    protected abstract void performBuy(StockData stockData, TradeParameters params, DataMediator mediator);
    protected abstract void performSell(StockData stockData, TradeParameters params, DataMediator mediator);
    protected abstract void performroi(StockData stockData, TradeParameters params, DataMediator mediator);


    protected boolean shouldStopTrading(DataMediator mediator, TradeParameters params) {
        double roi = mediator.getRoi();
        return roi >= params.getUpperRoi() || roi <= -params.getLowerRoi();
    }
}
