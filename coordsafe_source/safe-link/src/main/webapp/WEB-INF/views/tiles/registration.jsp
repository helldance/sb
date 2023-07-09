<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<h2 class="contentHeader">
		User Registration
	</h2>
	<div class="form">
		<form:form method="POST"  action="register">
			<fieldset>
				<form:hidden path="id"/>
				<table cellspacing="5" class="formTable">
					<tr>
						<th><label for="username">*Username:</label></th>
						<td><form:input path="username" id="username" /><br />
							<form:errors path="username" cssClass="error" />
						</td>
					</tr>
						<tr>
							<th><label for="password">*Password:</label></th>
							<td><form:password path="password"
									showPassword="false" id="password" /><br />
									<form:errors path="password" cssClass="error" />
							</td>
						</tr>
						<tr>
							<th><label for="confirmPassword">*Confirm Password:</label></th>
							<td><form:password path="confirmPassword"
									showPassword="false" id="confirmPassword" /><br />
									<form:errors path="confirmPassword" cssClass="error" />
							</td>
						</tr>

					<tr>
						<th><label for="email">E-mail:</label></th>
						<td><form:input path="email" id="email" /><br />
						<form:errors path="email" cssClass="error" />
						</td>
					</tr>
				</table>
				<p>* denotes compulsory fields.</p>
				<div class="actionButtons">
							<input type="submit" value="Register" />
					<input type="reset" value="Reset Form" />
				</div>
			</fieldset>
		</form:form>
	</div>
</body>
</html>