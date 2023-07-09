<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<div id="main-content" class="clearfix">

	<h2 class="contentHeader"><i class="icon-double-angle-right"></i>
		Resource Management					
		<a class="pull-right btn btn-primary no-border btn-small form-op" class="pageFetcher"
		href="/fleet-link/resource?new" onclick="loadModal(this); return false;"><i class="icon-plus"></i>&nbsp; 
		Create New
	</a>
</h2>

<table id="table_report" class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th>Name</th>
			<th>Type</th>
			<th>Value</th>
			<th>Description</th>
			<th>Actions</th>
		</tr>
	</thead>
	<c:forEach var="resource" items="${resources}"
	varStatus="status">

	<tr
	<c:if test="${status.count % 2 ne 0}">
</c:if> >

<td><c:out value="${resource.name}"></c:out></td>
<td><c:out value="${resource.type}"></c:out></td>
<td><c:out value="${resource.value}"></c:out></td>
<td><c:out value="${resource.description}"></c:out></td>
				<!--
				<td><a class="form-op" href="/fleet-link/resource/edit?name=${resource.name}">Edit</a> | <a class="form-op" href ="/fleet-link/resource/delete?name=${resource.name}"> Delete</a></td>
			-->
			<td>
				<a href="/fleet-link/resource/edit?name=${resource.name}" class="tooltip-info" data-rel="tooltip" title="Edit" data-placement="left" onclick="loadModal(this); return false;">
					<span class="blue">
						<i class="icon-edit  bigger-125"></i>
					</span>
				</a>
				<a href ="/fleet-link/resource/delete?name=${resource.name}" class="tooltip-info" data-rel="tooltip" title="Delete" data-placement="right" onclick="loadModal(this); return false;">
					<span class="blue">
						<i class="icon-trash bigger-125"></i>
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


<script>
function loadModal(p) {
	$(".modal-body").empty();
	var href=encodeURI($(p).attr("href"));
	$(".modal-body").load(href);
	$("#edit-modal").modal({backdrop:false});	            
}

$(function() {
    $("#admin-submenu").css("display","block");
	var oTable1 = $('#table_report').dataTable( {} );
	oTable1.$("a[data-rel=tooltip]").tooltip();


	$("#edit-modal").draggable({
		handle: ".modal-body"
	});

})
</script>
