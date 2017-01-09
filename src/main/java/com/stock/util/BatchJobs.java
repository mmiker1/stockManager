package com.stock.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.stock.dao.GeneralDAOImpl;
import com.stock.service.BatchSyncTask;

@EnableScheduling
public class BatchJobs {
	Logger logger = Logger.getLogger(BatchJobs.class.getName());
	@Autowired
	private AuxUtil auxUtil;
	
	@Autowired
	private GeneralDAOImpl generalDao;
	
	@Scheduled(fixedRate = 5000)
    public void refreshStockStart()
    {
		BatchSyncTask task = new BatchSyncTask(auxUtil, generalDao);
		task.start();
    }
	

}
