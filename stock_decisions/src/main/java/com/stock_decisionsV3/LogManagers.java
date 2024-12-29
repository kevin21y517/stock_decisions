package com.stock_decisionsV3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LogManagers {
    // 單例實例
    private static LogManagers instance;

    // 用於存儲所有的日誌處理器
    private final List<Consumer<String>> logHandlers = new ArrayList<>();

    // 私有構造函數，防止外部實例化
    private LogManagers() {}

    // 靜態方法，用於獲取單例實例
    public static synchronized LogManagers getInstance() {
        if (instance == null) {
            instance = new LogManagers();
        }
        return instance;
    }

    // 註冊日誌處理器
    public void registerHandler(Consumer<String> handler) {
        logHandlers.add(handler);
    }

    // 取消註冊日誌處理器（防止內存洩漏）
    public void unregisterHandler(Consumer<String> handler) {
        logHandlers.remove(handler);
    }

    // 發送日誌
    public void log(String message) {
        for (Consumer<String> handler : logHandlers) {
            handler.accept(message);
        }
    }

    public void log(String format, Object... args) {
        String formattedMessage = String.format(format, args);
        log(formattedMessage);
    }
}
