<!DOCTYPE html>

<%@page import="com.coordsafe.locator.service.LocatorService"%>
<%@page import="com.coordsafe.locator.entity.Locator"%>

<%@page import="java.util.List"%>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta charset="UTF-8">
<title>Drawing Tools</title>
<script type="text/javascript"
	src="http://maps.google.com/maps/api/js?v=3.3&sensor=false"></script>
<style type="text/css">
#map,html,body {
	padding: 0;
	margin: 0;
	height: 100%;
}

#panel {
	width: 200px;
	font-family: Arial, sans-serif;
	font-size: 13px;
	float: right;
	margin: 10px;
}

#color-palette {
	clear: both;
}

.color-button {
	width: 14px;
	height: 14px;
	font-size: 0;
	margin: 2px;
	float: left;
	cursor: pointer;
}

#delete-button {
	margin-top: 5px;
}
</style>
</head>
<body>
	<!-- <div id="panel">
		<div id="color-palette"></div>
		<div>
			<button id="delete-button">Delete Selected Shape</button>
		</div>
	</div> -->
	<div id="map" style="width: 100%; height: 100%"></div>
	<script type="text/javascript">
		//if (GBrowserIsCompatible()) {						
			var mapOptions = {
			          center: new google.maps.LatLng(1.395247, 103.751235),
			          zoom: 15,
			          mapTypeId: google.maps.MapTypeId.ROADMAP
			        };
			var map = new google.maps.Map(document.getElementById("map"), mapOptions);
			var locatorImage = '/CoordSafePortalApp/resources/images/locator/locator.png';
			var infoWindow = new google.maps.InfoWindow();
			
			// A function to create the marker and set up the event window
			// Dont try to unroll this function. It has to be here for the function closure
			// Each instance of the function preserves the contends of a different instance
			// of the "marker" and "html" variables which will be needed later when the event triggers.    
			function createMarker(point, info) {
		        //var locatorImage = '/resources/images/locator/locator.png';		
  				var marker = new google.maps.Marker({position: point, icon: locatorImage, map: map}); 
  				
				google.maps.event.addListener(marker,'click',
						function() {
							infoWindow.setContent(info);
							infoWindow.open(map,marker);
						});
				
				return marker;
			}

			// Display the map, with some controls and set the initial location 
			//var map = new GMap(document.getElementById("map"));
			//map.addControl(new GLargeMapControl());
			//map.addControl(new GMapTypeControl());
			//map.setCenter(new GLatLng(1.395247, 103.751235), 15);

			// Set up three markers with info windows
	<%org.springframework.context.ApplicationContext ctx = 
  			  org.springframework.web.context.support.WebApplicationContextUtils.
                      getWebApplicationContext(session.getServletContext());
  	  LocatorService locatorService = (LocatorService)ctx.getBean("locatorService");
  	  List<Locator> locators = null;//locatorService.findLocatorByUser(request.getUserPrincipal().getName());
  	  //out.print("alert("+locators.size()+");");
  	  if (locators.size()>0) {
  		out.print("map.setCenter(new google.maps.LatLng(" + locators.get(0).getGpsLocation().getLatitude()+","+ locators.get(0).getGpsLocation().getLongitude()+"));");
  	  }
  	  
  	  for (Locator loc : locators) {
  	  	  out.print("var point =  new google.maps.LatLng(" + loc.getGpsLocation().getLatitude()+","+ loc.getGpsLocation().getLongitude()+");");
  	  	  String info = "<b>" + loc.getLabel() + "</b><br/> Battery Level : " + 
  	  	  	loc.getDeviceStatus().getBatteryLeft() +" % <br/> GPS is " + 
  	  	  	(loc.getDeviceStatus().getIsGpsOn()? "ON":"OFF") + "<br/> GSM is " + (loc.getDeviceStatus().getIsGsmOn()? "ON":"OFF"); 
  	  	  out.print("var marker = createMarker(point,'" + info + "');");
  	  	  //out.print("map.addOverlay(marker);");
  	  	  //out.print("var marker = new google.maps.Marker({position: point, map: map});");
  	  }%>
		//}

		// display a warning if the browser was not compatible
		/* else {
			alert("Sorry, the Google Maps API is not compatible with this browser");
		} */
	</script>

</body>
</html>
