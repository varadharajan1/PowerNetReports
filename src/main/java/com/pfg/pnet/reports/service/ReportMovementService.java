package com.pfg.pnet.reports.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.pfg.pnet.reports.dto.ReportRequest;
import com.pfg.pnet.reports.dto.ReportResponse;

@Path("/movement")
public class ReportMovementService {
	@POST
	@Path("/savedreports")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReportResponse getSavedReportNames(ReportRequest request) {
		ReportMovementServiceImpl reportMovementServiceImpl = new ReportMovementServiceImpl();
		return reportMovementServiceImpl.getSavedReportNames(request);
	}

	@POST
	@Path("/getreports")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReportResponse getMovementReport(ReportRequest request) {
		ReportMovementServiceImpl reportMovementServiceImpl = new ReportMovementServiceImpl();
		return reportMovementServiceImpl.getMovementReport(request);
	}

}
