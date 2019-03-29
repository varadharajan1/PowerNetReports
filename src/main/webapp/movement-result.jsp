<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.pfg.pnet.reports.util.ReportConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="com.pfg.pnet.reports.nls.pnet_reports" var="resourceBundle"/>
<c:set var="MOVEMENT_REPORT" value="<%=ReportConstants.MOVEMENT_REPORT %>" scope="request"/>
<c:set var="MOVEMENT_CUSTOMER_REPORT" value="<%=ReportConstants.MOVEMENT_CUSTOMER_REPORT %>" scope="request"/>
<c:set var="MOVEMENT_CONSOLIDATED_REPORT" value="<%=ReportConstants.MOVEMENT_CONSOLIDATED_REPORT %>" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<c:choose>
	    <c:when test="${reportType == MOVEMENT_REPORT}">
		<title><fmt:message key="page.title.reports.movement" bundle="${resourceBundle}"/></title>
	    </c:when>
	    <c:when test="${reportType == MOVEMENT_CUSTOMER_REPORT}">
		<title><fmt:message key="page.title.reports.mcustomer" bundle="${resourceBundle}"/></title>
	    </c:when>
	    <c:when test="${reportType == MOVEMENT_CONSOLIDATED_REPORT}">
		<title><fmt:message key="page.title.reports.mconsolidated" bundle="${resourceBundle}"/></title>
	    </c:when>
	    <c:otherwise>
		<title><c:out value="${reportType}" /></title>
	    </c:otherwise>
	</c:choose>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css">
	<link rel="stylesheet" href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.dataTables.min.css">
	<link rel="stylesheet" href="https://cdn.datatables.net/rowgroup/1.0.4/css/rowGroup.dataTables.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js"></script>
	<script src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
	<script src="https://cdn.datatables.net/responsive/2.2.3/js/responsive.bootstrap.min.js"></script>
	<script src="https://cdn.datatables.net/rowgroup/1.0.4/js/dataTables.rowGroup.min.js"></script>
</head>

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
    
    .bg-light-grey {
        background: #ebedeb;
    }
    
    #res-data-table .odd,
    #res-data-table .odd:hover {
        background: #fff;
    }
    
    div#res-data-table_length {
        width: 30%;
        float: left;
    }
    
    div#res-data-table_info {
        width: 36%;
        float: left;
        padding-top: 0px;
    }
    #res-data-table.table td
	{
	 padding: .2rem .75rem;
	}
    @media screen and (max-width: 767px) {
        div#res-data-table_length,
        div#res-data-table_info {
            width: 100%;
            margin: auto;
            float: none;
        }
        div#res-data-table_length {
            padding-top: .5rem;
        }
    }
</style>

<script>
	var dataSet = JSON.stringify(<%=request.getAttribute("dataSet")%>);
	var dataObject = JSON.parse(dataSet);
	$(document).ready(function() {
	    $('#res-data-table').DataTable( {
	    	"responsive": true,
	        "searching": false,
	        "data": dataObject,
	        "columns": [
	            { "data": "itemNumber", "visible":true },
	            { "data": "UPCNumberItem", "visible":true  },
	            { "data": "itemPack", "visible":true  },
	            { "data": "itemSize", "visible":true  },
	            { "data": "itemDescription", "visible":true  },
				{ "data": "quantityShipped", "visible":true  },
				{ "data": "brokenCaseCode", "visible":true  },
				{ "data": "extCatchWeight", "visible":true  },
				{ "data": "priceBookHeadingNumber", "visible":false  }
	        ],
	        "rowGroup": {
	            "dataSrc": 'priceBookHeadingNumber'
	        },
	        "order": [[8, 'asc']],
	    } );
	} );
</script>

<body>
    <div class="container-fluid">
        <div class="row bg-red p-2">
            <div class="col-sm-3 text-center text-sm-left"><img src="${pageContext.servletContext.contextPath}/images/logo.jpg"></div>
            <div class="col-sm-5 py-3 py-sm-2 text-lg-left text-center">
                <div class="text-white">
				<c:choose>
				    <c:when test="${reportType == MOVEMENT_REPORT}">
	                   <h4 class="mb-0"><fmt:message key="page.heading.reports.movement" bundle="${resourceBundle}"/></h4>
				    </c:when>
				    <c:when test="${reportType == MOVEMENT_CUSTOMER_REPORT}">
	                   <h4 class="mb-0"><fmt:message key="page.heading.reports.mcustomer" bundle="${resourceBundle}"/></h4>
				    </c:when>
				    <c:when test="${reportType == MOVEMENT_CONSOLIDATED_REPORT}">
	                   <h4 class="mb-0"><fmt:message key="page.heading.reports.mconsolidated" bundle="${resourceBundle}"/></h4>
				    </c:when>
				    <c:otherwise>
	                   <h4 class="mb-0"><c:out value="${reportType}" /></h4>
				    </c:otherwise>
				</c:choose>
                </div>
            </div>
            <div class="col-sm-4 pt-3 clearfix">
                <ul class="nav float-right">
                    <li class="nav-item px-2"><a href="#" class="text-white"><i class="fa fa-home" aria-hidden="true"></i></a></li>
                    <li class="nav-item px-2"><a href="#" class="text-white"><i class="fa fa-print" aria-hidden="true"></i></a></li>
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
                        <a class="nav-link text-dark" href="#"> <i class="fa fa-arrow-left" aria-hidden="true"></i> <fmt:message key="label.reports.back" bundle="${resourceBundle}"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-dark" href="#"><i class="fa fa-plus mr-1" aria-hidden="true"></i> <fmt:message key="label.reports.addpage" bundle="${resourceBundle}"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-dark" href="#"><i class="fa fa-expand" aria-hidden="true"></i> <fmt:message key="label.reports.advanced" bundle="${resourceBundle}"/></a>
                    </li>
                </ul>
            </div>
        </div>

        <div class="row bg-light-grey text-center py-2">
            <div class="col-sm-6">
                <strong><fmt:message key="label.reports.category" bundle="${resourceBundle}"/>:</strong> <%=request.getAttribute("categories")%>
                <br>
                <strong><fmt:message key="label.reports.display" bundle="${resourceBundle}"/>:</strong> <%=request.getAttribute("limit")%>&nbsp;<%=request.getAttribute("sortOrderSel")%>
            </div>
            <div class="col-sm-6">
                <strong><fmt:message key="label.reports.from" bundle="${resourceBundle}"/>:</strong> <%=request.getAttribute("fromDate")%>
                <br>
                <strong><fmt:message key="label.reports.to" bundle="${resourceBundle}"/>:</strong> <%=request.getAttribute("toDate")%>
            </div>
        </div>

        <div class="row py-3">
            <div class="col-12">
                    <table id="res-data-table" class="table table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
                        <thead>
                            <tr>
                                <th><fmt:message key="label.reports.item" bundle="${resourceBundle}"/></th>
                                <th><fmt:message key="label.reports.upc" bundle="${resourceBundle}"/></th>
                                <th><fmt:message key="label.reports.pack" bundle="${resourceBundle}"/></th>
                                <th><fmt:message key="label.reports.size" bundle="${resourceBundle}"/></th>
                                <th><fmt:message key="label.reports.desc" bundle="${resourceBundle}"/></th>
                                <th><fmt:message key="label.reports.qty" bundle="${resourceBundle}"/></th>
                                <th><fmt:message key="label.reports.each" bundle="${resourceBundle}"/></th>
                                <th><fmt:message key="label.reports.wt" bundle="${resourceBundle}"/></th>
                            </tr>
                        </thead>
                   </table> 
            </div>
        </div>
    </div>
</body>
</html>