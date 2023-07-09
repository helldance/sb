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
	Ward
</h2>
</div>


<div class="widget-body" id = "ward_content">
	<form:form enctype="multipart/form-data" method="POST"  commandName="wardForm" action="wards/${action}"
	class="form-horizontal" style="margin-bottom: 0px">

	<form:hidden path="id" />
	<div class="widget-main">
		<fieldset>
			<div class="control-group">
				<label class="control-label" for="name">* Ward Name:</label>
				<div class="controls">
					<form:input path="name" id="name" name="name" />
					<span class="help-inline"></span>
			</div>
		</div>
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
						<form:input path="devicepassword" id="devicepassword"
						name="devicepassword" />
						<span class="help-inline"></span>
					</div>
				</div>
			</c:if>

			<div class="control-group">
				<label class="control-label" for="photourl">* Photo:</label>
				<div class="controls">
					<form:input path="photourl" id="photourl" name="photourl" />
					<span class="help-inline"></span>
				</div>
			</div>
			
			
			<div class="control-group">
				<label class="control-label" for="photo">* Photo:</label>
				<div class="controls">
					<form:input path="photo" type="file" />
					<span class="help-inline"></span>
				</div>
			</div>
			
<!--
			<div class="control-group">
	 				<label class="control-label" for="photo">* Photo:</label>
	 				<div class="controls">	

	 					<div class="fileupload fileupload-new" data-provides="fileupload">
	 						<div class="fileupload-new thumbnail" style="width: 210px; height: 150px;"><img src="http://www.placehold.it/200x150/EFEFEF/AAAAAA&text=no+image" />
	 						</div>
	 						<div class="fileupload-preview fileupload-exists thumbnail" style="max-width: 210px; max-height: 150px; line-height: 20px;"></div>
	 						<div>
	 							<span class="btn btn-file"><span class="fileupload-new">Select image</span><span class="fileupload-exists">Change</span><form:input path="photo" type="file" name="photo"/></span>
	 							<a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Remove</a>
	 						</div>
	 					</div>
	 				</div>
	 			</div>
	 		-->
			<p class="muted">* denotes compulsory fields.</p>
		</fieldset>
	</div>

	
	<div class="widget-toolbox  padding-8 clearfix">
		<div class="pull-left">
			<input class= "btn  btn-primary no-border" type="button" id="edit-cancel" onclick="cancelEditModal(this);" value="Back" />
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

<script src="${pageContext.request.contextPath}/resources/scripts/bootstrap-fileupload.min.js" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-fileupload.min.css" />

<script type="text/javascript">

	$(document).ready (function() {

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
			//var loadurl = $("#db-ward").attr("href");
			console.log("in the create/edit action");
			$("span.help-inline").text("");
			$(".control-group").removeClass("error");
			var formData = new FormData($("#myForm")[0]);

			$.ajax({
				type : 'POST',
				url: $("#myForm").attr("action"),
				//data : $("#myForm").serialize(),
				xhr: function() {  // Custom XMLHttpRequest
            		var myXhr = $.ajaxSettings.xhr();
                 		if(myXhr.upload){ // Check if upload property exists
                    	myXhr.upload.addEventListener('progress',progressHandlingFunction, false); 	// For handling the progress of the upload
            		}
            		return myXhr;
       			 },
       			 data: formData,
        		//Options to tell jQuery not to process data or worry about content-type.
        		cache: false,
        		contentType: false,
        		processData: false,
				success: function(result) {
					if (result.csCode === 600){ 
						//alert(result.userMsg);				
						humane.log("Ward is created successfully");
						$("#edit-modal").modal("hide");
						load_ward_popup("#wards_link");
						//window.location.reload(true); // true if want to get from server 
					}
					else if (result.csCode === 700){

						for (var i = 0; i < result.errorMessageList.length; i++) {
							var item = result.errorMessageList[i];
							console.log(item.message);
							var $controlGroup = $('#' + item.fieldName);
							$controlGroup.next().text(item.message).css("color","red");
							$controlGroup.parent().parent().addClass("error");
						}
						
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

	function Validate()
	{
	var image =document.getElementById("image").value;
	if(image!=''){
	var checkimg = image.toLowerCase();
	if (!checkimg.match(/(\.jpg|\.png|\.JPG|\.PNG|\.jpeg|\.JPEG)$/)){
	alert("Please enter Image File Extensions .jpg,.png,.jpeg");
	document.getElementById("photo").focus();
	return false;
	}
	}
	return true;
	} 
</script>



