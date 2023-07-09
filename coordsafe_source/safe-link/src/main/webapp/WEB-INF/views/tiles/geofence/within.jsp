<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<div id="main-content" class="clearfix">

	<h2 class="contentHeader">
		<i class="icon-table blue"></i> Geofence Management <a
			class="pull-right btn btn-primary no-border btn-small form-op"
			href="<s:url value="/geofence/new"/>"> <i class="icon-plus"></i>&nbsp;
			Create New
		</a>
	</h2>

	<table id="table_report"
		class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>ID</th>
				<th>Type</th>
				<th>Geometry</th>
				<th>Create By</th>
				<th>Create Date</th>
				<th>Action</th>
			</tr>
		</thead>
		<c:forEach var="geofence" items="${geofences}" varStatus="status">

			<tr>

				<td><c:out value="${geofence.id}"></c:out></td>
				<td><c:out value="${geofence.geometryType}"></c:out></td>
				<td><c:out value="${geofence.geometry}"></c:out></td>
				<td><c:out value="${geofence.createBy}"></c:out></td>
				<td><c:out value="${geofence.createDt}"></c:out></td>


				<td>
					<a
					href="${pageContext.request.contextPath}/geofence/assign?geofenceid=${geofence.id}"
					class="tooltip-info" data-rel="tooltip" title="Assign vehicles"
					data-placement="top" onclick="loadModal(this); return false;">
						<span class="blue"> <i class="icon-truck  bigger-125"></i>
					</span>
				</a> <a
					href="${pageContext.request.contextPath}/geofence/edit?geofenceid=${geofence.id}"
					class="tooltip-info form-op" data-rel="tooltip" title="Edit"
					data-placement="top" onclick="loadModal(this); return false;">
						<span class="blue"> <i class="icon-edit  bigger-125"></i>
					</span>
				</a> <a
					href="${pageContext.request.contextPath}/geofence/delete?geofenceid=${geofence.id}"
					class="form-op tooltip-info" data-rel="tooltip" title="Delete"
					data-placement="top" onclick="loadModal(this); return false;">
						<span class="blue"> <i class="icon-trash bigger-125"></i>
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

	$(function() {
		$("#zone-submenu").css("display", "block");

		var oTable1 = $('#table_report').dataTable({});

		$('[data-rel=tooltip]').tooltip();

		$("#edit-modal").draggable({
			handle : ".modal-body"
		});

	})
</script>


