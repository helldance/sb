<!DOCTYPE html>
<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<html>

<head>
<meta charset="UTF-8" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/direction.css"
	type="text/css" media="screen" charset="utf-8" />
<script
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAM2GxecuxmLMeUc21w3-QuAD_9d2CQj4k&sensor=false&libraries=geometry"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/draw_route.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/json2.js"></script>
<script>
	google.maps.event.addDomListener(window, "load", initialize);
	
	$(function() {
		$("#zone-submenu").css("display", "block");
	});
</script>
</head>

<body>
	<div id="main-content" class="clearfix">

		<h2 class="contentHeader">
			<c:choose>
				<c:when test="${action == 'edit'}">Edit Route
					<script type="text/javascript">
						load_data = ${geometry}.geometry;
						editEnable = true;
						gfId = ${geometry}.id;
					</script>
				</c:when>
				<c:otherwise>Create Route</c:otherwise>
			</c:choose>
		</h2>

		<!-- <div id="map2"></div> -->
		<div class="well">
			<button id="undo" type="submit" value="Undo" onClick="undoRoute()" class="btn btn-primary no-border btn-small"><i class="icon-reply"></i>&nbsp;Undo</button>
			<button id="reset" type="submit" value="Start over" onClick="resetRoute()" class="btn btn-warning no-border btn-small"><i class="icon-undo"></i>&nbsp;Start over</button>
			<button id="close-route" type="submit" value="Close route" onClick="closeRoute()" class="btn btn-danger no-border btn-small"><i class="icon-ok"></i>&nbsp;Close route</button>
			<button id="save" type="submit" value="Save" onClick="save()" class="btn btn-success no-border btn-small" style="margin-right: 12px" ><i class="icon-save"></i>&nbsp;Save</button>
			<input id="snap-to-road" type="checkbox"> <span class="lbl">Snap to road</span>
			<p class="pull-right">
				<span class="lighter text-info" style="font-size: 18px">Distance: 
				<span id="distance" class="text-error">0.00</span> km</span>
				<!-- <span class="lighter text-info" style="font-size: 18px">Distance: 
				<span id="dist" class="text-error">0.00</span> km</span><br><br> -->
			</p>
        			
			<!-- <input id="load" type="submit" value="Load" onClick="load()" class="btn btn-primary no-border btn-small"/> --> 
			<!-- <input
				id="save-image" type="submit" value="Save map image"
				onClick="saveMapImage()" /> -->
		</div>

		<div id="map"></div>
		<!-- <div id="display-image"></div> -->
	</div>
</body>
</html>