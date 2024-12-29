package com.stock_decisionsV3;

public interface DataMediator {
    // 更新 CalculationParameters 的數據
    void updateCurrentAssets(double currentAssets);
    void updateInvestmentCost(double investmentCost);
    void updateInvestmentProfit(double investmentProfit);
    void updateStockOwned(int stockOwned);
    void updateConsecutiveBuys(int consecutiveBuys);
    void updateDeltaCount(int deltaCount);
    void updateRoi(double roi);

    // 獲取 CalculationParameters 的數據
    double getCurrentAssets();
    double getInvestmentCost();
    double getInvestmentProfit();
    int getStockOwned();
    int getConsecutiveBuys();
    int getDeltaCount();
    double getRoi();
}
