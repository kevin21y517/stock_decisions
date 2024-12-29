package com.stock_decisionsV3;

import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.Timer;

public class StockTradingServer {
    private static StockTradingServer instance;
    private StockDataProvider stockDataProvider;
    private final TradeProcessor tradeProcessor;
    private Timer timer;
    private final DataMediator mediator;
    private boolean systemState;
    private StockData stockData;
    private Consumer<StockData> uiUpdateCallback; // UI 更新回調

    private StockTradingServer(DataMediator mediator) {
        // 初始化服務配置，例如連接數據庫或API
        this.mediator = mediator;
        tradeProcessor = new TradeProcessor(mediator);
        mediator.updateConsecutiveBuys(0);
        mediator.updateCurrentAssets(0);
        mediator.updateDeltaCount(0);
        mediator.updateInvestmentCost(0);
        mediator.updateInvestmentProfit(0);
        mediator.updateRoi(0);
        mediator.updateStockOwned(0);
        this.systemState = false;
    }

    public static synchronized StockTradingServer getInstance(DataMediator mediator) {
        if (instance == null) {
            instance = new StockTradingServer(mediator);
        }
        return instance;
    }

    public void setStockDataProvider(StockDataProvider provider) {
        this.stockDataProvider = provider;
    }

    public void setTradeStrategy(TradeStrategy strategy) {
        tradeProcessor.setStrategy(strategy);
    }

    public void setUIUpdateCallback(Consumer<StockData> callback) {
    this.uiUpdateCallback = callback;
    }

    public void start(TradeParameters params) {
        if (stockDataProvider == null) {
            throw new IllegalStateException("StockDataProvider is not set.");
        }
        mediator.updateCurrentAssets(params.getTotalAssets());
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                systemState = true;
                // 獲取股價數據
                stockData = stockDataProvider.fetchStockData(params.getStockCode());
                boolean shouldStop = tradeProcessor.processTrade(params, stockData, systemState);
                // 更新 UI
                if (uiUpdateCallback != null) {
                    uiUpdateCallback.accept(stockData);
                }
                if (shouldStop) {
                    stop(params);
                }
            }
        }, 0, 1000);
    }

    public void stop(TradeParameters params) {
        systemState = false;
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
        tradeProcessor.processTrade(params, stockData, systemState);
        if (uiUpdateCallback != null) {
            uiUpdateCallback.accept(stockData);
        }
        // 關閉服務，釋放資源
        LogManagers.getInstance().log("StockTradingServer stopped." );
    }

}
