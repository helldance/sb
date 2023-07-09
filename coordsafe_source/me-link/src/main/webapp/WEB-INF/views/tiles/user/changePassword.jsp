<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<div id="main-content" class="clearfix">

	<h2 class="contentHeader">Change Password for: "${user.username}"</h2>
	<div class="form">
		<form:form method="POST" modelAttribute="user" action="/fleet-link/user/changePassword">
			<form:hidden path="id" />
			<form:hidden path="username"/>
			<fieldset>
				<table cellspacing="5" class="formTable">
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
				</table>
				<p>* denotes compulsory fields.</p>
				<div class="actionButtons">
					<input type="submit" value="Change Password" />
					<input type="button" value="Back" onclick="javascript:history.back()" />
				</div>
			</fieldset>
		</form:form>
	</div>
	
</div>