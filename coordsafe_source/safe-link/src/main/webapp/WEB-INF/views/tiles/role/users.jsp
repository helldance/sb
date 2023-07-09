<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<div id="main-content" class="clearfix">
	<h2 class="contentHeader">${role.name}: Assign Users</h2>

	<div class="form">
		<form:form name="assignRoles" method="POST" modelAttribute="role">
		
			<display:table name="${userList.users}" defaultsort="2" defaultorder="ascending"
				pagesize="15" requestURI="" class="displaytag"
				decorator="roleShowUsersCheckboxTableDecorator">
				<display:column property="checkbox" title="" style="width: 1px" />
				<display:column property="username" title="Username" sortable="true" />
				<display:column property="enabled" title="Status" style="width: 100px;" />
			</display:table>
			<div class="actionButtons">
				<input type="submit" value="Submit" /> 
				<input type="button" value="Back" onclick="javascript:history.back()">
			</div>
		</form:form>
	</div>		

</div>