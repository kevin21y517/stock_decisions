package com.stock_decisionsV3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class LocalStockFetcher implements StockDataProvider {
    private final Map<String, List<Double>> stockData;
    private final List<String> dates;
    private int currentIndex;

    public LocalStockFetcher(String filePath) {
        stockData = new HashMap<>();
        dates = new ArrayList<>();
        currentIndex = 0;
        loadStockData(filePath);
    }

    private void loadStockData(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            String[] headers = line.split(",");

            for (int i = 1; i < headers.length; i++) {
                stockData.put(headers[i].trim(), new ArrayList<>());
            }

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                dates.add(values[0].trim());
                for (int i = 1; i < values.length; i++) {
                    String stockCode = headers[i].trim();
                    if (!values[i].trim().isEmpty()) {
                        stockData.get(stockCode).add(Double.parseDouble(values[i].trim()));
                    } else {
                        stockData.get(stockCode).add(null);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load stock data from CSV: " + e.getMessage(), e);
        }
    }

    @Override
    public StockData fetchStockData(String stockCode) {
        List<Double> prices = stockData.get(stockCode);
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("Stock code not found or no data available: " + stockCode);
        }

        // 不斷嘗試取得下一筆非 null 的價格
        Double price = null;
        while (currentIndex < prices.size()) {
            price = prices.get(currentIndex);
            if (price == null) {
                // 如果當前價格為 null，記錄「無資料」訊息並嘗試下一筆
                System.out.println(String.format("[%s] 無資料，嘗試下一筆", dates.get(currentIndex)));
                currentIndex++;
            } else {
                break;
            }
        }

        // 如果已經讀到底，且仍未取得有效價格
        if (currentIndex >= prices.size()) {
            // 已無更多資料，返回最後一筆或直接拋出異常
            // 這裡選擇返回最後一筆記錄（可能為 null）並讓上層決定怎麼做
            currentIndex = prices.size() - 1;
            price = prices.get(currentIndex);
            System.out.println("已無更多有效資料。");
        }

        String date = dates.get(currentIndex);
        currentIndex++;
        return new StockData(stockCode, date, price);
    }

    public void resetIndex() {
        currentIndex = 0;
    }
}
