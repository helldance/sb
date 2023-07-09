

<c:if test="${failed eq null }">
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<h4>Delete Vehicle Group</h4>
		</div>

		<div class="widget-body">
			<div class="widget-main">
				<h4><span style="align: center">Are you sure to delete vehicle group "${name}"?</span></h4>
			</div>

			<form:form method="POST" action="geofence/delete?geofenceid=${zone.id}" class="form-horizontal"  id="myForm" style="margin-bottom: 0px">
			
			<div class="widget-toolbox  padding-8 clearfix">
				<div class="pull-right">
				<input id="submit" class="btn  btn-danger no-border"name="commit" type="submit" value="Delete" />
				</div>
				<div class="pull-left">
				<button id="edit-cancel" type="button"  class="btn btn-primary no-border" value="Cancel" onclick="cancelEditModal(this);return false;">Cancel
				</button>

				</div>
			</div>
		</form:form>
	</div>
</div>
</c:if>



<script type="text/javascript">


$(document).ready(function() {


	$("#submit").click(function() {
		 var $form = $("#myForm");
		 $.ajax({
           type: "POST",
           url: $form.attr("action"),
           data: $form.serialize(), // serializes the form's elements.
           success: function()
           {
               alert("sucess");
               $("#edit-modal").modal("hide");
               load_ward_primary-modal("#geofence_link");
           },
		   statusCode: {
			   400: function (){alert("Request Failed");}
           		
		   }
         });
		return false;
	})

});

</script>  
