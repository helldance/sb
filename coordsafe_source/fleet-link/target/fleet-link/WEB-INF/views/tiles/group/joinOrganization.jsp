<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<h2 class="contentHeader">'${group.name}' Join Organization</h2>

	<div class="form">
		<form:form name="assignRoles" method="POST" modelAttribute="group">
			<display:table name="${organizations}" defaultsort="2" class="displaytag"
				defaultorder="ascending" pagesize="15" requestURI="" excludedParams="_chk"
				decorator="groupJoinOrganizationCheckboxTableDecorator">
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
</body>
</html>