<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="/fleet-link/resources/assets/js/date-time/bootstrap-datepicker.min.js"></script>
<script type="text/javascript">
	$(function() {
		
		$("#myForm").validate({
			rules : {
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

	});
</script>
</head>
<body>
	<div class="widget-box">
		<%-- <div class="widget-header  header-color-blue">
			<c:set var="action" value="/fleet-link/orderlist/create" />
			<h2 class="contentHeader">Create Task</h2>
		</div>  --%>
		
		<div class="widget-header header-color-blue">
			<c:if test="${orderList.create}"><c:set var="action" value="/fleet-link/orderlist/create" />
			</c:if>
			<c:if test="${!orderList.create}"><c:set var="action" value="/fleet-link/orderlist/edit" />
			</c:if>

			<h2 class="contentHeader">
				<c:choose><c:when test="${orderList.create}">Create </c:when>
					<c:otherwise>Edit </c:otherwise>
				</c:choose>
				Task
			</h2>
		</div>

		<div class="widget-body">
			<form:form method="POST" modelAttribute="orderList" action="${action}"
				class="form-horizontal" style="margin-bottom: 0px" id="myForm">
				<form:hidden path="id" />
				<div class="widget-main">
					<fieldset>
						<div class="control-group">
							<label class="control-label">Assignee:</label>
							<div class="controls">
								<%-- <form:input path="assignee" id="assignee" name="assignee" />
								<form:errors path="assignee" cssClass="error" /> --%>
								<div class="controls">
									<form:select path="assignee">
									<form:option value="" label="Select Vehicle" />
									<form:options items="${vehicles}" itemValue="id" itemLabel="name" />
								</form:select>
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