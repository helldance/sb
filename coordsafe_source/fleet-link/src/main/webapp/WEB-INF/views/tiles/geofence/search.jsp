<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<div id="main-content" class="clearfix">

	<h2 class="contentHeader">
		<i class="icon-double-angle-right"></i>
		<c:choose>
			<c:when test="${type == 'fence'}">
				Geofence Management <a
			class="pull-right btn btn-primary no-border btn-small form-op"
			href="<s:url value="/geofence/new?type=fence"/>"> <i class="icon-plus"></i>&nbsp;
			Create New
		</a>
			</c:when>
			<c:otherwise>
				Route Management <a
						class="pull-right btn btn-primary no-border btn-small form-op"
						href="<s:url value="/geofence/new?type=route"/>"> <i class="icon-plus"></i>&nbsp;
						Create New
					</a>
			</c:otherwise>
		</c:choose>			 
	</h2>

	<table id="table_report"
		class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Geometry</th>
				<th>Create By</th>
				<th>Create Date</th>
				<th>Action</th>
			</tr>
		</thead>
		<c:forEach var="geofence" items="${geofences}" varStatus="status">

			<tr>

				<td><c:out value="${geofence.id}"></c:out></td>
				<td><c:out value="${geofence.description}"></c:out></td>
				<%-- <td><c:out value="${geofence.geometry}"></c:out></td> --%>
				<td><img src="<c:out value="${geofence.thumbnail.url}&size=150x80" />" /></td>
				<td><c:out value="${geofence.createBy}"></c:out></td>
				<td><c:out value="${geofence.createDt}"></c:out></td>


				<td>
					<a
					href="${pageContext.request.contextPath}/geofence/assign?geofenceId=${geofence.id}"
					class="tooltip-info" data-rel="tooltip" title="Assign vehicles"
					data-placement="top" onclick="loadModal(this); return false;">
						<span class="blue"> <i class="icon-truck  bigger-125"></i>
					</span>
				</a> <a
					href="${pageContext.request.contextPath}/geofence/edit?geofenceid=${geofence.id}"
					class="tooltip-info form-op" data-rel="tooltip" title="Edit"
					data-placement="top" >
						<span class="blue"> <i class="icon-edit  bigger-125"></i>
					</span>
				</a> <a
					href="${pageContext.request.contextPath}/geofence/delete?geofenceid=${geofence.id}"
					class="form-op tooltip-info" data-rel="tooltip" title="Delete"
					data-placement="top" >
						<span class="blue"> <i class="icon-trash bigger-125"></i>
					</span>
				</a><a
					href="${geofence.thumbnail.url}&size=1024x768"
					class="form-op tooltip-info" data-rel="tooltip" title="Print"
					data-placement="top" onclick="loadModal2(this); return false;">
						<span class="blue"> <i class="icon-print bigger-125"></i>
					</span>
				</a>
				</td>
			</tr>

		</c:forEach>
	</table>
	<div id="edit-modal" class="modal hide fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-body"></div>
	</div>
	<!-- <div id="print-modal" class="modal hide fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal2-body"><img id="map_img" width=480 height=360/></div>
	</div> -->
	
	<div id="print-modal" class="modal hide fade widget-box" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" class="clearfix">
		<!-- <div class="widget-box"> -->
			<!-- <div class="widget-header  header-color-blue">
				<h4>Print Map </h4>
			</div>	 -->			
				<div class="widget-body">
					<div class="modal2-body widget-main"><img id="map_img" width=1024 height=768 /></div>

					<div class="widget-toolbox  padding-8 clearfix">
						<div class="pull-right">
							<input id="submit" class="btn  btn-danger no-border"
								name="commit" type="submit" value="Print" onclick="window.print()"/>
						</div>
						<div class="pull-left">
							<input id="cancel" type="button"
								class="btn  btn-primary no-border" value="Back" onclick="dismiss()" />
						</div>
					</div>
			</div>
	</div>
	
</div>
<script>
	function loadModal(p) {
		$(".modal-body").empty();
		var href = encodeURI($(p).attr("href"));
		$(".modal-body").load(href);
		$("#edit-modal").modal({
			backdrop : false
		});
	}
	
	function loadModal2(p) {
		//$(".modal2-body").empty();
		var href = encodeURI($(p).attr("href"));
		
		$("#map_img").attr("src", href);
		$("#print-modal").modal({
			backdrop : false
		});
	}

	$(function() {
		$("#zone-submenu").css("display", "block");

		var oTable1 = $('#table_report').dataTable({});

		$('[data-rel=tooltip]').tooltip();

		$("#edit-modal").draggable({
			handle : ".modal-body"
		});
		
		$("#print-modal").draggable({
			handle : ".modal2-body"
		});		
	});
	
	function dismiss (){
		$("#print-modal").modal("hide");
	}
</script>


