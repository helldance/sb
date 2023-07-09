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
	var oTable1 = $('#table_report').dataTable( {
		"iDisplayLength": 10
	} );
	oTable1.$("a[data-rel=tooltip]").tooltip();
	oTable1.$("a[rel=popover]").popover({
		html: 'true', //needed to show html of course
		content : function(){
			return '<img src=' + $(this).attr("href") + '>' ;
		} 
	}).click(function(e) {e.preventDefault();});

});
</script>

<div class="clearfix">
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<h2 class="contentHeader">
				Locator Management
			</h2>
		</div>
	</div>

	<div class="widget-body">
		<div class="widget-main">
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
<!-- Assign to button 
	<a  href="/safe-link/locator/assign?imeiCode=${locator.imeiCode}" class="tooltip-info" data-rel="tooltip" title="Assign To" data-placement="left" onclick="loadEditModal(this); return false;">
		<span class="blue">
			<i class="icon-truck  bigger-125"></i>
		</span>
	</a>
-->
	<a  href="/safe-link/locator/edit?imeiCode=${locator.imeiCode}" class="tooltip-info" data-rel="tooltip" title="Edit" data-placement="left" onclick="loadEditModal(this); return false;">
		<span class="blue">
			<i class="icon-edit  bigger-125"></i>
		</span>
	</a>
<!-- Unassign tool 
	<a href="/safe-link/locator/assign?imeiCode=${locator.imeiCode}&unassign=true" class="tooltip-info" data-rel="tooltip" title="Unassign" data-placement="left" onclick="loadEditModal(this); return false;">
		<span class="blue">
			<i class="icon-remove  bigger-125"></i>
		</span>
	</a>
-->
	<a data-placement="left" href = "http://maps.googleapis.com/maps/api/staticmap?center=${locator.gpsLocation.latitude},${locator.gpsLocation.longitude}&zoom=12&size=480x480&markers=color:blue%7Clabel:S%7C${locator.gpsLocation.latitude},${locator.gpsLocation.longitude}&sensor=false" rel="popover" data-trigger="hover">
		<span class="blue">
			<i class="icon-map-marker bigger-125"></i>
		</span>
	</a>
		<!-- <a onclick="loadUnassign(${locator.imeiCode})" href ="#" class="tooltip-info" data-rel="tooltip" title="Delete" data-placement="right">
			<span class="blue">
				<i class="icon-trash bigger-125"></i>
			</span>
		</a> -->

		<!--
		href ="/safe-link/locator/delete?imeiCode=${locator.imeiCode}"
	-->
</td>

<!--
	<td>
		<a href ="/safe-link/locator/assign?imeiCode=${locator.imeiCode}"> Assign To...</a>
		|<a href="/safe-link/locator/edit?imeiCode=${locator.imeiCode}">Edit</a> 
		| <a href ="/safe-link/locator/delete?imeiCode=${locator.imeiCode}"> Delete</a>
	</td>
-->
</tr>

</c:forEach>
</table>
</div>

<div class="widget-toolbox  padding-8 clearfix">
		<div class="pull-left">
			<button class= "btn  btn-primary no-border" id="cancel" value="Back" onclick="cancelPrimaryModal(this);"> Back </button>
		</div>
		<div class="pull-right">

			<a class="btn  btn-success btn-small no-border" href="<s:url value="/geofence/new"/>?type=fence"  onclick="loadEditModal(this); return false;">
			Create new
		</a>
	</div>
</div>  <!-- End of Widget toolbox -->

</div>  <!-- End of widget body -->
</div>  <!-- End of widget box -->
</div>
