<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>

<div  class="clearfix">
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<c:if test="${!user.create}"><c:set var="action" value="edit/${user.username}" /></c:if>
			<h2 class="contentHeader">
				<c:choose>
				<c:when test="${user.create}">Create </c:when>
				<c:otherwise>Edit </c:otherwise>
			</c:choose>
			User
		</h2>
	</div>

	<div class="widget-body">

		<form:form method="POST" modelAttribute="user" action="${action}"  class="form-horizontal"  style="margin-bottom: 0px" id="myForm">
		<form:hidden path="id"/>

		<div class="widget-main">
			<fieldset>
				<div class="control-group">
					<label class="control-label" for="username">*Username:</label>
					<div class="controls">
						<form:input path="username" id="username" name="username"/>
						<form:errors path="username" cssClass="error" />
					</div>
				</div>

				<c:if test="${user.create}">
				<div class="control-group">
					<label for="password" class="control-label">*Password:</label>
					<div class="controls">
						<form:password path="password" name="password" showPassword="false" id="password" />
						<form:errors path="password" cssClass="error" />
					</div>
				</div>

				<div class="control-group">
					<label for="confirmPassword" class="control-label">*Confirm Password:</label>
					<div class="controls">
						<form:password path="confirmPassword" name="confirmPassword" showPassword="false" id="confirmPassword" />
						<form:errors path="confirmPassword" cssClass="error" />
					</div>
				</div>
			</c:if>


			<div class="control-group">
				<label for="email" class="control-label">E-mail:</label>
				<div class="controls">
					<form:input path="email" name="email" id="email" />
					<form:errors path="email" cssClass="error" />
				</div>
			</div>

			<div class="control-group">
				<label for="contact" class="control-label">Mobile Phone:</label>
				<div class="controls">
					<form:input path="contact" name="contact" id="contact" />
					<form:errors path="contact" cssClass="error" />
				</div>
			</div>


			<div class="control-group">
				<label for="company" class="control-label">*Company:</label>
				<div class="controls">
					<form:select path="companyName"  name="companyName" class="required">
					<form:option value="" label="===Select Company===" />
					<form:options items="${companys}" itemValue="id" itemLabel="name" />
				</form:select>
				<form:errors path="companyName" cssClass="error" />
			</div>
		</div>
	<p class="muted">* denote compulsory fields</p>
</fieldset>
</div>

				<div class="widget-toolbox  padding-8 clearfix">
					<div class="pull-right">
						<!--
						<c:choose>
							<c:when test="${user.create}">
								<input type="submit" class="btn btn-success no-border"
									value="Create" />
							</c:when>
							<c:otherwise>
								<input type="submit" class="btn btn-success no-border"
									value="Edit" />
							</c:otherwise>
						</c:choose>
					-->
								<button class="btn  btn-success no-border" type="submit">
							<i class="icon-save bigger-125"></i> &nbsp;Save
						</button>
					</div>
					<div class="pull-left">
						<input id="cancel" class="btn btn-primary no-border" type="button"
							value="Back" /> <input type="reset"
							class="btn  btn-primary no-border" value="Reset Form" />

					</div>
				</div>
			</form:form>
	</div>
</div>
</div>

<script type="text/javascript">
$(document).ready(function() {

	$("#cancel").click(function() {
		$("#edit-modal").modal("hide");
	});
	
	$.validator.addMethod("alphanumeric", function(value, element) {
        return this.optional(element) || /^[a-zA-Z0-9\/_]+$/.test(value);
	});
	
	$.validator.addMethod("emailaddr", function(value, element) {
		if (this.optional(element)) // return true on optional element
            return true;
		
        valid = true;

        if (value.indexOf("@") > 0) {
            valid = valid && $.validator.methods.emailaddr.call(this, value, element);
        } else {
            valid = valid && $.validator.methods.alphanumeric.call(this, value, element);
        }
        
        return valid;
        
	}, 'Please provide a valid username or email');

	$("#myForm").validate({
		rules : {
			"username" : {required: true, alphanumeric: true,  maxlength: 8, minlength: 4},
			password : "required",
			confirmPassword : {
				required: true,
				equalTo: "#password"
			},
			"email" : {
				emailaddr: true
			},
			companyName: "required"
		},
		
		messages: {
			"username" : {required: "Please enter user name", alphanumeric: "Only alphanumeric characters, e.g, a-zA-Z0-9 and _ allowed"},
			"confirmPassword" : {equalTo: "Password does not match"}
		},
		
		submitHandler : function(form) {			
			$.ajax({
				type : 'POST',
				url: $("#myForm").attr("action"),
				data : $("#myForm").serialize(),
				success: function(result) {
					//alert(data);
					if (result.csCode === 600){
						//alert(result.userMsg);				
					
						$("#edit-modal").modal("hide");
						window.location.reload(true); // true if want to get from server 
					}
					else if (result.csCode === 700){
						alert(result.userMsg);
					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
		        	  alert(textStatus + ": failed to save user, unspecified error");
		        }
			});
		}
	});

});
</script>
