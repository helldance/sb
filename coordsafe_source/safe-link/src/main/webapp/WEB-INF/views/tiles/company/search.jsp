<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>


<div id="main-content" class="clearfix">
	<h2 class="contentHeader"><i class="icon-double-angle-right"></i>
		Company Mangagement
		<a class="pull-right btn btn-primary no-border btn-small form-op"
		href="/fleet-link/company?new"><i class="icon-plus"></i>&nbsp; 
		Create New
	</a>
	</h2>

	<table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>Name</th>
				<th>Description</th>
				<th>Connection</th>
				<th>Address</th>
				<th>Action</th>
			</tr>
		</thead>
		<c:forEach var="company" items="${companys}"
		varStatus="status">

		<tr
		<c:if test="${status.count % 2 ne 0}">

	</c:if> >

	<td><c:out value="${company.name}"></c:out></td>
	<td><c:out value="${company.description}"></c:out></td>
	<td><c:out value="${company.connection}"></c:out></td>
	<td><c:out value="${company.address}"></c:out></td>
<!--
	<td><a href="/fleet-link/company/edit?name=${company.name}">Edit</a> | <a href ="/fleet-link/company/delete?name=${company.name}"> Delete</a></td>
-->
<td>
				<a  href="/fleet-link/company/edit?name=${company.name}" class="tooltip-info form-op" data-rel="tooltip" title="Edit" data-placement="left" onclick="loadModal(this); return false;">
					<span class="blue">
						<i class="icon-edit  bigger-125"></i>
					</span>
				</a>
				<a href ="/fleet-link/company/delete?name=${company.name}" class="form-op tooltip-info" data-rel="tooltip" title="Delete" data-placement="right" onclick="loadModal(this); return false;">
					<span class="blue">
						<i class="icon-trash bigger-125"></i>
					</span>
				</a>
			</td>
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

	$("a").each(function() {
    	var href=$(this).attr("href");
    	$(this).attr("href", encodeURI(href));
    })

	$("a.form-op").click(function(){
		$(".modal-body").empty();
		$(".modal-body").load($(this).attr('href'));
		$("#edit-modal").modal({backdrop:false});	            
		return false;
	});
	
	$('[data-rel=tooltip]').tooltip();

	$("#edit-modal").draggable({
		handle: ".modal-body"
	});

})
</script>
