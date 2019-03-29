package com.pfg.pnet.reports.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pfg.pnet.reports.dao.DAOFactory;
import com.pfg.pnet.reports.dto.UserProfile;
import com.pfg.pnet.reports.util.ReportConstants;
import com.pfg.pnet.reports.util.ValueConvertor;

@WebServlet("/movement")
public class MovementReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(MovementReport.class.getName());
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.log(Level.INFO, "Reports: doGet() entered " );
		response.setContentType("text/html");
		
		Map<String,String> customers = new HashMap<>();
		customers.put("888-001-001","All (Test Company888)");
		customers.put("888-001-001-1008","Ship Name-1008 (Test Company888 - 1008)");

		HttpSession session = request.getSession(true);
		UserProfile uProfile = (UserProfile)session.getAttribute("uProfile");

        String reportType = request.getParameter("reportType");
        String reportFlag = request.getParameter("reportFlag");
		
        reportType = reportType == null ? ReportConstants.MOVEMENT_REPORT : reportType;
        
		Map<String,String> categoryList = null;
		List<String> reportNameList = null;
				
		if(uProfile != null && uProfile.getCompanyNumber() != null) {
        	categoryList = DAOFactory.getInstance().getMovementReportDAO().getCategories(uProfile.getCompanyNumber());
			reportNameList = DAOFactory.getInstance().getMovementReportDAO().getSavedReportNames(uProfile.getUserID(), reportType);
        }

		List<String> sortOrder = new ArrayList<>();
		sortOrder.add("Top Sellers");
		sortOrder.add("Bottom Dwellers");
		
		request.setAttribute("count", ReportConstants.DEFAULT_COUNT);
		request.setAttribute("sortOrder", sortOrder);
		request.setAttribute("customers", customers);
		request.setAttribute("categories", ValueConvertor.sortByValues(categoryList));
		request.setAttribute("fromDate",ReportConstants.REPORT_DEFAULT_FROMDATE);
		request.setAttribute("toDate",ReportConstants.REPORT_DEFAULT_TODATE);
		request.setAttribute("reportFlag", reportFlag);
		request.setAttribute("savedReportNames", reportNameList);

		RequestDispatcher dispatcher = null;
		if(ReportConstants.MOVEMENT_CUSTOMER_REPORT.equalsIgnoreCase(reportType)) {
			logger.log(Level.INFO, "Reports: doGet() before redirect to movement-customer.jsp" );
			request.setAttribute("reportType", ReportConstants.MOVEMENT_CUSTOMER_REPORT);
			dispatcher = request.getRequestDispatcher("movement-customer.jsp");
		} else if(ReportConstants.MOVEMENT_CONSOLIDATED_REPORT.equalsIgnoreCase(reportType)) {
			logger.log(Level.INFO, "Reports: doGet() before redirect to movement-consolidated.jsp" );
			request.setAttribute("reportType", ReportConstants.MOVEMENT_CONSOLIDATED_REPORT);
			dispatcher = request.getRequestDispatcher("movement-consolidated.jsp");
		} else {
			logger.log(Level.INFO, "Reports: doGet() before redirect to movement.jsp" );
			request.setAttribute("reportType", ReportConstants.MOVEMENT_REPORT);
			dispatcher = request.getRequestDispatcher("movement.jsp");
		}

		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
