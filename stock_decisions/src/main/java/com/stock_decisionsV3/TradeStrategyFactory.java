package com.stock_decisionsV3;

public class TradeStrategyFactory {
    public static TradeStrategy createStrategy(String type) {
        switch (type.toLowerCase()) {
            case "buy_high":
                return new DeltaBasedTradingStrategy();
            case "buy_low":
                return new BuyLowSellHighStrategy();
            default:
                throw new IllegalArgumentException("Unknown strategy type: " + type);
        }
    }
}
