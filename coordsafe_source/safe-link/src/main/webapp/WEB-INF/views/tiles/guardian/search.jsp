<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<div class="clearfix">
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<h2 class="contentHeader">
				Guardian Management
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

					<c:forEach var="guardian" items="${guardians}"	varStatus="status">
					<div class="itemdiv dialogdiv">
						<div class="user">
							<img alt="Alexa's Avatar" src="https://si0.twimg.com/profile_images/2814613165/f3c9e3989acac29769ce01b920f526bb_normal.png" />
						</div>

						<div class="body">
							<div class="name">
								<a href="#"><c:out value="${guardian.login}"></c:out></a>
							</div>

							<div class="text">

								"Wards"
								<c:forEach var="ward" items="${guardian.wards}">
								<c:out value="${ward.name}"></c:out> | 
							</c:forEach>
							<br>
							"Email"
							<c:out value="${guardian.email}"></c:out>
							<br>
							"Phone"
							<c:out value="${guardian.phone}"></c:out>
							<br>

							"status"
							<c:out value="${guardian.enabled}"></c:out>
							<br>
						</div>

<%-- 						<div class="tools">
							<a class="btn btn-minier btn-info" href="${pageContext.request.contextPath}/guardian/edit?name=${guardian.login}" class="tooltip-info" data-rel="tooltip" title="Edit" data-placement="left" onclick="loadModal(this); return false;">
								<i class=" bigger-125 icon-edit"></i>
							</a>

							<a class="btn btn-minier btn-info" href ="${pageContext.request.contextPath}/guardian/delete?name=${guardian.login}" class="tooltip-info" data-rel="tooltip" title="Delete" data-placement="right" onclick="loadModal(this); return false;">

								<i class="icon-trash bigger-125"></i>

							</a>

							<a   class="btn btn-minier btn-info" href="${pageContext.request.contextPath}/notification/wardNotification?name=${guardian.login}" class="tooltip-info" data-rel="tooltip" title="Notification" data-placement="left" onclick="loadModal(this); return false;">
								<i class="icon-cog 	  bigger-125"></i>
						
							</a>
						</div> --%>


</div>
</div>
</c:forEach>
</div>

</div>  <!-- End of widget main-->

<div class="widget-toolbox  padding-8 clearfix">
	<div class="pull-left">
		<button class= "btn  btn-primary no-border" id="cancel" onclick="cancelPrimaryModal(this); return false;" value="Back">Back</button>
	</div>
	<div class="pull-right">

		<a class="btn  btn-success btn-small no-border" href="<s:url value="/guardian/create"/>?guardian=<sec:authentication property="principal.username"/>" onclick="loadModal(this); return false;">
		Create new
	</a>
</div>
</div>  <!-- End of Widget toolbox -->

</div>  <!-- End of widget body -->
</div>  <!-- End of widget box -->
</div>




