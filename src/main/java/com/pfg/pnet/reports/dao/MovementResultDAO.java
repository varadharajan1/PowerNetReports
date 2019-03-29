package com.pfg.pnet.reports.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pfg.pnet.reports.core.ReportException;
import com.pfg.pnet.reports.core.ReportListener;
import com.pfg.pnet.reports.dto.ReportItem;
import com.pfg.pnet.reports.dto.ReportRequest;
import com.pfg.pnet.reports.util.ReportConstants;
import com.pfg.pnet.reports.util.Validator;
import com.pfg.pnet.reports.util.ValueConvertor;

import java8.util.stream.Collector;
import java8.util.stream.Collectors;
import java8.util.stream.Stream;
import java8.util.stream.StreamSupport;

public class MovementResultDAO {

	private static final Logger logger = Logger.getLogger(MovementResultDAO.class.getName());
	
	private String reportDates = "SELECT MIN(JXDTEI) AS MINDATE, MAX(JXDTEI) AS MAXDATE FROM PWRNTCD65.JXORDDP WHERE JXCMPN=? AND JXDIVN=? AND JXDPTN=? AND JXWHSN=?";
	//PARM04=type=String, value='001';PARM03=type=String, value='001';PARM02=type=String, value='001';PARM01=type=String, value='888';
	// where CompanyNumber, DivisionNumber, DepartmentNumber, WarehouseNumber
	
	//private String  movementReportWithAllCategory = "SELECT a.JXITMN, a.JXDTEI AS DTEI, a.JXQYSA AS QYSA, a.JXPBHN AS PBHN, a.JXPBHF AS PBHF, a.JXEXCW AS EXCW, a.JXCWCD AS CWCD, a.JXBCCD AS BCCD, a.JXVNDN AS VNDN, c.JIITMN AS ITMN, c.JIPCKI AS PCKI, c.JISZEI AS SZEI, c.JIUPCN AS UPCN, c.JIUPCP AS UPCP, c.JIBRDN AS BRDN, c.JIIDE1 AS IDE1, b.JJARCD AS ARCD FROM PWRNTCD65.JXORDDP a INNER JOIN PWRNTCD65.JIITMAP c ON  ( a.JXCMPN=c.JICMPN AND a.JXITMN=c.JIITMN ) INNER JOIN PWRNTCD65.JJITMBP b ON  ( a.JXCMPN=b.JJCMPN AND a.JXDIVN=b.JJDIVN AND a.JXDPTN=b.JJDPTN AND a.JXWHSN=b.JJWHSN AND a.JXITMN=b.JJITMN) WHERE a.JXWHSN=? AND ( a.JXDTEI >= ? AND a.JXDTEI <= ? ) ORDER BY PBHN, ITMN";
	//PARM03=type=int, value='20180707';PARM02=type=int, value='20180508';PARM01=type=String, value='001';
	// where WarehouseNumber, FromDateOfLastOrder, ToDateOfLastOrder
	private String  movementReportWithAllCategory = "SELECT ITMN, SUM(QYSA) AS QYSA, PBHN, PBHF, EXCW, CWCD, BCCD, VNDN, PCKI, SZEI, UPCN, UPCP, BRDN, IDE1, ARCD FROM ( SELECT a.JXITMN, a.JXDTEI AS DTEI, a.JXQYSA AS QYSA, a.JXPBHN AS PBHN, a.JXPBHF AS PBHF, a.JXEXCW AS EXCW, a.JXCWCD AS CWCD, a.JXBCCD AS BCCD, a.JXVNDN AS VNDN, c.JIITMN AS ITMN, c.JIPCKI AS PCKI, c.JISZEI AS SZEI, c.JIUPCN AS UPCN, c.JIUPCP AS UPCP, c.JIBRDN AS BRDN, c.JIIDE1 AS IDE1, b.JJARCD AS ARCD FROM PWRNTCD65.JXORDDP a INNER JOIN PWRNTCD65.JIITMAP c ON ( a.JXCMPN=c.JICMPN AND a.JXITMN=c.JIITMN ) INNER JOIN PWRNTCD65.JJITMBP b ON ( a.JXCMPN=b.JJCMPN AND a.JXDIVN=b.JJDIVN AND a.JXDPTN=b.JJDPTN AND a.JXWHSN=b.JJWHSN AND a.JXITMN=b.JJITMN ) WHERE TRIM(a.JXWHSN)=? AND ( a.JXDTEI >= ? AND a.JXDTEI <= ? ) ORDER BY PBHN, ITMN ) AS MOVEMENT_REPORT GROUP BY ITMN, PBHN, PBHF, EXCW, CWCD, BCCD, VNDN, PCKI, SZEI, UPCN, UPCP, BRDN, IDE1, ARCD ORDER BY PBHN, QYSA DESC";
	
	//private String  movementReportWithSelectedCategory = "SELECT a.JXITMN, a.JXDTEI AS DTEI, a.JXQYSA AS QYSA, a.JXPBHN AS PBHN, a.JXPBHF AS PBHF, a.JXEXCW AS EXCW, a.JXCWCD AS CWCD, a.JXBCCD AS BCCD, a.JXVNDN AS VNDN, c.JIITMN AS ITMN, c.JIPCKI AS PCKI, c.JISZEI AS SZEI, c.JIUPCN AS UPCN, c.JIUPCP AS UPCP, c.JIBRDN AS BRDN, c.JIIDE1 AS IDE1, b.JJARCD AS ARCD FROM PWRNTCD65.JXORDDP a INNER JOIN PWRNTCD65.JIITMAP c ON  ( a.JXCMPN=c.JICMPN AND a.JXITMN=c.JIITMN ) INNER JOIN PWRNTCD65.JJITMBP b ON  ( a.JXCMPN=b.JJCMPN AND a.JXDIVN=b.JJDIVN AND a.JXDPTN=b.JJDPTN AND a.JXWHSN=b.JJWHSN AND a.JXITMN=b.JJITMN) WHERE a.JXWHSN=? AND ( a.JXDTEI >= ? AND a.JXDTEI <= ? ) AND ( ~ )  ORDER BY PBHN, ITMN";
	//PARM04=type=String, value='03914';PARM03=type=int, value='20180707';PARM02=type=int, value='20180508';PARM01=type=String, value='001';
	// where WarehouseNumber, FromDateOfLastOrder, ToDateOfLastOrder, PriceBookHeadingNumber
	private String  movementReportWithSelectedCategory = "SELECT ITMN, SUM(QYSA) AS QYSA, PBHN, PBHF, EXCW, CWCD, BCCD, VNDN, PCKI, SZEI, UPCN, UPCP, BRDN, IDE1, ARCD FROM ( SELECT a.JXITMN, a.JXDTEI AS DTEI, a.JXQYSA AS QYSA, a.JXPBHN AS PBHN, a.JXPBHF AS PBHF, a.JXEXCW AS EXCW, a.JXCWCD AS CWCD, a.JXBCCD AS BCCD, a.JXVNDN AS VNDN, c.JIITMN AS ITMN, c.JIPCKI AS PCKI, c.JISZEI AS SZEI, c.JIUPCN AS UPCN, c.JIUPCP AS UPCP, c.JIBRDN AS BRDN, c.JIIDE1 AS IDE1, b.JJARCD AS ARCD FROM PWRNTCD65.JXORDDP a INNER JOIN PWRNTCD65.JIITMAP c ON ( a.JXCMPN=c.JICMPN AND a.JXITMN=c.JIITMN ) INNER JOIN PWRNTCD65.JJITMBP b ON ( a.JXCMPN=b.JJCMPN AND a.JXDIVN=b.JJDIVN AND a.JXDPTN=b.JJDPTN AND a.JXWHSN=b.JJWHSN AND a.JXITMN=b.JJITMN) WHERE TRIM(a.JXWHSN)=? AND ( a.JXDTEI >= ? AND a.JXDTEI <= ? ) AND ( ~ )  ORDER BY PBHN, ITMN ) AS MOVEMENT_REPORT_SELECTED GROUP BY ITMN, PBHN, PBHF, EXCW, CWCD, BCCD, VNDN, PCKI, SZEI, UPCN, UPCP, BRDN, IDE1, ARCD ORDER BY PBHN, QYSA DESC";

	//private String  movementCustReportWithAllCategory = "SELECT a.JXITMN, a.JXDTEI AS DTEI, a.JXQYSA AS QYSA, a.JXPBHN AS PBHN, a.JXPBHF AS PBHF, a.JXEXCW AS EXCW, a.JXCWCD AS CWCD, a.JXBCCD AS BCCD, a.JXVNDN AS VNDN, c.JIITMN AS ITMN, c.JIPCKI AS PCKI, c.JISZEI AS SZEI, c.JIUPCN AS UPCN, c.JIUPCP AS UPCP, c.JIBRDN AS BRDN, c.JIIDE1 AS IDE1, b.JJARCD AS ARCD FROM PWRNTCD65.JXORDDP a INNER JOIN PWRNTCD65.JIITMAP c ON  ( a.JXCMPN=c.JICMPN AND a.JXITMN=c.JIITMN ) INNER JOIN PWRNTCD65.JJITMBP b ON  ( a.JXCMPN=b.JJCMPN AND a.JXDIVN=b.JJDIVN AND a.JXDPTN=b.JJDPTN AND a.JXWHSN=b.JJWHSN AND a.JXITMN=b.JJITMN) WHERE  (  (a.JXCMPN=? AND a.JXDIVN=? AND a.JXDPTN=? AND a.JXCUSN=?) )  AND ( a.JXDTEI >= ? AND a.JXDTEI <= ? ) ORDER BY PBHN, ITMN";
	//PARM06=type=int, value='20160809';PARM05=type=int, value='20150701';PARM04=type=String, value='      1008';PARM03=type=String, value='001';PARM02=type=String, value='001';PARM01=type=String, value='888';
	// where CompanyNumber, DivisionNumber, DepartmentNumber, CustomerNumber, FromDateOfLastOrder, ToDateOfLastOrder
	private String  movementCustReportWithAllCategory = "SELECT ITMN, SUM(QYSA) AS QYSA, PBHN, PBHF, EXCW, CWCD, BCCD, VNDN, PCKI, SZEI, UPCN, UPCP, BRDN, IDE1, ARCD FROM ( SELECT a.JXITMN, a.JXDTEI AS DTEI, a.JXQYSA AS QYSA, a.JXPBHN AS PBHN, a.JXPBHF AS PBHF, a.JXEXCW AS EXCW, a.JXCWCD AS CWCD, a.JXBCCD AS BCCD, a.JXVNDN AS VNDN, c.JIITMN AS ITMN, c.JIPCKI AS PCKI, c.JISZEI AS SZEI, c.JIUPCN AS UPCN, c.JIUPCP AS UPCP, c.JIBRDN AS BRDN, c.JIIDE1 AS IDE1, b.JJARCD AS ARCD FROM PWRNTCD65.JXORDDP a INNER JOIN PWRNTCD65.JIITMAP c ON ( a.JXCMPN=c.JICMPN AND a.JXITMN=c.JIITMN ) INNER JOIN PWRNTCD65.JJITMBP b ON ( a.JXCMPN=b.JJCMPN AND a.JXDIVN=b.JJDIVN AND a.JXDPTN=b.JJDPTN AND a.JXWHSN=b.JJWHSN AND a.JXITMN=b.JJITMN) WHERE ( a.JXCMPN=? AND a.JXDIVN=? AND a.JXDPTN=? AND TRIM(a.JXCUSN)=? )  AND ( a.JXDTEI >= ? AND a.JXDTEI <= ? ) ORDER BY PBHN, ITMN ) AS MOVEMENT_CUSTOMER GROUP BY ITMN, PBHN, PBHF, EXCW, CWCD, BCCD, VNDN, PCKI, SZEI, UPCN, UPCP, BRDN, IDE1, ARCD ORDER BY PBHN, QYSA DESC";
	
	//private String  movementCustReportWithSelectedCategory = "SELECT a.JXITMN, a.JXDTEI AS DTEI, a.JXQYSA AS QYSA, a.JXPBHN AS PBHN, a.JXPBHF AS PBHF, a.JXEXCW AS EXCW, a.JXCWCD AS CWCD, a.JXBCCD AS BCCD, a.JXVNDN AS VNDN, c.JIITMN AS ITMN, c.JIPCKI AS PCKI, c.JISZEI AS SZEI, c.JIUPCN AS UPCN, c.JIUPCP AS UPCP, c.JIBRDN AS BRDN, c.JIIDE1 AS IDE1, b.JJARCD AS ARCD FROM PWRNTCD65.JXORDDP a INNER JOIN PWRNTCD65.JIITMAP c ON  ( a.JXCMPN=c.JICMPN AND a.JXITMN=c.JIITMN ) INNER JOIN PWRNTCD65.JJITMBP b ON  ( a.JXCMPN=b.JJCMPN AND a.JXDIVN=b.JJDIVN AND a.JXDPTN=b.JJDPTN AND a.JXWHSN=b.JJWHSN AND a.JXITMN=b.JJITMN) WHERE  (  (a.JXCMPN=? AND a.JXDIVN=? AND a.JXDPTN=? AND a.JXCUSN=?) )  AND ( a.JXDTEI >= ? AND a.JXDTEI <= ? ) AND ( ~ )  ORDER BY PBHN, ITMN";
	//PARM07=type=String, value='09651';PARM06=type=int, value='20160809';PARM05=type=int, value='20150701';PARM04=type=String, value='      1008';PARM03=type=String, value='001';PARM02=type=String, value='001';PARM01=type=String, value='888';
	// where CompanyNumber, DivisionNumber, DepartmentNumber, CustomerNumber, FromDateOfLastOrder, ToDateOfLastOrder, PriceBookHeadingNumber
	private String  movementCustReportWithSelectedCategory = "SELECT ITMN, SUM(QYSA) AS QYSA, PBHN, PBHF, EXCW, CWCD, BCCD, VNDN, PCKI, SZEI, UPCN, UPCP, BRDN, IDE1, ARCD FROM ( SELECT a.JXITMN, a.JXDTEI AS DTEI, a.JXQYSA AS QYSA, a.JXPBHN AS PBHN, a.JXPBHF AS PBHF, a.JXEXCW AS EXCW, a.JXCWCD AS CWCD, a.JXBCCD AS BCCD, a.JXVNDN AS VNDN, c.JIITMN AS ITMN, c.JIPCKI AS PCKI, c.JISZEI AS SZEI, c.JIUPCN AS UPCN, c.JIUPCP AS UPCP, c.JIBRDN AS BRDN, c.JIIDE1 AS IDE1, b.JJARCD AS ARCD FROM PWRNTCD65.JXORDDP a INNER JOIN PWRNTCD65.JIITMAP c ON ( a.JXCMPN=c.JICMPN AND a.JXITMN=c.JIITMN ) INNER JOIN PWRNTCD65.JJITMBP b ON ( a.JXCMPN=b.JJCMPN AND a.JXDIVN=b.JJDIVN AND a.JXDPTN=b.JJDPTN AND a.JXWHSN=b.JJWHSN AND a.JXITMN=b.JJITMN) WHERE ( a.JXCMPN=? AND a.JXDIVN=? AND a.JXDPTN=? AND TRIM(a.JXCUSN)=? )  AND ( a.JXDTEI >= ? AND a.JXDTEI <= ? ) AND ( ~ )  ORDER BY PBHN, ITMN ) AS MOVEMENT_CUSTOMER_SELECTED GROUP BY ITMN, PBHN, PBHF, EXCW, CWCD, BCCD, VNDN, PCKI, SZEI, UPCN, UPCP, BRDN, IDE1, ARCD ORDER BY PBHN, QYSA DESC";

	//private String  movementConsolidatedReportWithAllCategory = "SELECT a.JXITMN, a.JXDTEI AS DTEI, a.JXQYSA AS QYSA, a.JXPBHN AS PBHN, a.JXPBHF AS PBHF, a.JXEXCW AS EXCW, a.JXCWCD AS CWCD, a.JXBCCD AS BCCD, a.JXVNDN AS VNDN, c.JIITMN AS ITMN, c.JIPCKI AS PCKI, c.JISZEI AS SZEI, c.JIUPCN AS UPCN, c.JIUPCP AS UPCP, c.JIBRDN AS BRDN, c.JIIDE1 AS IDE1, b.JJARCD AS ARCD FROM PWRNTCD65.JXORDDP a INNER JOIN PWRNTCD65.JIITMAP c ON  ( a.JXCMPN=c.JICMPN AND a.JXITMN=c.JIITMN ) INNER JOIN PWRNTCD65.JJITMBP b ON  ( a.JXCMPN=b.JJCMPN AND a.JXDIVN=b.JJDIVN AND a.JXDPTN=b.JJDPTN AND a.JXWHSN=b.JJWHSN AND a.JXITMN=b.JJITMN) WHERE  (  (a.JXCMPN=? AND a.JXDIVN=? AND a.JXDPTN=? AND a.JXCUSN=?) )  AND ( a.JXDTEI >= ? AND a.JXDTEI <= ? ) ORDER BY PBHN, ITMN";
	//PARM06=type=int, value='20180711';PARM05=type=int, value='20180711';PARM04=type=String, value='      1008';PARM03=type=String, value='001';PARM02=type=String, value='001';PARM01=type=String, value='888';
	// where CompanyNumber, DivisionNumber, DepartmentNumber, CustomerNumber, FromDateOfLastOrder, ToDateOfLastOrder
	private String  movementConsolidatedReportWithAllCategory = "SELECT ITMN, SUM(QYSA) AS QYSA, PBHN, PBHF, EXCW, CWCD, BCCD, VNDN, PCKI, SZEI, UPCN, UPCP, BRDN, IDE1, ARCD FROM ( SELECT a.JXITMN, a.JXDTEI AS DTEI, a.JXQYSA AS QYSA, a.JXPBHN AS PBHN, a.JXPBHF AS PBHF, a.JXEXCW AS EXCW, a.JXCWCD AS CWCD, a.JXBCCD AS BCCD, a.JXVNDN AS VNDN, c.JIITMN AS ITMN, c.JIPCKI AS PCKI, c.JISZEI AS SZEI, c.JIUPCN AS UPCN, c.JIUPCP AS UPCP, c.JIBRDN AS BRDN, c.JIIDE1 AS IDE1, b.JJARCD AS ARCD FROM PWRNTCD65.JXORDDP a INNER JOIN PWRNTCD65.JIITMAP c ON ( a.JXCMPN=c.JICMPN AND a.JXITMN=c.JIITMN ) INNER JOIN PWRNTCD65.JJITMBP b ON ( a.JXCMPN=b.JJCMPN AND a.JXDIVN=b.JJDIVN AND a.JXDPTN=b.JJDPTN AND a.JXWHSN=b.JJWHSN AND a.JXITMN=b.JJITMN) WHERE ( a.JXCMPN=? AND a.JXDIVN=? AND a.JXDPTN=? AND TRIM(a.JXCUSN)=? ) AND ( a.JXDTEI >= ? AND a.JXDTEI <= ? ) ORDER BY PBHN, ITMN ) AS MOVEMENT_CONSOLIDATED GROUP BY ITMN, PBHN, PBHF, EXCW, CWCD, BCCD, VNDN, PCKI, SZEI, UPCN, UPCP, BRDN, IDE1, ARCD ORDER BY PBHN, QYSA DESC";

	public String[] getReportDates(ReportRequest request) {
		logger.log(Level.INFO, "MovementResultDAO: getReportDates() executed.");
		
		String[] minMaxDates = new String[2];
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    try {
			conn = ReportListener.getReportsDS().getConnection();
			ps = conn.prepareStatement(reportDates);
			ps.setString(1, request.getCompanyNumber());
			ps.setString(2, request.getDivisionNumber());
			ps.setString(3, request.getDepartmentNumber());
			ps.setString(4, request.getWarehouseNumber());
			rs = ps.executeQuery();
			while (rs.next()) {
				minMaxDates[0] = rs.getString("MINDATE");
				minMaxDates[1] = rs.getString("MAXDATE");
			    logger.log(Level.FINEST, "MovementResultDAO: minMaxDates: {0} , {1}", minMaxDates);
			}
	    } catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			throw new ReportException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "MovementResultDAO: getReportDates() ended.");
	    
	    return minMaxDates;
	}
	
	public List<ReportItem> getMovementReport(ReportRequest request) {
		logger.log(Level.INFO, "MovementResultDAO: getMovementReport() executed.");
		
		List<ReportItem> reportLimitList = new ArrayList<>();
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String reportType = null;
	    try {
		    if(request != null) {
		    	reportType = request.getReportType();
			    if (Validator.isEmpty(reportType)) {
			    	throw new ReportException(ReportConstants.ERRORINVALIDTYPE);
			    }
				logger.log(Level.INFO, "MovementResultDAO: reportType: {0} ", reportType);

				conn = ReportListener.getReportsDS().getConnection();

				String[] priceBookHeadingNumbers = request.getPriceBookHeadingNumber();
				StringBuilder sb = new StringBuilder();
				boolean isAllSelected = false;
				if((priceBookHeadingNumbers != null) && (priceBookHeadingNumbers.length > 0)) {
				    for(int i=0;i<priceBookHeadingNumbers.length;i++){
				    	sb.append("a.JXPBHN=?");
				    	if(i < priceBookHeadingNumbers.length-1) {
				    		sb.append(" OR ");
				    	}
				    	if("0".equals(priceBookHeadingNumbers[i])) {
				    		isAllSelected = true;
				    	}
				    }						
					logger.log(Level.INFO, "MovementResultDAO: priceBookHeadingNumbers: {0} ", sb);
				}
				logger.log(Level.INFO, "MovementResultDAO: isAllSelected: {0} ", isAllSelected);
				String query = null;
				String custQuery = null;
				if(isAllSelected) {
					query = movementReportWithAllCategory;
					custQuery = movementCustReportWithAllCategory;
				}else {
					logger.log(Level.INFO, "MovementResultDAO: getMovementReport:SQL1: {0} ", movementReportWithSelectedCategory);
					query = movementReportWithSelectedCategory.replace("~", sb.toString());
					custQuery = movementCustReportWithSelectedCategory.replace("~", sb.toString());
					logger.log(Level.INFO, "MovementResultDAO: getMovementReport:SQL2: {0} ", movementReportWithSelectedCategory);
				}
				
				if(ReportConstants.MOVEMENT_REPORT.equalsIgnoreCase(reportType)) {
					logger.log(Level.INFO, "MovementResultDAO: getMovementReport:SQL: {0} ", query);
					ps = conn.prepareStatement(query);
					ps.setString(1, request.getWarehouseNumber());
					ps.setLong(2, ValueConvertor.convertToLong(request.getFromDateOfLastOrder()));
					ps.setLong(3, ValueConvertor.convertToLong(request.getToDateOfLastOrder()));

					logger.log(Level.INFO, "MovementResultDAO: priceBookHeadingNumbers.length: {0} ", priceBookHeadingNumbers.length);
		    		
					if((!isAllSelected) && (priceBookHeadingNumbers != null) && (priceBookHeadingNumbers.length > 0)) {
						int queryIndex = 4;
					    for(String item: priceBookHeadingNumbers){
							logger.log(Level.INFO, "MovementResultDAO: priceBookHeadingNumbers.index: {0} ", queryIndex);
							ps.setString(queryIndex, item);
							queryIndex = queryIndex + 1;
					    }						
					}
				}else if(ReportConstants.MOVEMENT_CUSTOMER_REPORT.equalsIgnoreCase(reportType)) {
					logger.log(Level.INFO, "MovementResultDAO: getMovementReport:SQL: {0} ", custQuery);
					ps = conn.prepareStatement(custQuery);
					ps.setString(1, request.getCompanyNumber());
					ps.setString(2, request.getDivisionNumber());
					ps.setString(3, request.getDepartmentNumber());
					ps.setString(4, request.getCustomerNumber());
					ps.setLong(5, ValueConvertor.convertToLong(request.getFromDateOfLastOrder()));
					ps.setLong(6, ValueConvertor.convertToLong(request.getToDateOfLastOrder()));
					if((!isAllSelected) && (priceBookHeadingNumbers != null) && (priceBookHeadingNumbers.length > 0)) {
						int queryIndex = 7;
					    for(String item: priceBookHeadingNumbers){
							logger.log(Level.INFO, "MovementResultDAO: priceBookHeadingNumbers.index: {0} ", queryIndex);
							ps.setString(queryIndex, item);
							queryIndex = queryIndex + 1;
					    }						
					}
				}else if(ReportConstants.MOVEMENT_CONSOLIDATED_REPORT.equalsIgnoreCase(reportType)) {
					logger.log(Level.INFO, "MovementResultDAO: getMovementReport:SQL: {0} ", movementConsolidatedReportWithAllCategory);
					ps = conn.prepareStatement(movementConsolidatedReportWithAllCategory);
					ps.setString(1, request.getCompanyNumber());
					ps.setString(2, request.getDivisionNumber());
					ps.setString(3, request.getDepartmentNumber());
					ps.setString(4, request.getCustomerNumber());
					ps.setLong(5, ValueConvertor.convertToLong(request.getFromDateOfLastOrder()));
					ps.setLong(6, ValueConvertor.convertToLong(request.getToDateOfLastOrder()));
				}
				rs = ps.executeQuery();
				List<ReportItem> reportList = new ArrayList<>();
				while (rs.next()) {
				    ReportItem item = populateReportItem(rs);
					reportList.add(item);
				}
				
				reportLimitList = limitReportitemList(reportList, request.getLimit());
			    
		    }else {
				logger.log(Level.INFO, ReportConstants.ERRORINVALIDINPUT);
		    	throw new ReportException(ReportConstants.ERRORINVALIDINPUT);
		    }
	    } catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			throw new ReportException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "MovementResultDAO: getMovementReport() ended.");
	    
	    return reportLimitList;
	}
	
	private ReportItem populateReportItem(ResultSet rs) throws SQLException{
		ReportItem report = new ReportItem();

		report.setItemNumber(rs.getString("ITMN"));
		//report.setDateOfLastOrder(rs.getLong("DTEI"));
		report.setQuantityShipped(rs.getLong("QYSA"));
		report.setPriceBookHeadingNumber(rs.getString("PBHN"));
		report.setPriceBookHeadingFamily(rs.getString("PBHF"));
		report.setExtCatchWeight(rs.getDouble("EXCW"));
		report.setCatchWeightCode(rs.getString("CWCD"));
		report.setBrokenCaseCode(rs.getString("BCCD"));
		report.setVendorNumber(rs.getString("VNDN"));
		report.setItemPack(rs.getInt("PCKI"));
		report.setItemSize(rs.getString("SZEI"));
		report.setUPCNumberItem(rs.getString("UPCN"));
		report.setUPCNumberUnit(rs.getString("UPCP"));
		report.setItemBrand(rs.getString("BRDN"));
		report.setItemDescription(rs.getString("IDE1"));
		report.setActiveRecordCode(rs.getString("ARCD"));

		return report;
	}
	
	private List<ReportItem> limitReportitemList(List<ReportItem> reportList, int limit){
	    logger.log(Level.INFO, "MovementResultDAO:limitReportitemList:limit {0}", limit);
		List<ReportItem> limitReportitems = new ArrayList<>();
		
		Stream<ReportItem> reportItemStream = StreamSupport.stream(reportList);
	    
	    Map<String, List<ReportItem>> byPriceBookHeadingNumber = reportItemStream
				.collect(Collectors.groupingBy(ReportItem::getPriceBookHeadingNumber, TreeMap::new, limitingList(limit) ) );

	    logger.log(Level.FINEST, "MovementResultDAO:limitReportitemList:byPriceBookHeadingNumber {0}", byPriceBookHeadingNumber);
	    
	    for (Map.Entry<String, List<ReportItem>> entry : byPriceBookHeadingNumber.entrySet()) {
	    	List<ReportItem> value = entry.getValue();
	        String key = entry.getKey();

		    logger.log(Level.FINEST, "MovementResultDAO:limitReportitemList:key: {0}", key);
		    logger.log(Level.FINEST, "MovementResultDAO:limitReportitemList:value.size: {0}", value.size());
		    logger.log(Level.FINEST, "MovementResultDAO:limitReportitemList:value: {0}", value);
		    
		    limitReportitems.addAll(value);
	    }
	    
	    return limitReportitems;
	}
	
	private static <T> Collector<T, ?, List<T>> limitingList(int limit) {
	    return Collectors.of(
	                ArrayList::new, 
	                (l, e) -> { if (l.size() < limit) l.add(e); }, 
	                (l1, l2) -> {
	                    l1.addAll(l2.subList(0, Math.min(l2.size(), Math.max(0, limit - l1.size()))));
	                    return l1;
	                }
	           );
	}
}
