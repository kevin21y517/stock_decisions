package com.stock_decisionsV3;

public class DeltaBasedTradingStrategy extends AbstractTradeStrategy {
    private double buyPrice = 0.0;

    @Override
    protected boolean shouldBuy(StockData stockData, TradeParameters params, DataMediator mediator) {
        double buyValue = buyPrice * (1 + params.getDelta() / 100);
        return stockData.getPrice() >= buyValue || mediator.getStockOwned() == 0;
    }

    @Override
    protected boolean shouldSell(StockData stockData, TradeParameters params, DataMediator mediator, boolean systemState) {
        double sellValue = buyPrice * (1 - params.getDelta() / 100);
        return stockData.getPrice() <= sellValue || !systemState;
    }

    @Override
    protected void performBuy(StockData stockData, TradeParameters params, DataMediator mediator) {
        int stocksPerTransaction = params.getStocksPerTransaction();
        double currentAssets = mediator.getCurrentAssets();
        double stockPrice = stockData.getPrice();
        double stockValue = TradeCalculator.stockValue(stocksPerTransaction, stockPrice);

        mediator.updateCurrentAssets(TradeCalculator.buy(stockPrice, stocksPerTransaction, currentAssets));
        mediator.updateInvestmentCost(mediator.getInvestmentCost() + stockValue);
        mediator.updateStockOwned(mediator.getStockOwned() + stocksPerTransaction);
        mediator.updateConsecutiveBuys(mediator.getConsecutiveBuys() + 1);
        buyPrice = stockData.getPrice();

        // 日誌記錄，正確顯示時間
        LogManagers.getInstance().log(
            String.format("[%s] 買入 %d 股於 %.2f | 成本：%.2f | 連續買入次數：%d",
                stockData.getTimestamp(), // 填入正確的時間
                stocksPerTransaction,
                stockPrice,
                stockPrice * stocksPerTransaction,
                mediator.getConsecutiveBuys()
            )
        );
    }

    @Override
    protected void performSell(StockData stockData, TradeParameters params, DataMediator mediator) {
        int stockOwned = mediator.getStockOwned();
        double currentAssets = mediator.getCurrentAssets();
        double stockPrice = stockData.getPrice();
        double stockValue = TradeCalculator.stockValue(stockOwned, stockPrice);
        int consecutiveBuys = mediator.getConsecutiveBuys();
        int deltaSum = TradeCalculator.calculateDelta(consecutiveBuys);

        mediator.updateCurrentAssets(TradeCalculator.sell(stockPrice, stockOwned, currentAssets));
        mediator.updateInvestmentProfit(mediator.getInvestmentProfit() + stockValue);
        mediator.updateDeltaCount(mediator.getDeltaCount()+deltaSum);
        mediator.updateStockOwned(0);
        buyPrice = 0.0;
        mediator.updateConsecutiveBuys(0);
        // 日誌記錄，正確顯示時間
        LogManagers.getInstance().log(
            String.format("[%s] 賣出全部股票 [%d] 於 %.2f | 收益：%.2f | Δ: %d | 累計Δ: %d",
                stockData.getTimestamp(), // 填入正確的時間
                stockOwned,
                stockPrice,
                stockPrice * stockOwned,
                deltaSum,
                mediator.getDeltaCount()
            )
        );
    }

    @Override
    protected void performroi(StockData stockData, TradeParameters params, DataMediator mediator) {
        double stockValue = TradeCalculator.stockValue(mediator.getStockOwned(), stockData.getPrice());
        double roi = TradeCalculator.calculateROI(stockValue, mediator.getInvestmentProfit(), mediator.getInvestmentCost());
        mediator.updateRoi(roi);
    }


    @Override
    protected void beforeTrade(StockData stockData, TradeParameters params, DataMediator mediator, boolean systemState) {
        // System.out.println("buyprice: " + buyPrice);
    }

    @Override
    protected void afterTrade(StockData stockData, TradeParameters params, DataMediator mediator) {
        // System.out.println("investmentProfit: " + mediator.getInvestmentProfit());
        // System.out.println("111111111111111111111111111: " + mediator.getRoi());
    }
}
