<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<h2 class="deleteHeader">Delete Group "${name}"?</h2>
	
	<div class="form">
		<form:form method="POST" action="delete?name=${name}">
			<div class="actionButtons">
				<input name="commit" type="submit" value="DELETE!" />
				<input type="button" value="Back" onclick="javascript:history.back()" />
			</div>
		</form:form>
	</div>
	

</body>
</html>