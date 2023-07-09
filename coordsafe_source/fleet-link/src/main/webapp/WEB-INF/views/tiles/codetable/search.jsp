<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>


<div id="main-content" class="clearfix">
	<h2 class="contentHeader"><!-- <i class="icon-table blue"></i> -->
		Codetable Management						
		<a class="pull-right btn btn-primary no-border btn-small form-op" class="pageFetcher"
		href="/fleet-link/codetable?new" onclick="loadModal(this); return false;"><i class="icon-plus"></i>&nbsp; Create New
	</a>
</h2>


<%-- 	<display:table name="${codeTables}" defaultsort="1"
		defaultorder="ascending" pagesize="15" requestURI=""
		class="displaytag"
		decorator="com.coordsafe.core.codetable.decorators.CodeTableDecorator">
		<display:column property="type" title="Code Type" sortable="true" style="width: 160px;" />
		<display:column property="code"	title="Code" style="width: 160px;" />
		<display:column property="description" title="Description" />
		<display:column property="action" title="Actions" style="width: 15px;" />
	</display:table> --%>
	
	<table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>Type</th>
				<th>Code</th>
				<th>Description</th>
				<th>Action</th>
			</tr>
		</thead>
		<c:forEach var="codeTable" items="${codeTables}"
		varStatus="status">

		<tr
		<c:if test="${status.count % 2 ne 0}">

	</c:if> >

	<td><c:out value="${codeTable.type}"></c:out></td>
	<td><c:out value="${codeTable.code}"></c:out></td>
	<td><c:out value="${codeTable.description}"></c:out></td>
				<!--
				<td><a class="form-op" href="/fleet-link/codetable/edit?type=${codeTable.type}&code=${codeTable.code}">Edit</a> | <a class="form-op" href ="/fleet-link/codetable/delete?type=${codeTable.type}&code=${codeTable.code}"> Delete</a></td>
			-->
			<td>
				<a  href="/fleet-link/codetable/edit?type=${codeTable.type}&code=${codeTable.code}" class="tooltip-info" data-rel="tooltip" title="Edit" data-placement="left" onclick="loadModal(this); return false;">
					<span class="blue">
						<i class="icon-edit  bigger-125"></i>
					</span>
				</a>
				<a href ="/fleet-link/codetable/delete?type=${codeTable.type}&code=${codeTable.code}" class="tooltip-info" data-rel="tooltip" title="Delete" data-placement="right" onclick="loadModal(this); return false;">
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
	var oTable1 = $('#table_report').dataTable( {
		"iDisplayLength": 100
	} );
	oTable1.$("a[data-rel=tooltip]").tooltip();
	$("#edit-modal").draggable({
		handle: ".modal-body"
	});

})
</script>

