package com.pfg.pnet.reports.util;

public class ReportConstants {

	private ReportConstants() { }
	
	public static final String REPORT_DEFAULT_FROMDATE = "05/08/2018";
	public static final String REPORT_DEFAULT_TODATE = "07/07/2018";
	public static final String DEFAULT_COUNT = "10";
	public static final String MOVEMENT_REPORT = "MOVEMENT";
	public static final String MOVEMENT_CUSTOMER_REPORT = "MOVEMENT_CUSTOMER";
	public static final String MOVEMENT_CONSOLIDATED_REPORT = "MOVEMENT_CONSOLIDATED";

	public static final String REPORT_FLAG = "GENERATE";

	public static final String ERRORINVALIDINPUT = "Invalid Input";
	public static final String ERRORINVALIDTYPE = "Invalid Report Type";

	public static final String SUCCESSSTATUS = "Success";
	public static final String FAILURESTATUS = "Failure";
	
	public static final String PERMISSION_ISSUE = "User doesn't have the permission to access the reports.";
	public static final String INTEGRATION_ISSUE = "User has not linked to the PerformanceNet application. Please contact your administrator and link the service.";
	
	public static final String REST_WEB_SERVICE="http://njnetnd90d01:9090/reports/rest/movement/getreports"; 
	
}
