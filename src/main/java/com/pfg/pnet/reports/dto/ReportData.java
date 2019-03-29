package com.pfg.pnet.reports.dto;

import java.io.Serializable;
import java.util.List;

public class ReportData implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<String> reportNameList;
	private List<ReportItem> reportItemList;

	public List<String> getReportNameList() {
		return reportNameList;
	}
	public void setReportNameList(List<String> reportNameList) {
		this.reportNameList = reportNameList;
	}
	public List<ReportItem> getReportItemList() {
		return reportItemList;
	}
	public void setReportItemList(List<ReportItem> reportItemList) {
		this.reportItemList = reportItemList;
	}
}
