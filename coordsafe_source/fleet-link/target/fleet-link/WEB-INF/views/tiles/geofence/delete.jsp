<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
		<c:if test="${failed ne null }">
			<c:out value="${failed}"></c:out>
			<input type="button" value="Back" onclick="javascript:history.back()" />
		</c:if>
<c:if test="${failed eq null }">
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<h4>Delete Vehicle Group</h4>
		</div>

		<div class="widget-body">
			<div class="widget-main">
				<h4><span style="align: center">Are you sure to delete vehicle group "${name}"?</span></h4>
			</div>

			<form:form method="POST" action="delete?name=${name}" class="form-horizontal"  id="myForm" style="margin-bottom: 0px">
			
			<div class="widget-toolbox  padding-8 clearfix">
				<div class="pull-right">
				<input id="submit" class="btn  btn-danger no-border"name="commit" type="submit" value="Delete" />
				</div>
				<div class="pull-left">
				<input id="cancel" type="button"  class="btn  btn-primary no-border" value="Cancel"/>
				</div>
			</div>
		</form:form>
	</div>
</div>
</c:if>


<script type="text/javascript">
$(document).ready(function() {

	$("#cancel").click(function() {
		$("#edit-modal").modal("hide");
	})

	$("#submit").click(function() {
		 var $form = $("#myForm");
		 var loadurl = $("#db-vgroup").attr("href");
		 $.ajax({
           type: "POST",
           url: $form.attr("action"),
           data: $form.serialize(), // serializes the form's elements.
           success: function()
           {
               alert("sucess");
               $("#edit-modal").modal("hide");
			   window.location.reload(false); // true if want to get from server
           },
		   statusCode: {
			   400: function (){alert("Request Failed");}
           		
		   }
         });
		return false;
	})

});

</script>  
</body>
</html>