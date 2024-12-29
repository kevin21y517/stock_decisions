# stock_data_provider.py

import yfinance as yf
import sys
import json

def get_stock_data(stock_code):
    try:
        # 使用 yfinance 抓取股票数据
        stock = yf.Ticker(stock_code)
        stock_info = stock.history(period="1d")
        # 获取最新价格
        if not stock_info.empty:
            current_price = stock_info['Close'].iloc[-1]
            return {"stock_code": stock_code, "current_price": current_price}
        else:
            return {"error": f"No data found for stock: {stock_code}"}
    except Exception as e:
        return {"error": str(e)}

if __name__ == "__main__":
    # 从 Java 传入的参数（股票代码）
    if len(sys.argv) > 1:
        stock_code = sys.argv[1]
        result = get_stock_data(stock_code)
        print(json.dumps(result))  # 输出 JSON 格式数据
    else:
        print(json.dumps({"error": "No stock code provided"}))
