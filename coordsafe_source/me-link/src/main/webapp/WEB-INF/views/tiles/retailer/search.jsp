<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>


<div class="clearfix">
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<h2 class="contentHeader">
				<i class="icon-flag"></i>
				SIMs Management 
			</h2>
		</div>
		<!-- End of Widget-header -->

		<div class="widget-body">
			<div class="widget-main">

				<table id="table_report" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>Sim Owner</th>
							<th>Device ID</th>
							<th>Sim IMEI</th>
							<th>Phone</th>
							<th>Create Date</th>
						</tr>
					</thead>
					<c:forEach var="simcard" items="${simcards}"	varStatus="status">
						<tr>
						<td><c:out value="${simcard.simowner}"></c:out></td>
						<td><c:out value="${simcard.deviceid}"></c:out></td>
						<td><c:out value="${simcard.simimei}"></c:out></td>
						<td><c:out value="${simcard.simphone}"></c:out></td>
						<td><c:out value="${simcard.issuedate}"></c:out></td>
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

		<a class="btn  btn-success btn-small no-border" href="<s:url value="/retailer/create"/>?etailer=<sec:authentication property="principal.username"/>" onclick="loadEditModal(this); return false;">
		Create new 	</a>
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

