package com.stock_decisionsV3;

public interface TradeStrategy {
    boolean execute(TradeParameters params, StockData stockData, DataMediator mediator, boolean systemState);
}
