<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<style>
div.wow {
	background-color: blue;
}
</style>
<div id="main-content" class="clearfix">
	<h2 class="contentHeader"><i class="icon-double-angle-right"></i>
		Vehicle Management						
		<a class="pull-right btn btn-primary no-border btn-small form-op"
		href="<s:url value="/vehicle/new"/>?companyid=<sec:authentication property="principal.company.id"/>"  onclick="loadModal(this); return false;">
		<i class="icon-plus"></i>&nbsp; Create New</a>
	</h2>
	
	<script type="text/javascript">
		var h5 = false;
		var vehs = [];
		
		if (Modernizr.localstorage) {
		  h5 = true;
		} 
	</script>

	<table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>Name</th>
				<th>License</th>
				<th>Model</th>
				<th>Type</th>
				<th>Status</th>
				<th>Locator Model</th>
				<th>Group</th>
				<th>Action</th>
			</tr>
		</thead>
		<c:forEach var="vehicle" items="${vehicles}"
		varStatus="status">
			<script type="text/javascript">
				if (h5){
					var veh = new Object();
					
					veh.name = "${vehicle.name}";
					veh.license = "${vehicle.licensePlate}";
					
					vehs.push(veh);
				}
			</script>
		<tr>

	<td><c:out value="${vehicle.name}"></c:out></td>
	<td><c:out value="${vehicle.licensePlate}"></c:out></td>
	<td><c:out value="${vehicle.model}"></c:out></td>
	<td><c:out value="${vehicle.type}"></c:out></td>
	<td>
		<span 
		<c:choose>
		<c:when test="${vehicle.status eq \"STOPPED\"}"> class="label label-info arrowed-right arrowed-in" </c:when>
		<c:when test="${vehicle.status eq \"MOVING\"}"> class="label label-warning arrowed-right arrowed-in"</c:when>
		<c:when test="${vehicle.status eq \"PAUSED\"}"> class="label label-success arrowed-right arrowed-in"</c:when>
		<c:when test="${vehicle.status eq \"SPEEDING\"}"> class="label label-important arrowed-right arrowed-in"</c:when>
		<c:when test="${vehicle.status eq \"NOFIX\"}"> class="label arrowed-right arrowed-in"</c:when>
		<c:otherwise></c:otherwise>
	</c:choose>>
	<c:out value="${vehicle.status}"></c:out>
</span>
</td>
<td><c:out value="${vehicle.locator.model}"></c:out></td>
<td><c:out value="${vehicle.vehiclegroup.groupName}"></c:out></td>
<td>

	<a  href="${pageContext.request.contextPath}/vehicle/edit?name=${vehicle.name}" class="tooltip-info" data-rel="tooltip" title="Edit" data-placement="left" onclick="loadModal(this); return false;">
		<span class="blue">
			<i class="icon-edit  bigger-125"></i>
		</span>
	</a>
	<a href ="${pageContext.request.contextPath}/vehicle/delete?name=${vehicle.name}" class="tooltip-info" data-rel="tooltip" title="Delete" data-placement="right" onclick="loadModal(this); return false;">
		<span class="blue">
			<i class="icon-trash bigger-125"></i>
		</span>
	</a>
	<%-- <a href ="${pageContext.request.contextPath}/geofence/within?name=${vehicle.name}" class="tooltip-info" data-rel="tooltip" title="Check Zone" data-placement="right" onclick="return false;">
		<span class="blue">
			<i class="icon-flag bigger-125"></i>
		</span>
	</a>
	<a  href="${pageContext.request.contextPath}/notification/vehicleNotification?name=${vehicle.name}" class="tooltip-info" data-rel="tooltip" title="Notification" data-placement="left" onclick="return false;">
		<span class="blue">
			<i class="icon-cog 	  bigger-125"></i>
		</span>
	</a> --%>
</td>
</tr>
</c:forEach>
<script type="text/javascript">
if (h5){
	//console.log(vehs);
	localStorage.removeItem("vehicles");
	localStorage.vehicles = JSON.stringify(vehs);
}
</script>

</table>

<div id="edit-modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-body" >

	</div>
</div>


</div>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/js/media/css/TableTools_JUI.css" />
<script type="text/javascript" src="/fleet-link/resources/assets/js/media/js/TableTools.min.js"></script>


<script>
function loadModal(p) {
	$(".modal-body").empty();
	var href=encodeURI($(p).attr("href"));
	$(".modal-body").load(href);
	$("#edit-modal").modal({backdrop:false});	            
}

$(function() {
	$("#vehicle-submenu").css("display","block");

	var oTable1 = $('#table_report').dataTable({ 
		/* "sDom": '<"row-fluid"<"span4"T><"span3"l><"span5"fr>tip', */
		/* "oTableTools": {
			"sSwfPath": "/fleet-link/resources/assets/js/media/swf/copy_csv_xls_pdf.swf"
		} */
		"iDisplayLength": 25
	});
	oTable1.$("a[data-rel=tooltip]").tooltip();
/*
 	$("a.form-op").click(function(){
		$(".modal-body").empty();
		var href=encodeURI($(this).attr("href"));
		$(".modal-body").load(href);
		$("#edit-modal").modal({backdrop:false});	            
		return false;
	}); 
*/

$("#edit-modal").draggable({
	handle: ".modal-body"
});

});
</script>


