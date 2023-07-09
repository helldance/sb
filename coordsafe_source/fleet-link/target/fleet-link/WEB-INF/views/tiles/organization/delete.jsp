<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>


	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<h2>Confirmation</h2>
		</div>

		<div class="widget-body">
			<div class="widget-main">
				<h4><span>Are you sure to delete organization <span style="color: red">"${name}"?</span></h4>
			</div>

			<form:form method="POST" action="delete?name=${name}" class="form-horizontal"  style="margin-bottom: 0px">
			
			<div class="widget-toolbox  padding-8 clearfix">
				<input  class="btn  btn-primary no-border"name="commit" type="submit" value="DELETE!" />
				<input id="cancel" type="button"  class="btn  btn-primary no-border" value="Back"/>
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

