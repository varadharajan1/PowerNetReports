package com.pfg.pnet.reports.dto;

import java.io.Serializable;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReportRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String companyNumber;
	private String divisionNumber;
	private String departmentNumber;
	private String customerNumber;
	private String warehouseNumber;
	private String chainStoreCode;
	private String fromDateOfLastOrder;
	private String toDateOfLastOrder;
	private String[] priceBookHeadingNumber; 
	private String sortOrder;
	private String reportUser;
	private String reportType;
	private int limit = 10;

	public String getCompanyNumber() {
		return companyNumber;
	}
	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	public String getDivisionNumber() {
		return divisionNumber;
	}
	public void setDivisionNumber(String divisionNumber) {
		this.divisionNumber = divisionNumber;
	}
	public String getDepartmentNumber() {
		return departmentNumber;
	}
	public void setDepartmentNumber(String departmentNumber) {
		this.departmentNumber = departmentNumber;
	}
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getWarehouseNumber() {
		return warehouseNumber;
	}
	public void setWarehouseNumber(String warehouseNumber) {
		this.warehouseNumber = warehouseNumber;
	}
	public String getChainStoreCode() {
		return chainStoreCode;
	}
	public void setChainStoreCode(String chainStoreCode) {
		this.chainStoreCode = chainStoreCode;
	}
	public String getFromDateOfLastOrder() {
		return fromDateOfLastOrder;
	}
	public void setFromDateOfLastOrder(String fromDateOfLastOrder) {
		this.fromDateOfLastOrder = fromDateOfLastOrder;
	}
	public String getToDateOfLastOrder() {
		return toDateOfLastOrder;
	}
	public void setToDateOfLastOrder(String toDateOfLastOrder) {
		this.toDateOfLastOrder = toDateOfLastOrder;
	}
	public String[] getPriceBookHeadingNumber() {
		return priceBookHeadingNumber;
	}
	public void setPriceBookHeadingNumber(String[] priceBookHeadingNumber) {
		this.priceBookHeadingNumber = priceBookHeadingNumber;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
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
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	@Override
	public String toString() {
		return "ReportRequest [companyNumber=" + companyNumber + ", divisionNumber=" + divisionNumber
				+ ", departmentNumber=" + departmentNumber + ", customerNumber=" + customerNumber + ", warehouseNumber="
				+ warehouseNumber + ", chainStoreCode=" + chainStoreCode + ", fromDateOfLastOrder="
				+ fromDateOfLastOrder + ", toDateOfLastOrder=" + toDateOfLastOrder + ", priceBookHeadingNumber="
				+ Arrays.toString(priceBookHeadingNumber) + ", sortOrder=" + sortOrder + ", reportUser=" + reportUser
				+ ", reportType=" + reportType + ", limit=" + limit + "]";
	}
}
