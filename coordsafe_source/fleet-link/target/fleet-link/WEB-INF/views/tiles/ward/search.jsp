<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<h2 class="contentHeader">Search Wards</h2>

	<display:table name="${wardList}" defaultsort="1" defaultorder="ascending"
		pagesize="15" requestURI="" class="displaytag">
		<display:column property="name" title="Name" sortable="true" style="width: 100px;" />
		<display:column property="nric" title="NRIC" sortable="true" style="width: 50px;" />
<%-- 		<display:column property="locator" title="Locator" sortable="true" style="width: 50px;" />
 --%>	</display:table>
</body>
</html>