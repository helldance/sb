<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>

<link rel="stylesheet"	href="${pageContext.request.contextPath}/resources/css/direction.css"	type="text/css" media="screen" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css" />

<script src="${pageContext.request.contextPath}/resources/assets/js/jquery-1.9.1.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/scripts/jquery.validate.js"></script>

<style>
	#pw-box {
		position: absolute;
		overflow: hidden;
		width: 40%;
		margin-left: 30%;
		border-bottom: medium none;
		padding: 6px;
		box-shadow: 0px 0px 3px rgb(136, 136, 136);
	}
	form {
		margin: 3%;
	}
</style>

<script type="text/javascript">
	$(document).ready(function() {

		$("#pw-form").validate({
			rules : {
				password: { 
					required: true, minlength: 6
				}, 
				confirmPassword: { 
					required: true, minlength: 6,
					equalTo: "#password"
				}
			},

			messages : {
			},

			submitHandler: function(form) {
				form.submit();
			}

		});
	});
	</script>

	<div class="row-fluid">
		<div style="text-align:center">

			<h1>
				<img src="${pageContext.request.contextPath}/resources/img/logo.png"><span> Coord</span>Safe<span>.</span>
			</h1>

		</div>
	</div>
	<div id="pw-box">
	<div>
			<form:form method="POST" action="/safe-link/guardian/resetpassword" class="form-horizontal" modelAttribute="userinfo" style="margin-bottom: 0px" id="pw-form">
			<fieldset>

				<form:hidden path="id" />
				<form:hidden path="login"/>

				<div class="control-group">
					<label class="control-label" for="password"> *New
						Password </label>
						<div class="controls">
							<form:password path="passwd" 
							id="password" />
							<form:errors path="passwd" cssClass="error" />
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="confirmPassword">
							*Confirm Password </label>
							<div class="controls">
								<form:password path="confirmPassword" 
								id="confirmPassword" />
								<br />
								<form:errors path="confirmPassword" cssClass="error" />
							</div>
						</div>
						<p class="muted">* denotes compulsory fields.</p>
					</fieldset>


					<div>
						<button 
						class="btn btn-success pull-right" type="submit">
						Submit <i class="icon-arrow-right icon-on-right bigger-110"></i>
					</button>
				</div>
			</form:form>

		</div>
	</div>
</div>