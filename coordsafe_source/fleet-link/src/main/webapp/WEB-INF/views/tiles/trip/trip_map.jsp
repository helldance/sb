<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<!-- <script type="text/javascript" src="http://maps.google.com/maps/api/js?v=3.3&sensor=false"></script> -->
	<!-- <script	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyCSJXB2P-T_MZBpwGp8c_Y7C3bq0pGLXTE&sensor=false"></script> -->
	<style type="text/css">
		/* fix map control broken */
		#map-canvas img {
			max-width: none;
		}
	</style>
	
	<script type="text/javascript">	
		var map;	
		
		var ctx = "${pageContext.request.contextPath}";
		
		var _start = ctx + '/resources/images/marker_greenA.png'; 
	    var _end = ctx + '/resources/images/marker_greenB.png';   
	        
		function initializeMap() {
			console.log("loaidng map");
			
			var mapOptions = {
				zoom : 8,
				center : new google.maps.LatLng(1.29, 103.754)
			};
			
			map = new google.maps.Map(document.getElementById('map-canvas'),
					mapOptions);
		}

		//google.maps.event.addDomListener(window, 'load', initialize);

		function drawTrip(tripData) {
			console.log("draw trip");
			
			var polyOptions = {
				path : tripData,
				strokeColor : '#FF0000',
				strokeOpacity : 1,
				strokeWeight : 6
			}
			
			var poly = new google.maps.Polyline(polyOptions);
			
			poly.setMap(map);
			
			map.fitBounds(bounds);
			
			// draw start, end flag
			var startP = tripData[0];
			var endP = tripData[tripData.length - 1];
			
			plantFlag(startP, _start);
			plantFlag(endP, _end);
		}
		
		function plantFlag(point, icon){
			var marker = new google.maps.Marker({position: point, icon: icon, map: map}); 
		}
	</script>
</head>
<body>
	<div class="widget-box">
		<div class="widget-header  header-color-blue">			
			<h2 class="contentHeader">
				Trip Map
			</h2>
		</div>

		<div class="widget-body">
				<%-- <form:hidden path="id"/> --%>
					<div class="widget-main">
						<div id="map-canvas" style="height:480px">
						</div>						
							<p> 
								<script type="text/javascript">
									var his_data = [];
									var bounds = new google.maps.LatLngBounds();
									
									<c:forEach var="his" items="${tripDetail.history}">	
											var loc = new google.maps.LatLng(${his.lat}, ${his.lng});
											
											his_data.push(loc);					
											
											var curBounds = bounds.toSpan();
											
											if(loc.lat() > curBounds.lat() || loc.lng() > curBounds.lng()){
				                            	bounds.extend(loc);
				                            }
									</c:forEach>		
								
									//drawTrip(his_data);
								</script>
							</p>
						
				</div>
	
				<div class="widget-toolbox  padding-8 clearfix">
					<div class="pull-right">	
						<input class="btn btn-primary no-border" type="button" id="cancel" value="Ok" />
					</div>
				</div>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			initializeMap();
			drawTrip(his_data);
			
			$("#cancel").click(function() {
				$("#edit-modal").modal("hide");
			});
		});
	</script>

</body>
</html>