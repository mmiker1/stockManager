package com.stock.bean;

import java.sql.Timestamp;

public class PortfolioItem {
	int id;
	int count;
	String symbol;
	String name;
	String currency;
	double price;
    double change;
	double marketValue;
	Timestamp updated;
	
	public PortfolioItem(){
		this.id             = 0;
		this.count          = 1;
		this.symbol         = "";
		this.name           = "";
		this.currency       = "";
		this.price          = 0;
		this.change         = 0;
		this.marketValue    = 0;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getChange() {
		return change;
	}

	public void setChange(double change) {
		this.change = change;
	}

	public double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}

	public Timestamp getUpdated() {
		return updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}

	
	
	

}
