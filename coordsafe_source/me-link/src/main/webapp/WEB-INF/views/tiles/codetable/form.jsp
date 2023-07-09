<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>

<script src="${pageContext.request.contextPath}/resources/assets/js/date-time/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="/fleet-link/resources/assets/css/datepicker.css" />

<script type="text/javascript">
$(function() {

	$("#cancel").click(function() {
		$("#edit-modal").modal("hide");
	})

	$("#startDate").datepicker({
		format: "dd/mm/yyyy"
	});
	$("#endDate").datepicker({
		format: "dd/mm/yyyy"
	});

	$("#myForm").validate({
		rules : {
			type : "required",
			code : "required",
			description : "required",
			startDate: "required",
			endDate: "required"
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


<div class="clearfix">
	<div class="widget-box">
		<div class="widget-header  header-color-blue">

			<c:if test="${!codeTable.create}"><c:set var="action" value="edit" /></c:if>
			<h2 class="contentHeader">
				<c:choose>
				<c:when test="${codeTable.create}">Create </c:when>
				<c:otherwise>Edit </c:otherwise>
			</c:choose>
			Code
		</h2>
	</div>


	<div class="widget-body">
		<form:form method="POST" modelAttribute="codeTable" action="/fleet-link/codetable/${action}" class="form-horizontal"  style="margin-bottom: 0px" id="myForm">
		<form:hidden path="id"/>
		<form:hidden path="createdDate"/>
		<form:hidden path="deleteIndicator"/>

		<div class="widget-main">
			<fieldset>

				<div class="control-group">
					<label class="control-label" for="type">*Code Type:</label>
					<div class="controls">
						<form:input path="type" id="type" name = "type"/>
						<form:errors path="type" cssClass="error" />
					</div>
				</div>



				<div class="control-group">
					<label class="control-label" for="code">*Code:</label>
					<div class="controls">
						<c:choose>
						<c:when test="${codeTable.create}"><form:input path="code" id="code"  name = "code"/></c:when>
						<c:otherwise><form:input path="code" id="code" readonly="true" name="code"/></c:otherwise>
					</c:choose>
					<form:errors path="code" cssClass="error" />
				</div>
			</div>



			<div class="control-group">
				<label class="control-label" for="description">*Description:</label>
				<div class="controls">
					<form:textarea path="description" id="description" cssClass="textArea" name="description"/>
					<form:errors path="description" cssClass="error" />
				</div>
			</div>


			<div class="control-group">
				<label class="control-label" for="startDate">*Starting Date:</label>
				<div class="controls">
					<form:input path="startDate" readonly="true" type="text" name="startDate" id="startDate"/>
					<form:errors path="startDate" cssClass="error" />
				</div>
			</div>



			<div class="control-group">				
				<label class="control-label" for="endDate">*Ending Date:</label>
				<div class="controls">
					<form:input path="endDate" readonly="true" id="endDate" name="endDate" type="text"/>
					<form:errors path="endDate" cssClass="error" />
				</div>
			</div>

			<p class="muted">* denote compulsory fields</p>
		</fieldset>
	</div>


	<div class="widget-toolbox  padding-8 clearfix">
		<div class="pull-right">
			<!--
			<c:choose>
			<c:when test="${codeTable.create}">
			<input type="submit" class= "btn btn-success no-border" value="Create" />
		</c:when>
		<c:otherwise>
		<input type="submit" class= "btn btn-success no-border" value="Edit" />
	</c:otherwise>
</c:choose>
-->
			<button class="btn  btn-success no-border" type="submit">
							<i class="icon-save bigger-125"></i> &nbsp;Save
						</button>
</div>
<div class="pull-left">
	<input id="cancel" class= "btn btn-primary  no-border" type="button" value="Back"/>
	<input type="reset" class= "btn  btn-primary no-border" value="Reset Form" />
</div>
</div>
</form:form>
</div>
</div>
</div>



