package com.stock.test.db;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.stock.bean.PortfolioItem;
import com.stock.dao.GeneralDAOImpl;


@ContextConfiguration(locations = "classpath:system-servlet-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestStockHSQL {
	
	
	@Autowired
	private GeneralDAOImpl generalDao;
	
	@Before
	public void testPrepareHSQLDB(){
		PortfolioItem item = new PortfolioItem();
		item.setCount(5);
		item.setSymbol("AA");
		item.setName("American Airlines");
		item.setCurrency("USD");
		item.setPrice(56.43);
		item.setChange(0.23);
		generalDao.insertPortfolioItem(item);
	}
	
	@Test
	public void testHSQLDB(){
		Assert.assertEquals(1, generalDao.getPortfolioCount());
	}

}
