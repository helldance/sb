<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>



<script type="text/javascript">
$(document).ready(function() {
	$("#profile-submenu").css("display","block");

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

	$("#myForm").submit(function(e) {
		e.preventDefault();
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
	})


	$("#cancel-pw-btn").click(function() {
		$("#pw-modal").modal("hide");
		return false;
	})


});
</script>

<div id="main-content" class="clearfix">
	
	<h2 class="contentHeader">
		<i class="icon-double-angle-right"></i> Edit Profile
	</h2>

	<div class="row-fluid">
		<div class="span6">
			<div class="widget-box">
				<div class="widget-header widget-header-flat">
					<h4>My Profile</h4>
				</div>

				<div class="widget-body">
					<div class="widget-main no-padding">
						<form:form method="POST" action="/fleet-link/user/edit"
							class="form-horizontal" modelAttribute="userinfo" id="myForm">
							<fieldset>
							<form:hidden path="id"/>
							<form:hidden path="password"/>
							<form:hidden path="confirmPassword"/>
							<div class="control-group">
									<label class="control-label" for="loginID">
										Login Id </label>
									<div class="controls">
										<form:input  readonly="true" class="span8 no-border-input" id="loginID"
											path="username"></form:input>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label" for="displayName">
										Display Name </label>
									<div class="controls">
										<form:input class="span8 no-border-input" id="displayName" name = "displayName"
											path="userInformation.nickName"></form:input>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label" for="phone"> Phone Number
									</label>
									<div class="controls">
										<form:input class="span8 no-border-input" id="phone"
											path="contact"></form:input>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label" for="email"> Email </label>
									<div class="controls">
										<form:input class="span8 no-border-input" id="email"
											path="email"></form:input>
										<form:errors path="email" />
									</div>
								</div>

								<div class="control-group">
									<label class="control-label" for="address"> Address </label>
									<div class="controls">
										<textarea class="autosize-transition span12" disabled>
											<c:out value="${userinfo.company.address}"></c:out>
											</textarea>
									</div>
								</div>

							</fieldset>
							<div class="widget-toolbox padding-8 clearfix">
								<button class="btn btn-small btn-success pull-right"
									id="edit-btn" type="submit"><i class="icon-save bigger-125"></i> &nbsp;Save</button>
								<div class="pull-left">
									<button class="btn btn-primary btn-small no-border" id="setPW">
										<i class="icon-key bigger-125"></i> &nbsp;Change Password
									</button> 
									<button class="btn btn-primary btn-small no-border" type="reset">
										<i class="icon-undo bigger-125"></i> &nbsp;Reset
									</button>
								</div>
								<!-- <button class="btn btn-small btn-primary pull-left" id="setPW">
									Change Password</button> -->
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
					<form:form method="POST" action="/fleet-link/user/changePassword"
						class="form-horizontal" modelAttribute="userinfo"
						style="margin-bottom: 0px" id="pw-form">
							<div class="widget-main">
								<fieldset>
									<!-- <div class="control-group">
									<label class="control-label" for="old-password"> Old
										password </label>
									<div class="controls">
										<input class="span11" id="old-password" type="password"
											placeholder="Password"></input>
									</div>
								</div> -->
									<form:hidden path="id" />
									<form:hidden path="username"/>
									<form:hidden path="oldPassword"/>
									
									<div class="control-group">
										<label class="control-label" for="password"> *New
											Password </label>
										<div class="controls">
										<form:password path="password" 
											id="password" />
										<form:errors path="password" cssClass="error" />
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
									class="btn btn-success pull-right" type="submit">
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


	<!-- <div id="profile-modal" class="modal hide fade" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-body">
				<div class="widget-box">
					<div class="widget-header">
						<h4>Modify profile</h4>
					</div>

					<div class="widget-body">
						<div class="widget-main no-padding">
							<form class="form-horizontal">
								<fieldset>
									<div class="control-group">
										<label class="control-label" for="display-name"> Display
											Name </label>
											<div class="controls">
												<input class="span8" id="display-name" value="Password"></input>
											</div>
										</div>

										<div class="control-group">
											<label class="control-label" for="email"> Email </label>
											<div class="controls">
												<input class="span8" id="email" value="email"></input>
											</div>
										</div>



										<div class="control-group">
											<label class="control-label" for="phone"> Phone number </label>
											<div class="controls">
												<input class="span8" id="phone" value="phone"></input>
											</div>
										</div>


										<div class="control-group">
											<label class="control-label" for="address"> Address </label>
											<div class="controls">
												<textarea rows="5" cols="30">The cat was playing in the garden.
												</textarea>
											</div>
										</div>

									</fieldset>
								</div>
								<div class="widget-toolbox padding-8 clearfix">
									<button onclick="return false;"
									class="btn btn-small btn-success pull-left">
									Submit <i class="icon-arrow-right icon-on-right bigger-110"></i>
								</button>

								<button class="btn btn-small btn-danger pull-right"
								id="cancel-edit-btn">
								<i class="icon-remove"></i>Cancel
							</button>

						</div>
					</form>
				</div>
			</div>
		</div>
	</div> -->
</div>



