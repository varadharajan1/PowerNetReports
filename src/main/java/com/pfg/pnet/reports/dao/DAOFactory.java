package com.pfg.pnet.reports.dao;

public class DAOFactory {

	private static final DAOFactory factory = new DAOFactory();
	private MovementResultDAO reportMovementDAO;
	private MovementReportDAO reportsDAO;
  
	private DAOFactory() { }
	
	public static DAOFactory getInstance() {
		return factory;
	}

	public MovementResultDAO getMovementResultDAO() {
		if(reportMovementDAO == null) {
			reportMovementDAO = new MovementResultDAO();
		}
		return reportMovementDAO;
	}

	public MovementReportDAO getMovementReportDAO() {
		if(reportsDAO == null) {
			reportsDAO = new MovementReportDAO();
		}
		return reportsDAO;
	}
}
