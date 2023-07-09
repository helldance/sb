<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<div class="clearfix">
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<h2 class="contentHeader">'${user.username}' Roles Assignment</h2>
		</div>

		<div class="widget-body">
			<form:form name="assignRoles" method="POST" style="margin-bottom: 0px" id="myForm" class="form-horizontal" >
			<%-- 			<display:table name="${roleList.roles}" defaultsort="2" class="displaytag"
				defaultorder="ascending" pagesize="15" requestURI="" excludedParams="_chk"
				decorator="userAssignRolesCheckboxDecorator">
				<display:column property="checkbox" title="" style="width: 1px" />
				<display:column property="name" title="Role Name" sortable="true" />
				<display:column property="description" title="Description" />
			</display:table>
			<div class="actionButtons">
				<input type="submit" value="Submit" /> 
				<input type="button" value="Back" onclick="javascript:history.back()">
			</div> --%>

			<div class="widget-main">
				<fieldset>
					<table id="table_report" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>Select Role</th>
								<th>Role Name</th>
								<th>Description</th>
							</tr>
						</thead>
						<c:forEach var="role" items="${roleList.getRoles()}"
						varStatus="status">

						<tr><td>
							<c:choose>
							<c:when test="${user.getRoles().contains(role)}">
							<%-- <td><form:checkbox name="_checked" path="" value="" checked /></td> --%>
							<input type="checkbox" name="_chk" value="${role.name}" checked/>
						</c:when>
						<c:otherwise>
						<%-- <td><form:checkbox name="_checked" path="" value="" /></td> --%>
						<input type="checkbox" name="_chk" value="${role.name}" />
					</c:otherwise>
				</c:choose></td>
				<td><c:out value="${role.name}"></c:out></td>
				<td><c:out value="${role.description}"></c:out></td>
			</tr>
		</c:forEach>
	</table>
    </fieldset>
</div>
	<div class="widget-toolbox  padding-8 clearfix">
		<div class="pull-right">
				<button class="btn  btn-success no-border" type="submit">
				<i class="icon-save bigger-125"></i> &nbsp;Save
			</button>
		</div>
		<div class="pull-left">
			<input  class= "btn btn-primary  no-border" type="button" id="cancel" value="Back">
		</div>
	</div>
</form:form>
</div>
</div>
</div>

<script type="text/javascript">
$(function() {
	$("#cancel").click(function() {
		$("#edit-modal").modal("hide");
	})
})
</script>