<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.pfg.pnet.reports.util.ReportConstants"%>
<%@ page import="com.pfg.pnet.reports.util.Validator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="com.pfg.pnet.reports.nls.pnet_reports" var="resourceBundle"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title><fmt:message key="page.title.reports" bundle="${resourceBundle}"/></title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
		
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>	
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
	    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>	
		<style>
		    .bg-grey {
		        background: #dcdedc;
				border: none;
		    }
			.dis_mob {
				display:none;
			}
			a.navbar-brand.dis_mob{	
				display:none;
			}
			.navbar {
				padding:0;
			}
			.navbar .nav-item{
				border-top: 5px solid transparent;
				padding: .5rem .5rem;
			}
			.navbar .nav-item:hover{
				border-top: 5px solid #535453;
			}
		    .bg-red {
		        background: #b6121b;
		    }
			.dropdown:hover .dropdown-menu {
				display: block;
			}
			.navbar .dropdown-menu {
				margin: -.125rem 0 0 0;
			}
			.navbar .dropdown-item{
				border-left: 5px solid transparent;
			}
			.navbar .dropdown-item:hover{
				border-left: 5px solid #535453;
			}
			span.input-group-addon {
			    background: #ddd;
			    padding: 7px;
			    cursor: pointer;
				border-radius: 0px 4px 4px 0px;
			}
			@media only screen and (max-width: 767px) {
				a.navbar-brand.dis_mob {	
					display:inline-block;
				}
			}
		</style>
	</head>
	<body>
	    <div class="container-fluid">
	        <div class="row bg-red p-2">
	            <div class="col-md-3 text-center text-sm-left"><img src="${pageContext.servletContext.contextPath}/images/logo.jpg"></div>
	            <div class="col-md-3 py-3 py-sm-2 text-lg-left text-center">
	                <div class="text-white">
	                    <h4 class="mb-0"><fmt:message key="page.heading.reports" bundle="${resourceBundle}"/></h4></div>
	            </div>
				<div class="col-md-4 pt-3 text-white">Ship Name - 1008 (Test Company 888 - 1008)</div>
	            <div class="col-md-2 pt-3 clearfix">
	                <ul class="nav float-right">
	                    <li class="nav-item px-2"><a href="#" class="text-white"><i class="fa fa-home" aria-hidden="true"></i></a></li>
	                    <li class="nav-item px-2"><a href="#" class="text-white"><i class="fa fa-user" aria-hidden="true"></i></a></li>
	                    <li class="nav-item px-2"><a href="#" class="text-white"><i class="fa fa-question" aria-hidden="true"></i></a></li>
	                    <li class="nav-item px-2"><a href="#" class="text-white"><i class="fa fa-power-off" aria-hidden="true"></i></a></li>
	                </ul>
	            </div>
	        </div>
	    </div>
	
	    <nav class="navbar navbar-expand-md navbar-light bg-grey">
	        <a class="navbar-brand dis_mob" href="#">Menu</a>
	        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
	            <span class="navbar-toggler-icon"></span>
	        </button>
	        <div class="collapse navbar-collapse" id="collapsibleNavbar">
	            <ul class="navbar-nav">
	                <li class="nav-item dropdown">
	                    <a class="nav-link text-dark <c:if test="${reportNames.size() > 0}">dropdown-toggle</c:if>" href="#"  id="navbarDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fa fa-industry" aria-hidden="true"></i> Reports</a>
						<c:if test="${reportNames.size() > 0}">
							<div class="dropdown-menu bg-grey" aria-labelledby="navbarDropdown">
						</c:if>
                            <c:forEach items="${reportNames}" var="reportName">
								<c:choose>
								    <c:when test="${reportName == 'ReportMovement'}">
								    	<a class="dropdown-item" href="/reports/movement"><fmt:message key="${reportName}" bundle="${resourceBundle}"/></a>
								    </c:when>
								    <c:when test="${reportName == 'ReportConsolidatedMovement'}">
								    	<a class="dropdown-item" href="/reports/movement?reportType=<%=ReportConstants.MOVEMENT_CONSOLIDATED_REPORT %>"><fmt:message key="${reportName}" bundle="${resourceBundle}"/></a>
								    </c:when>
								    <c:when test="${reportName == 'ReportCustomerMovement'}">
								    	<a class="dropdown-item" href="/reports/movement?reportType=<%=ReportConstants.MOVEMENT_CUSTOMER_REPORT %>"><fmt:message key="${reportName}" bundle="${resourceBundle}"/></a>
								    </c:when>
								    <c:otherwise>
								    	<a class="dropdown-item" href="/reports/movement"><c:out value="${reportName}" /></a>
								    </c:otherwise>
								</c:choose>
							</c:forEach>
						<c:if test="${reportNames.size() > 0}">
							</div>
						</c:if>
	                </li>
	            </ul>
	        </div>
	    </nav>
	    
	    <div class="container-fluid">
	        <div class="row">
	        	<div class="col-md-12"></div>
			    <% if(Validator.isNotEmpty((String)request.getAttribute("error"))) { %>
		        	<div class="col-md-12">
						<div class="alert alert-danger">
						    <strong>Error: </strong> <c:out value="${error}" />
						</div>
					</div>
				<% } else { %>
		        	<div class="col-md-12">
						<div class="alert alert-info">
						    <strong><fmt:message key="report.welcome" bundle="${resourceBundle}"/></strong>
						</div>
					</div>
				<% } %>
			</div>
		</div>
	</body>
</html>
