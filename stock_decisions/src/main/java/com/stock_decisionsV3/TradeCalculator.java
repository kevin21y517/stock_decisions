package com.stock_decisionsV3;

public class TradeCalculator {

    // 計算當前 ROI
    public static double calculateROI(double stockValue, double investmentProfit, double investmentCost) {
        if(investmentCost == 0) {
            return 0;
        }
        return ((stockValue + investmentProfit) - investmentCost) / investmentCost * 100;
    }


    // 模擬買入操作
    public static double buy(double stockPrice, int stocksPerTransaction,double currentAssets) {
        double totalCost = stockValue(stocksPerTransaction, stockPrice);
        currentAssets = currentAssets - totalCost;

        System.out.println("Bought " + stocksPerTransaction + " stocks at price " + stockPrice +
                           ". Total assets: " + currentAssets);
        return currentAssets;


    }

    // 模擬賣出操作
    public static double sell(double stockPrice, int stockOwned, double currentAssets) {
        double totalProfit = stockValue(stockOwned, stockPrice);
        currentAssets = currentAssets + totalProfit;

        System.out.println("Sold " + stockOwned + " stocks at price " + stockPrice +
                           ". Total assets: " + currentAssets);
        return currentAssets;
    }

    public static double stockValue(int stockOwned, double stockPrice) {
        return stockOwned * stockPrice;
    }

    public static int calculateDelta(int consecutiveBuys) {
        int deltaSum = 0;
        for (int k = 1; k < consecutiveBuys; k++) {
            deltaSum += (consecutiveBuys - k);
        }
        deltaSum -= 1;
        return deltaSum;
    }
}
