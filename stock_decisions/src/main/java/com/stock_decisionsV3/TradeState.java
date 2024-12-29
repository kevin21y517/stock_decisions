package com.stock_decisionsV3;

public class TradeState {
    private double currentAssets; // 現金
    private double investmentCost; // 投資成本
    private double investmentProfit; // 投資收益
    private int stockOwned; // 持有股票數量
    private int consecutiveBuys; // 連續買入次數
    private int deltaCount; // Delta 總和
    private double roi; // ROI

    public double getCurrentAssets() {
        return currentAssets;
    }

    public void setCurrentAssets(double currentAssets) {
        this.currentAssets = currentAssets;
    }

    public double getInvestmentCost() {
        return investmentCost;
    }

    public void setInvestmentCost(double investmentCost) {
        this.investmentCost = investmentCost;
    }

    public double getInvestmentProfit() {
        return investmentProfit;
    }

    public void setInvestmentProfit(double investmentProfit) {
        this.investmentProfit = investmentProfit;
    }

    public int getStockOwned() {
        return stockOwned;
    }

    public void setStockOwned(int stockOwned) {
        this.stockOwned = stockOwned;
    }

    public int getConsecutiveBuys() {
        return consecutiveBuys;
    }

    public void setConsecutiveBuys(int consecutiveBuys) {
        this.consecutiveBuys = consecutiveBuys;
    }

    public int getDeltaCount() {
        return deltaCount;
    }

    public void setDeltaCount(int deltaCount) {
        this.deltaCount = deltaCount;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }
}
