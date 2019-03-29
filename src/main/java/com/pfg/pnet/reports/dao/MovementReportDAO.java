package com.pfg.pnet.reports.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pfg.pnet.reports.core.ReportException;
import com.pfg.pnet.reports.core.ReportListener;
import com.pfg.pnet.reports.dto.ReportParam;
import com.pfg.pnet.reports.dto.UserProfile;
import com.pfg.pnet.reports.util.ReportConstants;
import com.pfg.pnet.reports.util.Validator;

public class MovementReportDAO {

	private static final Logger logger = Logger.getLogger(MovementReportDAO.class.getName());
	
	private String userProfileWithSSOID = "SELECT CBUSER, CBPSWR, CBCMPN, CBDIVN, CBDPTN, CBCUSN, CBARCD, CBCTPR, CBEMAD, CBPRFL, CBPAYID, CBSSOID FROM PWRNTCD65.CBUSERP WHERE CBARCD='A' AND CBSSOID=?";
	//PARM01=type=String, value='79dcfcfa-35b6-46ca-808a-1f6186b2f8a2';
	// where activeRecordCode='A', ssoID
	
	private String userProfile = "SELECT CBUSER, CBPSWR, CBCMPN, CBDIVN, CBDPTN, CBCUSN, CBARCD, CBCTPR, CBEMAD, CBPRFL, CBPAYID, CBSSOID FROM PWRNTCD65.CBUSERP WHERE CBARCD='A' AND CBCMPN=? AND TRIM(CBCUSN)=? AND CBUSER=?";
	//PARM03=type=String, value='VARADHA';PARM02=type=String, value='1008';PARM01=type=String, value='888';
	// where activeRecordCode='A', companyNumber, customerNumber, userID
	
	private String eligibleReportNames = "SELECT CPFCKEY FROM PWRNTCD65.CPRFLCP WHERE CPFCFLG='Y' AND CPFCPRFL=?";
	//PARM01=type=String, value='VARADHA';
	// where userProfileFlag='Y', userID
	
	private String categories = "SELECT DISTINCT(LPAD(TRIM(A.JXPBHF),5,'0')) AS CODE, B.JHPNME AS DESCRIPTION FROM PWRNTCD65.JXORDDP A, PWRNTCD65.JHPBHAP B WHERE A.JXCMPN= ? AND B.JHCMPN=? AND LPAD(TRIM(A.JXPBHF),5,'0')=TRIM(B.JHPBHN) ORDER BY B.JHPNME";
	
	private String savedReportNames = "SELECT DISTINCT CRPTNAME FROM PWRNTCD65.CRPTCP WHERE CRPTUSER=? AND CRPTTYPE=? ORDER BY CRPTNAME";
	//PARM02=type=String, value='movement';PARM01=type=String, value='VARADHA';
	// where reportUser, reportType

	private String forInsertUpdateReportParamInfo = "SELECT DISTINCT CRPTNAME FROM PWRNTCD65.CRPTCP WHERE CRPTUSER=? AND CRPTTYPE=? AND CRPTNAME=? ORDER BY CRPTNAME";
	//PARM03=type=String, value='movementvaradha';PARM02=type=String, value='movement';PARM01=type=String, value='VARADHA';
	// where reportUser, reportType, reportName

	private String savedReportParamInfo = "SELECT CRPTSLCT FROM PWRNTCD65.CRPTCP WHERE CRPTUSER=? AND CRPTTYPE=? AND CRPTNAME=?";
	//PARM03=type=String, value='movementvaradha';PARM02=type=String, value='movement';PARM01=type=String, value='VARADHA';
	// where reportUser, reportType, reportName

	private String insertReportParamInfo = "INSERT INTO PWRNTCD65.CRPTCP (CRPTUSER, CRPTTYPE, CRPTNAME, CRPTSLCT) VALUES ( ?, ?, ?, ?)";
	//PARM04=type=String, value='xml-report-info';PARM03=type=String, value='movement-bakery-varadha';PARM02=type=String, value='movement';PARM01=type=String, value='VARADHA';
	// where reportUser, reportType, reportName, reportInfo

	private String updateReportParamInfo = "UPDATE PWRNTCD65.CRPTCP SET CRPTSLCT=? WHERE CRPTUSER=? AND CRPTTYPE=? AND CRPTNAME=?";
	//PARM04=type=String, value='xml-report-info';PARM03=type=String, value='movement-bakery-varadha';PARM02=type=String, value='movement';PARM01=type=String, value='VARADHA';
	// where reportInfo, reportUser, reportType, reportName 

	
	public UserProfile getUserProfile(String ssoID) {
		logger.log(Level.INFO, "MovementReportDAO: getUserProfile() executed.");
		
		UserProfile uProfile = null;
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    if (Validator.isEmpty(ssoID)) {
			logger.log(Level.INFO, "MovementReportDAO: getUserProfile: Validation Error: {0}", ReportConstants.ERRORINVALIDINPUT);
	    	throw new ReportException(ReportConstants.ERRORINVALIDINPUT);
	    }
	    try {
			logger.log(Level.INFO, "MovementReportDAO: getUserProfile(): {0}", userProfileWithSSOID);
			conn = ReportListener.getReportsDS().getConnection();
			ps = conn.prepareStatement(userProfileWithSSOID);
			ps.setString(1, ssoID);
			rs = ps.executeQuery();
			if (rs.next()) {
				uProfile = populateUserProfile(rs);
			    logger.log(Level.INFO, "MovementReportDAO: getUserProfile: {0} ", uProfile);
			} else {
				logger.log(Level.INFO, "MovementReportDAO: {0} ", ReportConstants.INTEGRATION_ISSUE );
			}
	    } catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			throw new ReportException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "MovementReportDAO: getUserProfile() ended.");
	    
	    return uProfile;
	}
	
	public UserProfile getUserProfile(String companyNumber, String customerNumber, String userID) {
		logger.log(Level.INFO, "MovementReportDAO: getUserProfile() executed.");
		
		UserProfile uProfile = null;
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    if (Validator.isEmpty(companyNumber) && Validator.isEmpty(customerNumber) && Validator.isEmpty(userID)) {
			logger.log(Level.INFO, "MovementReportDAO: getUserProfile: Validation Error: {0}", ReportConstants.ERRORINVALIDINPUT);
	    	throw new ReportException(ReportConstants.ERRORINVALIDINPUT);
	    }
	    try {
			logger.log(Level.INFO, "MovementReportDAO: getUserProfile(): {0}", userProfile);
			conn = ReportListener.getReportsDS().getConnection();
			ps = conn.prepareStatement(userProfile);
			ps.setString(1, companyNumber);
			ps.setString(2, customerNumber);
			ps.setString(3, userID);
			rs = ps.executeQuery();
			if (rs.next()) {
				uProfile = populateUserProfile(rs);
			    logger.log(Level.INFO, "MovementReportDAO: getUserProfile: {0} ", uProfile);
			} else {
				logger.log(Level.INFO, "MovementReportDAO: {0} ", ReportConstants.INTEGRATION_ISSUE );
			}
	    } catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			throw new ReportException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "MovementReportDAO: getUserProfile() ended.");
	    
	    return uProfile;
	}
	
	public List<String> getEligibleReportNames(String reportUserID) {
		logger.log(Level.INFO, "MovementReportDAO: getEligibleReportNames() executed.");
		
		List<String> reportNameList = new ArrayList<>();
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    if (Validator.isEmpty(reportUserID)) {
			logger.log(Level.INFO, "MovementReportDAO: getEligibleReportNames: Validation Error: {0}", ReportConstants.ERRORINVALIDINPUT);
	    	throw new ReportException(ReportConstants.ERRORINVALIDINPUT);
	    }
	    try {
			conn = ReportListener.getReportsDS().getConnection();
			ps = conn.prepareStatement(eligibleReportNames);
			ps.setString(1, reportUserID);
			rs = ps.executeQuery();
			while (rs.next()) {
			    String reportName = rs.getString("CPFCKEY");
			    logger.log(Level.INFO, "MovementReportDAO: reportName: {0}", reportName);
			    reportName = (reportName != null) ? reportName.trim() : reportName;
			    reportNameList.add(reportName);
			}
		    logger.log(Level.FINEST, "MovementReportDAO: reportNameList.size: {0}", reportNameList.size());
	    } catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			throw new ReportException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "MovementReportDAO: getEligibleReportNames() ended.");
	    
	    return reportNameList;
	}
	
	public List<String> getUsersReportNames(String ssoID) {
		logger.log(Level.INFO, "MovementReportDAO: getUsersReportNames() executed.");
		
		List<String> reportNameList = new ArrayList<>();
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    if (Validator.isEmpty(ssoID)) {
			logger.log(Level.INFO, "MovementReportDAO: getUsersReportNames: Validation Error: {0}", ReportConstants.ERRORINVALIDINPUT);
	    	throw new ReportException(ReportConstants.ERRORINVALIDINPUT);
	    }
	    try {
			UserProfile uProfile = null;
			conn = ReportListener.getReportsDS().getConnection();
			ps = conn.prepareStatement(userProfileWithSSOID);
			ps.setString(1, ssoID);
			rs = ps.executeQuery();
			if (rs.next()) {
				uProfile = populateUserProfile(rs);
			    logger.log(Level.INFO, "MovementReportDAO: getUsersReportNames: {0} ", uProfile);

				DAOUtil.close(rs);
				DAOUtil.closePreparedStatement(ps);
				
				if(uProfile.getUserID() != null) {
					ps = conn.prepareStatement(eligibleReportNames);
					ps.setString(1, uProfile.getUserID());
					rs = ps.executeQuery();
					while (rs.next()) {
					    String reportName = rs.getString("CPFCKEY");
					    logger.log(Level.INFO, "MovementReportDAO: reportName: {0}", reportName);
					    reportName = (reportName != null) ? reportName.trim() : reportName;
					    reportNameList.add(reportName);
					}
				} else {
					logger.log(Level.INFO, "MovementReportDAO: {0} ", ReportConstants.INTEGRATION_ISSUE );
				}
			}else {
				logger.log(Level.INFO, "MovementReportDAO: {0} ", ReportConstants.INTEGRATION_ISSUE );
			}
		    logger.log(Level.FINEST, "MovementReportDAO: reportNameList.size: {0}", reportNameList.size());
	    } catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			throw new ReportException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "MovementReportDAO: getUsersReportNames() ended.");
	    
	    return reportNameList;
	}
	
	public List<String> getUsersReportNames(String companyNumber, String customerNumber, String userID) {
		logger.log(Level.INFO, "MovementReportDAO: getUsersReportNames() executed.");
		
		List<String> reportNameList = new ArrayList<>();
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    if (Validator.isEmpty(companyNumber) && Validator.isEmpty(customerNumber) && Validator.isEmpty(userID)) {
			logger.log(Level.INFO, "MovementReportDAO: getUsersReportNames: Validation Error: {0}", ReportConstants.ERRORINVALIDINPUT);
	    	throw new ReportException(ReportConstants.ERRORINVALIDINPUT);
	    }
	    try {
			UserProfile uProfile = null;
			conn = ReportListener.getReportsDS().getConnection();
			ps = conn.prepareStatement(userProfile);
			ps.setString(1, companyNumber);
			ps.setString(2, customerNumber);
			ps.setString(3, userID);
			rs = ps.executeQuery();
			if (rs.next()) {
				uProfile = populateUserProfile(rs);
			    logger.log(Level.INFO, "MovementReportDAO: getUsersReportNames: {0} ", uProfile);

				DAOUtil.close(rs);
				DAOUtil.closePreparedStatement(ps);
				
				if(uProfile.getUserID() != null) {
					ps = conn.prepareStatement(eligibleReportNames);
					ps.setString(1, uProfile.getUserID());
					rs = ps.executeQuery();
					while (rs.next()) {
					    String reportName = rs.getString("CPFCKEY");
					    logger.log(Level.INFO, "MovementReportDAO: reportName: {0}", reportName);
					    reportName = (reportName != null) ? reportName.trim() : reportName;
					    reportNameList.add(reportName);
					}
				} else {
					logger.log(Level.INFO, "MovementReportDAO: {0} ", ReportConstants.INTEGRATION_ISSUE );
				}
			}else {
				logger.log(Level.INFO, "MovementReportDAO: {0} ", ReportConstants.INTEGRATION_ISSUE );
			}
		    logger.log(Level.FINEST, "MovementReportDAO: reportNameList.size: {0}", reportNameList.size());
	    } catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			throw new ReportException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "MovementReportDAO: getUsersReportNames() ended.");
	    
	    return reportNameList;
	}
	
	public Map<String,String> getCategories(String companyNumber) {
		logger.log(Level.INFO, "MovementReportDAO: getCategories() executed.");
		
		Map<String,String> categoryList = new HashMap<>();
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    if (Validator.isEmpty(companyNumber)) {
			logger.log(Level.INFO, "MovementReportDAO: getCategories: Validation Error: {0}", ReportConstants.ERRORINVALIDINPUT);
	    	throw new ReportException(ReportConstants.ERRORINVALIDINPUT);
	    }
	    try {
			conn = ReportListener.getReportsDS().getConnection();
			ps = conn.prepareStatement(categories);
			ps.setString(1, companyNumber);
			ps.setString(2, companyNumber);
			rs = ps.executeQuery();
			while (rs.next()) {
			    String code = rs.getString("CODE");
			    String description = rs.getString("DESCRIPTION");
			    logger.log(Level.INFO, "MovementReportDAO: Code : Description = " + code + " : "+description);
			    categoryList.put(code, description);
			}
	    } catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			throw new ReportException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "MovementReportDAO: getCategories() ended.");
	    
	    return categoryList;
	}

	public List<String> getSavedReportNames(String reportUser, String reportType) {
		logger.log(Level.INFO, "MovementReportDAO: getSavedReportNames() executed.");
		
		List<String> savedReportList = new ArrayList<>();
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
		logger.log(Level.INFO, "MovementReportDAO: reportUser: {0}",reportUser);
		logger.log(Level.INFO, "MovementReportDAO: reportType: {0}",reportType);
	    if (Validator.isEmpty(reportUser) && Validator.isEmpty(reportType)) {
	    	throw new ReportException("Invalid inputs");
	    }
	    try {
			conn = ReportListener.getReportsDS().getConnection();
			ps = conn.prepareStatement(savedReportNames);
			ps.setString(1, reportUser);
			ps.setString(2, reportType);
			rs = ps.executeQuery();
			while (rs.next()) {
			    String reportName = rs.getString("CRPTNAME").trim();
			    logger.log(Level.FINEST, "MovementReportDAO: reportName: {0}", reportName);
				savedReportList.add(reportName);
			}
	    } catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			throw new ReportException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "MovementReportDAO: getSavedReportNames() ended.");
	    
	    return savedReportList;
	}
	
	private boolean hasReportSaved(ReportParam reportParam) {
		logger.log(Level.INFO, "MovementReportDAO: hasReportSaved() executed.");
		
		boolean resultValue = false;
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    if (reportParam == null || (Validator.isEmpty(reportParam.getReportUser()) && Validator.isEmpty(reportParam.getReportType()) && Validator.isEmpty(reportParam.getReportName()))) {
	    	throw new ReportException("Invalid inputs");
	    }
	    try {
			conn = ReportListener.getReportsDS().getConnection();
			ps = conn.prepareStatement(forInsertUpdateReportParamInfo);
			ps.setString(1, reportParam.getReportUser());
			ps.setString(2, reportParam.getReportType());
			ps.setString(3, reportParam.getReportName());
			rs = ps.executeQuery();
			
			if(rs.next()) {
			    String reportName = rs.getString("CRPTNAME");
			    resultValue = true;
			    logger.log(Level.FINEST, "MovementReportDAO: hasReportSaved: reportName: {0}", reportName);
			}
	    } catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			throw new ReportException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "MovementReportDAO: hasReportSaved() ended.");
	    
	    return resultValue;
	}
	
	public String getSaveReportInputs(ReportParam reportParam) {
		logger.log(Level.INFO, "MovementReportDAO: getSaveReportInputs() executed.");
		
		String reportInputs = null;
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    if (reportParam == null || (Validator.isEmpty(reportParam.getReportUser()) && Validator.isEmpty(reportParam.getReportType()) && Validator.isEmpty(reportParam.getReportName()))) {
	    	throw new ReportException("Invalid inputs");
	    }
	    try {
			conn = ReportListener.getReportsDS().getConnection();
			ps = conn.prepareStatement(savedReportParamInfo);
			ps.setString(1, reportParam.getReportUser());
			ps.setString(2, reportParam.getReportType());
			ps.setString(3, reportParam.getReportName());
			rs = ps.executeQuery();
			
			if(rs.next()) {
			    reportInputs = rs.getString("CRPTSLCT");
			    logger.log(Level.FINEST, "MovementReportDAO: getSaveReportInputs: reportInputs: {0}", reportInputs);
			}
	    } catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			throw new ReportException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "MovementReportDAO: hasReportSaved() ended.");
	    
	    return reportInputs;
	}
	
	public int saveReportInputs(ReportParam reportParam) {
		logger.log(Level.INFO, "MovementReportDAO: saveReportInputs() executed.");
		
		int retValue = 0;
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
			conn = ReportListener.getReportsDS().getConnection();
		    if (hasReportSaved(reportParam)) {
				logger.log(Level.INFO, "MovementReportDAO: saveReportInputs: updateReportParamInfo ");
				ps = conn.prepareStatement(updateReportParamInfo);
				ps.setString(1, reportParam.getReportParamXML());
				ps.setString(2, reportParam.getReportUser());
				ps.setString(3, reportParam.getReportType());
				ps.setString(4, reportParam.getReportName());
		    }else {
				logger.log(Level.INFO, "MovementReportDAO: saveReportInputs: insertReportParamInfo ");
				ps = conn.prepareStatement(insertReportParamInfo);
				ps.setString(1, reportParam.getReportUser());
				ps.setString(2, reportParam.getReportType());
				ps.setString(3, reportParam.getReportName());
				ps.setString(4, reportParam.getReportParamXML());
		    }
			retValue = ps.executeUpdate();
			logger.log(Level.INFO, "MovementReportDAO: saveReportInputs: {0} of row(s) affected.", retValue);
			
	    } catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			throw new ReportException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "MovementReportDAO: saveReportInputs() ended.");
	    return retValue;
	}

	private UserProfile populateUserProfile(ResultSet rs) throws SQLException{
		UserProfile uProfile = new UserProfile();

		uProfile.setUserID(rs.getString("CBUSER"));
		uProfile.setPassword(rs.getString("CBPSWR"));
		uProfile.setCompanyNumber(rs.getString("CBCMPN"));
		uProfile.setDivisionNumber(rs.getString("CBDIVN"));
		uProfile.setDepartmentNumber(rs.getString("CBDPTN"));
		uProfile.setCustomerNumber(rs.getString("CBCUSN"));
		uProfile.setActiveRecordCode(rs.getString("CBARCD"));
		uProfile.setContactName(rs.getString("CBCTPR"));
		uProfile.setEmail(rs.getString("CBEMAD"));
		uProfile.setProfile(rs.getString("CBPRFL"));
		uProfile.setPayerID(rs.getString("CBPAYID"));
		uProfile.setSsoID(rs.getString("CBSSOID"));

		return uProfile;
	}
}
