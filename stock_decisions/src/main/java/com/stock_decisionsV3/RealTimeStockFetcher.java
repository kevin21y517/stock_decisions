package com.stock_decisionsV3;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class RealTimeStockFetcher implements StockDataProvider {
    private final String pythonScriptPath;

    public RealTimeStockFetcher(String pythonScriptPath) {
        this.pythonScriptPath = pythonScriptPath;
    }

    @Override
    public StockData fetchStockData(String stockCode) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, stockCode);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                String jsonData = output.toString();
                JSONObject jsonObject = new JSONObject(jsonData);

                if (jsonObject.has("error")) {
                    throw new RuntimeException("Python script error: " + jsonObject.getString("error"));
                } else {
                    String stockCodeResponse = jsonObject.getString("stock_code");
                    Double currentPrice = jsonObject.optDouble("current_price", Double.NaN);

                    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

                    return new StockData(stockCodeResponse, timestamp, Double.isNaN(currentPrice) ? null : currentPrice);
                }
            } else {
                throw new RuntimeException("Python script execution failed with exit code: " + exitCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch stock data: " + e.getMessage(), e);
        }
    }
}