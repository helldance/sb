<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>

<script type="text/javascript">
$(function() {

	var names = [];
	var licenses = [];
	
	// get list of vehicles
	if (Modernizr.localstorage) {
		var vehicles = JSON.parse(localStorage.vehicles);
		
		//console.log(vehicles);
		
		$.each(vehicles, function(index, v){		
			names.push(v.name);
			licenses.push(v.license);
		});
	} 
	
	$("#name").focusout(function() {	
		if ($.inArray($("#name").val(), names) !== -1){
			//alert("Ward name already exists");
			$("#name").addClass("tooltip-error");
		}
		else {
			$("#name").removeClass("tooltip-error");
		}
	});
	
	$("#licensePlate").focusout(function() {
		if ($.inArray($("#licensePlate").val(), licenses) !== -1 && createAction){
			//alert("License number already exists");
		}
	});
	
	$("#cancel").click(function() {
		$("#edit-modal").modal("hide");
	});
	
	$.validator.addMethod("alphanumeric", function(value, element) {
        return this.optional(element) || /^[-a-zA-Z0-9\/_]+$/.test(value);
	}); 

	$("#myForm").validate({
		rules : {
			"name" : {required: true, alphanumeric: true,  maxlength: 12},
			vehiclegroup : "required",
			//licensePlate : "required",
			"licensePlate" : {required: true, alphanumeric: true,  maxlength: 12},
			"model" : {maxlength: 20},
			vehiclegroup : "required"
		},
		
		messages: {
			/* name: "Please enter ward name",
			licensePlate: "Please enter license plate" */
			"name" : {required: "Please enter name", alphanumeric: "Only alphanumeric characters allowed, e.g. a-zA-Z0-9 and _ or -"},
			vehiclegroup: "Please select a group",
			"licensePlate": {required: "Please enter license plate", alphanumeric: "Only alphanumeric characters allowed, e.g. a-zA-Z0-9 and _ or -"}
		},

		submitHandler : function(form) {				
			var loadurl = $("#db-ward").attr("href");
			$.ajax({
				type : 'POST',
				url: $("#myForm").attr("action"),
				data : $("#myForm").serialize(),
				success: function(result) {
					//alert(data);
					if (result.csCode === 600){
						//alert(result.userMsg);				
						humane.log("Ward is created successfully");
						$("#edit-modal").modal("hide");
						window.location.reload(true); // true if want to get from server 
					}
					else if (result.csCode === 700){
						alert(result.userMsg);
					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
		        	  alert(textStatus + ": failed to save ward, unspecified error");
		        }
			});
		}
	});

	$("#myForm").submit(function(e) {
		e.preventDefault();
	});
});
</script>

<div class="clearfix">
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<c:if test="${!ward.create}"><c:set var="action" value="edit" />
			</c:if>
			<c:if test="${ward.create}"><c:set var="action" value="create" />

			</c:if>

			<h2 class="contentHeader">
				<c:choose><c:when test="${ward.create}">Create </c:when>
				<c:otherwise>Edit </c:otherwise>
			</c:choose>
			Ward
		</h2>
	</div>


	<div class="widget-body">
		<form:form method="POST" modelAttribute="ward" action="${action}" class="form-horizontal"  style="margin-bottom: 0px" id="myForm">
		<form:hidden path="id"/>
		<div class="widget-main">
			<fieldset>
				<div class="control-group">
					<label class="control-label" for="deviceid">* Device Serial Number:</label>
					<div class="controls">	
						<form:input path="nric" id="nric" name="deviceid"/>
						<form:errors path="nric" cssClass="error" />
					</div>
				</div>		
				<div class="control-group">
					<label class="control-label" for="name">* Ward Name:</label>
					<div class="controls">	
						<form:input path="name" id="name" name="name"/>
						<form:errors path="name" cssClass="error" />
					</div>
				</div>		

				<div class="control-group">
					<label class="control-label" for="photourl">* Photo:</label>
					<div class="controls">	
						<form:input path="photourl" id="photourl" name="photourl"/>
						<form:errors path="photourl" cssClass="error" />
					</div>
				</div>


			</div>		

	

			<p class="muted">* denotes compulsory fields.</p>
		</fieldset>
	</div>
	
	<div class="widget-toolbox  padding-8 clearfix">
		<div class="pull-left">
			<input class= "btn  btn-primary no-border" type="button" id="cancel" value="Back" />
			<input class= "btn  btn-primary no-border" type="reset" value="Reset Form" />		
		</div>
		<div class="pull-right">	
			<button class="btn  btn-success btn-small no-border" type="submit">
				<i class="icon-save bigger-125"></i> &nbsp;Save
			</button>
		</div>
	</div>
</form:form>
</div>
</div>
</div>



