<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>

<script src="${pageContext.request.contextPath}/resources/assets/js/date-time/bootstrap-datepicker.min.js"></script>

<script>
$(document).ready(function() {

	$("#cancel").click(function() {
		$("#edit-modal").modal("hide");
	})

	$("#madeDate").datepicker({
		format: "mm/dd/yyyy"
	});

	$("#myForm").validate({
		rules : {
			imeiCode : "required",
			madeDate : "required",
			label : "required",
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
					//alert("success");
					$("#edit-modal").modal("hide");
					//$('#contentCoordsafe').load("/fleet-link/locator/search");
					window.location.reload(false);
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
			<c:if test="${!locator.create}">
			<c:set var="action" value="edit" />
		</c:if>
		<h2 class="contentHeader">
			<c:choose>
			<c:when test="${locator.create}">Create </c:when>
			<c:otherwise>Edit </c:otherwise>
		</c:choose>
		Locator
	</h2>
</div>

<div class="widget-body">
	<form:form method="POST" modelAttribute="locator"
	action="/fleet-link/locator/${action}" class="form-horizontal"
	style="margin-bottom: 0px" id="myForm">
	<form:hidden path="id" />

	<div class="widget-main">
		<fieldset>

			<div class="control-group">
				<label for="name" class="control-label">* IMEI Code:</label>
				<div class="controls">
					<form:input disabled="${!locator.create}" path="imeiCode"
					id="imeiCode" />
					<form:errors path="imeiCode" cssClass="error" />
				</div>
			</div>

			<div class="control-group">
				<label for="nric" class="control-label">Made By:</label>
				<div class="controls">
								<%-- 						<td><form:input path="madeBy" id="madeBy" /><br />
						<form:errors path="madeBy" cssClass="error" />
						</td>
						--%>
						<form:select path="madeBy">
						<form:option value="" label="Select Manufacturer" />
						<form:options items="${madeByType}" itemValue="code"
						itemLabel="description" />
					</form:select>
					<form:errors path="madeBy" cssClass="error" />
				</div>
			</div>

			<div class="control-group">
				<label for="model" class="control-label">Model:</label>
				<div class="controls">
					<form:select path="model">
					<form:option value="" label="Select Model" />
					<form:options items="${modelType}" itemValue="code"
					itemLabel="description" />
				</form:select>
				<form:errors path="model" cssClass="error" />
			</div>
		</div>

		<div class="control-group">
			<label for="madeDate" class="control-label">* Made Date:</label>
			<div class="controls">
				<form:input path="madeDate" readonly="true" id="madeDate"
				name="madeDate" />
				<form:errors path="madeDate" cssClass="error" />
			</div>
		</div>

		<div class="control-group">
			<label for="label" class="control-label">* Label:</label>
			<div class="controls">
				<form:input path="label" id="label" />
				<form:errors path="label" cssClass="error" />
			</div>
		</div>

		<div class="control-group">
			<label for="contact" class="control-label">Allocate To:</label>
			<div class="controls">
				<form:select path="assignedTo">
				<form:option value="" label="Select Company" />
				<form:options items="${company}" itemValue="id"
				itemLabel="name" />
			</form:select>
			<form:errors path="assignedTo" cssClass="error" />
		</div>
	</div>

	<p class="muted">* denotes compulsory fields.</p>
</fieldset>
</div>

				<div class="widget-toolbox  padding-8 clearfix">
					<div class="pull-left">
						<input class="btn  btn-primary no-border" type="button"
							value="Back" id="cancel" /> <input
							class="btn  btn-primary no-border" type="reset"
							value="Reset Form" />

					</div>
					<div class="pull-right">

						<button class="btn  btn-success no-border" type="submit">
							<i class="icon-save bigger-125"></i> &nbsp;Save
						</button>
					</div>
				</div>
			</form:form>
</div>
</div>
</div>