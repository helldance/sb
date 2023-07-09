<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<div  class="clearfix">
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<h2 class="contentHeader">Reset Password for username:"${user.username}"</h2>
		</div>


		<div class="widget-body">
			<div class="form">
				<form:form method="POST" modelAttribute="user" action="resetPassword" class="form-horizontal"  style="margin-bottom: 0px" id="myForm">
				<form:hidden path="id" />
				<form:hidden path="username"/>

				<div class="widget-main">
					<fieldset>

						<div class="control-group">
							<label class="control-label" for="password">*Password:</label>
							<div class="controls">
								<form:password path="password" showPassword="false" id="password" />
								<form:errors path="password" cssClass="error" />
							</div>
						</div>




						<div class="control-group">
							<label class="control-label"  for="confirmPassword">*Confirm Password:</label>
							<div class="controls">
								<form:password path="confirmPassword"
								showPassword="false" id="confirmPassword" />
								<form:errors path="confirmPassword" cssClass="error" />
							</div>
						</div>
						<p class="muted">* denote compulsory fields</p>
					</fieldset>
				</div>


				<div class="widget-toolbox  padding-8 clearfix">
					<div class="pull-right">
						<input type="submit"  class= "btn btn-success no-border" value="Reset Password" />
					</div>
					<div class="pull-left">
						<input type="button" id="cancel" class= "btn btn-primary  no-border" value="Back" />
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>


<script>
$(document).ready(function() {	
	$("#cancel").click(function() {
		$("#edit-modal").modal("hide");
	})
})
</script>