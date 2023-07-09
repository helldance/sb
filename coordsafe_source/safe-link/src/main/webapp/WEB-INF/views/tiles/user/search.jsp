<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<div id="main-content" class="clearfix">


	<h2 class="contentHeader"><i class="icon-double-angle-right"></i>
		User Management						
		<a class="pull-right btn btn-primary no-border btn-small form-op" class="pageFetcher"
		href="/fleet-link/user?new" onclick="loadModal(this); return false;"><i class="icon-plus"></i>&nbsp; 
		Create New
	</a>
</h2>



<table id="table_user" class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th>Username</th>
			<th>Groups</th>
			<th>Roles</th>
			<th>Company</th>

			<th>Status</th>
			<th>Actions</th>
		</tr>
	</thead>


	<c:forEach var="user" items="${userList.users}"
	varStatus="status">

	<tr
	<c:if test="${status.count % 2 ne 0}">
</c:if> >


<td><c:out value="${user.username}"></c:out></td>
<td><c:out value="${user.groups}"></c:out></td>



<td><c:forEach var="role" items="${user.roles}">
	<c:out value="${role.name}"></c:out> |					
</c:forEach>
</td>

<td>
	<c:out value="${user.company.name}"></c:out>
</td>

<td>
	<c:choose>
	<c:when test="${user.enabled 	}">
	<i class="icon-circle green"></i> &nbsp;Active
</c:when>
<c:otherwise>
<i class="icon-circle red"></i> &nbsp;Inactive
</c:otherwise>
</c:choose>
</td>
<!--
<td>
	<a class="form-op" href="/fleet-link/user/assignRoles?username=${user.username}">Assign Roles</a> 
	|<a class="form-op" href ="/fleet-link/user/assignGroups?username=${user.username}"> Assign Group</a>
	|<a class="form-op" href ="/fleet-link/user/disable?username=${user.username}">Disable</a>
	|<a class="form-op" href ="/fleet-link/user/resetPassword?username=${user.username}">Reset Password</a>
	|<a class="form-op" href ="/fleet-link/user/edit?username=${user.username}">Edit</a>
</td>
-->

<td>
	<a  href="/fleet-link/user/assignRoles?username=${user.username}" class="tooltip-info form-op" data-rel="tooltip" title="Assign Roles" data-placement="top" onclick="loadModal(this); return false;" >
		<span class="blue">
			<i class="icon-user  bigger-125"></i>
		</span>
	</a>

	<a  href="/fleet-link/user/assignGroups?username=${user.username}" class="tooltip-info form-op" data-rel="tooltip" title="Assign Group" data-placement="top"  onclick="loadModal(this); return false;">
		<span class="blue">
			<i class="icon-group  bigger-125"></i>
		</span>
	</a>	

	<a  href="/fleet-link/user/resetPassword?username=${user.username}" class="tooltip-info form-op" data-rel="tooltip" title="Reset Password" data-placement="top" onclick="return false;">
		<span class="blue">
			<i class="icon-key  bigger-125"></i>
		</span>
	</a>	

	<c:choose>
						<c:when test="${user.enabled}">
							<a href="/fleet-link/user/disable?username=${user.username}"
								class="tooltip-info form-op" data-rel="tooltip" title="Disable"
								data-placement="top"> <span class="blue"> <i
									class="icon-remove  bigger-125"></i>
							</span>
							</a>
						</c:when>
						<c:otherwise>
							<a href="/fleet-link/user/enable?username=${user.username}"
								class="tooltip-info form-op" data-rel="tooltip" title="Enable"
								data-placement="top"> <span class="blue"> <i
									class="icon-ok  bigger-125"></i>
							</span>
							</a>
						</c:otherwise>
					</c:choose> 
	<a href ="/fleet-link/user/edit?username=${user.username}" class="tooltip-info form-op" data-rel="tooltip" title="Edit" data-placement="top" onclick="loadModal(this); return false;">
		<span class="blue">
			<i class="icon-edit  bigger-125"></i>
		</span>
	</a>																

</td>

</tr>

</c:forEach>
</table> 

<div id="edit-modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-body" >

	</div>
	<!--
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
		<button class="btn btn-primary">Save changes</button>
	</div>
-->
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
	var oTable1 = $('#table_user').dataTable( {} );
	oTable1.$("a[data-rel=tooltip]").tooltip();
	$("#edit-modal").draggable({
		handle: ".modal-body"
	});
})
</script>
