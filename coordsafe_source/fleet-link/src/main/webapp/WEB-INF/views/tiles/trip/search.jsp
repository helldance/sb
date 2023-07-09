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
	<script	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAM2GxecuxmLMeUc21w3-QuAD_9d2CQj4k&sensor=false"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/datepicker.css" />
</head>
<body>
	<script type="text/javascript">
		var groupVehicleList = [];
		

		<%
			org.springframework.context.ApplicationContext ctx = 
			org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
	
			UserService userService = (UserService)ctx.getBean("userService");
	
			String companyId = userService.findByUsername(request.getUserPrincipal().getName()).getCompany().getId().toString();
			out.print("var companyId = " + companyId);
		%>
		
		$(document).ready(function() {
			retrieveGroupVehicle();
			
			$("#start_time").datepicker({
				format: "dd/mm/yyyy"
			});
			$("#end_time").datepicker({
				format: "dd/mm/yyyy"
			});
			
			var startDt = new Date(${startdate});
			var endDt = new Date(${enddate} - 3600*24*1000);
			
			$("#start_time").datepicker('setDate', startDt);
			$("#end_time").datepicker('setDate', endDt);
			
			$("#search").click(function() {
				var ts = $("#start_time").val().replace(/\//g, '-'); 
				var te = $("#end_time").val().replace(/\//g, '-'); 
					
				var loadurl = "/fleet-link/trip/company/" + companyId + "/" + ts + "," + te;
				
				window.location = loadurl;
				
				/* $.ajax({
					type : 'GET',
					url: loadurl,
					data : $("#myForm").serialize(),
					success: function() {				
						//window.location.assign(loadurl); // true if want to get from server
						window.location.reload(true);
						}
					});  */
			});
		});
		
		function searchTrip() {
			var ts = $("#start_time").val().replace(/\//g, '-'); 
			var te = $("#end_time").val().replace(/\//g, '-'); 
				
			var loadurl = "/fleet-link/trip/company/" + companyId + "/" + ts + "," + te;
			
			window.location = loadurl;
		};
		
		function retrieveGroupVehicle(){
			var req = "/fleet-link/vgroup/company/" + companyId + "/list";
			
			$.getJSON(req, function(data){
				groupVehicleList = data;
				
				$("#group-option").empty();
				$("#group-option").append($("<option/>", {value: '-1', text: '====Select Group===='}));
				
				$.each(data, function (i, item) {    
	        		$("#group-option").append($("<option/>", {value: item.group.groupId, text: item.group.groupName}));
	        	});
			});
		}
		
		/* function selectType() {
			var type = $("#type-option option:selected").val();

			if (type == "weekly") {
				populateDurationOption("weekly", (new Date()).getWeek());
			} else if (type == "monthly") {
				populateDurationOption("monthly", (new Date()).getMonth());
			}
		} */

		function filterByVehicle() {
			var fil = $("#vehicle-option option:selected").val();
			//alert(fil);
			oTable1.fnFilter(fil);
		}

		function clearFilter() {
			oTable1.fnFilter('');
		}

		function loadEditDiag(tripData) {
			//console.log(tripData);
			document.write();
		}

		function selectGroupOption() {
			var groupOption = $("#group-option option:selected").val();
			
			console.log(groupOption);
			
			$.each(groupVehicleList, function (i, item){
				console.log(item);
				
				if (item.group.groupId === parseInt(groupOption)){
					$("#vehicle-option").empty();
					$("#vehicle-option").append($("<option/>", {value: '-1', text: '====Select Vehicle===='}));
					
					var vehicleData = item.vehicles;
										
					$.each(vehicleData, function (i, item){
						$("#vehicle-option").append($("<option/>", {value: item.vehicleName, text: item.vehicleName}));
					});
				}
			});
		}
	</script>

	<div id="main-content" class="clearfix">

		<h2 class="contentHeader"><i class="icon-double-angle-right"></i>
			Trip History<%-- <a
			class="pull-right btn btn-danger no-border btn-small form-op"
			href="<s:url value="#"/>"> <i class="icon-calendar"></i>&nbsp;
			Calendar View
		</a> --%>
		</h2>

		<c:if test="${ empty(trips)}">
			<div class="alert alert-error">
				<button data-dismiss="alert" class="close" type="button">
					<i class="icon-remove"></i>
				</button>

				<i class="icon-remove"></i> <span><c:out
						value="No trips are found between chosen time period."></c:out></span>
			</div>
		</c:if>

		<div class="well">
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
						<%-- <c:forEach items="${vehiclegroups}" var="vehiclegroup">
						<option value="${vehiclegroup.id}">${vehiclegroup.name}</option>
					</c:forEach> --%>
					</select> <select id="vehicle-option" name="Select Vehicle"
						onchange="filterByVehicle()" style="margin: 3px 0px 0px 0px">
						<%-- <c:forEach items="${vehicles}" var="vehicle">
						<option value="${vehicle.name}">${vehicle.name}</option>
					</c:forEach> --%>
					</select>
				</td>
				<td>
					<button class="btn btn-small btn-danger" onclick="clearFilter()">
						<i class="icon-remove bigger-110"></i> Clear &nbsp;
					</button>
				</td>
				</tr>
			</table>
			<!-- <div>
				<i class="icon-search icon-large"></i> &nbsp;
				<span style="font-size: 18px; margin-right: 58px" class="lighter">Search Between:</span>
				<div class="input-prepend" style="margin: 10px 0px 0px 10px">
					<span class="add-on">From</span>
					<div class="input-append">
						<input id="start_time" type="text" value="" name="start_time"></input>
						<span class="add-on"><i class="icon-calendar"></i></span>
					</div>
				</div>
				<div class="input-append" style="margin: 10px 10px 0px 10px">
					<span class="add-on">To</span>
					<div class="input-append">
						<input id="end_time" type="text" value="" name="end_time"></input>
						<span class="add-on"><i class="icon-calendar"></i></span>
					</div>
				</div>
				<button class="btn btn-small btn-success" style="margin-left: 10px" type="submit">
					Submit <i class="icon-arrow-right icon-on-right bigger-110"></i>
				</button>
				<input class="btn btn-primary btn-small no-border" type="button" id="search" value="Search" />
			</div> -->
			
			<%-- <div>
				<i class="icon-filter icon-large"></i> &nbsp;
				<span style="font-size: 18px; margin-right: 76px" class="lighter">Filter by Vehicle:</span>
				<select id="group-option" onchange="selectGroupOption()">
					<c:forEach items="${vehiclegroups}" var="vehiclegroup">
					<option value="${vehiclegroup.id}">${vehiclegroup.name}</option>
				</c:forEach>
				</select> <select id="vehicle-option" name="Select Vehicle"
					onchange="filterByVehicle()">
					<c:forEach items="${vehicles}" var="vehicle">
					<option value="${vehicle.name}">${vehicle.name}</option>
				</c:forEach>
				</select>
				<!-- <input class="btn btn-primary btn-small no-border" type="button" id="clearF" value="Clear" /> -->
				<button class="btn btn-small btn-danger" style="margin-left: 10px" onclick="clearFilter()">
					<i class="icon-remove icon-on-right bigger-110"></i> Clear 
				</button>
			</div> --%>
		</div>
		
		<!-- <div class="row-fluid"><span class="pull-right muted">Mileage unit is Km</span></div> -->

		<!-- <select id="type-option" onchange="selectType()">
			<option value="weekly">By Week</option>
			<option value="monthly">By Month</option>
		</select> -->
		<!-- <div id="duration" style="float: right; height: 30px"></div> -->

	<table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>Vehicle</th>
				<th>Start Time</th>
				<th>End Time</th>
				<th>Mileage (km)</th>
				<th>Pause</th>
				<th>Speeding</th>
				<th>Fuel</th>
				<th>Action</th>
			</tr>
		</thead>

		<c:forEach var="trip" items="${trips}" varStatus="status">						
		<tr>
				<!-- <script type="text/javascript">
					//jsTrips.push('<c:out value="${trip}" />');
					var tripId = <c:out value="${trip.id}" />;
					var tripFuel = <c:out value="${trip.fuel_manual}" />;
					var tripMile = <c:out value="${trip.mileage_manual}" />;
					var tripData = [tripFuel, tripMile];
					//jsTrips.push([tripid, tripData]);
				</script> -->
				<td>
					<c:forEach items="${vehicles}" var="vehicle">
					<c:if test="${vehicle.locator.id eq trip.locatorId}">
					<c:out value="${vehicle.name}"></c:out>
				</c:if>
			</c:forEach>
		</td>
		<td><c:out value="${trip.tripStartTime}"></c:out></td>
		<td><c:out value="${trip.tripEndTime}"></c:out></td>
		<td><c:out value="${trip.mileage}"> </c:out> 
			<c:if test="${trip.mileage_manual ne 0.0}">
				<span class="text-error">
					<c:out value= " -> ${trip.mileage_manual}"></c:out>
				</span> 
			</c:if>
		</td>
		<td><c:out value="${trip.pause}"></c:out></td>
		<td><c:out value="${trip.speeding}"></c:out></td>
		<td><c:out value="${trip.fuel}"></c:out>
			<c:if test="${trip.fuel_manual ne 0.0}">
				<span class="text-error">
					<c:out value= " -> ${trip.fuel_manual}"></c:out>
				</span> 
			</c:if>
		</td>
		<td>
			<a href="/fleet-link/trip/edit/${trip.id}" class="tooltip-info" onclick="loadModal(this); return false;" data-rel="tooltip" title="Edit" data-placement="left">
				<span class="blue">
					<i class="icon-edit  bigger-125"></i>
				</span>
			</a>
			<a href="/fleet-link/trip/${trip.id}/map" class="tooltip-info" onclick="loadModal(this); return false;" data-rel="tooltip" title="View" data-placement="left">
				<span class="blue">
					<i class="icon-bullseye bigger-125"></i>
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
			var oTable1 = $('#table_report').dataTable( {
				"iDisplayLength": 50
			} );
			
			oTable1.$("a[data-rel=tooltip]").tooltip();
			oTable1.fnSort( [ [2,'desc'] ] );
			$("#report-submenu").css("display", "block");

			$("#edit-modal").draggable({
				handle : ".modal-body"
			});

		});
	</script>
</body>
</html>