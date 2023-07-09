<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<style>
div.wow {
	background-color: blue;
}
</style>
<div id="main-content" class="clearfix">
	<h2 class="contentHeader"><i class="icon-double-angle-right"></i>
		Ward Management		
		
				<a class="pull-right btn btn-primary no-border btn-small form-op"
		href="<s:url value="/wards/create"/>?guardian=<sec:authentication property="principal.username"/>"  onclick="loadModal(this); return false;">
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
				<th>Photo</th>
				<th>Followed By</th>
				<th>Device Serial Number</th>
				<th>Status</th>
				<th>Action</th>
			
			</tr>
		</thead>
		<c:forEach var="ward" items="${wardList}"	varStatus="status">

		<tr>

			<td><c:out value="${ward.name}"></c:out></td>
			<td><c:out value="${ward.photourl}"></c:out></td>
			<td>
 				<c:forEach var="guardian" items="${ward.guardians}">
					<c:out value="${guardian.login}"></c:out> | 
				</c:forEach>
			</td>
			<td><c:out value="${ward.locator.imeiCode}"></c:out></td>
			<td><c:out value="${ward.status}"></c:out></td>

<td>

	<a  href="${pageContext.request.contextPath}/wards/edit?name=${ward.name}" class="tooltip-info" data-rel="tooltip" title="Edit" data-placement="left" onclick="loadModal(this); return false;">
		<span class="blue">
			<i class="icon-edit  bigger-125"></i>
		</span>
	</a>
	<a href ="${pageContext.request.contextPath}/wards/delete?name=${ward.name}" class="tooltip-info" data-rel="tooltip" title="Delete" data-placement="right" onclick="loadModal(this); return false;">
		<span class="blue">
			<i class="icon-trash bigger-125"></i>
		</span>
	</a>
	<a href ="${pageContext.request.contextPath}/geofence/within?name=${ward.name}" class="tooltip-info" data-rel="tooltip" title="Check Zone" data-placement="right" onclick="loadModal(this); return false;">
		<span class="blue">
			<i class="icon-flag bigger-125"></i>
		</span>
	</a>
	<a  href="${pageContext.request.contextPath}/notification/wardNotification?name=${ward.name}" class="tooltip-info" data-rel="tooltip" title="Notification" data-placement="left" >
		<span class="blue">
			<i class="icon-cog 	  bigger-125"></i>
		</span>
	</a>
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
<script type="text/javascript" src="/safe-link/resources/assets/js/media/js/TableTools.min.js"></script>


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

		"iDisplayLength": 25
	});
	oTable1.$("a[data-rel=tooltip]").tooltip();


$("#edit-modal").draggable({
	handle: ".modal-body"
});

});
</script>


