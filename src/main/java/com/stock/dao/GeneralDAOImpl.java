package com.stock.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.stock.bean.PortfolioItem;
import com.stock.bean.StockItem;
import com.stock.util.AuxUtil;


public class GeneralDAOImpl {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AuxUtil auxUtil;
	
	private DataSource dataSource;
	Logger logger = Logger.getLogger(GeneralDAOImpl.class.getName());
	
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public boolean checkSymbolExists(String symb){
		boolean ret = false;
		String query = "SELECT COUNT(*) FROM portfolio "
		             + "WHERE UPPER(symbol) = UPPER(?)";
	
	    int cc = jdbcTemplate.queryForObject(query, Integer.class, symb);
	    if(cc > 0) ret = true;	
	  
	    return ret;
    }
	
	
	public int getPortfolioCount(){
		String query = "SELECT COUNT(*) FROM portfolio";
		int cc = jdbcTemplate.queryForObject(query, Integer.class);
		
		return cc;
	}
	
	public void insertPortfolioItem(PortfolioItem item){
		String query = "INSERT INTO portfolio (stock_count, "
				                          + "price, "
				                          + "change, "
				                          + "symbol, "
				                          + "name, "
				                          + "currency, "
				                          + "updated) "
				                     + "VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
		
		jdbcTemplate.update(query, item.getCount(),
				                   item.getPrice(),
				                   item.getChange(),
				                   item.getSymbol(),
				                   item.getName(),
				                   item.getCurrency());
		
		
	}
	
	
	public List<PortfolioItem> getPortfolioItemList(){
		List<PortfolioItem> items = new ArrayList<PortfolioItem>();
		
		String query = "SELECT id, "
				            + "stock_count, "
				            + "price, "
				            + "change, "
				            + "symbol, "
				            + "name, "
				            + "currency, "
				            + "updated "
				     + "FROM portfolio "
				     + "ORDER BY symbol";
		
		List<Map<String, Object>> results = jdbcTemplate.queryForList(query);
		for (Map<String, Object> result : results) {
			PortfolioItem item = new PortfolioItem();
			int count = (Integer) result.get("stock_count");
			double price = ((BigDecimal)  result.get("price")).doubleValue();
			item.setId          ((Integer) result.get("id"));
			item.setCount       (count);
            item.setPrice       (price);
			item.setChange      (((BigDecimal)  result.get("change")).doubleValue());
			item.setSymbol      ((String) result.get("symbol"));
			item.setName        ((String) result.get("name"));
			item.setCurrency    ((String) result.get("currency"));
			item.setUpdated     ((Timestamp) result.get("updated"));
			item.setMarketValue (count * price);
			items.add(item);
		}
		
		return items;
	}
	
	public PortfolioItem getPortfolioItem(int id){
		PortfolioItem item = new PortfolioItem();
		
		String query = "SELECT id, "
	            + "stock_count, "
	            + "price, "
	            + "change, "
	            + "symbol, "
	            + "name, "
	            + "currency, "
	            + "updated "
	          + "FROM portfolio "
	          + "WHERE id = ?";
		
		List<Map<String, Object>> results = jdbcTemplate.queryForList(query, id);
		for (Map<String, Object> result : results) {
			int count = (Integer) result.get("stock_count");
			double price = ((BigDecimal)  result.get("price")).doubleValue();
			item.setId          ((Integer) result.get("cafe_id"));
			item.setCount       (count);
            item.setPrice       (price);
			item.setChange      (((BigDecimal)  result.get("change")).doubleValue());
			item.setSymbol      ((String) result.get("symbol"));
			item.setName        ((String) result.get("name"));
			item.setCurrency    ((String) result.get("currency"));
			item.setUpdated     ((Timestamp) result.get("updated"));
			item.setMarketValue (count * price);
		}
		
		return item;
	}
	
	public PortfolioItem getPortfolioItem(String symb){
		PortfolioItem item = new PortfolioItem();
		
		String query = "SELECT id, "
	            + "stock_count, "
	            + "price, "
	            + "change, "
	            + "symbol, "
	            + "name, "
	            + "currency, "
	            + "updated "
	          + "FROM portfolio "
	          + "WHERE UPPER(symbol) = UPPER(?)";
		
		List<Map<String, Object>> results = jdbcTemplate.queryForList(query, symb);
		for (Map<String, Object> result : results) {
			int count    = (Integer) result.get("stock_count");
			double price = ((BigDecimal)  result.get("price")).doubleValue();
			item.setId          ((Integer) result.get("id"));
			item.setCount       (count);
            item.setPrice       (price);
			item.setChange      (((BigDecimal)  result.get("change")).doubleValue());
			item.setSymbol      ((String) result.get("symbol"));
			item.setName        ((String) result.get("name"));
			item.setCurrency    ((String) result.get("currency"));
			item.setUpdated     ((Timestamp) result.get("updated"));
			item.setMarketValue (count * price);
		}
		
		return item;
	}
	
	
	public void updateStockCount(int id, int stockCount){
		String query = "UPDATE portfolio SET stock_count = ? WHERE id = ?";
		jdbcTemplate.update(query, stockCount, id);
	}
	
	public void updatePortfolioItem (int id, double price, double change){
		String query = "UPDATE portfolio SET price = ?, change = ? WHERE id = ?";
		jdbcTemplate.update(query, price, change, id);
	}
	
	public void deletePortfolioItem(int id){
		String query = "DELETE FROM portfolio WHERE id = ?";
		jdbcTemplate.update(query, id);
	}
	
	public void refreshPortfolio(){
		List<PortfolioItem> pitemList = getPortfolioItemList();
		List<String> symbList = new ArrayList<String>();
		for(PortfolioItem pitem : pitemList){
			if(!pitem.getSymbol().equals("")) symbList.add(pitem.getSymbol());
		}
		
		Map<String,StockItem> stockItems = auxUtil.getStockItemList (symbList);
		
		for(PortfolioItem pitem : pitemList){
			StockItem stockItem = stockItems.get(pitem.getSymbol());
			updatePortfolioItem(pitem.getId(), stockItem.getPrice(), stockItem.getChange());
		}
		
		
	}
	

}
