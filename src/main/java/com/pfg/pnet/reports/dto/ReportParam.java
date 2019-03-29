package com.pfg.pnet.reports.dto;

import java.io.Serializable;

public class ReportParam implements Serializable {
	private static final long serialVersionUID = 1L;

	private String reportUser;
	private String reportType;
	private String reportName;
	private String reportParamXML;

	public String getReportUser() {
		return reportUser;
	}
	public void setReportUser(String reportUser) {
		this.reportUser = reportUser;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportParamXML() {
		return reportParamXML;
	}
	public void setReportParamXML(String reportParamXML) {
		this.reportParamXML = reportParamXML;
	}
}
