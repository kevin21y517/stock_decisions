package com.stock_decisionsV3;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class StockTradingUI {
    private final JFrame frame;
    private final JComboBox<String> stockCodeComboBox;
    private final JTextField totalAssetsField;
    private final JTextField stocksPerTransactionField;
    private final JTextField deltaField;
    private final JTextField upperRoiField;
    private final JTextField lowerRoiField;
    private final JTextArea logArea;
    private final JButton startButton;
    private final JButton stopButton;

    private final JLabel remainingAssetsLabelUI;
    private final JLabel investmentValueLabelUI;
    private final JLabel roiLabelUI;
    private final JLabel deltaCountLabelUI;
    private final JLabel currentPriceLabelUI;

    public StockTradingUI(DataMediator mediator) {

        LogManagers.getInstance().registerHandler(this::logMessage);

        frame = new JFrame("Stock Trading System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel stockCodeLabel = new JLabel("Stock Code:");
        String[] stockCodes = {"0050.TW", "006208.TW", "0056.TW", "00878.TW", "00919.TW", "2330.TW"};
        stockCodeComboBox = new JComboBox<>(stockCodes);

        JLabel totalAssetsLabel = new JLabel("Total Assets:");
        totalAssetsField = new JTextField();

        JLabel stocksPerTransactionLabel = new JLabel("Stocks per Transaction:");
        stocksPerTransactionField = new JTextField();

        JLabel deltaLabel = new JLabel("Price Delta (Δ)%:");
        deltaField = new JTextField();

        JLabel upperRoiLabel = new JLabel("Upper ROI Limit (%):");
        upperRoiField = new JTextField();

        JLabel lowerRoiLabel = new JLabel("Lower ROI Limit (%):");
        lowerRoiField = new JTextField();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        inputPanel.add(stockCodeLabel);
        inputPanel.add(stockCodeComboBox);
        inputPanel.add(totalAssetsLabel);
        inputPanel.add(totalAssetsField);
        inputPanel.add(stocksPerTransactionLabel);
        inputPanel.add(stocksPerTransactionField);
        inputPanel.add(deltaLabel);
        inputPanel.add(deltaField);
        inputPanel.add(upperRoiLabel);
        inputPanel.add(upperRoiField);
        inputPanel.add(lowerRoiLabel);
        inputPanel.add(lowerRoiField);

        inputPanel.add(new JLabel()); // 空白佔位
        inputPanel.add(buttonPanel); // 按鈕面板添加到最後一行

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);
        logScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 新增 currentPriceLabelUI 並將其放置在 logArea 的上方
        JPanel centerPanel = new JPanel(new BorderLayout());
        currentPriceLabelUI = new JLabel("Current Price: ---", SwingConstants.CENTER);
        centerPanel.add(currentPriceLabelUI, BorderLayout.NORTH);
        centerPanel.add(logScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        remainingAssetsLabelUI = new JLabel("Remaining Assets: 0.00", SwingConstants.CENTER);
        investmentValueLabelUI = new JLabel("Investment Value: 0.00", SwingConstants.CENTER);
        roiLabelUI = new JLabel("ROI: 0.00%", SwingConstants.CENTER);
        deltaCountLabelUI = new JLabel("Δ Count: 0", SwingConstants.CENTER);

        bottomPanel.add(remainingAssetsLabelUI);
        bottomPanel.add(investmentValueLabelUI);
        bottomPanel.add(roiLabelUI);
        bottomPanel.add(deltaCountLabelUI);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        TradeParameters params = TradeParameters.getInstance();

        startButton.addActionListener(e -> {

            params.setStockCode((String) stockCodeComboBox.getSelectedItem());
            params.setTotalAssets(Double.parseDouble(totalAssetsField.getText()));
            params.setStocksPerTransaction(Integer.parseInt(stocksPerTransactionField.getText()));
            params.setDelta(Double.parseDouble(deltaField.getText()));
            params.setUpperRoi(Double.parseDouble(upperRoiField.getText()));
            params.setLowerRoi(Double.parseDouble(lowerRoiField.getText()));

            // 傳遞給業務邏輯層
            StockTradingServer tradingServer = StockTradingServer.getInstance(mediator);
            tradingServer.start(params);

            logMessage("Start trading with parameters: " + params.toString());
        });

        stopButton.addActionListener(e -> {
            StockTradingServer tradingServer = StockTradingServer.getInstance(mediator);
            tradingServer.stop(params);
        });

        frame.setVisible(true);
    }

    // public void logMessage(String message) {
    //     logArea.append(message + "\n");
    // }

    public void logMessage(String message) {
        SwingUtilities.invokeLater(() -> logArea.append(message + "\n"));
    }

    public void bindToServer(StockTradingServer server, DataMediator mediator) {
        server.setUIUpdateCallback(stockData -> {
            SwingUtilities.invokeLater(() -> {
                currentPriceLabelUI.setText("[" + stockData.getTimestamp() + "]Current Price: " + String.format("%.2f", stockData.getPrice()));
                // 根據數據更新其他UI元素
                remainingAssetsLabelUI.setText("Remaining Assets: " + String.format("%.2f", mediator.getCurrentAssets()));
                investmentValueLabelUI.setText("Investment Value: " + String.format("%.2f", stockData.getPrice() * mediator.getStockOwned()));
                roiLabelUI.setText("ROI: " + String.format("%.2f", mediator.getRoi()) + "%");
                deltaCountLabelUI.setText("Δ Count: " + mediator.getDeltaCount());

            });
        });
    }

}
