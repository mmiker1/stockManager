package com.stock.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.stock.bean.StockItem;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class AuxUtil {
	Logger logger = Logger.getLogger("AuxUtil");
	public String formatNumber2(double num){
		return String.format("%1$.2f", num);
        
	}
	
	public String formatNumber3(double num){
		return String.format("%1$.3f", num);
        
	}
	
	public StockItem getStockItem(Stock stock){
		StockItem item = new StockItem();
		
		try {
			BigDecimal price = stock.getQuote().getPrice();
		    BigDecimal change = stock.getQuote().getChangeInPercent();
		    
		    item.setSymbol (stock.getSymbol());
		    item.setName (stock.getName());
		    item.setCurrency (stock.getCurrency());
		    item.setPrice (price.doubleValue());
		    item.setChange (change.doubleValue());
		    
		} catch(Exception e){
			logger.error("Error getStockItem " + e.getMessage());
		}
		
		return item;
	}
	
	public StockItem lookupStockItem(String symb){
		StockItem item = new StockItem();
		try {
		    Stock stock = YahooFinance.get(symb);
		    if(stock != null){
		        BigDecimal price = stock.getQuote().getPrice();
		        BigDecimal change = stock.getQuote().getChangeInPercent();
		    
		        item.setSymbol (stock.getSymbol());
		        item.setName (stock.getName());
		        item.setCurrency (stock.getCurrency());
		        item.setPrice (price.doubleValue());
		        item.setChange (change.doubleValue());
		    }
		} catch(Exception e){
			logger.error("Error getStock " + e.getMessage());
		}
		
		return item;
	}
	
	public Map<String,StockItem> getStockItemList(List<String> symbList){
		Map<String,StockItem> items = new HashMap<String,StockItem>();
		try {
			String[] symbArray = symbList.toArray(new String[symbList.size()]);
			Map<String, Stock> stocks = YahooFinance.get(symbArray);
			for (String key : stocks.keySet()) {
				StockItem item = getStockItem(stocks.get(key));
				items.put(key, item);
			}
			
	    } catch(IOException e){
	    	logger.error("Error getStockList " + e.getMessage());
	    }
		
		return items;
	}
	
	public String filterDigits(String s){
    	if(s == null) s = "";
    	String ret = "";
    	for(int i=0;i < s.length();i++){
    		char c = s.charAt(i);
    		if (Character.isDigit(c))   		
        	{
        			ret += c;
        	}
    	}
    	
    	return "0" + ret;
    }

}
