<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<c:if test="${!group.create}"><c:set var="action" value="edit" /></c:if>
	<h2 class="contentHeader">
		<c:choose>
			<c:when test="${group.create}">Create </c:when>
			<c:otherwise>Edit </c:otherwise>
		</c:choose>
		Group
	</h2>
	<div class="form">
		<form:form method="POST" modelAttribute="group" action="${action}">
			<form:hidden path="id"/>
			<fieldset>
				<table cellspacing="5" class="formTable">
					<tr>
						<th><label for="name">*Name:</label></th>
						<td><form:input path="name" id="name" /><br />
							<form:errors path="name" cssClass="error" />
						</td>
					</tr>
					<tr>
						<th><label for="description">Description:</label></th>
						<td><form:textarea path="description" id="description" cssClass="textArea" /></td>
					</tr>
				</table>
				<p class="muted">* denotes compulsory fields.</p>
				<div class="actionButtons">
					<c:choose>
						<c:when test="${group.create}">
							<input type="submit" value="Create Group" />
						</c:when>
						<c:otherwise>
							<input type="submit" value="Edit Group" />
						</c:otherwise>
					</c:choose>
					<input type="reset" value="Reset Form" />
					<c:if test="${!group.create}">
						<input type="button" value="Back" onclick="javascript:history.back()" />
					</c:if>
				</div>
			</fieldset>
		</form:form>
	</div>

</body>
</html>