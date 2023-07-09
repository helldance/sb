<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- <script	src="/fleet-link/resources/assets/js/date-time/bootstrap-datepicker.min.js"></script> -->

<script type="text/javascript">
	$(function() {
		/* $("#targetCompletionDt").datepicker({
			format: "mm/dd/yyyy"
		}); */
		
		$("#targetCompletionDt").datetimepicker({
			dateFormat: 'mm/dd/yyyy',
		});
		
		$("#myForm").validate({
			rules : {
				mileage_maunal : "digits",
				fuel_manual : "digits",
			},

			submitHandler : function(form) {
				var jsonStr = {};

				$.ajax({
					type : 'POST',
					url : $("#myForm").attr("action"),
					data : $("#myForm").serialize(),
					success : function() {
						$("#edit-modal").modal("hide");
						window.location.reload(false); // true if want to get from server	
					}
				});
			}
		});  

		$("#myForm").submit(function(e) {
			e.preventDefault();
		});
		
		$("#address").geocomplete();
		
		$("#address").focusout(function() {
			// restrict addr to 'Singapore' 
			var req = "http://maps.googleapis.com/maps/api/geocode/json?address=" + $("#address").val() + ", singapore&sensor=true";
			
			console.log(req);
			
			$.getJSON(req, function(data){
				console.log(data);
				
				if (data.results.length === 0){
					$("#err_addr").show();
					
					return;
				}
				
				else {
					$("#err_addr").hide();
					
					var latLng = data.results[0].geometry.location;
				
					console.log(latLng);
				
					$("#lat").val(latLng.lat);
					$("#lng").val(latLng.lng);
				}
			});
		});

	});
</script>
</head>
<body>
	<div class="widget-box">
		<%-- <div class="widget-header  header-color-blue">
			<c:set var="action" value="/fleet-link/workorder/create" />
			<h2 class="contentHeader">Create Work Order</h2>
		</div>  --%>
		
		<div class="widget-header header-color-blue">
			<c:if test="${!order.create}"><c:set var="action" value="/fleet-link/workorder/edit" />
			</c:if>
			<c:if test="${order.create}"><c:set var="action" value="/fleet-link/workorder/create" />
			</c:if>

			<h2 class="contentHeader">
				<c:choose><c:when test="${order.create}">Create </c:when>
					<c:otherwise>Edit </c:otherwise>
				</c:choose>
				Work Order
			</h2>
		</div>

		<div class="widget-body">
			<form:form method="POST" modelAttribute="order" action="${action}"
				class="form-horizontal" style="margin-bottom: 0px" id="myForm">
				<form:hidden path="id" />
				<div class="widget-main">
					<fieldset>
						<div class="control-group">
							<label class="control-label" for="orderFrom">* Order From:</label>
							<div class="controls">
								<form:input path="orderFrom" id="orderFrom" name="orderFrom" />
								<form:errors path="orderFrom" cssClass="error" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">* Address:</label>
							<div class="controls">
								<form:input path="address" id="address"
									name="address" />
								<form:errors path="address" cssClass="error" />
								<label id="err_addr" style="display: none" class="error">Invalid address</label>
							</div>
						</div>
						
						<div class="control-group">
							<!-- <label class="control-label">Lat:</label> -->
							<div class="controls">
								<form:hidden path="place.latitude" id="lat"
									name="lat" />
							</div>
							<!-- <label class="control-label">Lng:</label> -->
							<div class="controls">
								<form:hidden path="place.longitude" id="lng"
									name="lng" />
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label">Quantity: </label>
							<div class="controls">
								<form:input path="quantity" id="quantity" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">Target Date:</label>
							<div class="controls">
								<form:input path="targetCompletionDt" id="targetCompletionDt" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label">Remarks:</label>
							<div class="controls">
								<form:input path="remark" id="remark" />
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
						<input class="btn btn-primary no-border" type="button" id="cancel"
							value="Cancel" /> <input class="btn btn-primary no-border"
							type="reset" value="Reset Form" />
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