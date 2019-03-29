package com.pfg.pnet.reports.servlet;

import java.io.IOException;
import java.util.List;
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
import com.pfg.pnet.reports.util.Validator;

@WebServlet("/dashboard")
public class ReportDashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ReportDashboard.class.getName());
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.log(Level.INFO, "ReportDashboard: doGet() entered " );
		response.setContentType("text/html");
		
		HttpSession session = request.getSession(true);
		
		String gtwayUUID = request.getParameter("gtwayUUID");
		String companyNumber = request.getParameter("companyNumber");
		String customerNumber = request.getParameter("customerNumber");
		String userID = request.getParameter("userID");
		UserProfile uProfile = null;
		
	    if (Validator.isNotEmpty(gtwayUUID)) {
	        uProfile = DAOFactory.getInstance().getMovementReportDAO().getUserProfile(gtwayUUID);
	    } else  if (Validator.isNotEmpty(companyNumber) && Validator.isNotEmpty(customerNumber) && Validator.isNotEmpty(userID)) {
	        uProfile = DAOFactory.getInstance().getMovementReportDAO().getUserProfile(companyNumber, customerNumber, userID);
	    }
		
        if(uProfile != null && uProfile.getUserID() != null) {
        	session.setAttribute("uProfile", uProfile);
			
	    	List<String> reportNameList = DAOFactory.getInstance().getMovementReportDAO().getEligibleReportNames(uProfile.getUserID());

			logger.log(Level.INFO, "ReportDashboard: doGet() reportNameList.size: {0}", reportNameList.size() );
			
			request.setAttribute("reportNames", reportNameList);
			
			if(reportNameList.isEmpty()) {
				logger.log(Level.INFO, "ReportDashboard: {0} ", ReportConstants.PERMISSION_ISSUE );
				request.setAttribute("error", ReportConstants.PERMISSION_ISSUE);
			}
        } else {
			logger.log(Level.INFO, "ReportDashboard: {0} ", ReportConstants.INTEGRATION_ISSUE );
			request.setAttribute("error", ReportConstants.INTEGRATION_ISSUE);
        }
	    
		RequestDispatcher dispatcher = null;
		logger.log(Level.INFO, "ReportDashboard: doGet() before redirect to dashboard.jsp" );
		dispatcher = request.getRequestDispatcher("dashboard.jsp");

		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
