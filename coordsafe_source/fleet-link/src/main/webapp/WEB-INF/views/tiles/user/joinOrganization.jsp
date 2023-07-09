<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<div id="main-content" class="clearfix">

	<h2 class="contentHeader">'${user.username}' Join Organization</h2>

	<div class="form">
		<form:form name="assignRoles" method="POST" modelAttribute="user">
			<display:table name="${organizations}" defaultsort="2" class="displaytag"
				defaultorder="ascending" pagesize="15" requestURI="" excludedParams="_chk"
				decorator="userJoinOrganizationCheckboxTableDecorator">
				<display:column property="checkbox" title="" style="width: 1px" />
				<display:column property="name" title="Organization Name" sortable="true" />
				<display:column property="description" title="Description" />
			</display:table>
			<div class="actionButtons">
				<input type="submit" value="Submit" /> 
				<input type="button" value="Back" onclick="javascript:history.back()">
			</div>
		</form:form>
	</div>
</div>