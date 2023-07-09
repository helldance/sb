<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<h2 class="contentHeader">Search Groups</h2>

	<display:table name="${groups}" defaultsort="1" defaultorder="ascending"
		pagesize="15" requestURI="" class="displaytag"
		decorator="com.coordsafe.core.rbac.decorators.GroupSearchTableDecorator">
		<display:column property="name" title="Name" sortable="true" style="width: 200px;" />
		<display:column property="description" title="Description" />
		<display:column property="action" title="Actions" style="width: 120px;"/>
	</display:table>
</body>

</body>
</html>