<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>

<div class="clearfix">
		<c:if test="${!role.create}"><c:set var="action" value="edit" /></c:if>

		<div class="widget-box">
			<div class="widget-header  header-color-blue">
				<h2 class="contentHeader">
					<c:choose>
					<c:when test="${role.create}">Create </c:when>
					<c:otherwise>Edit </c:otherwise>
				</c:choose>
				Role
			</h2>
		</div>
		<div class="widget-body">
			<form:form method="POST" modelAttribute="role" action="${action}"  id="myForm" class="form-horizontal" style="margin-bottom: 0px">
			<form:hidden path="id"/>
			<div class="widget-main">

				<fieldset>
					<div class="control-group">
						<label class="control-label" for="name">*Name:</label>
						<div class="controls">
							<form:input path="name" id="name" name="name" />
							<form:errors path="name" cssClass="error" />
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="type">*Role:</label>
						<div class="controls">
							<form:select path="type" id="type" name="type">
							<form:option value="" label="Select Role Type" />
							<form:options items="${roleType}" itemValue="code" itemLabel="description" />
						</form:select>
						<form:errors path="type" cssClass="error" />
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
							<button class="btn  btn-success no-border" type="submit">
								<i class="icon-save bigger-125"></i> &nbsp;Save
							</button>
						</div>
						<div class="pull-left">
							<input id="cancel" class="btn  btn-primary no-border"
								type="button" value="Back" /> <input
								class="btn  btn-primary no-border" type="reset"
								value="Reset Form" />
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
	})

	$("#myForm").validate({
		rules : {
			name : "required",
			type: "required",
		},

		submitHandler : function(form) {
						//$("#checkbox_agree").click();

						$.ajax({
							type : 'POST',
							url: $("#myForm").attr("action"),
							data : $("#myForm").serialize(),
							success: function() {
								alert("success");
								$("#edit-modal").modal("hide");
								window.location.reload(false); // true if want to get from server
							}
						});
					}
				});

	$("#myForm").submit(function(e) {
		e.preventDefault();
	})
});

</script>    
