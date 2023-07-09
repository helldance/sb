<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>

<script src="${pageContext.request.contextPath}/resources/assets/js/date-time/bootstrap-datepicker.min.js"></script>
<script>
$(document).ready(function() {
	$("#cancel").click(function() {
		$("#edit-modal").modal("hide");
	})


	$("#createdDate").datepicker({
		format: "dd/mm/yyyy"
	});
	$("#myForm").validate({
		rules : {
			name: "required",
			phone : "required",
			mobile: "required",
			createdDate: "required",
			emailaddress : {
				required : true,
				email : true
			}
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
})
</script>

<div class="clearfix">

	<c:if test="${!company.create}">
	<c:set var="action" value="edit" />
</c:if>

<c:if test="${company.create}">
<c:set var="action" value="create" />
</c:if>


<div class="widget-box">
	<div class="widget-header  header-color-blue">

		<h2 class="contentHeader">
			<c:choose>
			<c:when test="${company.create}">Create </c:when>
			<c:otherwise>Edit </c:otherwise>
		</c:choose>
		Company
	</h2>
</div>

<div class="widget-body">

	<form:form method="POST" modelAttribute="company" action="/fleet-link/company/${action}"  class="form-horizontal"  style="margin-bottom: 0px" id="myForm">

	<form:hidden path="id"/>

	<div class="widget-main">
		<fieldset>

			<div class="control-group">
				<label class="control-label" for="name">*Name:</label>
				<div class="controls">
					<form:input path="name" id="name" name="name"/>
					<form:errors path="name" cssClass="error" />
				</div>
			</div>



			<div class="control-group">
				<label class="control-label" for="description">Description:</label>
				<div class="controls">
					<form:textarea path="description" id="description" cssClass="textArea" />
				</div>
			</div>


			<div class="control-group">
				<label class="control-label" for="connection">Contact Person:</label>
				<div class="controls">
					<form:input path="connection" id="connection"  />
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="phone">*Phone:</label>
				<div class="controls">
					<form:input path="phone" id="phone" name="phone"/>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label" for="mobile">*Mobile Phone:</label>
				<div class="controls">
					<form:input path="mobile" id="mobile" name="mobile"/>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label" for="emailaddress">*Email:</label>
				<div class="controls">
					<form:input path="emailaddress" id="emailaddress" name="emailaddress"/>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label" for="address">Address:</label>
				<div class="controls">
					<form:textarea path="address" id="address" cssClass="textArea"/>
				</div>
			</div>
<!--
			<div class="control-group">
				<label class="control-label" for="createdDate">*Created Date:</label>
				<div class="controls">
					<form:input path="createdDate" id="createdDate" name="createdDate"/>
				</div>
			</div>
-->
			<p class="muted">* denotes compulsory fields.</p>
		</fieldset>
	</div>

	<div class="widget-toolbox  padding-8 clearfix">
		<div class="pull-left">
			<input id="cancel" class= "btn  btn-primary no-border" type="button" value="Back"/>
			<input class= "btn  btn-primary no-border" type="reset" value="Reset Form" />

		</div>
		<div class="pull-right">
			<!--
			<c:choose>
			<c:when test="${company.create}">
			<input type="submit" class= "btn  btn-success no-border" value="Create Company" />
		</c:when>
		<c:otherwise>
		<input class= "btn  btn-success no-border" type="submit" value="Edit Company" />
	</c:otherwise>
</c:choose>	
-->
<button class="btn  btn-success no-border" type="submit">
	<i class="icon-save bigger-125"></i> &nbsp;Save
</button>
</div>
</div>
</form:form>
</div>
</div>
</div>

