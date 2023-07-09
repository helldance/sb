<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to CoordSafe Portal</title>
<link href="<s:url value="/resources" />/css/msap.css" rel="stylesheet" type="text/css" />
<link href="<s:url value="/resources" />/css/displaytag.css" rel="stylesheet" type="text/css" />

</head>
<body>
	<div id="container">

		<div id="content">
			<t:insertAttribute name="content" />
		</div>

	</div>
</body>
</html>