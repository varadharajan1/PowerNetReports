package com.pfg.pnet.reports.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

public class ReportListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(ReportListener.class.getName());
	@Resource(name="RICWC01B")
	private static DataSource reportsDS;
	  
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	    logger.log(Level.INFO, "releasing the reportsDS datasource");
	    reportsDS = null;
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.log(Level.INFO, "ReportsListener initialized.");
	}

	public static DataSource getReportsDS() {
		return reportsDS;
	}
}
