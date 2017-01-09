package com.stock.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.stock.bean.PortfolioItem;
import com.stock.bean.StockItem;
import com.stock.dao.GeneralDAOImpl;
import com.stock.util.AuxUtil;

public class BatchSyncTask extends Thread {
	Logger logger = Logger.getLogger("BatchSyncTask");
	
	private AuxUtil auxUtil;
	private GeneralDAOImpl generalDao;
	
	public BatchSyncTask(AuxUtil auxUtil, GeneralDAOImpl generalDao){
		this.auxUtil = auxUtil;
		this.generalDao = generalDao;
	}
	
    public void run() {
		
		logger.info("task start. ");
		
		List<PortfolioItem> pitemList = generalDao.getPortfolioItemList();
		List<String> symbList = new ArrayList<String>();
		for(PortfolioItem pitem : pitemList){
			if(!pitem.getSymbol().equals("")) symbList.add(pitem.getSymbol());
			
		}
		if(symbList.size() > 0){
		    Map<String,StockItem> stockItems = auxUtil.getStockItemList (symbList);
		
		    for(PortfolioItem pitem : pitemList){
			    StockItem stockItem = stockItems.get(pitem.getSymbol());
			    generalDao.updatePortfolioItem(pitem.getId(), stockItem.getPrice(), stockItem.getChange());
		    }
		}
		
    }
	

}
