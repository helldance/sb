<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>


<div class="clearfix">
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<h2 class="contentHeader">
				Ward Management
			</h2>
		</div>

		<script type="text/javascript">
			var h5 = false;
			var vehs = [];

			if (Modernizr.localstorage) {
				h5 = true;
			} 
		</script>

		<div class="widget-body">
			<div class="widget-main">
				<div class="dialogs">

					<c:forEach var="ward" items="${wardList}"	varStatus="status">
					<div class="itemdiv dialogdiv">
						<div class="user">
							<img alt="Alexa's Avatar" src="${ward.photourl}" />
						</div>

						<div class="body">
							<div class="name">
								<a href="#"><c:out value="${ward.name}"></c:out></a>
							</div>

							<div class="text">

								"Guardians"
								<c:forEach var="guardian" items="${ward.guardians}">
								<c:out value="${guardian.login}"></c:out> | 
							</c:forEach>
							<br>
							"locator imei"
							<c:out value="${ward.locator.imeiCode}"></c:out>
							<br>

							"status"
							<c:out value="${ward.status}"></c:out>
							<br>
						</div>

						<div class="tools">
							<a class="btn btn-minier btn-info" href="${pageContext.request.contextPath}/wards/edit?name=${ward.name}" class="tooltip-info" data-rel="tooltip" title="Edit" data-placement="left" onclick="loadEditModal(this); return false;">
								<i class=" bigger-125 icon-edit"></i>
							</a>

							<a class="btn btn-minier btn-info" href ="${pageContext.request.contextPath}/wards/delete?name=${ward.name}" class="tooltip-info" data-rel="tooltip" title="Delete" data-placement="right" onclick="loadEditModal(this); return false;">

								<i class="icon-trash bigger-125"></i>

							</a>
<!--
			<a  class="btn btn-minier btn-info" href ="${pageContext.request.contextPath}/geofence/within?name=${ward.name}" class="tooltip-info" data-rel="tooltip" title="Check Zone" data-placement="right" onclick="loadEditModal(this); return false;">
				<i class="icon-flag bigger-125"></i>
				
			</a>
			<a  class="btn btn-minier btn-info" href ="<s:url value="/geofence/search"/>?createby=<sec:authentication property="principal.username"/>&type=fence" class="tooltip-info" data-rel="tooltip" title="Manages Zone" data-placement="right" >
			<i class="icon-ok bigger-125"></i>
			
		</a>
	-->
	<a   class="btn btn-minier btn-info" href="${pageContext.request.contextPath}/notification/wardNotification?name=${ward.name}" class="tooltip-info" data-rel="tooltip" title="Notification" data-placement="left" onclick="loadEditModal_notification(this); return false;">
		<i class="icon-cog 	  bigger-125"></i>

	</a>
</div>
</div>
</div>
</c:forEach>
</div>

</div>  <!-- End of widget main-->

<div class="widget-toolbox  padding-8 clearfix">
	<div class="pull-left">
		<button class= "btn  btn-primary no-border" id="cancel" value="Back" onclick = "cancelPrimaryModal(this); return false;">Back</button>
	</div>
	<div class="pull-right">

		<a class="btn  btn-success btn-small no-border" href="<s:url value="/wards/create"/>?guardian=<sec:authentication property="principal.username"/>" onclick="loadEditModal(this); return false;">
		Create new
	</a>
</div>
</div>  <!-- End of Widget toolbox -->

</div>  <!-- End of widget body -->
</div>  <!-- End of widget box -->
</div>



<script>

	function loadEditModal_notification(p) {
		loadEditModal(p);
		$("#edit-modal").addClass("extra-wide");
	}

	$(document).ready(function() {


		$("img").each(function(){
			src=encodeURI(this.src);
			//alert(src);
			this.src = src;
		}); 
	})

</script>


