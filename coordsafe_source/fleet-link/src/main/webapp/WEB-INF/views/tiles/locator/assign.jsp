<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<div  class="clearfix">

	<script>
	$(document).ready(function() {
		$("#cancel").click(function() {
			$("#edit-modal").modal("hide");
		})

		$("#myForm").validate({
			rules : {
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


	<div  class="clearfix">

		<div class="widget-box">
			<div class="widget-header  header-color-blue">
				<c:set var="action" value="assign" />
				<h2 class="contentHeader">
					Assign Locator (Label: ${locator.label})
				</h2>
			</div>


			<div class="widget-body">
				<form:form method="POST" modelAttribute="locator" action="/fleet-link/locator/${action}" class="form-horizontal"  style="margin-bottom: 0px" id="myForm">
				<form:hidden path="id"/>

				<div class="widget-main">
					<fieldset>
					<!--
					<label for="label" class="center" >Label: ${locator.label}</label>
				-->

				<div class="control-group">
					<label for="vehicle" class="control-label">Assign To:</label>
					<div class="controls">
						<form:select path="assignedTo">
						<form:option value="" label="Select Vehicle" />
						<form:options items="${vehicles}" itemValue="id" itemLabel="name" />
					</form:select>
				</div>
			</div>
			<p>* <i>denotes compulsory fields.</i></p>
		</fieldset>
	</div>


	<div class="widget-toolbox  padding-8 clearfix">
		<div class="pull-right"> 
			<input  class= "btn  btn-success no-border"  type="submit" value="Assign" />
		</div>
		<div class="pull-left">
			<input  class= "btn  btn-primary no-border" type="button" value="Back" id="cancel" />
		</div>
	</div>
</form:form>
</div>
</div>
</div>




