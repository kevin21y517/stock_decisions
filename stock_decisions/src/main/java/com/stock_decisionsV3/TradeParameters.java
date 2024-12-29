package com.stock_decisionsV3;

public class TradeParameters {
    private static TradeParameters instance;

    private String stockCode;
    private double totalAssets;
    private int stocksPerTransaction;
    private double delta;
    private double upperRoi;
    private double lowerRoi;

    // 私有化構造函數，防止外部直接實例化
    private TradeParameters() {}

    // 提供全局唯一的實例
    public static synchronized TradeParameters getInstance() {
        if (instance == null) {
            instance = new TradeParameters();
        }
        return instance;
    }

    // Getters and Setters
    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public double getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(double totalAssets) {
        this.totalAssets = totalAssets;
    }

    public int getStocksPerTransaction() {
        return stocksPerTransaction;
    }

    public void setStocksPerTransaction(int stocksPerTransaction) {
        this.stocksPerTransaction = stocksPerTransaction;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public double getUpperRoi() {
        return upperRoi;
    }

    public void setUpperRoi(double upperRoi) {
        this.upperRoi = upperRoi;
    }

    public double getLowerRoi() {
        return lowerRoi;
    }

    public void setLowerRoi(double lowerRoi) {
        this.lowerRoi = lowerRoi;
    }

    @Override
    public String toString() {
        return "TradeParameters : " +
               "stockCode=" + stockCode + '\n' +
               ", totalAssets=" + totalAssets +
               ", stocksPerTransaction=" + stocksPerTransaction +
               ", delta=" + delta +
               ", upperRoi=" + upperRoi +
               ", lowerRoi=" + lowerRoi;
    }
}
