package com.pfg.pnet.reports.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pfg.pnet.reports.dao.DAOFactory;
import com.pfg.pnet.reports.dto.ReportData;
import com.pfg.pnet.reports.dto.ReportDetails;
import com.pfg.pnet.reports.dto.ReportItem;
import com.pfg.pnet.reports.dto.ReportRequest;
import com.pfg.pnet.reports.dto.ReportResponse;
import com.pfg.pnet.reports.util.ReportConstants;
import com.pfg.pnet.reports.util.Validator;

public class ReportMovementServiceImpl {
	private static final Logger logger = Logger.getLogger(ReportMovementServiceImpl.class.getName());
	
	public ReportResponse getSavedReportNames(ReportRequest request) {
		ReportResponse reportNameResponse = new ReportResponse();
		try {
			if(request != null) {
				logger.log(Level.INFO, "ReportMovementServiceImpl: getSavedReportNames() invoked for report user: [{0}]", request.getReportUser());
				if (Validator.isEmpty(request.getReportUser()) && Validator.isEmpty(request.getReportType())) {
					return generateFailureResponse(generateFailureDetails("1", ReportConstants.ERRORINVALIDINPUT));
				}
				List<String> reportNameList = DAOFactory.getInstance().getMovementReportDAO().getSavedReportNames(request.getReportUser(), request.getReportType());
				
				ReportData data = new ReportData();
				data.setReportNameList(reportNameList);
				reportNameResponse.setData(data);
				reportNameResponse.setDetails(generateSuccessDetails());
				
				logger.log(Level.INFO, "ReportMovementServiceImpl: getSavedReportNames(): {0}", reportNameList.size());
			}else {
				return generateFailureResponse(generateFailureDetails("1", ReportConstants.ERRORINVALIDINPUT));
			}
		} catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			return generateFailureResponse(generateFailureDetails("128", e.getMessage()));
		}
		logger.log(Level.INFO, "ReportMovementServiceImpl: getSavedReportNames: ended");
		return reportNameResponse;
	}

	public ReportResponse getMovementReport(ReportRequest request) {
		ReportResponse reportNameResponse = new ReportResponse();
		try {
			if(request != null) {
				logger.log(Level.INFO, "ReportMovementServiceImpl: getMovementReport() invoked the report of : [{0}]", request.getReportType());
				if (Validator.isEmpty(request.getReportType())) {
					return generateFailureResponse(generateFailureDetails("1", ReportConstants.ERRORINVALIDINPUT));
				}
				List<ReportItem> reportItemList = DAOFactory.getInstance().getMovementResultDAO().getMovementReport(request);
				
				ReportData data = new ReportData();
				data.setReportItemList(reportItemList);
				reportNameResponse.setData(data);
				reportNameResponse.setDetails(generateSuccessDetails());
				
				logger.log(Level.INFO, "ReportMovementServiceImpl: getMovementReport(): {0}", reportItemList.size());
			}else {
				return generateFailureResponse(generateFailureDetails("1", ReportConstants.ERRORINVALIDINPUT));
			}
		} catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			return generateFailureResponse(generateFailureDetails("128", e.getMessage()));
		}
		logger.log(Level.INFO, "ReportMovementServiceImpl: getMovementReport: ended");
		return reportNameResponse;
	}

	private ReportDetails generateFailureDetails(String problemTypes, String message) {
		ReportDetails details = new ReportDetails();
		details.setStatus(ReportConstants.FAILURESTATUS);
		if (problemTypes == null) {
			details.setProblemTypes("65536");
		} else {
			details.setProblemTypes(problemTypes);
		}
		details.setReturnCode("-1");
		details.setMessage(message);
		return details;
	}
	  
	private ReportDetails generateSuccessDetails() {
		ReportDetails details = new ReportDetails();
		details.setStatus(ReportConstants.SUCCESSSTATUS);
		details.setProblemTypes("0");
		details.setReturnCode("0");
		return details;
	}
	  
	private ReportResponse generateFailureResponse(ReportDetails details) {
		ReportResponse reportNameResponse = new ReportResponse();
		ReportData data = new ReportData();
		
		reportNameResponse.setData(data);
		reportNameResponse.setDetails(details);
		
		return reportNameResponse;
	}	
}
