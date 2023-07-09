<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<%@page import="com.coordsafe.core.rbac.service.UserService"%>

<script type="text/javascript" src="/fleet-link/resources/scripts/chart/raphael-min.js"></script>
<script type="text/javascript" src="/fleet-link/resources/scripts/chart/g.raphael-min.js"></script>
<script type="text/javascript" src="/fleet-link/resources/scripts/chart/g.bar-min.js"></script>
<script type="text/javascript" src="/fleet-link/resources/scripts/chart/barchart.js"></script>
<script type="text/javascript" src="/fleet-link/resources/scripts/chart/raphael.export.js"></script>
<script type="text/javascript" src="/fleet-link/resources/scripts/chart/svgfix-0.2.js"></script>
<script type="text/javascript" src="/fleet-link/resources/scripts/chart/canvg.js"></script>
<script type="text/javascript" src="/fleet-link/resources/scripts/chart/canvas2image.js"></script>
<script type="text/javascript" src="/fleet-link/resources/scripts/chart/rgbcolor.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/datepicker.css" />
<script src="${pageContext.request.contextPath}/resources/assets/js/date-time/bootstrap-datepicker.min.js"></script>

<script type="text/javascript">
var groupVehicleList = [];

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
});

<%
	org.springframework.context.ApplicationContext ctx = 
	org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
	
	UserService userService = (UserService)ctx.getBean("userService");
	
	String companyId = userService.findByUsername(request.getUserPrincipal().getName()).getCompany().getId().toString();
	out.print("var companyId = " + companyId);
%>

function retrieveGroupVehicle(){
	var req = "/fleet-link/vgroup/company/" + companyId + "/list";

	$.getJSON(req, function(data){
		groupVehicleList = data;

		$("#group-option").empty();
		$("#group-option").append($("<option/>", {value: '-1', text: '====Select Group===='}));

		$.each(data, function (i, item) {    
			$("#group-option").append($("<option/>", {value: item.group.groupId, text: item.group.groupName}));
		});
		
		$("#search").click(function() {
			var ts = $("#start_time").val().replace(/\//g, '-'); 
			var te = $("#end_time").val().replace(/\//g, '-'); 
				
			var loadurl = "/fleet-link/event/company/" + companyId + "/page?timeRange=" + ts + "," + te;
			
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
}

function searchEvent(){
	var ts = $("#start_time").val().replace(/\//g, '-'); 
	var te = $("#end_time").val().replace(/\//g, '-'); 
		
	var loadurl = "/fleet-link/event/company/" + companyId + "/page?timeRange=" + ts + "," + te;
	
	window.location = loadurl;
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

function filterByVehicle(){
	var fil = $("#vehicle-option option:selected").val();
	//alert(fil);
	oTable1.fnFilter(fil);
}

function clearFilter(){
	oTable1.fnFilter('');
}

function loadEditDiag(tripData){
	//console.log(tripData);
	document.write();
}
</script>

	<div id="main-content" class="clearfix">
		<h2 class="contentHeader"><i class="icon-double-angle-right"></i>
			Event Log		
		</h2>

	<c:if test="${ empty(events)}">
		<div class="alert alert-error">
			<button data-dismiss="alert" class="close" type="button">
				<i class="icon-remove"></i>
			</button>

			<i class="icon-remove"></i> <span><c:out
					value="No events are found between chosen time period."></c:out></span>
		</div>
	</c:if>
	
	<div class="well">
	<table>
		<tr>
			<td><i class="icon-search icon-large"></i> &nbsp;</td>
			<td><span style="font-size: 18px; margin-right: 42px" class="lighter">Search Between:</span></td>
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
				<button class="btn btn-small btn-primary" onclick="searchEvent()">
						<i class="icon-search bigger-110"></i> Search 
				</button>
			</td>
		</tr>
		<tr>
			<td><i class="icon-filter icon-large"></i> &nbsp;</td>
			<td><span style="font-size: 18px; margin-right: 58px" class="lighter">Filter by Vehicle:</span></td>
			<td>
				<select id="group-option" onchange="selectGroupOption()" style="margin: 4px 0px 0px 0px">
					<%-- <c:forEach items="${vehiclegroups}" var="vehiclegroup">
					<option value="${vehiclegroup.id}">${vehiclegroup.name}</option>
				</c:forEach> --%>
				</select> <select id="vehicle-option" name="Select Vehicle"
					onchange="filterByVehicle()" style="margin: 4px 0px 0px 0px">
					<%-- <c:forEach items="${vehicles}" var="vehicle">
					<option value="${vehicle.name}">${vehicle.name}</option>
				</c:forEach> --%>
				</select>
			</td>
			<td>
				<button class="btn btn-small btn-danger" onclick="clearFilter()">
					<i class="icon-remove bigger-110"></i> Clear &nbsp;&nbsp;
				</button>
			</td>
		</tr>
	</table>
	</div>
	
	<table id="table_event" class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th>Vehicle</th>
			<th>Event</th>
			<th>Time</th>
			<th>Location</th>
		</tr>
	</thead>

	<c:forEach var="event" items="${events}" varStatus="status">	
	<tr <c:if test="${event.location.latitude == 0 && event.location.longitude == 0}">class="error"</c:if>>
		<td>
			<c:forEach items="${vehicles}" var="vehicle">
			<c:if test="${vehicle.locator.id eq event.locatorId}">
			<c:out value="${vehicle.name}"></c:out>
		</c:if>
	</c:forEach>
</td>
<td><c:out value="${event.type}"></c:out></td>
<td><c:out value="${event.eventTime}"></c:out></td>
<td>
	<a data-placement="left" href = "http://maps.googleapis.com/maps/api/staticmap?center=${event.location.latitude},${event.location.longitude}&zoom=18&size=480x480&markers=color:blue%7Clabel:S%7C${event.location.latitude},${event.location.longitude}&sensor=false" rel="popover"  data-trigger="hover">
		<span class="blue">
			<i class="icon-map-marker bigger-125"></i>
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
var oTable1;
var jsTrips = [];

$(function() {	
	oTable1 = $('#table_event').dataTable({});
	oTable1.fnSort( [ [2,'desc'] ] );
	oTable1.$("a[rel=popover]").popover({
		html: 'true', //needed to show html of course
		content : function(){
			return '<img src=' + $(this).attr("href") + '>' ;
		} 
	}).click(function(e) {e.preventDefault();});
	
	$("#report-submenu").css("display","block");
	$("#edit-modal").draggable({
		handle : ".modal-body"
	});
	
});
</script>
