<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<s:url value="/resources" />/css/ui-lightness/jquery-ui-1.8.16.custom.css" 
	rel="stylesheet"
	type="text/css" />
<script src="<s:url value="/resources" />/scripts/jquery.min.js" type="text/javascript" > </script>
<script src="<s:url value="/resources" />/scripts/jquery-ui-1.8.16.custom.min.js" type="text/javascript" > </script>
<script type="text/javascript">
	$(function() {

		var dates = $("#birthDate")
			.datepicker(
					{
						minDate : 0,
						changeMonth : true,
						changeYear : true,
						dateFormat : 'dd/mm/yy',
						showOn : "button",
						buttonImage : "/CoordSafePortalApp/resources/images/common/calendar_edit.png",
						buttonImageOnly : true,
						onSelect : function(selectedDate) {
							var option = this.id == "birthDate" ? "minDate"
									: "maxDate", instance = $(this).data(
									"datepicker"), date = $.datepicker
									.parseDate(
											instance.settings.dateFormat
													|| $.datepicker._defaults.dateFormat,
											selectedDate, instance.settings);
							dates.not(this).datepicker("option", option,
									date);
						}
					});

	});
</script>
</head>
<body>
	<c:if test="${!ward.create}"><c:set var="action" value="edit" /></c:if>
	<h2 class="contentHeader">
		<c:choose>
			<c:when test="${ward.create}">Create </c:when>
			<c:otherwise>Edit </c:otherwise>
		</c:choose>
		Ward
	</h2>
		<div class="form">
		<form:form method="POST" modelAttribute="ward" action="${action}">
			<fieldset>
				<form:hidden path="id"/>
				<table cellspacing="5" class="formTable">
					<tr>
						<th><label for="name">*Name:</label></th>
						<td><form:input path="name" id="name" /><br />
							<form:errors path="name" cssClass="error" />
						</td>
					</tr>
					<tr>
						<th><label for="nric">NRIC:</label></th>
						<td><form:input path="nric" id="nric" /><br />
						<form:errors path="nric" cssClass="error" />
						</td>
					</tr>
					<tr>
						<th><label for="email">E-mail:</label></th>
						<td><form:input path="email" id="email" /><br />
						<form:errors path="email" cssClass="error" />
						</td>
					</tr>
					<tr>
						<th><label for="birthDate">* Date of Birth:</label></th>
						<td><form:input path="birthDate" readonly="true" id="birthDate" /><br />
							<form:errors path="birthDate" cssClass="error" />
						</td>
					</tr>
					
				</table>
				
				<p>* denotes compulsory fields.</p>
				<div class="actionButtons">
					<c:choose>
						<c:when test="${ward.create}">
							<input type="submit" value="Create Ward" />
						</c:when>
						<c:otherwise>
							<input type="submit" value="Edit Ward" />
						</c:otherwise>
					</c:choose>
					<input type="reset" value="Reset Form" />
					<c:if test="${!ward.create}">
						<input type="button" value="Back" onclick="javascript:history.back()" />
					</c:if>
				</div>
			</fieldset>
		</form:form>
	</div>
	<h5>Copyright © 2012 CoordSafe Pte. Ltd. </h5>
</body>
</html>