package com.pfg.pnet.reports.servlet;

import java.io.IOException;
import java.lang.reflect.Type;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pfg.pnet.reports.dao.DAOFactory;
import com.pfg.pnet.reports.dto.ReportItem;
import com.pfg.pnet.reports.dto.ReportParam;
import com.pfg.pnet.reports.dto.ReportRequest;
import com.pfg.pnet.reports.dto.UserProfile;
import com.pfg.pnet.reports.util.ObjectXmlConvertor;
import com.pfg.pnet.reports.util.ValueConvertor;

@WebServlet("/results")
public class MovementResult extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(MovementResult.class.getName());
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.log(Level.INFO, "MovementResult: doGet() entered " );
		response.setContentType("text/html");

		HttpSession session = request.getSession(true);
    	UserProfile uProfile = (UserProfile)session.getAttribute("uProfile");

    	// {"warehouseNumber":"001","fromDateOfLastOrder":20180508,"toDateOfLastOrder":20180707,"reportType":"MovementReportWithAllCategory"}
        // {"warehouseNumber":"001","fromDateOfLastOrder":20180508,"toDateOfLastOrder":20180707,"priceBookHeadingNumber":"03914","reportType":"MovementReportWithSelectedCategory"}
        // {"companyNumber":"888", "divisionNumber":"001", "departmentNumber":"001", "customerNumber":"1008","fromDateOfLastOrder":20150701,"toDateOfLastOrder":20160809,"reportType":"MovementCustReportWithAllCategory"}
        // {"companyNumber":"888", "divisionNumber":"001", "departmentNumber":"001", "customerNumber":"1008","priceBookHeadingNumber":"09651","fromDateOfLastOrder":20160809,"toDateOfLastOrder":20150701,"reportType":"MovementCustReportWithSelectedCategory"}
        // {"companyNumber":"888", "divisionNumber":"001", "departmentNumber":"001", "customerNumber":"1008","fromDateOfLastOrder":20180711,"toDateOfLastOrder":20180711,"reportType":"MovementConsolidatedReportWithAllCategory"}

        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String count = request.getParameter("count");
        String[] categoriesSel = request.getParameterValues("categoriesSel");
        String reportType = request.getParameter("reportType");
        String warehouseNumber = request.getParameter("warehouseNumber");
        String sortOrderSel = request.getParameter("sortOrderSel");
        String[] customersSel = request.getParameterValues("customersSel");

        String reportFlag = request.getParameter("reportFlag");
        String reportName = request.getParameter("reportName");
        
		ReportRequest reportRequest = new ReportRequest();
        reportRequest.setWarehouseNumber(warehouseNumber);
        reportRequest.setFromDateOfLastOrder(fromDate);
        reportRequest.setToDateOfLastOrder(toDate);
        reportRequest.setReportType(reportType);
        reportRequest.setLimit(ValueConvertor.convertToInt(count));
        reportRequest.setPriceBookHeadingNumber(categoriesSel);
        reportRequest.setSortOrder(sortOrderSel);
        
        if(customersSel != null && customersSel.length > 0) {
        	reportRequest.setCompanyNumber(uProfile.getCompanyNumber());
        	reportRequest.setDivisionNumber(uProfile.getDivisionNumber());
        	reportRequest.setDepartmentNumber(uProfile.getDepartmentNumber());
        	reportRequest.setCustomerNumber(uProfile.getCustomerNumber());
        }
        
		logger.log(Level.INFO, "MovementResult: doGet() ReportRequest {0}", reportRequest );
		
		request.setAttribute("reportType",reportType);
		request.setAttribute("fromDate",fromDate);
		request.setAttribute("toDate",toDate);
		request.setAttribute("limit",count);
		request.setAttribute("sortOrderSel",sortOrderSel);

		RequestDispatcher dispatcher = null;
		
		logger.log(Level.INFO, "MovementResult: doGet() reportFlag {0}", reportFlag );

		if("GENERATE".equalsIgnoreCase(reportFlag)) {
			List<ReportItem> reportItemList = DAOFactory.getInstance().getMovementResultDAO().getMovementReport(reportRequest);
			logger.log(Level.INFO, "MovementResult: doGet() reportItemList.size: {0}", reportItemList.size() );

			if((categoriesSel != null) && (categoriesSel.length > 0)) {
			    for(int i=0;i<categoriesSel.length;i++){
			    	if("0".equals(categoriesSel[i])) {
			    		request.setAttribute("categories","All");
			    		break;
			    	}
			    	if(categoriesSel.length > 1) {
			    		request.setAttribute("categories","Multiple");
			    		break;
			    	}else {
			    		request.setAttribute("categories",categoriesSel[i]);
			    	}
			    }						
			}

			Gson gson = new Gson();
	        Type type = new TypeToken<List<ReportItem>>() {}.getType();
	        String json = gson.toJson(reportItemList, type);

			request.setAttribute("dataSet", json);

			logger.log(Level.INFO, "MovementResult: doGet() before redirect to movement-result.jsp" );
			dispatcher = request.getRequestDispatcher("movement-result.jsp");

		} else if("SAVE".equalsIgnoreCase(reportFlag)) {

			String reportSelection = ObjectXmlConvertor.convertToXML(reportRequest);
			reportSelection = reportSelection.replaceAll("\\r\\n|\\r|\\n", "");
			logger.log(Level.INFO, "MovementResult: doGet() ObjectXmlConvertor {0}", reportSelection);
			
			ReportParam reportParam = new ReportParam();
			reportParam.setReportName(reportName);
			reportParam.setReportParamXML(reportSelection);
			reportParam.setReportType(reportType);
			reportParam.setReportUser(uProfile.getUserID());
			
			int savedReportInfo = DAOFactory.getInstance().getMovementReportDAO().saveReportInputs(reportParam);
			
			request.setAttribute("reportName", reportName);
			request.setAttribute("savedReportInfo", reportRequest);
			logger.log(Level.INFO, "MovementResult: doGet() savedReportInfo {0}", savedReportInfo );

			dispatcher = request.getRequestDispatcher("/movement?reportType="+reportType);
		} else if("SELECT".equalsIgnoreCase(reportFlag)) {
			
			reportName = request.getParameter("savedReportSel");
			
			ReportParam reportParam = new ReportParam();
			reportParam.setReportName(reportName);
			reportParam.setReportType(reportType);
			reportParam.setReportUser(uProfile.getUserID());

			String reportSelection = DAOFactory.getInstance().getMovementReportDAO().getSaveReportInputs(reportParam);
			logger.log(Level.INFO, "MovementResult: doGet() getSaveReportInputs {0}", reportSelection);

			reportRequest = ObjectXmlConvertor.convertToObject(reportSelection);
			request.setAttribute("reportName", reportName);
			request.setAttribute("savedReportInfo", reportRequest);
			
			logger.log(Level.INFO, "MovementResult: doGet() convertToObject {0}", reportRequest );
			dispatcher = request.getRequestDispatcher("/movement?reportType="+reportType);
		}
        dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
