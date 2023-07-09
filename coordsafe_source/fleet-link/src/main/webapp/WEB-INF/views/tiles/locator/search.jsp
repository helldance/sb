<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>


<script type="text/javascript">
function loadUnassign(imei){
	$("#edit-modal").html("<div class='widget-box'><div class='widget-header header-color-blue'>" +
		"<h4>Unassign Locator</h4></div><div class='widget-body'><div class='widget-main'>" +
		"<h4><span>Are you sure to unassign locator" + imei + "?</span></h4></div>" +
		"<form method='POST' action='locator/assign?imeiCode=" + imei + "&unassign=true' class='form-horizontal'  id='myForm2' " +
		"style='margin-bottom: 0px'><div class='widget-toolbox  padding-8 clearfix'>" +
		"<div class='pull-right'><input id='submit' class='btn  btn-danger no-border' name='commit' type='submit' value='Delete' />" + 
		"</div><div class='pull-left'><input id='cancel' type='button'  class='btn  btn-primary no-border' value='Cancel'/>" +
		"</div></div></form></div></div>");
}

function loadModal(p) {
	$(".modal-body").empty();
	var href=encodeURI($(p).attr("href"));
	$(".modal-body").load(href);
	$("#edit-modal").modal({backdrop:false});	            
}


$(function() {
    $("#admin-submenu").css("display","block");
	var oTable1 = $('#table_report').dataTable( {
		"iDisplayLength": 100
	} );
	oTable1.$("a[data-rel=tooltip]").tooltip();
	oTable1.$("a[rel=popover]").popover({
		html: 'true', //needed to show html of course
		content : function(){
			return '<img src=' + $(this).attr("href") + '>' ;
		} 
	}).click(function(e) {e.preventDefault();});

	$("#edit-modal").draggable({
		handle: ".modal-body"
	});
});
</script>

<div id="main-content" class="clearfix">
	<h2 class="contentHeader"><i class="icon-double-angle-right"></i>
		Locator Management
		<a class="pull-right btn btn-primary no-border btn-small form-op" class="pageFetcher"
		href="/fleet-link/locator?new" onclick="loadModal(this); return false;">
		<i class="icon-plus"></i>&nbsp; Create New
	</a>
</h2>

<%-- 	<display:table name="${locatorList}" defaultsort="1" defaultorder="ascending"
		pagesize="15" requestURI="" class="displaytag" decorator="com.coordsafe.locator.decorator.LocatorSearchDecorator">
		<display:column property="label" title="Label" sortable="true" style="width: 100px;" />
		<display:column property="imeiCode" title="IMEI Code" sortable="true" style="width: 100px;" />
		<display:column property="model" title="Model" sortable="true" style="width: 50px;" />
		<display:column property="assignedTo" title="Assigned To" sortable="true" style="width: 50px;" />
		<display:column property="address" title="Location" sortable="true" style="width: 150px;" />
		<display:column property="action" title="Actions" sortable="true" style="width: 100px;" />
	</display:table> --%>
	
	<table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>Label</th>
				<th>IMEI</th>
				<th>Model</th>
				<th>Company</th>
				<!-- <th>Location</th> -->
				<th>Vehicle</th>
				<th>Last Update</th>
				<th>Actions</th>
			</tr>
		</thead>
		<c:forEach var="locator" items="${locatorList}"
		varStatus="status">

		<tr
		<c:if test="${status.count % 2 ne 0}">
	</c:if> >

	<td><c:out value="${locator.label}"></c:out></td>
	<td><c:out value="${locator.imeiCode}"></c:out></td>
	<td><c:out value="${locator.model}"></c:out></td>
	<td>
		<c:forEach items="${companyList}" var="company">
			<c:if test="${company.id eq locator.assignedTo}">
				<c:out value="${company.name}"></c:out>
			</c:if>
		</c:forEach>
	</td>
	<%-- <td><c:out value="${locator.assignedTo}"></c:out></td> --%>
	<%-- <td><c:out value="${locator.location}"></c:out></td> --%>
	<%-- <td><c:out value="${locator.vehicle.name}"></c:out></td> --%>
	<td>
		<c:forEach items="${vehicleList}" var="vehicle">
			<c:if test="${vehicle.locator.id eq locator.id}">
				<c:out value="${vehicle.name}"></c:out>
			</c:if>
		</c:forEach>
	</td>
	<td><c:out value="${locator.lastLocationUpdate}"></c:out></td>
<td>
	<a  href="/fleet-link/locator/assign?imeiCode=${locator.imeiCode}" class="tooltip-info" data-rel="tooltip" title="Assign To" data-placement="left" onclick="loadModal(this); return false;">
		<span class="blue">
			<i class="icon-truck  bigger-125"></i>
		</span>
	</a>
	<a  href="/fleet-link/locator/edit?imeiCode=${locator.imeiCode}" class="tooltip-info" data-rel="tooltip" title="Edit" data-placement="left" onclick="loadModal(this); return false;">
		<span class="blue">
			<i class="icon-edit  bigger-125"></i>
		</span>
	</a>
	<a href="/fleet-link/locator/assign?imeiCode=${locator.imeiCode}&unassign=true" class="tooltip-info" data-rel="tooltip" title="Unassign" data-placement="left" onclick="loadModal(this); return false;">
		<span class="blue">
			<i class="icon-remove  bigger-125"></i>
		</span>
	</a>
	<a data-placement="left" href = "http://maps.googleapis.com/maps/api/staticmap?center=${locator.gpsLocation.latitude},${locator.gpsLocation.longitude}&zoom=12&size=480x480&markers=color:blue%7Clabel:S%7C${locator.gpsLocation.latitude},${locator.gpsLocation.longitude}&sensor=false" rel="popover" data-trigger="hover">
		<span class="blue">
			<i class="icon-map-marker bigger-125"></i>
		</span>
	</a>
	<%-- <c:out value = "${locator.deviceStatus.batteryLeft}" /> --%>
	<c:choose>
		<c:when test="${locator.deviceStatus.batteryLeft eq '50'}">
			<i class="icon-circle grey"></i>
		</c:when>
		<c:otherwise>
			<i class="icon-circle blue"></i>
		</c:otherwise>
	</c:choose>
		<!-- <a onclick="loadUnassign(${locator.imeiCode})" href ="#" class="tooltip-info" data-rel="tooltip" title="Delete" data-placement="right">
			<span class="blue">
				<i class="icon-trash bigger-125"></i>
			</span>
		</a> -->

		<!--
		href ="/fleet-link/locator/delete?imeiCode=${locator.imeiCode}"
	-->
</td>

<!--
	<td>
		<a href ="/fleet-link/locator/assign?imeiCode=${locator.imeiCode}"> Assign To...</a>
		|<a href="/fleet-link/locator/edit?imeiCode=${locator.imeiCode}">Edit</a> 
		| <a href ="/fleet-link/locator/delete?imeiCode=${locator.imeiCode}"> Delete</a>
	</td>
-->
</tr>

</c:forEach>
</table>

<div id="edit-modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-body-wrap" >
		<div class="modal-body" >
		</div>
	</div>
</div>
</div>

