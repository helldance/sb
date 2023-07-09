<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

	<script type="text/javascript">
	$(function() {
		$.validator.addMethod("alphanumeric", function(value, element) {
	        return this.optional(element) || /^[-a-zA-Z0-9\/_]+$/.test(value);
		});
		
		$("#myForm").validate({
			rules : {
				"groupName" : {required: true, alphanumeric: true,  maxlength: 12}
			},
			
			messages: {
				"groupName" : {required: "Please enter group name", alphanumeric: "Only alphanumeric characters allowed, e.g. a-zA-Z0-9 and _ or -"}
			},

			submitHandler : function(form) {				
				var loadurl = $("#db-vgroup").attr("href");
				$.ajax({
					type : 'POST',
					url: $("#myForm").attr("action"),
					data : $("#myForm").serialize(),
					success: function() {
						//alert("Group is saved");
						$("#edit-modal").modal("hide");
						window.location.reload(false); // true if want to get from server
					}
				});
			}
		});

		$("#myForm").submit(function(e) {
			e.preventDefault();
		});

	});
	</script>



</head>
<body>
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<c:if test="${!vGroup.create}"><c:set var="action" value="edit" /></c:if>
			<c:if test="${vGroup.create}"><c:set var="action" value="create" /></c:if>

			<h2 class="contentHeader">
				<c:choose><c:when test="${vGroup.create}">Create </c:when>
				<c:otherwise>Edit </c:otherwise>
			</c:choose>
			Vehicle Group
		</h2>
	</div>

	<div class="widget-body">
		<form:form method="POST" modelAttribute="vGroup"  action="${action}" class="form-horizontal"  style="margin-bottom: 0px" id="myForm">
		<form:hidden path="id"/>
		<div class="widget-main">	
			<fieldset>


				<div class="control-group">
					<label class="control-label" for="groupName">*Group Name:</label>
					<div class="controls">	
						<form:input path="groupName" id="groupName" />
						<form:errors path="groupName" cssClass="error" />
					</div>
				</div>	

				<div class="control-group">
					<label class="control-label" for="description">Description:</label>
					<div class="controls">	
						<form:textarea path="description" id="description" cssClass="textArea" />
					</div>
				</div>	

				<%-- <div class="control-group">
					<label class="control-label" for="valid">Status:</label>
					<div class="controls">	
						<form:checkbox path="valid" id="valid" />
					</div>
				</div> --%>	

				<p class="muted">* denotes compulsory fields.</p>
			</fieldset>
		</div>
				<div class="widget-toolbox  padding-8 clearfix">
					<div class="pull-right">
						<button class="btn  btn-success no-border" type="submit">
							<i class="icon-save bigger-125"></i> &nbsp;Save
						</button>
					</div>
					<div class="pull-left">
						<input class="btn btn-primary no-border"
							type="button" id="cancel" value="Cancel" />
						<input class="btn  btn-primary no-border" type="reset"
							value="Reset Form" /> 
					</div>
				</div>
			</form:form>
</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	$("#cancel").click(function() {
		$("#edit-modal").modal("hide");
	})
});
</script>
</body>
</html>