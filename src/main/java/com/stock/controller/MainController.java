package com.stock.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.bean.PortfolioItem;
import com.stock.bean.StockItem;
import com.stock.dao.GeneralDAOImpl;
import com.stock.util.AuxUtil;


@Controller
public class MainController {
	Logger logger = Logger.getLogger(MainController.class.getName());
	
	@Autowired
	private AuxUtil auxUtil;
	
	@Autowired
	private GeneralDAOImpl generalDao;
	
	
	@RequestMapping(value = "portfolioFunctions.ajax", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String portfolioFunctions(
			HttpServletRequest request, 
    		HttpServletResponse response,
    		@RequestParam(value = "command", required = false, defaultValue = "") String command,
    		@RequestParam(value = "symbol", required = false, defaultValue = "") String symbol,
    		@RequestParam(value = "count", required = false, defaultValue = "") String count,
    		@RequestParam(value = "id", required = false, defaultValue = "") String id
			){
		
		JSONObject jsonOut = new JSONObject();
		if(command.equals("add")){
		    count = auxUtil.filterDigits(count);
		    try{
		       StockItem  stockItem = auxUtil.lookupStockItem(symbol);
		       if(stockItem != null && !stockItem.getSymbol().equals("") ){
		           PortfolioItem item = new PortfolioItem();
		           item.setCount(Integer.parseInt(count));
		           item.setSymbol(stockItem.getSymbol());
		           item.setName(stockItem.getName());
		           item.setCurrency(stockItem.getCurrency());
		           item.setPrice(stockItem.getPrice());
		           item.setChange(stockItem.getChange());
		           if(!generalDao.checkSymbolExists(symbol)){
		               generalDao.insertPortfolioItem(item);
		               jsonOut.put("status", "OK");
		               jsonOut.put("error", "");
		           } else {
		        	   jsonOut.put("status", "Error");
		               jsonOut.put("error", "Error. Symbol already exists in the portfolio.");
		           }
		       }
		    } catch(Exception e){
		    	jsonOut.put("status", "Error");
	               jsonOut.put("error", "Error occurred.");
		    }
		} else if(command.equals("remove")){
			id = auxUtil.filterDigits(id);
			generalDao.deletePortfolioItem(Integer.parseInt(id));
			jsonOut.put("status", "OK");
            jsonOut.put("error", "");
		}
		
		
		
		
		return jsonOut.toString();
	}
	
	
	@RequestMapping(value = "getPortfolioItems.ajax", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getPortfolioItems(
			HttpServletRequest request, 
    		HttpServletResponse response
			){
		JSONObject jsonOut = new JSONObject();
		
		List<PortfolioItem> items = generalDao.getPortfolioItemList();
		JSONArray array = new JSONArray();
		try {
			
		    for(PortfolioItem item : items){
		    	JSONObject itemJSON = new JSONObject();
		    	itemJSON.put("id", item.getId());
		    	itemJSON.put("symbol", item.getSymbol());
		    	itemJSON.put("name", item.getName());
		    	itemJSON.put("currency", item.getCurrency());
		    	itemJSON.put("price", auxUtil.formatNumber2(item.getPrice()));
		    	itemJSON.put("change", auxUtil.formatNumber3(item.getChange()));
		    	itemJSON.put("marketValue", auxUtil.formatNumber2(item.getMarketValue()));
		    	itemJSON.put("count", item.getCount());
		    	
		    	if(item.getChange() > 0){
		    		itemJSON.put("up", true);
		    	} else {
		    		itemJSON.put("up", false);
		    	}
		    	array.put(itemJSON);
		    }
		    
		    jsonOut.put("items", array);
		    jsonOut.put("itemsCount", items.size());
        } catch(Exception e){
			
		}
		
		return jsonOut.toString();
	}
	
	
	@RequestMapping(value = "searchSymbol.ajax", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String searchSymbol(
			HttpServletRequest request, 
    		HttpServletResponse response,
			@RequestParam(value = "symbol", required = false, defaultValue = "") String symbol
			){
		
		JSONObject jsonOut = new JSONObject();
		StockItem  stockItem = auxUtil.lookupStockItem(symbol);
		int itemsCount = generalDao.getPortfolioCount();
		
		try {
			jsonOut.put("symbol", stockItem.getSymbol());
			jsonOut.put("name", stockItem.getName());
			jsonOut.put("currency", stockItem.getCurrency());
			jsonOut.put("price", auxUtil.formatNumber2(stockItem.getPrice()));
			jsonOut.put("change", auxUtil.formatNumber3(stockItem.getChange()));
			jsonOut.put("itemsCount", itemsCount);
			if(stockItem.getChange() > 0){
				jsonOut.put("up", true);
			} else {
				jsonOut.put("up", false);
			}
			
		} catch(Exception e){
			
		}
		
		return jsonOut.toString();
		
    }

}
