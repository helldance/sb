<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/direction.css"
	type="text/css" media="screen" charset="utf-8" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/draw_fence.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/json2.js"></script>
<script type="text/javascript"
	src="http://maps.googleapis.com/maps/api/js?sensor=false&libraries=drawing"></script>

<div id="main-content" class="clearfix">
	<div class="widget-header  header-color-blue">
	<h2 class="contentHeader">
		<c:choose>
			<c:when test="${action == 'edit'}">Check Geofence
					<script type="text/javascript">
						load_circle_data =${geometry};
						load_data = ${geometry}.geometry;
						editEnable = true;
						gfId = ${geometry}.id;
						zoneType = ${geometry}.zonetype;
					</script>
			</c:when>
			<c:otherwise>Create Geofence</c:otherwise>
		</c:choose>
	</h2>
	</div>

	<div class="well">
		<c:choose>
			<c:when test="${action != 'edit'}">


<!-- 				<button id="undo" type="submit" value="Undo" onClick="undoRoute()"
					class="btn btn-primary no-border btn-small">
					<i class="icon-reply"></i>&nbsp;Undo
				</button>
				<button id="reset" type="submit" value="Start over"
					onClick="resetRoute()" class="btn btn-warning no-border btn-small">
					<i class="icon-undo"></i>&nbsp;Start over
				</button>
				<button id="close-route" type="submit" value="Close route"
					onClick="closeRoute()" class="btn btn-danger no-border btn-small">
					<i class="icon-ok"></i>&nbsp;Close route
				</button> -->
			</c:when>
		</c:choose>
		<p class="pull-right">
			<!-- <span class="lighter text-info" style="font-size: 18px">Area:
				<span id="area" class="text-error">0.00</span> km<sup>2</sup>
			</span> <br>
			<br>
			<br> -->
						<button id="edit-cancel" value="Back"
						class="btn btn-warning no-border btn-small pull-right" onclick="cancelEditModal(this); return false;">
						<i class="icon-undo"></i>&nbsp;Cancel
					</button>
			<c:choose>
				<c:when test="${action == 'edit'}">
				</c:when>
				<c:otherwise>
					<button id="save" type="submit" value="Save" onClick="save()"
						class="btn btn-success no-border btn-small pull-right">
						<i class="icon-save"></i>&nbsp;Save
					</button>
				</c:otherwise>
			</c:choose>
		</p>

		<c:choose>
			<c:when test="${action != 'edit'}">

				<div class="controls" style="margin-top: 12px">
					<form id="zone" class="form-inline">
						<input type="text" name="zoneName" placeholder ="zone name"> <span class="lbl"> </span>

						<label> <input type="radio" name="zone" value="circle"
							onclick="chooseZone()" checked='checked'> <span class="lbl"> Circle
								Zone</span>
						</label>
						
					</form>
				</div>
			</c:when>
		</c:choose>

		<a id="myTooltip" title="Please choose a zone"></a>


	</div>

	<div id="map">

		<script>
			//$('#edit-modal').on('shown', function () {
			    
				initialize2();
				
			//});
		//google.maps.event.addDomListener(window, "load", );
		//google.maps.event.addDomListener(document.getElementById("map2"), "load", initialize2);
		
 
			</script>

	</div>

	<!-- <div id="display-image"></div> -->
</div>


<script>



	$(document).ready(function() {

		$("img").each(function(){
			src=encodeURI(this.src);
			this.src = src;
		}); 

	})

</script>

