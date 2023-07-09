<!DOCTYPE html>
<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
        html {
            height: 100%;
        }

        body {
            height: 100%;
            margin: 0;
            padding: 0;
        }

        #map-canvas {
            height: 100%;
        }

        .info {
            width: 250px;
            height: 250px;
        }
    </style>
    <script type="text/javascript" src="resources/scripts/chart/date.format.1.2.3.min.js"></script>
    <script type="text/javascript" src="resources/scripts/main_page.js"></script>
    <script type="text/javascript" src="resources/scripts/chart/g.line.js"></script>
    <script type="text/javascript" src="resources/scripts/chart/playback.js"></script>
    <script type="text/javascript"
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDutajpk9PbTWpSFv3Cg-O3Nec7L8rMhPY&sensor=false">
    </script>

    <script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/scripts/modal-operation.js"></script>

    <script type="text/javascript">
        var map1;
        var infowindow;
        var poly = [];
        var pathList = [];
        var trip_markers = [];
        function initialize() {
            var mapOptions = {
                center : new google.maps.LatLng(1.365247, 103.801235),
                zoom : 12,
                mapTypeId : google.maps.MapTypeId.ROADMAP
            };
            map1 = new google.maps.Map(document.getElementById("map-canvas"),
                    mapOptions);

            markLocators(map1);

        }
        google.maps.event.addDomListener(window, 'load', initialize);
    </script>
    <script>
        function markLocators(map1) {
            //clear locators
            locators = [];
            //var infowindow;

            var address;
            var bounds = new google.maps.LatLngBounds();
            var geocoder = new google.maps.Geocoder();
            var req = "/safe-link/guardian/id/" + ${guardian.id};
            $.getJSON(req, function(data) {

                for ( var i = 0; i < data.length; i++) {
                    var latlng = new google.maps.LatLng(
                            data[i].lat, data[i].lon);
                    bounds.extend(latlng);
                    console.log("photo64=", document.URL
                            + data[i].photo32);
                    photourl = document.URL + data[i].photo32;
                    img = {
                        url : photourl,
                        size : new google.maps.Size(32, 32),
                        origin : new google.maps.Point(0, 0),
                        anchor : new google.maps.Point(16, 32)
                    };
                    console.log("the name is " + data[i].name + " "
                            + latlng);
                    var marker = new google.maps.Marker({
                        position : latlng,
                        map : map1,
                        title : data[i].name,
                        icon : img
                    });

                    // Adding an event-listener
                    (function(i, marker) {
                        google.maps.event.addListener(
                             marker, 'click',function() {
                                 // Check to see if the infowindow already exists
                                 if (!infowindow) {
                                     // Create a new InfoWindow object
                                     infowindow = new google.maps.InfoWindow();
                                 }

                                 //Finding address of ward

                                 latlng = new google.maps.LatLng(
                                         data[i].lat,
                                         data[i].lon);
                                 geocoder.geocode({
                                     'latLng' : latlng
                                 },
        function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                if (results[0]) {
                    address = results[0].formatted_address;
                    console
                            .log(data[i].name
                                    + address + "deviceid=" + data[i].deviceid);
                    var deviceid = data[i].deviceid;
                    var geofence = '';
                    console.log("data[i].gfs.length" + data[i].gfs.length);
                    for(var j = 0; j< data[i].gfs.length; j++){

																						

                        geofence += '<br/>' + data[i].gfs[j] ;

                    }
                    console.log("geofence = " + geofence);
                    var content = '<div class="info"><b>'
                            + data[i].name
                            + '</b><br/> <img src='+ data[i].photo64 + ' alt=""/>'
                            //+ data[i].gfs 
                            + geofence
                            +'<br/><hr><b>Current Address:</b>'
                            + address
                            + '<hr>';
                    content += '<div id="action_btn" class="pull-right"><input class="btn btn-purple btn-small no-border" type="button" value="View History" onclick="queryHistory('+ deviceid +')"/>';
                    content += '&nbsp;<input class="btn btn-info btn-small no-border" type="button" value="Zoom to" onclick="zoomToObject('
                            + data[i].lat
                            + ','
                            + data[i].lon
                            + ', 20)" />';
                    content += '</div>';
                    content += '</div>';
                    //infowindow
                    infowindow
                            .setContent(content);

                    infowindow
                            .open(
                                    map1,
                                    marker);
                }
            } else {
                // alert("Geocoder failed due to: " + status);

                //infowindow
                infowindow
                        .setContent('<div class="info"><b>'
                                + data[i].name
                                + '</b><br/> <img src='+ data[i].photo64 + ' alt=""/><br/>'
                                + "Geocoder failed due to: "
                                + status
                                + '</div>');

                infowindow
                        .open(
                                map1,
                                marker);
            }
        });

                                 // Setting the content of the InfoWindow
                                 // Defining the position

                             });

                    })(i, marker);

                    map1.fitBounds(bounds);

                }

            });

        }
    </script>
</head>
<body>
    <div id="map-canvas"></div>

    <div class="modal fade hide" id="primary-modal">
        <div class="modal-body">
            <p>One fine body…</p>
        </div>
        <!--
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal">Close</button>
			<a onclick="loadModal(this); return false;" class="btn btn-primary"
				id="modal_action">Save changes</a>
		</div>
    -->
    </div>

    <div id="edit-modal" class="modal hide fade">
        <div class="modal-body"></div>
    </div>

    <div id="print-modal" class="modal hide fade">

        <div class="modal-body">
            <img id="map_img" width="1024" height="768" />
        </div>
    </div>

    </div>


	<script>
	    function loadPrimaryModal(p) {
	        $("#primary-modal > .modal-body").empty();
	        var href = encodeURI($(p).attr("href"));
	        $("#primary-modal > .modal-body").load(href, function() {
	            $("#primary-modal").modal();
	        });
	    }


	    $(document).ready(function() {

	        /*
			 $("#primary-modal").draggable({
			 handle: ".modal-body"
			 });
			 $("#edit-modal").draggable({
			 handle: ".modal-body"
			 });
			 */

	    });

	    function zoomToObject(lat, lng, level) {
	        map1.panTo(new google.maps.LatLng(lat, lng));
	        map1.setZoom(level);
	    }

	    function queryHistory(loc){
	        $("#btn_search").attr('disabled','disabled');
	        $("#loading").show();
	        //clearHistory();
	        var currentdate = new Date();
        	
	        var month = currentdate.getMonth()+1;
	        var day = currentdate.getDate();
	        var year = currentdate.getFullYear();
	        var hh = currentdate.getHours();
	        var mm = currentdate.getMinutes();

	        var yesterday = new Date();
	        yesterday.setDate(currentdate.getDate()-1);
        	
        	
	        /*         	var stime = (yesterday.getMonth()+1) + '-' + yesterday.getDate() + '-' + year + ' ' + hh +':' + mm;
            
                        var etime = month + '-' + day + '-' + year + ' ' + hh +':' + mm; */
	        var stime = '05-27-2013 14:30';
	        var etime = '05-27-2013 17:36';
        	        	

	        console.log("loc=" + loc);
	        var req = "/safe-link/trip/detail/" + loc + "/" + stime + "," + etime;
            
	        $.getJSON(req, function(data) {
	            $("#loading").fadeOut();
	            $("#btn_search").removeAttr('disabled');

	            //alert(data);
                
	            tripData = data;         
                
	            // if no trip data, alert
	            if ($.isEmptyObject(tripData)){
	                humane.log("No trip performed for chosen period");
	                //alert("No trips performed within chosen period");
	                //loadModal("No trips are found");
	            }                
	            else {
	                drawPath(tripData);
	            }
	        });
            
	        //gr_history(loc, stime, etime);
            
	    }

	    function drawPath(data) {
	        var lng_MAX = -180, lng_MIN = 180, lat_MAX = -90, lat_MIN = 90;
	        var points = [];

	        //draw all trips
	        for ( var i = 0; i < data.length; i++) {
	            tempData = data[i].history;
	            //Get color for polylines and set their option                
	            colors = getColors(data.length);

	            var polyOptions = {
	                strokeColor : colors[i],
	                strokeOpacity : 1,
	                strokeWeight : 6
	            }

	            //draw a hidden polyline used for click event
	            poly[i] = new google.maps.Polyline(polyOptions);

	            poly[i].setMap(map1);

	            var len = tempData.length;

	            // escape 0 len, but why get retrieved?
	            if (len === 0) {
	                continue;
	            }

	            for ( var j = 0; j < len; j++) {
	                pathList[i] = poly[i].getPath();
	                pathList[i].push(new google.maps.LatLng(tempData[j].lat,
							tempData[j].lng));

	                if (tempData[j].lng > lng_MAX)
	                    lng_MAX = tempData[j].lng;
	                if (tempData[j].lng < lng_MIN)
	                    lng_MIN = tempData[j].lng;
	                if (tempData[j].lat > lat_MAX)
	                    lat_MAX = tempData[j].lat;
	                if (tempData[j].lat < lng_MIN)
	                    lat_MIN = tempData[j].lat;
	            }

	            //Add numbered marker
	            var midP = new google.maps.LatLng(
						tempData[Math.floor(len / 2)].lat, tempData[Math
								.floor(len / 2)].lng);
	            var _mkr = createNumberMarker(midP, i + 1);

	            //TODO: Add infowindow on mouseover
	            addInfowindowToMarker2(data[i].trip, _mkr,infowindow);

	            //Add click/hover event to polyline
	            createPolylineEvent(poly, i);

	            //Set zoom and center of map                
	            points.push(new google.maps.LatLng(lat_MIN, lng_MIN));
	            points.push(new google.maps.LatLng(lat_MAX, lng_MAX));

	        }

	        //$("#trip-option").show();
	        if (points.length > 0) {
	            setMapZoomCenter(map1, points);
	        }

	        points = [];
	    };

	    function createNumberMarker (point, number){             
	        var number_icon = "/safe-link/resources/numbered_marker/number_" + number + ".png";
        	
	        var numMarker = new google.maps.Marker({position: point, icon: number_icon, map: map1}); 
        	
	        trip_markers.push(numMarker);
        	
	        return numMarker;            	
	    }
	
    </script>
</body>
</html>
