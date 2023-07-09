<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>

<div class="clearfix">
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<h4>Delete Vehicle </h4>
		</div>

			
			<div class="widget-body">
				<div class="widget-main">
					<h4><span>Are you sure to delete vehicle "${name}"?</span></h4>
				</div>
	
				<form:form method="POST" action="wards/delete?name=${name}" class="form-horizontal"  id="myForm" style="margin-bottom: 0px">
				
				<div class="widget-toolbox  padding-8 clearfix">
					<div class="pull-right">
					<input id="submit" class="btn  btn-danger no-border"name="commit" type="submit" value="Delete" />
				</div>
				<div class="pull-left">
					<input id="cancel" type="button"  class="btn  btn-primary no-border" onclick="cancelEditModal(this);" value="Back"/>
				</div>
				</div>
			</form:form>
		</div>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function() {

	$("#submit").click(function() {
		//alert("here");
		var $form = $("#myForm");
		var loadurl = $("#db-vehicle").attr("href");
		alert($form.attr("action"));
		$.ajax({
			type: "POST",
			url: $form.attr("action"),
           data: $form.serialize(), // serializes the form's elements.
           success: function()
           {
           	alert("Successfully deleted vehicle");
           	$("#edit-modal").modal("hide");
           	window.location.reload(false); // true if want to get from server
           }
       });
		return false;
	})

});
</script>
