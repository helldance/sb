<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<div  class="clearfix">
	<h2 class="contentHeader">'${role.name}' Role Permissions Assignment</h2>

	<div class="form">
		<form:form name="assignPermissions" method="POST" action="">
			<display:table name="${resources}" defaultsort="1" class="displaytag"
				defaultorder="ascending" pagesize="15" requestURI="" excludedParams="currentPerms"
				decorator="roleAssignPermissionsTableDecorator">
				<display:column property="name" title="Resource" sortable="true" />
				<display:column property="type" title="Type" sortable="true" style="width: 200px" />
				<display:column property="viewPermission" title="View" style="width: 140px" />
				<display:column property="openPermission" title="Open" style="width: 140px" />
				<display:column property="executePermission" title="Execute" style="width: 140px" />
			</display:table>
			<div class="actionButtons">
				<input type="submit" value="Submit" /> 
				<input type="button" value="Back" onclick="javascript:history.back()">
			</div>
		</form:form>
	</div>
</div>