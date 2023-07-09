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
	<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?libraries=places&sensor=false"></script>
	<script	src="/fleet-link/resources/scripts/jquery.geocomplete.js"></script>
	<%-- <script src="${pageContext.request.contextPath}/resources/assets/js/date-time/bootstrap-datepicker.min.js"></script> --%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/datepicker.css" />
	<link rel="stylesheet" type="text/css" href="/fleet-link/resources/css/bootstrap-datetimepicker.min.css">
	<script src="/fleet-link/resources/scripts/jquery-ui-timepicker-addon.js"></script>
</head>
<body>
	<script type="text/javascript">
		
	</script>

	<div id="main-content" class="clearfix">

		<h2 class="contentHeader"><i class="icon-double-angle-right"></i>
			WorkOrder Management
			<p class="pull-right">
				<a class="btn btn-primary no-border btn-small btn-danger form-op"
					href="<s:url value="/workorder/autoplan"/>"  onclick="loadModal(this); return false;">
				<i class="icon-group"></i>&nbsp; Plan</a>
				<a class="btn btn-primary no-border btn-small form-op"
					href="<s:url value="/workorder/create"/>"  onclick="loadModal(this); return false;">
				<i class="icon-plus"></i>&nbsp; Create New</a>&nbsp;&nbsp;
			</p>
		</h2>

		<c:if test="${ empty(orders)}">
			<div class="alert alert-error">
				<button data-dismiss="alert" class="close" type="button">
					<i class="icon-remove"></i>
				</button>

				<i class="icon-remove"></i> <span><c:out
						value="No WorkOrders are found within chosen time period."></c:out></span>
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
				<th>Order Number</th>
				<th>From</th>
				<th>Target Date</th>
				<th>Status</th>
				<th>Address</th>
				<th>Quantity</th>
				<th>Remarks</th>
				<th>Assignee</th>
				<th>Create On</th>
				<th></th>
			</tr>
		</thead>

		<c:forEach var="order" items="${orders}" varStatus="status">						
		<tr>
		<td><c:out value="${order.orderNumber}"></c:out></td>
		<td><c:out value="${order.orderFrom}"></c:out></td>
		<td><c:out value="${order.targetCompletionDt}"> </c:out></td>
		<%-- <td><c:out value="${order.status}"></c:out></td> --%>
		
		<td>
			<span 
				<c:choose>
					<c:when test="${order.status eq \"NEW\"}"> class="label label-primary arrowed-right arrowed-in" </c:when>
					<c:when test="${order.status eq \"ASSIGNED\"}"> class="label label-warning arrowed-right arrowed-in"</c:when>
					<c:when test="${order.status eq \"PROCESSING\"}"> class="label label-warning arrowed-right arrowed-in"</c:when>
					<c:when test="${order.status eq \"COMPLETED\"}"> class="label label-success arrowed-right arrowed-in"</c:when>
					<c:when test="${order.status eq \"INVALIDATED\"}"> class="label label arrowed-right arrowed-in"</c:when>
					<c:when test="${order.status eq \"EXPIRED\"}"> class="label label-important arrowed-right arrowed-in"</c:when>
					<c:when test="${order.status eq \"REOPENED\"}"> class="label label-info arrowed-right arrowed-in"</c:when>
					<c:otherwise></c:otherwise>
				</c:choose>>
				<c:choose>
					<c:when test="${order.status eq \"INVALIDATED\"}"><s><c:out value="${order.status}"></c:out></s></c:when>
					<c:otherwise><c:out value="${order.status}"></c:out></c:otherwise>
				</c:choose>
			</span>
		</td>
		
		<td><c:out value="${order.address}"></c:out></td>
		<td><c:out value="${order.quantity}"></c:out></td>
		<td><c:out value="${order.remark}"></c:out></td>
		<td><c:out value="${order.assignee.name}"></c:out></td>
		<td><c:out value="${order.orderDt}"></c:out></td>
		<td>
			<a href="/fleet-link/workorder/edit?orderId=${order.id}" class="tooltip-info" onclick="loadModal(this); return false;" data-rel="tooltip" title="Edit" data-placement="left">
				<span class="blue">
					<i class="icon-edit  bigger-125"></i>
				</span>
			</a>
			
			<c:choose>
				<c:when test="${ empty(order.assignee)}">
					<a href="/fleet-link/workorder/assign?orderId=${order.id}" class="tooltip-info form-op" onclick="loadModal(this); return false;" data-rel="tooltip" title="Assign" data-placement="left">
						<span class="blue">
							<i class="icon-truck  bigger-125"></i>
						</span>
					</a>
				</c:when>
				<c:otherwise>
					<a href="/fleet-link/workorder/assign?orderId=${order.id}" class="tooltip-info form-op" onclick="loadModal(this); return false;" data-rel="tooltip" title="Reassign" data-placement="left">
						<span class="muted">
							<i class="icon-truck  bigger-125"></i>
						</span>
					</a>
				</c:otherwise>
			</c:choose>
			
			<c:choose>
				<c:when test="${! empty(order.orderList)}"> 
					<a href="/fleet-link/workorder/add?orderId=${order.id}" class="tooltip-info form-op" onclick="loadModal(this); return false;" data-rel="tooltip" title="Add to Task" data-placement="left">
						<span class="muted">
							<i class="icon-plus  bigger-125"></i>
						</span>
					</a>
				</c:when>
				<c:otherwise>
					<a href="/fleet-link/workorder/add?orderId=${order.id}" class="tooltip-info form-op" onclick="loadModal(this); return false;" data-rel="tooltip" title="Add to Task" data-placement="left">
						<span class="blue">
							<i class="icon-plus  bigger-125"></i>
						</span>
					</a>
				</c:otherwise>
			</c:choose>
				
			<a href="/fleet-link/workorder/done?orderId=${order.id}" class="tooltip-info form-op" data-rel="tooltip" title="Mark Complete" data-placement="left">
				<span class="blue">
					<i class="icon-ok  bigger-125"></i>
				</span>
			</a>
			<a href="/fleet-link/workorder/invalidate?orderId=${order.id}" class="tooltip-info form-op" data-rel="tooltip" title="Invalidate" data-placement="left">
				<span class="blue">
					<i class="icon-remove  bigger-125"></i>
				</span>
			</a>
			<a href="/fleet-link/workorder/reopen?orderId=${order.id}" class="tooltip-info form-op" data-rel="tooltip" title="Reopen" data-placement="left">
				<span class="blue">
					<i class="icon-repeat  bigger-125"></i>
				</span>
			</a>
			<a href="/fleet-link/workorder/delete?orderId=${order.id}" class="tooltip-info form-op" data-rel="tooltip" title="Delete" data-placement="left">
				<span class="blue">
					<i class="icon-trash  bigger-125"></i>
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
			oTable1.fnSort( [ [7,'desc'] ] );
			$("#order-submenu").css("display", "block");

			$("#edit-modal").draggable({
				handle : ".modal-body"
			});

		});
	</script>
</body>
</html>