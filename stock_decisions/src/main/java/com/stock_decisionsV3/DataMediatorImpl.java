package com.stock_decisionsV3;

public class DataMediatorImpl implements DataMediator {
    private TradeState state;

    public DataMediatorImpl() {
        this.state = new TradeState();
    }

    @Override
    public double getCurrentAssets() {
        return state.getCurrentAssets();
    }

    @Override
    public double getInvestmentCost() {
        return state.getInvestmentCost();
    }

    @Override
    public double getInvestmentProfit() {
        return state.getInvestmentProfit();
    }

    @Override
    public int getStockOwned() {
        return state.getStockOwned();
    }

    @Override
    public int getConsecutiveBuys() {
        return state.getConsecutiveBuys();
    }

    @Override
    public int getDeltaCount() {
        return state.getDeltaCount();
    }

    @Override
    public double getRoi() {
        return state.getRoi();
    }

    @Override
    public void updateCurrentAssets(double currentAssets) {
        state.setCurrentAssets(currentAssets);
    }

    @Override
    public void updateInvestmentCost(double investmentCost) {
        state.setInvestmentCost(investmentCost);
    }

    @Override
    public void updateInvestmentProfit(double investmentProfit) {
        state.setInvestmentProfit(investmentProfit);
    }

    @Override
    public void updateStockOwned(int stockOwned) {
        state.setStockOwned(stockOwned);
    }

    @Override
    public void updateConsecutiveBuys(int consecutiveBuys) {
        state.setConsecutiveBuys(consecutiveBuys);
    }

    @Override
    public void updateDeltaCount(int deltaCount) {
        state.setDeltaCount(deltaCount);
    }

    @Override
    public void updateRoi(double roi) {
        state.setRoi(roi);
    }
}
