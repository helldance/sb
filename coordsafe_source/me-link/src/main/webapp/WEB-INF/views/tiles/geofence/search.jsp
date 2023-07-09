<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>


<div class="clearfix">
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<h2 class="contentHeader">
				<i class="icon-flag"></i>
				Geofence Management 
			</h2>
		</div>
		<!-- End of Widget-header -->

		<div class="widget-body">
			<div class="widget-main">

				<table id="table_report" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>ID</th>
							<th>Type</th>
							<th>Geometry</th>
							<th>Name</th>
							<th>Create Date</th>
							<th>Action</th>
						</tr>
					</thead>
					<c:forEach var="geofence" items="${geofences}" varStatus="status">
					<tr>
						<td><c:out value="${status.count}"></c:out></td>
						<!--
						<td><c:out value="${geofence.zoneType}"></c:out></td>
						<td>
						-->


						<td>
						<c:choose>
						<c:when test="${geofence.zoneType == 'safe'}">
							<span class="label label-success arrowed">
    						  safe
   							 </span>
						</c:when>
						<c:otherwise><span class="label label-important arrowed "> dangerous </span></c:otherwise>
						</c:choose>
						

						</td>
						<%-- <td><c:out value="${geofence.geometry}"></c:out></td> --%>
						<td><img src="<c:out value="${geofence.thumbnail.url}&size=150x80" />" /></td>
						<td><c:out value="${geofence.description}"></c:out></td>
						<td><c:out value="${geofence.createDt}"></c:out></td>
						<td>
							<a
							href="${pageContext.request.contextPath}/geofence/assign?guardianName=<sec:authentication property="principal.username"/>&geofenceid=${geofence.id}"
							class="tooltip-info" data-rel="tooltip" title="Assign wards"
							data-placement="top" onclick="loadEditModal(this); return false;">
							<span class="blue"> <i class="icon-user bigger-125"></i>
							</span>	</a> 
							<a
							href="${pageContext.request.contextPath}/geofence/edit?geofenceid=${geofence.id}"
							class="tooltip-info" data-rel="tooltip" title="Check"
							data-placement="top"  onclick="loadEditModal(this); return false;" >
							<span class="blue"> <i class="icon-edit  bigger-125"></i>
							</span></a> 
							<a
							href="${pageContext.request.contextPath}/geofence/delete?geofenceid=${geofence.id}"
							class="tooltip-info" data-rel="tooltip" title="Delete"
							data-placement="top"  onclick="loadEditModal(this); return false;" >
							<span class="blue"> <i class="icon-trash bigger-125"></i></span></a>
							<a
							href="${geofence.thumbnail.url}&size=1024x768"
							class="tooltip-info" data-rel="tooltip" title="Print"
							data-placement="top">
							<span class="blue"> <i class="icon-print bigger-125"></i>
							</span>
						</a>
					</td>
				</tr>
			</c:forEach>
		</table>

	</div>
	<!--End of Widget-main -->
	<div class="widget-toolbox  padding-8 clearfix">
		<div class="pull-left">
			<button class= "btn  btn-primary no-border" id="cancel" value="Back" onclick="cancelPrimaryModal(this);"> Back </button>
		</div>
		<div class="pull-right">

			<a class="btn  btn-success btn-small no-border" href="<s:url value="/geofence/new"/>?type=fence"  onclick="loadEditModal(this); return false;">
			Create new
		</a>
	</div>
</div>  <!-- End of Widget toolbox -->

</div>  <!-- End of widget body -->
</div>  <!-- End of widget box -->
</div>





<script> 
	$(document).ready(function() {

		var oTable1 = $('#table_report').dataTable({});

		$('[data-rel=tooltip]').tooltip();

	})


</script>

