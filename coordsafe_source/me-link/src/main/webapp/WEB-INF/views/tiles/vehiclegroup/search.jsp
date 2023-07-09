<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<div id="main-content" class="clearfix">

	<h2 class="contentHeader"><i class="icon-double-angle-right"></i>
		Vehicle Group 
		<a class="pull-right btn btn-primary no-border btn-small form-op"
		href="<s:url value="/vgroup/new"/>?companyid=<sec:authentication property="principal.company.id"/>"  onclick="loadModal(this); return false;">
		<i class="icon-plus"></i>&nbsp; Create New</a>
	</h2>

	<table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>Group Name</th>
				<th>Group Description</th>
				<!-- <th>Group Status</th> -->
				<th>Action</th>
			</tr>
		</thead>
		<c:forEach var="vgroup" items="${vgroups}"
		varStatus="status">

		<tr>

	<td><c:out value="${vgroup.groupName}"></c:out></td>
	<td><c:out value="${vgroup.description}"></c:out></td>
	<%-- <td><c:out value="${vgroup.valid}"></c:out></td> --%>


		<td>
			<!-- href="${pageContext.request.contextPath}/vgroup/assign?groupName=${vgroup.groupName}"-->
			<authorize:authorize resourceName="MNU-ADMIN-ROLE-001">
				<a href="${pageContext.request.contextPath}/vgroup/assign?groupName=${vgroup.groupName}" class="tooltip-info" data-rel="tooltip" title="Assign vehicles" data-placement="top"  onclick="loadModal(this); return false;">
					<span class="blue">
						<i class="icon-truck  bigger-125"></i>
					</span>
				</a>
			</authorize:authorize>

			<a  href="${pageContext.request.contextPath}/vgroup/edit?name=${vgroup.groupName}"class="tooltip-info form-op" data-rel="tooltip" title="Edit" data-placement="top"  onclick="loadModal(this); return false;">
				<span class="blue">
					<i class="icon-edit  bigger-125"></i>
				</span>
			</a>
			<a href ="${pageContext.request.contextPath}/vgroup/delete?name=${vgroup.groupName}" class="form-op tooltip-info" data-rel="tooltip" title="Delete" data-placement="top"  onclick="loadModal(this); return false;">
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
	$("#vehicle-submenu").css("display","block");

	var oTable1 = $('#table_report').dataTable( {} );
	oTable1.$("a[data-rel=tooltip]").tooltip();

	$("#edit-modal").draggable({
		handle: ".modal-body"
	});

})
</script>


