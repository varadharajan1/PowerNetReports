package com.pfg.pnet.reports.dto;

import java.io.Serializable;

public class UserProfile implements Serializable{
	private static final long serialVersionUID = 1L;

	private String userID;
	private String password;
	private String companyNumber;
	private String divisionNumber;
	private String departmentNumber;
	private String customerNumber;
	private String activeRecordCode;
	private String contactName;
	private String email;
	private String profile;
	private String payerID;
	private String ssoID;

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
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
	public String getActiveRecordCode() {
		return activeRecordCode;
	}
	public void setActiveRecordCode(String activeRecordCode) {
		this.activeRecordCode = activeRecordCode;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getPayerID() {
		return payerID;
	}
	public void setPayerID(String payerID) {
		this.payerID = payerID;
	}
	public String getSsoID() {
		return ssoID;
	}
	public void setSsoID(String ssoID) {
		this.ssoID = ssoID;
	}
	@Override
	public String toString() {
		return "UserProfile [userID=" + userID + ", password=" + password + ", companyNumber=" + companyNumber
				+ ", divisionNumber=" + divisionNumber + ", departmentNumber=" + departmentNumber + ", customerNumber="
				+ customerNumber + ", activeRecordCode=" + activeRecordCode + ", contactName=" + contactName
				+ ", email=" + email + ", profile=" + profile + ", payerID=" + payerID + ", ssoID=" + ssoID + "]";
	}

}
