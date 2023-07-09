<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<%@page import="com.coordsafe.core.rbac.service.UserService"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" src="/fleet-link/resources/scripts/chart/raphael-min.js"></script>
	<script type="text/javascript" src="/fleet-link/resources/scripts/chart/g.raphael-min.js"></script>
	<script type="text/javascript" src="/fleet-link/resources/scripts/chart/barchart.js"></script>
	<script type="text/javascript" src="/fleet-link/resources/scripts/chart/svgfix-0.2.js"></script>
	<script type="text/javascript" src="/fleet-link/resources/scripts/chart/canvg.js"></script>
	<script src="${pageContext.request.contextPath}/resources/assets/js/date-time/bootstrap-datepicker.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/datepicker.css" />
</head>
<body>
	<script type="text/javascript">
		
	</script>

	<div id="main-content" class="clearfix">

		<h2 class="contentHeader"><i class="icon-double-angle-right"></i>
			Task Management
			<a class="pull-right btn btn-primary no-border btn-small form-op"
				href="<s:url value="/orderlist/create?companyId=" /><sec:authentication property="principal.company.id"/>"  onclick="loadModal(this); return false;">
			<i class="icon-plus"></i>&nbsp; Create New</a>
		</h2>

		<c:if test="${ empty(orderLists)}">
			<div class="alert alert-error">
				<button data-dismiss="alert" class="close" type="button">
					<i class="icon-remove"></i>
				</button>

				<i class="icon-remove"></i> <span><c:out
						value="No tasks are found for chosen period."></c:out></span>
			</div>
		</c:if>

		<%-- <div class="well">
			<table>
				<tr>
				<td><i class="icon-search icon-large"></i></td>
				<td><span style="font-size: 18px; margin-right: 58px" class="lighter">Search Between:</span></td>
				<td>
					<div class="input-prepend" style="margin: 10px 0px 0px 0px">
						<span class="add-on">From</span>
						<div class="input-append">
							<input id="start_time" type="text" value="" name="start_time"></input>
							<span class="add-on"><i class="icon-calendar"></i></span>
						</div>
					</div>
					<div class="input-append" style="margin: 10px 10px 0px 0px">
						<span class="add-on">To</span>
						<div class="input-append">
							<input id="end_time" type="text" value="" name="end_time"></input>
							<span class="add-on"><i class="icon-calendar"></i></span>
						</div>
					</div>
				</td>
				<td>
					<!-- <input class="btn btn-primary btn-small no-border" type="button" id="search" value="Search" /> -->
					<button class="btn btn-small btn-primary" onclick="searchTrip()">
						<i class="icon-search bigger-110"></i> Search 
					</button>
				</td>
				</tr>
				<tr>
				<td><i class="icon-filter icon-large"></i> &nbsp;</td>
				<td><span style="font-size: 18px; margin-right: 76px" class="lighter">Filter by Vehicle:</span></td>
				<td>
					<select id="group-option" onchange="selectGroupOption()" style="margin: 3px 0px 0px 0px">
						<c:forEach items="${vehiclegroups}" var="vehiclegroup">
						<option value="${vehiclegroup.id}">${vehiclegroup.name}</option>
					</c:forEach>
					</select> <select id="vehicle-option" name="Select Vehicle"
						onchange="filterByVehicle()" style="margin: 3px 0px 0px 0px">
						<c:forEach items="${vehicles}" var="vehicle">
						<option value="${vehicle.name}">${vehicle.name}</option>
					</c:forEach>
					</select>
				</td>
				<td>
					<button class="btn btn-small btn-danger" onclick="clearFilter()">
						<i class="icon-remove bigger-110"></i> Clear &nbsp; &nbsp;
					</button>
				</td>
				</tr>
			</table>
		</div> --%>

	<table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>Assignee</th>
				<!-- <th>Task Number</th> -->
				<th># of Orders</th>
				<th>Completion</th>
				<th>Create On</th>
				<th></th>
			</tr>
		</thead>

		<c:forEach var="orderList" items="${orderLists}" varStatus="status">						
		<tr>
		<td><c:out value="${orderList.assignee.name}"> </c:out></td>
		<%-- <td><c:out value="${orderList.taskListNum}"></c:out></td> --%>
		<td><c:out value="${fn:length(orderList.orders)}"> </c:out></td>
		
		<td><div data-percent="<c:out value="${orderList.completion}"></c:out>%" class="progress progress-info">
				<div style="width: <c:out value="${orderList.completion}"></c:out>%;" class="bar"></div>
			</div>
		</td>
		
		<td><c:out value="${orderList.createDt}"></c:out></td>
		<%-- <td><fmt:formatDate value="${orderList.createDt}" type="both" pattern="MM-dd-yyyy" /></td> --%>
		
		<td>
			<a href="/fleet-link/orderlist/edit/${orderList.id}" class="tooltip-info" onclick="loadModal(this); return false;" data-rel="tooltip" title="Edit" data-placement="left">
				<span class="blue">
					<i class="icon-edit  bigger-125"></i>
				</span>
			</a>
			
			<a href="/fleet-link/orderlist/delete/${orderList.id}" class="tooltip-info" data-rel="tooltip" title="Delete" data-placement="left">
				<span class="blue">
					<i class="icon-trash  bigger-125"></i>
				</span>
			</a>
			
			<a href="/fleet-link/orderlist/print?orderId=${orderList.id}" class="tooltip-info" onclick="loadModal(this); return false;" data-rel="tooltip" title="Detail" data-placement="left">
				<span class="blue">
					<i class="icon-tasks bigger-125"></i>
				</span>
			</a>
				</td>
			</tr>
		</c:forEach>
	</table>

	<div id="edit-modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-body" >
		</div>
	</div>
</div>
	<script type="text/javascript">
		function loadModal(p) {

			$(".modal-body").empty();
			var href = encodeURI($(p).attr("href"));
			$(".modal-body").load(href);
			$("#edit-modal").modal({
				backdrop : false
			});
		}

		var oTable1;
		var jsTrips = [];

		$(function() {
			oTable1 = $('#table_report').dataTable({});
			oTable1.$("a[data-rel=tooltip]").tooltip();
			oTable1.fnSort( [ [3,'desc'] ] );
			$("#order-submenu").css("display", "block");

			$("#edit-modal").draggable({
				handle : ".modal-body"
			});

		});
	</script>
</body>
</html>