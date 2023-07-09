<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>

<script>
function dismiss() {
	$("#edit-modal").modal("hide");
}

$(function() {
	$("#myForm").validate({
		submitHandler : function(form) {				
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

	$("#cancel").click(function() {
		$("#edit-modal").modal("hide");
	})
});
</script>

<div class="clearfix">
	<c:if test="${!resource.create}"><c:set var="action" value="edit" /></c:if>

	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<h2 class="contentHeader">
				<c:choose>
				<c:when test="${resource.create}">Create </c:when>
				<c:otherwise>Edit </c:otherwise>
			</c:choose>
			Resource
		</h2>
	</div>


	<div class="widget-body">
		<form:form method="POST" modelAttribute="resource"  id="myForm" action = "/fleet-link/resource/${action}" class="form-horizontal" style="margin-bottom: 0px">
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
					<label class="control-label" for="type">*Type:</label></th>
					<div class="controls">
						<form:select path="type">
						<form:option value="" label="Select Resource Type" />
						<form:options items="${resourceType}" itemValue="code" itemLabel="description" />
					</form:select>
					<form:errors path="type" cssClass="error" />
				</div>
			</div>



			<div class="control-group">
				<label class="control-label" for="value">Value:</label>
				<div class="controls">
					<form:input path="value" id="value"/>
				</div>
			</div>


			<div class="control-group">
				<label class="control-label" for="description">Description:</label>
				<div class="controls">
					<form:textarea path="description" id="description" cssClass="textArea" />
				</div>
			</div>
			
			<p class="muted">* denote compulsory fields</p>

		</fieldset>
	</div>


	<div class="widget-toolbox  padding-8 clearfix">
		<div class="pull-right">
<!--
			<c:choose>
			<c:when test="${resource.create}">
			<input class="btn btn-success no-border" type="submit" value="Create" />
		</c:when>
		<c:otherwise>
		<input class="btn btn-success no-border" type="submit" value="Edit" />
	</c:otherwise>
</c:choose>
-->
<button class="btn  btn-success no-border" type="submit">
	<i class="icon-save bigger-125"></i> &nbsp;Save
</button>
</div>
<div class="pull-left">
	<input class="btn  btn-primary no-border" type="button" id="cancel" value="Back" />
	<input class="btn  btn-primary no-border" type="reset" value="Reset Form" />	
</div>
</div>
</form:form>
</div>
</div>
</div>


