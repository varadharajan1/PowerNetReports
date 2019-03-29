package com.pfg.pnet.reports.dto;

import java.io.Serializable;

public class ReportResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private ReportData data = null;
	private ReportDetails details = null;

	public ReportData getData() {
		return data;
	}
	public void setData(ReportData data) {
		this.data = data;
	}
	public ReportDetails getDetails() {
		return details;
	}
	public void setDetails(ReportDetails details) {
		this.details = details;
	}
	
}
