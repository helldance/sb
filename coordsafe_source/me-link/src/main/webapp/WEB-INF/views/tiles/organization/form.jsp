<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script>
        function dismiss() {
    	$("#edit-modal").modal("hide");
    }
    </script>
</head>
<body >
	<c:if test="${!organization.create}"><c:set var="action" value="edit" /></c:if>
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<h2 class="contentHeader">
				<c:choose>
				<c:when test="${organization.create}">Create </c:when>
				<c:otherwise>Edit </c:otherwise>
			</c:choose>
			Organization
		</h2>

	</div>

	<div class="widget-body">

		<form:form method="POST" modelAttribute="organization" action="${action}" class="form-horizontal" style="margin-bottom: 0px">
		<form:hidden path="id"/>

		<div class="widget-main">
			<fieldset>
				<div class="control-group">
					<label class="control-label" for="name">*Name:</label>
					<div class="controls">
						<form:input path="name" id="name" />
						<form:errors path="name" cssClass="error" />
					</div>
				</div>


				<div class="control-group">
					<label class="control-label" for="parentOrganization">*Parent Organization:</label>
					<div class="controls">
						<form:select path="parentOrganization" size="5" cssClass="selectArea">
						<form:option value="" label="None" />
						<form:options items="${organizations}" />
					</form:select>
					<form:errors path="parentOrganization" cssClass="error" />
				</div>
			</div>		


			<div class="control-group">
				<label class="control-label" for="description">Description:</label>
				<div class="controls">
					<form:textarea path="description" id="description" cssClass="textArea" />
				</div>
			</div>

			<p class="muted">* denotes compulsory fields.</p>
		</fieldset>
	</div>


	<div class="widget-toolbox  padding-8 clearfix">
		<div class="pull-right">
			<c:choose>
			<c:when test="${organization.create}">
			<input class="btn  btn-primary no-border" type="submit" value="Create Organization" />
		</c:when>
		<c:otherwise>
		<input class="btn  btn-primary no-border" type="submit" value="Edit Organization" />
	</c:otherwise>
</c:choose>
<input class= "btn  btn-primary no-border" type="reset" value="Reset Form" />
<c:if test="${!organization.create}">
<input class="btn  btn-primary no-border" type="button" value="Back" onclick="dismiss()" />
</c:if>
</div>
</div>

</form:form>


</div>
</div>



<script type="text/javascript">


$(document).ready(function() {

	$("#cancel").click(function() {
		$("#edit-modal").modal("hide");
	})

});

</script>    

</body>
</html>
