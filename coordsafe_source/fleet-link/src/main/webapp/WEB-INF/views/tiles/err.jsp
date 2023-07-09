<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>

<div class="clearfix">
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<h4>Error </h4>
		</div>
			
		<div class="widget-body">
			<div class="widget-main">
				<h4><span>error ?</span></h4>
				<hr>
				<a href = "/fleet-link/">Go back Home</a>
			</div>
				
			<div class="widget-toolbox  padding-8 clearfix">
				<div class="pull-left">
					<input id="cancel" type="button"  class="btn  btn-primary no-border" value="Ok, I know"/>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function() {

	$("#cancel").click(function() {
		$("#edit-modal").modal("hide");
	});
});
</script>
