<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script src="/fleet-link/resources/assets/js/date-time/bootstrap-datepicker.min.js"></script>
	<script type="text/javascript">
	$(function() {
		$("#myForm").validate({
			rules : {
				mileage_maunal : "digits",
				fuel_manual : "digits",
			},

			submitHandler : function(form) {				
				var loadurl = $("#db-vehicle").attr("href");
				var jsonStr = {};
				
				
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
</head>
<body>
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<c:set var="action" value="/fleet-link/trip/${trip.id}" />
			<h2 class="contentHeader">
				Edit Trip
			</h2>
		</div>

		<div class="widget-body">
			<form:form method="POST" modelAttribute="trip" action="${action}" class="form-horizontal"  style="margin-bottom: 0px" id="myForm">
				<form:hidden path="id"/>
					<div class="widget-main">
						<fieldset>
							<%-- <div class="control-group">
								<label class="control-label" for="name">Vehicle:</label>
								<div class="controls">	
									<form:input type="text" readonly="true" value= "${trip.locatorId}" />
								</div>
							</div> --%>		
			
							<%-- <div class="control-group">
								<label class="control-label" for="model">Start Time:</label>
								<div class="controls">		
									<form:input path="tripStartTime" id="tripStartTime" />
								</div>
							</div>		
			
							<div class="control-group">
								<label class="control-label" for="type">End Time:</label>
								<div class="controls">					
									<form:input path="tripEndTime" id="tripEndTime" />
								</div>
							</div> --%>		
			
							<div class="control-group">
								<label class="control-label">Add Mileage:</label>
								<div class="controls">					
									<form:input path="mileage_manual" id="mileage_manual" />	
								</div>
							</div>		
			
							<div class="control-group">
								<label class="control-label">Add Fuel:</label>
								<div class="controls">					
									<form:input path="fuel_manual" id="fuel_manual" />
								</div>
						</div>		
			
						<p class="muted">* denotes compulsory fields</p>
					</fieldset>
				</div>
	
				<div class="widget-toolbox  padding-8 clearfix">
					<div class="pull-right">	
			<button class="btn  btn-success no-border" type="submit">
							<i class="icon-save bigger-125"></i> &nbsp;Save
						</button>
					</div>
					<div class="pull-left">
						<input class="btn btn-primary no-border" type="button" id="cancel" value="Cancel" />
						<input class="btn btn-primary no-border" type="reset" value="Reset Form" /> 
					</div>
				</div>
			</form:form>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {

			$("#cancel").click(function() {
				$("#edit-modal").modal("hide");
			});
		});
	</script>

</body>
</html>