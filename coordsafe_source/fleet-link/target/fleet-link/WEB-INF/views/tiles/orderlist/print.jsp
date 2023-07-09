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
	<link rel="stylesheet" href="/fleet-link/resources/css/hor_table.css" type="text/css" media="screen" charset="utf-8"/>
</head>
<body>
	<div class="widget-box">
		<div class="widget-header header-color-blue">
			<h2 class="contentHeader">
				Task Details 
			</h2>
		</div>
	<div class="widget-body">
		<div class="widget-main">
		<div id="header" style="margin: 8px; margin-bottom: 16px" class="muted">
			<span class="pull-left"><i class="icon-angle-right"></i><strong> ${orderList.assignee.name}</strong></span>
			<span class="pull-right">Date: <%= new java.util.Date() %></span>
		</div>
		<!-- <hr class="hr-double"> -->
			<table id="hor-minimalist-a" style="margin: 8px">
				<thead>
					<tr>
						<th>Order Number</th>
						<th>From</th>
						<!-- <th>Target Date</th> -->
						<th>Address</th>
						<th>Quantity</th>
						<th>Remarks</th>
						<!-- <th>Create On</th> -->
						<th></th>
					</tr>
				</thead>
		
				<c:forEach var="order" items="${orderList.orders}" varStatus="status">		
				<c:choose>
					<c:when test="${order.status eq \"COMPLETED\"}">
						<tr class="green">
					</c:when>
					<c:otherwise>
						<tr>
					</c:otherwise>
				</c:choose>				
				
				<td><c:out value="${order.orderNumber}"></c:out></td>
				<td><c:out value="${order.orderFrom}"></c:out></td>
				<%-- <td><c:out value="${order.targetCompletionDt}"> </c:out></td> --%>
				<%-- <td><c:out value="${order.status}"></c:out></td> --%>
				
				<td><c:out value="${order.address}"></c:out></td>
				<td><c:out value="${order.quantity}"></c:out></td>
				<td><c:out value="${order.remark}"></c:out></td>
				<%-- <td><c:out value="${order.orderDt}"></c:out></td> --%>
				</tr>
				</c:forEach>
			</table>
		</div>
		
		<div class="widget-toolbox  padding-8 clearfix">
					<div class="pull-right">
						<button class="btn  btn-success no-border" onclick="window.print()">
							<i class="icon-print bigger-125"></i> &nbsp;Print
						</button>
						
						<button class="btn  btn-purple no-border" type="submit" id="send">
							<i class="icon-share bigger-125"></i> &nbsp;Send
						</button>
					</div>
					<div class="pull-left">
						<input class="btn btn-primary no-border" type="button" id="cancel"
							value="Cancel" /> 
					</div>
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
			$("#order-submenu").css("display", "block");

			$("#edit-modal").draggable({
				handle : ".modal-body"
			});

			$("#cancel").click(function() {
				console.log("clicked");
				$("#edit-modal").modal("hide");
			});
			
			$("#send").click(function() {
				console.log("clicked");
				$("#edit-modal").modal("hide");
			});
		});
	</script>
</body>
</html>