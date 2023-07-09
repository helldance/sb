<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>

<link rel="stylesheet"	href="${pageContext.request.contextPath}/resources/css/direction.css"	type="text/css" media="screen" />

<script type="text/javascript">
$(document).ready(function() {


	$(".no-border-input").focus(function() {
		$(this).removeClass("no-border-input");
	});

	$("#setPW").click(function() {
		$("#pw-modal").modal({backdrop:false});	
		return false;
	}) 

	$("#myForm").validate({
		rules : {
			phone : "required",
			displayName: "required",
			email : {
				required : true,
				email : true
			}
		},

		messages : {
		},

		submitHandler : function(form) {
						//$("#checkbox_agree").click();

						$.ajax({
							type : 'POST',
							url: $("#myForm").attr("action"),
							data : $("#myForm").serialize(),
							success: function() {
								alert("success");
								window.location.reload(false); // true if want to get from server
							}
						});
					}
				});

	$("#edit-btn").click(function() {
		$("#myForm").submit();
	})


	$("#pw-form").validate({
		rules : {
			password: { 
                required: true, minlength: 6
          }, 
          confirmPassword: { 
                required: true, minlength: 6/* , equalTo: $("#password").val() */
          }
		},

		messages : {
		},

		submitHandler : function(form) {
						//$("#checkbox_agree").click();

						$.ajax({
							type : 'POST',
							url: $("#pw-form").attr("action"),
							data : $("#pw-form").serialize(),
							success: function() {
								alert("Your password is updated.");
								window.location.reload(false); // true if want to get from server
							}
						});
					}
				});

	$("#pw-form").submit(function(e) {
		e.preventDefault();
	});


	$("#cancel-pw-btn").click(function() {
		$("#pw-modal").modal("hide");
		return false;
	});

});
</script>

<div id="main-content" class="clearfix">
	


	<div class="row-fluid">
		<div class="span12">
			<div class="widget-box">
				<div class="widget-header header-color-blue">
					<h2 class="contentHeader">Edit Profile</h2>
				</div>

				<div class="widget-body">
					<div class="widget-main no-padding">
						<form:form method="POST" action="/safe-link/guardian/edit"
							class="form-horizontal" modelAttribute="userinfo" id="myForm">
							<fieldset>
							<form:hidden path="id"/>
							<form:hidden path="passwd"/>
							<form:hidden path="confirmPassword"/>
							<div class="control-group">
									<label class="control-label" for="loginID">
										Login Id </label>
									<div class="controls">
										<form:input  readonly="true" class="span8 no-border-input" id="login"
											path="login"></form:input>
									</div>
								</div>


								<div class="control-group">
									<label class="control-label" for="phone"> Phone Number
									</label>
									<div class="controls">
										<form:input class="span8 no-border-input" id="phone"
											path="phone"></form:input>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label" for="email"> Email </label>
									<div class="controls">
										<form:input class="span8 no-border-input" id="email"
											path="email"></form:input>
									</div>
								</div>
							</fieldset>
							<div class="widget-toolbox padding-8 clearfix">
								<button class="btn btn-small btn-success pull-right"
									id="edit-btn" ><i class="icon-save bigger-125"></i> &nbsp;Save</button>
								<div class="pull-left">
									<button class="btn btn-primary btn-small no-border" id="setPW">
										<i class="icon-key bigger-125"></i> &nbsp;Change Password
									</button> 
									<button class="btn btn-primary btn-small no-border"  id="edit-cancel" onclick="cancelPrimaryModal(this); return false;">
										<i class="icon-undo bigger-125"></i> &nbsp;Cancel
									</button>
									
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="pw-modal" class="modal hide fade" tabindex="-1">
		<div class="modal-body">
			<div class="widget-box">
				<div class="widget-header header-color-blue">
					<h4><i class="icon-key"></i>Change Password</h4>
				</div>

				<div class="widget-body">
					<form:form method="POST" action="/safe-link/guardian/changePassword"
						class="form-horizontal" modelAttribute="userinfo"
						style="margin-bottom: 0px" id="pw-form">
							<div class="widget-main">
								<fieldset>

									<form:hidden path="id" />
									<form:hidden path="login"/>
<%-- 									<form:hidden path="oldPassword"/>
 --%>									
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
							</div>
							
							<div class="widget-toolbox padding-8 clearfix">
								<button 
									class="btn btn-success pull-right" >
									Submit <i class="icon-arrow-right icon-on-right bigger-110"></i>
								</button>

								<button class="btn btn-primary pull-left"
									id="cancel-pw-btn">
									<i class="icon-remove"></i>Cancel
								</button>
							</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>


</div>



