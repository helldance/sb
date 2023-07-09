<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>

<div class="clearfix">
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			
			<c:if test="${!create}"><c:set var="action" value="edit" />
		</c:if>
		<c:if test="${create}"><c:set var="action" value="create" />

	</c:if>

	<h2 class="contentHeader">
		<c:choose><c:when test="${create}">Create </c:when>
		<c:otherwise>Edit </c:otherwise>
	</c:choose>
	SIM
</h2>
</div>


<div class="widget-body" id = "ward_content">
	<form:form method="POST" id="myForm" commandName="retailerInputForm" action="retailer/${action}"
	class="form-horizontal" style="margin-bottom: 0px">

	<form:hidden path="id" />
	<div class="widget-main">
		<fieldset>

		<c:if test="${create}">
		<div class="control-group">
			<label class="control-label" for="deviceid">* Device
				Serial Number:</label>
				<div class="controls">
					<form:input path="deviceid" id="deviceid" name="deviceid" />
					<span class="help-inline"></span>
				</div>

			</div>
			<div class="control-group">
				<label class="control-label" for="gender">* Device
					Password:</label>
					<div class="controls">
						<form:input path="devicepasswd" id="devicepasswd"
						name="devicepasswd" />
						<span class="help-inline"></span>
					</div>
				</div>
				<hr>
							<div class="control-group">
				<label class="control-label" for="gender">* SIM
					Phone:</label>
					<div class="controls">
						<form:input path="simphone" id="simphone"
						name="simphone" />
						<span class="help-inline"></span>
					</div>
				</div>
				
							<div class="control-group">
				<label class="control-label" for="gender">* SIM
					IMEI:</label>
					<div class="controls">
						<form:input path="simimei" id="simimei"
						name="simimei" />
						<span class="help-inline"></span>
					</div>
				</div>
			</c:if>

			

			<p class="muted">* denotes compulsory fields.</p>
		</fieldset>
	</div>

	
	<div class="widget-toolbox  padding-8 clearfix">
		<div class="pull-left">
			<input class= "btn  btn-primary no-border" type="button" id="edit-cancel" value="Back" />
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


<script type="text/javascript">

	$(document).ready (function() {

		var names = [];
		var licenses = [];

	$("#name").focusout(function() {	
		if ($.inArray($("#name").val(), names) !== -1){
			//alert("Ward name already exists");
			$("#name").addClass("tooltip-error");
		}
		else {
			$("#name").removeClass("tooltip-error");
		}
	});
	

	
	$.validator.addMethod("alphanumeric", function(value, element) {
		return this.optional(element) || /^[-a-zA-Z0-9\/_]+$/.test(value);
	}); 

	$("#myForm").submit(function(e) {
		return false;
	});

	
	$("#myForm").validate({
		rules : {
			"name" : {required: true, alphanumeric: true,  maxlength: 12},
			vehiclegroup : "required",
			//licensePlate : "required",
			"licensePlate" : {required: true, alphanumeric: true,  maxlength: 12},
			"model" : {maxlength: 20},
		},
		
		messages: {
			/* name: "Please enter ward name",
			licensePlate: "Please enter license plate" */

			
			"name" : {required: "Please enter name", alphanumeric: "Only alphanumeric characters allowed, e.g. a-zA-Z0-9 and _ or -"},
			vehiclegroup: "Please select a group",
			"licensePlate": {required: "Please enter license plate", alphanumeric: "Only alphanumeric characters allowed, e.g. a-zA-Z0-9 and _ or -"}
		},

		submitHandler : function(form) {				
			//var loadurl = $("#db-ward").attr("href");
			console.log("in the create/edit action");
			$("span.help-inline").text("");
			$(".control-group").removeClass("error");
			var oMyForm = new FormData($("#myForm")[0]);
			$.ajax({
				type : 'POST',
				url: $("#myForm").attr("action"),
       			data: oMyForm,
    			dataType: 'json',
        		//Options to tell jQuery not to process data or worry about content-type.
        		cache: false,
        		contentType: false,
        		processData: false,

				success: function(result) {
			
					if (result.csCode === 600){ 
						
						humane.log("SIM is created successfully");
						$("#edit-modal").modal("hide");
						//window.location.reload(true); // true if want to get from server 
					}
					else if (result.csCode === 700){
						alert("ererere");
						for (var i = 0; i < result.errorMessageList.length; i++) {
							var item = result.errorMessageList[i];
							console.log(item.message);
							var $controlGroup = $('#' + item.fieldName);
							$controlGroup.next().text(item.message).css("color","red");
							$controlGroup.parent().parent().addClass("error");
						}
						
					}
				},

				/*
				error: function(jqXHR, textStatus, errorThrown) {
					alert(textStatus + ": failed to save ward, unspecified error");
				}
				*/
			});
}
});


});

</script>



