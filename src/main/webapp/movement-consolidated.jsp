<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.pfg.pnet.reports.util.ReportConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="com.pfg.pnet.reports.nls.pnet_reports" var="resourceBundle"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><fmt:message key="page.title.reports.mconsolidated" bundle="${resourceBundle}"/></title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.css" rel="stylesheet" type="text/css" />
	
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>	
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>
	<style>
	    .bg-grey {
	        background: #dcdedc;
	    }
	    .bg-red {
	        background: #b6121b;
	    }
		span.input-group-addon {
	    background: #ddd;
	    padding: 7px;
	    cursor: pointer;
		border-radius: 0px 4px 4px 0px;
	}
	</style>
	<script>
		$(function () {
			$("#datetimepicker1").datepicker({ 
				format: 'mm/dd/yyyy',
				autoclose: true, 
				todayHighlight: true
			});
			$("#datetimepicker2").datepicker({ 
				format: 'mm/dd/yyyy',
				autoclose: true, 
				todayHighlight: true
			});
			$("#generate").click(function() {
				$("#movement-report").submit();
			});
		});
	</script>
</head>
<body>
	<div class="container-fluid">
        <div class="row bg-red p-2">
            <div class="col-sm-3 text-center text-sm-left"><img src="${pageContext.servletContext.contextPath}/images/logo.jpg"></div>
            <div class="col-sm-6 py-3 py-sm-2 text-lg-left text-center">
                <div class="text-white">
                    <h4 class="mb-0"><fmt:message key="page.heading.reports.mconsolidated" bundle="${resourceBundle}"/></h4>
                </div>
            </div>
            <div class="col-sm-3 pt-3 clearfix">
                <ul class="nav float-right">
                    <li class="nav-item px-2"><a href="#" class="text-white"><i class="fa fa-home" aria-hidden="true"></i></a></li>
                    <li class="nav-item px-2"><a href="#" class="text-white"><i class="fa fa-user" aria-hidden="true"></i></a></li>
                    <li class="nav-item px-2"><a href="#" class="text-white"><i class="fa fa-question" aria-hidden="true"></i></a></li>
                    <li class="nav-item px-2"><a href="#" class="text-white"><i class="fa fa-power-off" aria-hidden="true"></i></a></li>
                </ul>
            </div>
        </div>
        <div class="row bg-grey">
            <div class="col">
                <ul class="nav">
                    <li class="nav-item">
                        <a id="generate" class="nav-link text-dark" href="#"> <i class="fa fa-square" aria-hidden="true"></i> <fmt:message key="label.reports.generate" bundle="${resourceBundle}"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-dark" href="#"><i class="fa fa-refresh mr-1" aria-hidden="true"></i> <fmt:message key="label.reports.reset" bundle="${resourceBundle}"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-dark" href="#"><i class="fa fa-save" aria-hidden="true"></i> <fmt:message key="label.reports.save" bundle="${resourceBundle}"/></a>
                    </li>
                </ul>
            </div>
        </div>
		<form id="movement-report" action="/reports/results" method="POST">
		<input type="hidden" id="reportType" name="reportType" value="<%= request.getAttribute("reportType") %>">
		<input type="hidden" id="warehouseNumber" name="warehouseNumber" value="001">
        <div class="row px-lg-4 pt-lg-4 pt-3">
            <div class="col-md-6 col-xl-5 col-12">
                <div class="row p-sm-2">
                    <div class="col-sm-4 pb-2">
                        <strong><fmt:message key="label.reports.daterange" bundle="${resourceBundle}"/></strong>
                    </div>
                    <div class="col-sm-8">
					<div class="row">
                        <div class="col-6">
                            <h6><fmt:message key="label.reports.from" bundle="${resourceBundle}"/></h6>
                            <div class="input-group date" id="datetimepicker1">
                                <input type="text" class="form-control" id="fromDate" name="fromDate" value="<%= request.getAttribute("fromDate") %>"/>
                                <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
                            </div>
                        </div>
                        <div class="col-6">
                            <h6><fmt:message key="label.reports.to" bundle="${resourceBundle}"/></h6>
                            <div class="input-group date" id="datetimepicker2">
                                <input type="text" class="form-control" id="toDate" name="toDate" value="<%= request.getAttribute("toDate") %>"/>
                                <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
                            </div>
                        </div>
						</div>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-xl-7 col-12">
                <div class="py-2 p-sm-2">
                    <strong>Dates: </strong> Enter the from and to dates for the report (MM/DD/YYYY)
                    <br> Date is available for this customer for the following dates inclusive:
                    <div>From:04/07/2009</div>
                    <div>To:07/07/2018</div>
                </div>
            </div>
        </div>

        <div class="row px-lg-4">
            <div class="col-md-6 col-xl-5 col-12">
                <div class="row p-sm-2">
                    <div class="col-sm-4 pb-2">
                        <strong><fmt:message key="label.reports.customer" bundle="${resourceBundle}"/></strong>
                    </div>
                    <div class="col-sm-8">
                        <select multiple class="form-control" id="customersSel" name="customersSel">
							<!-- <option value="0" selected="selected">All</option> -->
							<c:forEach items="${customers}" var="customers">
								<option value="${customers.key}"><c:out value="${customers.value}" />
							</c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-xl-7 col-12">
                <div class="py-2 p-sm-2"><strong>Customers: </strong> Select one or more for the report.
                    <div class="pt-md-4"><fmt:message key="text.reports.note" bundle="${resourceBundle}"/></div>
                </div>
            </div>
        </div>

        <div class="row px-lg-4">
            <div class="col-md-6 col-xl-5 col-12">
                <div class="row p-sm-2">
                    <div class="col-sm-4 pb-2">
                        <strong><fmt:message key="label.reports.categories" bundle="${resourceBundle}"/></strong>
                    </div>
                    <div class="col-sm-8">
						<select multiple class="form-control" id="categoriesSel" name="categoriesSel">
							<option value="0" selected="selected">All</option>
							<c:forEach items="${categories}" var="categories">
								<option value="${categories.key}"><c:out value="${categories.value}" />
							</c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-xl-7 col-12">
                <div class="py-2 p-sm-2"><strong>Categories: </strong> Select one or more for the report.
                    <div class="pt-md-4"><fmt:message key="text.reports.note" bundle="${resourceBundle}"/></div>
                </div>
            </div>
        </div>

        <div class="row px-lg-4">
            <div class="col-md-6 col-xl-5 col-12">
                <div class="row p-sm-2">
                    <div class="col-sm-4 pb-2">
                        <strong><fmt:message key="label.reports.count" bundle="${resourceBundle}"/></strong>
                    </div>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="count" name="count" value="<%= request.getAttribute("count") %>">
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-xl-7 col-12">
                <div class="py-2 p-sm-2"><strong>Count: </strong> Select the number of items to be display in each category.</div>
            </div>
        </div>

        <div class="row px-lg-4">
            <div class="col-md-6 col-xl-5 col-12">
                <div class="row p-sm-2">
                    <div class="col-sm-4 pb-2">
                        <strong><fmt:message key="label.reports.sortorder" bundle="${resourceBundle}"/></strong>
                    </div>
                    <div class="col-sm-8">
                        <select class="form-control" id="sortOrderSel" name="sortOrderSel">
                            <c:forEach items="${sortOrder}" var="sortOrder">
								<option value="${sortOrder}"><c:out value="${sortOrder}" />
							</c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-xl-7 col-12">
                <div class="py-2 p-sm-2"><strong>Sort Order: </strong> Use the drop down list box to select whether you want the report ordered by top sellers or by slow movers.
                </div>
            </div>
        </div>
        </form>
    </div>
    </body>
</html>