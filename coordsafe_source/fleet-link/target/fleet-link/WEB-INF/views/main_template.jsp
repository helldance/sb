<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Welcome to CoordSafe Portal</title>
	<link href="<s:url value="/resources" />/css/msap.css" rel="stylesheet"	type="text/css" />

	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css" />
	<script src="${pageContext.request.contextPath}/resources/assets/js/jquery-1.9.1.min.js"></script>

<!-- 
	The part from profile.jsp
-->	

<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />
<style>
.no-border-input {
	border: none;
	background: transparent;
}

</style>

<script>
jQuery(window).load(function () {
	$("#loading").fadeOut();
});


$(function(){
	$("#sidebar-collapse").click(function() {
		$("#main-content").toggleClass("expand");
		$("#left-bottom").toggle();
	});
}); 

</script>


<!--basic scripts for dashboard begin-->

<!--basic styles-->

<link href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/assets/css/bootstrap-responsive.min.css" rel="stylesheet" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/font-awesome.min.css" />

<!--ace styles-->

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/uncompressed/ace.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/ace-responsive.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/ace-skins.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jackedup.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/uncompressed/custom.css" />


<style>

.modal .btn {
	line-height: 17px;
}


#loading {
	width: 72%;
	margin-left: 28%;
	height: 100%;
	top: 0px;
	left: 0px;
	position: fixed;
	opacity: 0.7;
	background-color: #fff;
	z-index: 99;
}

#loading-icon {
	position: absolute;
	top: 50%;
	left: 50%;
	z-index: 100;
}
</style>


<!--basic scripts for dashboard end-->	

<!-- <script src="resources/assets/js/jquery-1.9.1.min.js"></script> -->
<script src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>

<!--ace scripts-->

<script src="${pageContext.request.contextPath}/resources/assets/js/ace-elements.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/js/ace.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/scripts/jquery-ui-1.10.3.custom.min.withDTpicker.js"></script>
<script src="${pageContext.request.contextPath}/resources/scripts/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath}/resources/scripts/humane.min.js"></script>


<!--page specific plugin scripts-->

<script src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.bootstrap.js"></script>

<!--inline scripts related to this page-->		  

</head>
<body>
	<!-- <div class="wrapper"> -->
<div id="loading">
		<i id="loading-icon" class="icon-refresh blue icon-2x icon-spin"></i>
	</div>


	<div class="row-fluid">
		<div id="header" class="span12" style="text-align: left">
			<t:insertAttribute name="header" />
		</div>
	</div>

	<div id="menu" style="position: fixed">
		<t:insertAttribute name="menu" />
	</div>

	<div class="row-fluid" style="height:100%">
		<div id="content" class="span12" >
			<t:insertAttribute name="content" />
		</div>
	</div>			
</body>
</html>