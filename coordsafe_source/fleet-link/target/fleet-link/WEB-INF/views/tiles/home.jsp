
<!DOCTYPE html>
<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<%@page import="com.coordsafe.locator.service.LocatorService"%>
<%@page import="com.coordsafe.locator.entity.Locator"%>
<%@page import="java.util.List"%>
<%@page import="com.coordsafe.core.rbac.service.UserService"%>

<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
	<meta charset="UTF-8">
	<title>CoordSafe - FleetLink System</title>
	<!--
	<link rel="stylesheet" type="text/css" href="resources/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="resources/css/bootstrap-responsive.min.css">
-->
<link rel="stylesheet" type="text/css" href="resources/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/jquery-ui-1.10.3.custom.css">
<link rel="stylesheet" href="resources/assets/css/uncompressed/ace.css" />
<link rel="stylesheet" href="resources/assets/css/ace-responsive.min.css" />
<link rel="stylesheet" href="resources/assets/css/ace-skins.min.css" />
<!-- <link rel="stylesheet" href="resources/css/card.css" /> -->
	<!--
	<link rel="stylesheet" href="resources/assets/css/font-awesome.min.css" />
	<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300"></link>
-->
<link rel="stylesheet" href="resources/assets/css/uncompressed/custom.css" />
<link rel="stylesheet" href="resources/css/chart/linechart.css" />
<link rel="stylesheet" href="resources/css/jackedup.css" />

<!-- <script type="text/javascript" src="http://maps.google.com/maps/api/js?v=3.3&sensor=false"></script> -->
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?libraries=drawing&sensor=true"></script>
<script type="text/javascript" src="resources/scripts/chart/date.format.1.2.3.min.js"></script>
<script type="text/javascript" src="resources/scripts/chart/raphael.js"></script>
<script type="text/javascript" src="resources/scripts/chart/g.raphael.js"></script>
<script type="text/javascript" src="resources/scripts/chart/g.line.js"></script>
<script type="text/javascript" src="resources/scripts/chart/playback.js"></script>
<script type="text/javascript" src="resources/scripts/main_page.js"></script>
<script type="text/javascript" src="resources/scripts/humane.min.js"></script>
<!-- <script type="text/javascript" src="resources/scripts/underscore.min.js"></script> -->
	<!--
	<script type="text/javascript" src="resources/assets/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="resources/assets/js/bootstrap.min.js"></script>
-->

<!--page specific plugin scripts-->

<script src="resources/assets/js/jquery.dataTables.min.js"></script>
<script src="resources/assets/js/jquery.dataTables.bootstrap.js"></script>

<!--ace scripts-->

	<!--
	<script src="resources/assets/js/ace-elements.min.js"></script>
	<script src="resources/assets/js/ace.min.js"></script>
	<script src="resources/scripts/jquery-ui-1.10.3.custom.min.withDTpicker.js"></script>
-->
<script type="text/javascript" src="resources/scripts/v3_epoly.js"></script>

<script src="resources/scripts/jquery-ui-timepicker-addon.js"></script>
<!--
	<script src="resources/assets/js/bootstrap.min.js"></script>
-->

<style type="text/css">
.ui-timepicker-div .ui-widget-header {
	margin-bottom: 8px;
}

.ui-timepicker-div dl {
	text-align: left;
}

.ui-timepicker-div dl dt {
	height: 25px;
	margin-bottom: -25px;
}

.ui-timepicker-div dl dd {
	margin: 0 10px 10px 65px;
}

.ui-timepicker-div td {
	font-size: 90%;
}

.ui-tpicker-grid-label {
	background: none;
	border: none;
	margin: 0;
	padding: 0;
}

.ui-timepicker-rtl {
	direction: rtl;
}

.ui-timepicker-rtl dl {
	text-align: right;
}

.ui-timepicker-rtl dl dd {
	margin: 0 65px 10px 10px;
}

#cpanel {
	width: 28%;
	height: 0%;
	position: absolute;
	left: 0px;
	z-index: 1;
}

/* fix map control broken */
#map img {
	max-width: none;
}

#info_tabs {
	padding-left: 6px;
	padding-right: 6px;
	background-color: rgb(249, 249, 249);
	-webkit-box-shadow: 2px 2px 3px #666;
}

#resize-btn-chart {
	background-color: rgb(249, 249, 249);
	-moz-box-shadow: 0 0 3px #888;
	-webkit-box-shadow: 0 0 3px #888;
	box-shadow: 0 0 3px #888;
	padding: 8px 10px 8px 10px;
	z-index: 3;
	position: fixed;
	bottom: 220px;
	right: 0px;
}

#resize-btn-chart-2 {
	background-color: rgb(249, 249, 249);
	-moz-box-shadow: 0 0 3px #888;
	-webkit-box-shadow: 0 0 3px #888;
	box-shadow: 0 0 3px #888;
	padding: 8px 10px 8px 10px;
	z-index: 3;
	position: fixed;
	bottom: 0px;
	right: 0px;
	display: none;
}

#resize-btn-2 {
	background-color: rgb(249, 249, 249);
	padding: 8px 10px 8px 10px;
	position: absolute;
	left: 0px;
	z-index: 1;
	/* border-style: solid;
	border-width: 2px; */
	-moz-box-shadow: 0 0 3px #888;
	-webkit-box-shadow: 0 0 3px #888;
	box-shadow: 0 0 3px #888
}

.table-infobox {
	width: 96.5%;
	margin-bottom: 10px;
	margin-right: 10px;
}

form {
	padding-bottom: 0px;
	margin-bottom: 10px;
}

td:nth-child(even) {
	font-weight: bold;
}

div.tab-content {
	margin-bottom: 10px;
}

.active {
	background-position: right 12px;
}

table td {
	vertical-align: middle;
}

.table td {
	border-top: none;
}
</style>
</head>
<body>
	<div id="map" style="width: 100%; height: 100%; float: left; z-index: 0;"></div>
	
	<script type="text/javascript">
	function add0(p) {
		if (p<10) return ('0'+p);
			else return p;
	}
	$(document).ready(function() {
		var op = window.location.hash;
		
		if (op !== null){
			console.log(op);
			
			// get trip
		}
		
		$("#btn_clear").click(function(){
			clearHistory();
		});

		$("#getEventLog").click(function() {
			var currentdate = new Date();
			var date = currentdate.getDate();
			var month = currentdate.getMonth();
			var year =  currentdate.getFullYear();

			var date30 = new Date(year, month, date-30);
			var date2 = date30.getDate();
			var month2 = date30.getMonth();
			var year2 =  date30.getFullYear();

			var href = $(this).attr("href");
			href = href + add0(date2) +'-'+ add0(month2+1) +'-'+ add0(year2) +','+ add0(date) +'-' +add0(month+1) +'-'+ add0(year);
			$(this).attr("href", href);
		});

		$("#resize-btn-chart").click(function() {
			$("#line-chart").hide("fast");
			$("#resize-btn-chart-2").show("fast");
		});

		$("#resize-btn-chart-2").click(function() {
			$(this).hide("fast");
			$("#line-chart").show("fast");
		});

		var width=$("#cpanel").width();       

		$("#resize-btn").click(function() {
			$("#cpanel").animate({
				left: '-='+width,
			}, 150, function() {});
			$("#resize-btn-2").show("fast");
		});

		$("#resize-btn-2").click(function() {
			$("#cpanel").animate({
				left: '+='+width,
			}, 150, function() {});
			$("#resize-btn-2").hide("fast");
		});

		var start=$('#start_time');
		var end = $("#end_time");

		start.datetimepicker({
			dateFormat: 'mm/dd/yy',
		});

		end.datetimepicker({
			dateFormat: 'mm/dd/yy',
		}); 

		$("#past-12h").click(function() {
			var currentdate = new Date();
			var date = currentdate.getDate();
			var month = currentdate.getMonth();
			var year =  currentdate.getFullYear();
			var hour =  currentdate.getHours();
			var min =  currentdate.getMinutes();
			end.datetimepicker('setDate', currentdate);
			start.datetimepicker('setDate', new Date(year, month, date, hour-12, min));
			return true;
		});

		$("#past-24h").click(function() {
			var currentdate = new Date();
			var date = currentdate.getDate();
			var month = currentdate.getMonth();
			var year =  currentdate.getFullYear();
			var hour =  currentdate.getHours();
			var min =  currentdate.getMinutes();
			end.datetimepicker('setDate', currentdate);
			start.datetimepicker('setDate', new Date(year, month, date-1, hour, min));
			return true;
		});

		$("#past-3d").click(function() {
			var currentdate = new Date();
			var date = currentdate.getDate();
			var month = currentdate.getMonth();
			var year =  currentdate.getFullYear();
			var hour =  currentdate.getHours();
			var min =  currentdate.getMinutes();
			end.datetimepicker('setDate', currentdate);
			start.datetimepicker('setDate', new Date(year, month, date-3, hour, min));
			return true;
		});

		$("#past-7d").click(function() {
			var currentdate = new Date();
			var date = currentdate.getDate();
			var month = currentdate.getMonth();
			var year =  currentdate.getFullYear();
			var hour =  currentdate.getHours();
			var min =  currentdate.getMinutes();
			end.datetimepicker('setDate', currentdate);
			start.datetimepicker('setDate', new Date(year, month, date-7, hour, min));
			return true;
		});

		$("#marquee").mouseover(function() {
			        $(this).attr("scrollamount", "0");
			    });

		$("#marquee").mouseout(function() {
			$(this).attr("scrollamount", "2");
		});
		
		$("#trip_info").hide();
		
		window.setTimeout("gr_locator()", 5000);
	});

		var locators = [];
		var curVehicles = [];
		var activities = [];
		var curSelectedLocator;
		//var companyid;
		
		var locatorImage = '/fleet-link/resources/images/truck_.png';
		var locatorImage_Idle = '/fleet-link/resources/images/truck_1.png';
		var locatorImage_Stopped = '/fleet-link/resources/images/truck_0.png';
		var locatorImage_Speeding = '/fleet-link/resources/images/truck_2.png';
		var locatorImage_Broken = '/fleet-link/resources/images/truck_3.png';
		var boxImage = '/fleet-link/resources/images/box_.png';
		var boxImage_Open = '/fleet-link/resources/images/box_open.png';
		
		var infoWindow = new google.maps.InfoWindow();
		
		var geocoder = new google.maps.Geocoder();
		var formatted_address = "NA";
		var format_addr = "NA";
		
		var locator_markers = [];
		var flag_markers = [];
		var milestone_markers = [];
		var trip_markers = [];
		var order_markers = [];
		var groups = [];
		var amt_all;
		var amt_active;
		var amt_pause;
		var amt_stop;
		var drawing;
		var drawingBounds;
		var orders = [];
		var selOrders = [];
		
		// add map style
		var styles = [
		  {
		    "stylers": [
		      { "lightness": -40 },
		      { "saturation": -65 },
		      { "gamma": 1.31 }
		    ]
		  }
		];
		
		var styledMap = new google.maps.StyledMapType(styles,
			    {name: "Dim"});

		var mapOptions = {
			center : new google.maps.LatLng(1.365247, 103.801235),
			zoom : 12,
			mapTypeControl : true,
			mapTypeControlOptions : {
				position : google.maps.ControlPosition.TOP_RIGHT,
				mapTypeIds: [google.maps.MapTypeId.ROADMAP, 
				             google.maps.MapTypeId.TERRAIN, 
				             google.maps.MapTypeId.SATELLITE, 'map_style']
			},
			zoomControl: true,
			zoomControlOptions: {
				position : google.maps.ControlPosition.TOP_RIGHT
			},
			scaleControl: true,
		    scaleControlOptions: {
		        position: google.maps.ControlPosition.BOTTOM_CENTER
		    },
			overviewMapControl: true,
			overviewMapControlOptions: {
		        position: google.maps.ControlPosition.BOTTOM_RIGHT
		    },
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		
		var map = new google.maps.Map(document.getElementById("map"), mapOptions);		
		
		// add drawing mngr		
		var drawingManager = new google.maps.drawing.DrawingManager({
			drawingControl : true,
			drawingControlOptions : {
				position : google.maps.ControlPosition.TOP_RIGHT,
				drawingModes : [ 
						google.maps.drawing.OverlayType.CIRCLE,
						google.maps.drawing.OverlayType.RECTANGLE ]
			},
			/* markerOptions : {
				icon : 'http://www.example.com/icon.png'
			}, */
			circleOptions : {
				fillColor : '#44ff00',
				fillOpacity : 1,
				strokeWeight : 5,
				clickable : false,
				zIndex : 1,
				editable : true
			}
		});
		
		drawingManager.setMap(map);
		
		google.maps.event.addListener(drawingManager, 'overlaycomplete', function(event) {
			  if (event.type == google.maps.drawing.OverlayType.RECTANGLE) {
				  // clear prev drawing
				  if (typeof(drawing) !== 'undefined' ){
					  drawing.setMap(null);
					  selOrders = [];
				  }
				  
				drawing = event.overlay;
			    drawingBounds = event.overlay.getBounds();
			    
			    filterOrders();
			    
			    drawing.setMap(null);
			    
			    //alert(drawingBounds.toString());
			  }
		});		
		
		function filterOrders (){
			for (var i = 0; i < orders.length; i ++){
				var order = orders[i];
				
				if (order.place !== null && drawingBounds.contains(new google.maps.LatLng(order.place.latitude, order.place.longitude))){
					selOrders.push(order);
				}
			}
			
			console.log(selOrders);
		}
		
		<%
			org.springframework.context.ApplicationContext ctx = 
			org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
	
			LocatorService locatorService = (LocatorService)ctx.getBean("locatorService");
			UserService userService = (UserService)ctx.getBean("userService");
	
			String companyId = userService.findByUsername(request.getUserPrincipal().getName()).getCompany().getId().toString();
			out.print("var companyId = " + companyId);
		%>
		
		markLocators();
		
		getActivities();
		
		//retrieveOrders();
		
		function retrieveOrders (){
			var req = "/fleet-link/workorder/json?companyid=" + companyId;
			
			$.getJSON(req, function(data) {     
				$.each(data, function(key, order){
					orders.push(order);
					
					if (order.place !== null)
						createOrderMarker(order);
				});
			})
			.error(function (){
				console.log("error retrieve order data, reload now..");
            	//location.reload();
            	retrieveOrders();
			});
		}
		
		var drop_added = false;
		var locator_listed = false;
		
		function getActivities (){
			activities = [];
			
			var req = "/fleet-link/event/company/" + companyId + "?count=20";

			$.getJSON(req, function(data) {     
				$.each(data, function(key, activity){
					activities.push(activity);

					$("#feed-area").append("<div><span class='muted' style='margin-right: 24px'>" + 
						(new Date(activity.eventTime)).format("m/dd HH:MM") + "</span><span class='green' style='margin-right: 18px'>" + 
						activity.message + "</span><span class='brown'>" + activity.bearerName +" </div>");
				});
			})
			.error(function (){
				console.log("error retrieve event data, reload now..");
            	//location.reload();
            	getActivities();
			});
		}
		
		function markLocators() {
			//clear locators
			locators = [];
	
			var req = "/fleet-link/vehicle/company/" + companyId;

	                $.getJSON(req, function(data) {     
	                	amt_all = 0;
	                	amt_active = 0;
	                	amt_pause = 0;
	                	amt_stop = 0;
	                	points = [];

	                	curVehicles = [];
	                	var bounds = new google.maps.LatLngBounds();

	                	$.each(data, function(key, value){
	                		var grp_vehicle= [];

	                		$.each(value, function(key, vehicles){
                        //console.log(key, vehicles);

                        // populate vehicle group
                        if (!drop_added){
                        	$("#sel_group").append($("<option/>", {value: key, text: key}));
                        }

                        // enumerate vehicles
                        $.each(vehicles, function (i, item) {    
                            //console.log(i, item.latLng.latitude);
                            //var testObj = JSON.parse(item);
                            
                            amt_all += 1;

                            var point =  new google.maps.LatLng(item.latLng.latitude, item.latLng.longitude);
                            var title = item.licensePalette;
                            var info = "<div><b>" + item.vehicleName +  "</b>"  + " - <i>" + item.licensePalette + "</i><span class='pull-right' style='margin-right: 24px'>" + item.status 
                            	+ "</span></div><hr><div style='width: 100%'><span><i class='icon-rocket icon-large'></i>&nbsp;&nbsp;  " + parseFloat(item.speed).toFixed(2) + "&nbsp; KM/H</span><br><span><span><i class='icon-time icon-large'></i> &nbsp; " + dateFormat(new Date(item.lastUpdate), "dddd, mmmm dS, yyyy, h:MM:ss TT") + "</span>"; 
                            
                            locators.push(item);
                            
                            if(!(point.lat() + point.lng() < 1))
                            	bounds.extend(point);
                            
                            //points.push(point);

                            //createLocatorMarker(point, title, info, item.deviceStatus.isGpsOn);
                            
                            var markerExist = false;
                            var marker;

                            //if locator markers contains the vehicle - update
                            for (var i = 0; i < locator_markers.length; i ++){
                            	if (locator_markers[i].getTitle() === title){
                            		markerExist = true;
                            		marker = locator_markers[i];
                            	}
                            }
                            
                            if (markerExist){
                            	marker.setPosition(point);

                                // update infowindow
                                addInfowindowToMarker(info, marker);
                                
                                if (item.status === "MOVING"){
                                	marker.setIcon(locatorImage);
                                	amt_active += 1;
                                }
                                else if (item.status === "SPEEDING"){
                                	marker.setIcon(locatorImage_Speeding);
                                	amt_active += 1;
                                }
                                else if (item.status === "STOPPED") {
                                	marker.setIcon(locatorImage_Stopped);
                                	amt_stop += 1;
                                }
                                else if (item.status === "NOFIX" || item.status === "BROKEN") {
                                	marker.setIcon(locatorImage_Broken);
                                	//amt_pause += 1;
                                }
                                else {
                                	marker.setIcon(locatorImage_Idle);
                                	amt_pause += 1;
                                }
                            }
                            else{
                            	createLocatorMarker(point, title, info, item.status);
                            }

                            /* if (!drop_added){
                            	$("#sel_locator").append($("<option/>", {value: item.locatorId, text: item.licensePalette}));
                            } */

                            grp_vehicle.push(item);
                        });
					});
					
					curVehicles.push(grp_vehicle);
				
					if (!drop_added) { 
						populateVehicleDrop(0);
						map.fitBounds(bounds);
			        }
				});
			})
			.error(function (){
				console.log("error retrieve data, reload now..");
            	location.reload();	 
			});

            //console.log(curVehicles);

            drop_added = true;

            updateStatusPanel();

            window.setTimeout("markLocators()", 5000);
        }

        function populateVehicleDrop (groupIndex){
        	var vehicleToPop = curVehicles[groupIndex];

        	$("#sel_locator").empty();

        	$.each(vehicleToPop, function (i, item) {    
        		$("#sel_locator").append($("<option/>", {value: item.locatorId, text: item.vehicleName + " - " + item.licensePalette}));
        	});
        }

        function createLocatorMarker(point, title, info, status) {
        	var marker;

        	if (status === "MOVING")
        		marker = new google.maps.Marker({position: point, title: title, icon: locatorImage, map: map}); 
        	else if (status === "PAUSED")
        		marker = new google.maps.Marker({position: point, title: title, icon: locatorImage_Idle, map: map}); 
        	else if (status === "SPEEDING")
        		marker = new google.maps.Marker({position: point, title: title, icon: locatorImage_Speeding, map: map}); 
        	else if (status === "NOFIX" || status === "BROKEN")
        		marker = new google.maps.Marker({position: point, title: title, icon: locatorImage_Broken, map: map}); 
        	else { // "STOPPED"
        		marker = new google.maps.Marker({position: point, title: title, icon: locatorImage_Stopped, map: map}); 
        	}

        	addInfowindowToMarker(info, marker);			
        	locator_markers.push(marker);

        	return marker;
        }
        
        function createOrderMarker (order){       
        	var point = new google.maps.LatLng(order.place.latitude, order.place.longitude);

          	var marker = new google.maps.Marker({position: point, title: order.orderNumber, map: map});
          	
          	if (order.status === "COMPLETED")
          		marker.setIcon(boxImage);
          	else
          		marker.setIcon(boxImage_Open);
          	
          	addInfowindowToMarker3(order, marker);
          	
          	order_markers.push(marker);
        }

        function fillLocatorData (value, moveMap){              
            //console.log(value, moveMap);
            for (var i = 0; i < locators.length; i ++){
            	if (locators[i].licensePalette === value){
                    // update cpanel with locator info
                    var curLocator = locators[i];
                    curSelectedLocator = curLocator;

                    $("#sel_locator").val(curLocator.locatorId);

                    var lat = curLocator.latLng.latitude;
                    var lng = curLocator.latLng.longitude;                

                    /* $("#i_lat").html(lat.toFixed(5));
                    $("#i_lng").html(lng.toFixed(5)); */
                    $("#i_spd").html(curLocator.speed.toFixed(2));  
                    /* $("#i_alt").html(curLocator.altitude); */
                    $("#i_loc").html(format_addr);
                    $("#vh_type").html(curLocator.vehicleType);
                    $("#vh_model").html(curLocator.vehicleModel);
                    $("#vh_lp").html(curLocator.licensePalette);
                    $("#vh_name").html(curLocator.vehicleName);

                    if (moveMap)
                    	zoomToObject(lat, lng, 16);
                }
            }
        }

        //var path;
        var flagImage_Start = 'resources/images/marker_greenA.png'; 
        var flagImage_End = 'resources/images/marker_greenB.png';   
        var flagImage_Milestone = 'resources/images/locator/milestone_16.png';
        var tripData;
        var poly=[];
        var pathList = [];
        
        function queryHistory(){
        	$("#btn_search").attr('disabled','disabled');
            $("#loading").show();
        	clearHistory();

        	//var loc = $("#sel_locator").val();
        	var loc = curSelectedLocator.locatorId;
        	
        	if (loc === null){
        		return;
        	}
        	
        	var stime = $("#start_time").val().replace(/\//g, '-'); 
        	var etime = $("#end_time").val().replace(/\//g, '-');
        	
			//TODO: restrict period

            //var req = "http://www.coordsafe.com.sg:8070/CoordSafePortalApp/locators/lc/history/" + loc + "/" + stime + "," + etime;
            //var req = "/CoordSafePortalApp/api/history/" + loc + "/" + stime + "," + etime + "?key=test-1234qwer"
            var req = "/fleet-link/trip/detail/" + loc + "/" + stime + "," + etime;
            
            $.getJSON(req, function(data) {
                $("#loading").fadeOut();
            	$("#btn_search").removeAttr('disabled');            	
        		
        		//Associate the styled map with the MapTypeId and set it to display.
        		//map.mapTypes.set('map_style', styledMap);
        		//map.setMapTypeId('map_style');

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
                //drawChart(0);
            })
            .error(function (){
            	humane.log("Can not perform search right now, please try again later");
            	setTimeout(function(){location.reload();}, 2000);	 
			});
            
            gr_history(loc, stime, etime);
            
            //event.preventDefault();
        }

        function populateHistory (index){
            //populate history summary
            //$("#_locator").val($("#sel_locator option:selected").text());
            //$("#trip_info").css('display', '');
            $("#trip_info").show();
            $("#search_box").css('display', 'none');
            
            var timeStartInMilli = tripData[index].history[0].location_time;
            var timeEndInMilli = tripData[index].history[tripData[index].history.length - 1].location_time;

            var temp = new Date(timeStartInMilli);
            var mth = temp.getMonth() + 1;
            $("#trip_startTime").html(mth + "/" + temp.getDate() + "/" + temp.getFullYear() + " " + temp.getHours() + ":" + temp.getMinutes());
            
            var temp2 = new Date(timeEndInMilli);
            var mth2 = temp2.getMonth() + 1;                    
            $("#trip_endTime").html(mth2 + "/" + temp2.getDate() + "/" + temp2.getFullYear() + " " + temp2.getHours() + ":" + temp2.getMinutes());

            var dr = ((timeEndInMilli - timeStartInMilli)/(3.6*1E6)).toFixed(3);
            var hr = parseInt(dr);
            var mr = parseInt((dr - hr) * 60);
            
            $("#trip_duration").html(hr + "h" + " " + mr + "mins");
            // presumably use server data.
            if (tripData[index].trip.mileage > 0){
            	$("#trip_mileage").html(tripData[index].trip.mileage + " Km");
            }
            else {
            	$("#trip_mileage").html((poly[index].Distance()/1000).toFixed(3) + " Km");
            }
            $("#trip_speed").html((parseFloat($("#trip_mileage").html())/dr).toFixed(3) + " Km/h");

            //active history tab
            //$('#info_tabs').tabs('select', 1);
        }

        function drawPath(data){
        	var lng_MAX=-180,lng_MIN=180,lat_MAX=-90,lat_MIN=90;
        	var points=[];
        	
            //draw all trips
            for(var i=0;i<data.length;i++){
            	tempData=data[i].history;

                /* //draw past journey
                if(pathList[i] != null){                
                    clearHistory();
                } */
                
                //Get color for polylines and set their option                
                colors=getColors(data.length);
                
                var polyOptions = {
                	strokeColor: colors[i],
                	strokeOpacity: 1,
                	strokeWeight: 6
                }

                //draw a hidden polyline used for click event
                /*var hiddenPolyline={
                    strokeColor: "#FF0000",
                    strokeOpacity: 0.9,
                    strokeWeight: 25
                }*/

                poly[i] = new google.maps.Polyline(polyOptions);
                
                poly[i].setMap(map);
                
                var len = tempData.length;
                
                // escape 0 len, but why get retrieved?
                if (len === 0){
                	continue;
                }

                for(var j=0;j<len;j++){
                	pathList[i]=poly[i].getPath();
                	pathList[i].push(new google.maps.LatLng(tempData[j].lat,tempData[j].lng));

                	if(tempData[j].lng>lng_MAX) lng_MAX=tempData[j].lng;
                	if(tempData[j].lng<lng_MIN) lng_MIN=tempData[j].lng;
                	if(tempData[j].lat>lat_MAX) lat_MAX=tempData[j].lat;
                	if(tempData[j].lat<lng_MIN) lat_MIN=tempData[j].lat;
                }
                
                //Add numbered marker
                var midP = new google.maps.LatLng(tempData[Math.floor(len/2)].lat,tempData[Math.floor(len/2)].lng);
                var _mkr = createNumberMarker(midP, i + 1);
                
              	//TODO: Add infowindow on mouseover
              	addInfowindowToMarker2(data[i].trip, _mkr);
                
                //Add click/hover event to polyline
                createPolylineEvent(poly,i);
                
                //Set zoom and center of map                
                points.push(new google.maps.LatLng(lat_MIN,lng_MIN));
                points.push(new google.maps.LatLng(lat_MAX,lng_MAX));
               
            }
            
            //$("#trip-option").show();
            if (points.length > 0){
            	setMapZoomCenter(map,points);
            }
            
            points = [];
        };
        
        function drawChart(option){
            //display on a line chart
            data = tripData[option].history;
            playback(data, map, option);
            
            // put start and end flags
            /* if(search==0){
                if(flag_markers.length!=0){
                    for (var i = 0; i < flag_markers.length; i ++){
                        flag_markers[i].setMap(null);
                    }    
                }*/
                for (var i = 0; i < flag_markers.length; i ++){
                	flag_markers[i].setMap(null);
                }
                
                createFlagMarker(new google.maps.LatLng(data[0].lat,data[0].lng), "Start", flagImage_Start);
                createFlagMarker(new google.maps.LatLng(data[data.length-1].lat,data[data.length-1].lng), "End", flagImage_End);  
            }

            function createFlagMarker (point, title, image){
            	flag_markers.push(new google.maps.Marker({position: point, title: title, icon: image, map: map}));
            }
            
            function createNumberMarker (point, number){             
            	var number_icon = "/fleet-link/resources/numbered_marker/number_" + number + ".png";
            	
            	var numMarker = new google.maps.Marker({position: point, icon: number_icon, map: map}); 
            	
            	trip_markers.push(numMarker);
            	
            	return numMarker;            	
            }


            function clearHistory(){
            	//$("#trip_info").css('display', 'none');
            	$("#trip_info").hide();
                $("#search_box").css('display', '')	
            
            	for (var i = 0; i < poly.length; i++){
            		poly[i].setMap(null);
            	}

            	for (var i = 0; i < flag_markers.length; i ++){
            		flag_markers[i].setMap(null);
            	}
            	
            	for (var i = 0; i < trip_markers.length; i ++){
            		trip_markers[i].setMap(null);
            	}
            	
            	for (var i = 0; i < speeding_markers.length; i ++){
            		speeding_markers[i].setMap(null);
            	}
            	
            	for (var i = 0; i < pause_markers.length; i ++){
            		pause_markers[i].setMap(null);
            	}

            	pathList = []; 

            //new code
            clearChart();
            //$("#line-chart").hide();
            //tracking_marker.setVisible(false);
            //r.remove();
        }

        function getSelectedGroup(sel){
        	populateVehicleDrop(sel.selectedIndex);
        }

        function getSelectedLocator(sel){
        	$(".active").removeClass("active");
        	$("#vehicle-li").addClass("active");
        	$("#vehicle").addClass("active");
        	var loc_name = sel.options[sel.selectedIndex].label;  
        	
        	//dismiss infowindow
        	infoWindow.close();
        	
        	fillLocatorData(loc_name.substring(loc_name.lastIndexOf("-") + 2), true);
        }
        
        function zoomToObject(lat, lng, level){
        	map.panTo(new google.maps.LatLng(lat, lng));
        	map.setZoom(level);
        }
        
        function updateStatusPanel(){
        	$("#vh_all").html(amt_all);
        	$("#vh_active").html(amt_active);
        	$("#vh_stop").html(amt_stop);
        	$("#vh_pause").html(amt_pause);
        }
        </script>
        <span><a id="resize-btn-2" href="#"><i class="icon-chevron-right"></i></a></span>
        <div id="cpanel" class="row-fluid">
        	<div id="info_tabs">
        		<form>
        			<fieldset>
        				<legend>
        					VEHICLE &amp; GROUP <a id="resize-btn" class="pull-right" href="#"
        					style="margin-top: 10px"> <i id="resize-icon"
        					class="icon-chevron-left"></i>
        				</a>
        			</legend>

        			<div class="input-prepend">
        				<span class="add-on span4">Group</span>
        				<select id="sel_group" onchange="getSelectedGroup(this)"  onfocus="this.selectedIndex = -1">
        				</select>
        			</div>

        			<div class="input-prepend">
        				<span class="add-on span4">Vehicle</span>
        				<select id="sel_locator" onchange="getSelectedLocator(this)"  onfocus="this.selectedIndex = -1">
        				</select>
        			</div>
        		</fieldset>
        	</form>

        	<div class="tabbable">
        		<ul class="nav nav-tabs" id="myTab">
        			<li class="active"><a data-toggle="tab" href="#overview">
        				Overview
        			</a></li>

        			<li id="vehicle-li"><a data-toggle="tab" href="#vehicle">  Vehicle
        			</a></li>
        			
        			<li id="feed-li"><a data-toggle="tab" href="#feed">  Activity
        			</a></li>
        		</ul>

        		<div class="tab-content">
        			<div id="overview" class="tab-pane in active">
        				<div class="infobox infobox-green ">
        					<!-- <a data-toggle="tab" href="#">
        							<i class="blue icon-eye-open bigger-110 pull-right"></i> 
        					</a> -->
        					<div class="infobox-data">
        						<span id="vh_all" class="infobox-data-number">0</span>        						
        						<div class="infobox-content">Total Vehicles</div>
        					</div>
        				</div>

        				<div class="infobox infobox-blue  ">
        					<!-- <a data-toggle="tab" href="#">
        							<i class="grey icon-eye-close bigger-110 pull-right"></i> 
        					</a> -->
        					<div class="infobox-data">
        						<span id="vh_active" class="infobox-data-number">0</span>
        						<div class="infobox-content">Active Vehicles</div>
        					</div>
        				</div>
        				<div class="infobox infobox-pink  ">
        					<div class="infobox-data">
        						<span id="vh_pause" class="infobox-data-number">0</span>
        						<div class="infobox-content">Paused Vehicles</div>
        					</div>
        				</div>
        				<div class="infobox infobox-red  ">
        					<div class="infobox-data">
        						<span id="vh_stop" class="infobox-data-number">0</span>
        						<div class="infobox-content">Stopped Vehicles</div>
        					</div>
        				</div>

        				<%-- <div class="widget-box transparent" style="margin-top: 12px">
        					<div class="widget-header widget-header-flat">
        						<h4 class="lighter">
        							<i class="icon-bullhorn"></i>
        							ACTIVITY
        						</h4>

        						<div class="widget-toolbar">
        							<!-- <a data-action="reload" href="#">
        								<i class="icon-refresh blue"></i>
        							</a> -->

        						<a id="getEventLog" href="<s:url value="/event/company/"/><sec:authentication property="principal.company.id"/>/page?timeRange=">
        							<i class="icon-reorder blue"></i>
        						</a>

        						<a data-action="collapse" href="#">
        							<i class="icon-chevron-down blue"></i>
        						</a>
        					</div>
        				</div>

        				<div class="widget-body">
        					<div class="widget-body-inner">
        						<div class="widget-main">
        							<marquee behavior="scroll" direction="up" scrollamount="2"
        							id="marquee">
        						</marquee>
        					</div>
        				</div>
        			</div>
        		</div> --%>
        	</div>

        	<div id="vehicle" class="tab-pane">

        		<div class="widget-box transparent">
        			<div class="widget-header widget-header-flat">
        				<h4 class="lighter"><i class="icon-truck"></i>&nbsp;VEHILCE INFO</h4>

        				<div class="widget-toolbar no-border">
        					<a href="#" data-action="collapse">
        						<i class="icon-chevron-up"></i>
        					</a>
        				</div>
        			</div>

        			<div class="widget-body">
        				<div class="widget-main padding-6">
        					<table class="table table-striped table-condensed table-hover" style="border-top: none;" >
        						<tr>
	        						<td>Name</td>
	        						<td id="vh_name"></td>
	        						<td>License</td>
	        						<td id="vh_lp"></td>
        						</tr>
        						<tr >
        							<td >Type</td>
        							<td id="vh_type"></td>
        							<td >Model</td>
        							<td  id="vh_model"></td>
        						</tr>
        						<tr>
        							<td><span class="add-on">Location</span></td>
        							<td id="i_loc" colspan="3"></td>
        						</tr>
        						<!-- <tr>
        							<td>Status</td>
        							<td id="i_sts"></td>
        							<td>Speed</td>
        							<td id="i_spd"></td>
        						</tr> -->
        						<!-- <tr>
        							<td>Date</td>
        							<td id="vh_dt"></td>
        							<td>Driver</td>
        							<td id="vh_driver"></td>
        						</tr> -->
        					</table>
        				</div>
        			</div>
        		</div>

        		<div class="widget-box transparent">
        			<div class="widget-header">
        				<h4 class="lighter"><i class="icon-road"></i>&nbsp;HISTORY</h4>

        				<div class="widget-toolbar no-border">
        					<a href="#" data-action="collapse">
        						<i class="icon-chevron-up"></i>
        					</a>
        				</div>
        			</div>

        			<div class="widget-body">
        				<div class="widget-main padding-6">
        					<table id="search_his" class="table table-striped table-condensed table-hover" style="border-top: none;" >
        						<form>
        							<fieldset id="search_box">
        								
        								<div class="input-append input-prepend">
        									<span class="add-on">Start Time</span>
        									<input id="start_time" type="text" value="" name="start_time"></input>
        									<span class="add-on"><i class="icon-calendar"></i></span>
        								</div>
        								<br /> 
        								<div class="input-prepend input-append">
        									<span class="add-on">End Time &nbsp;</span>
        									<input id="end_time" type="text" value="" name="end_time"></input>
        									<span class="add-on"><i class="icon-calendar"></i></span>
        								</div>
        								<br>
        							</fieldset>
        							<!-- <fieldset id="trip_info" style="display: none"> -->
										<table id="trip_info" style="border-top: 0px"
											class="table table-striped table-condensed table-hover"
											style="border-top: none;">
											<tr>
												<td>Start Time</td>
												<td id="trip_startTime"></td>
												<td>End Time</td>
												<td id="trip_endTime"></td>
											</tr>
											<tr>
												<td>Duration</td>
												<td id="trip_duration"></td>
											</tr>
											<tr>
												<td>Mileage</td>
												<td id="trip_mileage"></td>
												<td>Avg Speed</td>
												<td id="trip_speed"></td>
											</tr>
											<!-- <tr>
												<td><span class="add-on">Location</span></td>
												<td id="i_loc" colspan="3"></td>
											</tr> -->
										</table>
									<!-- </fieldset> -->
        							<div>

        								<a id="btn_clear"
        								class="btn btn-danger btn-small no-border pull-left"> <i
        								class="icon-eraser"></i>&nbsp;Clear
        							</a>

        							<div class="pull-right">
        								<div class="dropup" style="display: inline-block">
        									<a
        									class="btn no-border btn-small btn-primary dropdown-toggle"
        									data-toggle="dropdown"> &nbsp;Past&nbsp;<i
        									class="icon-angle-down"></i>
        								</a>
        								<ul class="dropdown-menu">
        									<li><a href="#" id="past-12h">12 hours</a></li>
        									<li><a href="#" id="past-24h">24 hours</a></li>
        									<li><a href="#" id="past-3d">3 days</a></li>
        									<li><a href="#" id="past-7d">7 days</a></li>
        								</ul>
        							</div>

        							<span> <a id="btn_clear"
        								class="btn btn-small btn-primary no-border"
        								onClick="queryHistory()"> <i class="icon-search"></i>&nbsp;Search
        							</a>
        						</span>
        					</div>
        				</div>
        			</form>
        		</table>
        	</div>
        </div>
    			</div>
			</div>

					<div id="feed" class="tab-pane">
						<div class="widget-box transparent" style="margin-top: 12px">
							<div class="widget-header widget-header-flat">
								<h4 class="lighter">
									<i class="icon-bullhorn"></i>

								</h4>

								<div class="widget-toolbar">
									<!-- <a data-action="reload" href="#">
    								<i class="icon-refresh blue"></i>
    							</a> -->

									<a id="getEventLog"
										href="<s:url value="/event/company/"/><sec:authentication property="principal.company.id"/>/page?timeRange=">
										<i class="icon-reorder blue"></i>
									</a> <a data-action="collapse" href="#"> <i
										class="icon-chevron-down blue"></i>
									</a>
								</div>

								<div class="widget-body">
									<div class="widget-body-inner">
										<div class="widget-main">
											<marquee behavior="scroll" direction="up" scrollamount="2"
												id="feed-area"> </marquee>
										</div>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
</div>
<div id="line-chart">
	<span><a id="resize-btn-chart" href="#"><i class="icon-chevron-down"></i></a></span>
</div>
<span><a id="resize-btn-chart-2" href="#"><i class="icon-chevron-up"></i></a></span>

<div id="alert-modal" class="modal hide fade widget-box" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" class="clearfix">
		<!-- <div class="widget-box"> -->
			<!-- <div class="widget-header  header-color-blue">
				<h4>Print Map </h4>
			</div>	 -->			
				<div class="widget-body">
					<div class="modal-body widget-main" style="text-align: center">
						<h5><span id="alert_msg"></span></h5>
					</div>
					<div class="widget-toolbox  padding-8 clearfix">
						<div>
							<input id="submit" class="btn btn-primary no-border pull-right"
								name="commit" type="button" value="OK" onclick="dismissAlert()"/>
						</div>
					</div>
			</div>
	</div>

<script type="text/javascript">
	function loadModal(p) {
		$("#alert_msg").html(p);
		$("#alert-modal").modal({backdrop:true});	            
	}
	
	function dismissAlert (){
		$("#alert-modal").modal("hide");
	}
</script>

</body>
</html>
