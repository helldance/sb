    <!DOCTYPE html>
    <%@page import="com.coordsafe.locator.service.LocatorService"%>
    <%@page import="com.coordsafe.locator.entity.Locator"%>
    <%@page import="java.util.List"%>
    <%@page import="com.coordsafe.core.rbac.service.UserService"%>

    <html>
    <head>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
        <meta charset="UTF-8">
        <title>Welcome to CoordSafe</title>
        <link rel="stylesheet" type="text/css" href="resources/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="resources/css/bootstrap-responsive.min.css">

        <!--
        <link rel="stylesheet" type="text/css" href="resources/css/bootstrap-datetimepicker.min.css">
    -->
    <link rel="stylesheet" type="text/css" href="resources/css/jquery-ui-1.10.3.custom.css">


    <link rel="stylesheet" href="resources/assets/css/uncompressed/ace.css" />
    <link rel="stylesheet" href="resources/assets/css/ace-responsive.min.css" />
    <link rel="stylesheet" href="resources/assets/css/ace-skins.min.css" />
    <link rel="stylesheet" href="resources/assets/css/font-awesome.min.css" />
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" rel="stylesheet"></link>
    <link rel="stylesheet" href="resources/assets/css/uncompressed/custom.css" />
    <link rel="stylesheet" href="resources/css/chart/linechart.css" />

    <script type="text/javascript" src="http://maps.google.com/maps/api/js?v=3.3&sensor=false"></script>

    <script type="text/javascript" src="resources/scripts/chart/date.format.1.2.3.min.js"></script>
    <script type="text/javascript" src="resources/scripts/chart/raphael.js"></script>
    <script type="text/javascript" src="resources/scripts/chart/g.raphael.js"></script>
    <script type="text/javascript" src="resources/scripts/chart/g.line.js"></script>		
    <script type="text/javascript" src="resources/scripts/chart/playback.js"></script>

    <style type="text/css">

    /* css for timepicker */
    .ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
    .ui-timepicker-div dl { text-align: left; }
    .ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
    .ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
    .ui-timepicker-div td { font-size: 90%; }
    .ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }

    .ui-timepicker-rtl{ direction: rtl; }
    .ui-timepicker-rtl dl { text-align: right; }
    .ui-timepicker-rtl dl dd { margin: 0 65px 10px 10px; }



    #locator_panel {
        height: 680px;
        width: 200px;
        float: left;
        display: none;

        /*
        background: #F2F5F7;
        */
        position:absolute; 
        left:10px; 
        top:126px; 
        z-index:1;
        padding: 5px 15px 5px 15px; 
        -moz-box-shadow: 0 0 3px #888;
        -webkit-box-shadow: 0 0 3px #888;
        box-shadow: 0 0 3px #888;
    }

    #cpanel {
        /*
     background-color:rgb(249, 249, 249);
     background-color:rgb(112, 112, 112);
     */
     width: 28%; 
     height: 0%; 
     position: absolute; 
     left:0px; z-index:1;

 }

 #resize-btn-2 {
   background-color: rgb(249, 249, 249);
   padding: 8px 10px 8px 10px;
    position: absolute; 
    left:0px; z-index:1;
    border-style:solid;
    border-width: 2px;
  
}

#info_tabs {
    padding-left: 6px; 
    padding-right: 6px;
    background-color:rgb(249, 249, 249);
    -webkit-box-shadow: 2px 2px 3px #666;
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
</style>

</head>
<body>
    <div id="map" style="width: 100%; height: 100%; float: left; z-index:0;"></div>
    <script type="text/javascript">

    $(function() {


    /*
        $("#start_time").datetimepicker({
             //  startDate: new Date(2008, 10, 11, 4, 30),
             pickSeconds: false,
             language: 'pt-BR'
         });
        $("#end_time").datetimepicker({
           pickSeconds: false,
           language: 'pt-BR'  
       });
    */
});

    var locators = [];

    var locatorImage = '/fleet-link/resources/images/truck_.png';
    var locatorImage_Inactive = '/fleet-link/resources/images/truck_0.png';

    var infoWindow = new google.maps.InfoWindow();

    var geocoder = new google.maps.Geocoder();
    var formatted_address = "NA";
    var format_addr = "NA";

    var locator_markers = [];
    var flag_markers = [];
    var milestone_markers = [];	
    var groups = [];

    var mapOptions = {
        center: new google.maps.LatLng(1.365247, 103.801235),
        zoom: 12,
        mapTypeControl: true,
        mapTypeControlOptions: {
            position: google.maps.ControlPosition.TOP_CENTER
        },            
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    var map = new google.maps.Map(document.getElementById("map"), mapOptions);

    markLocators();

    function listLocator(label){
        var htmlStr = $('#locator_panel').html() + "<p><img src='resources/images/truck_35.png' />&nbsp;&nbsp;&nbsp;&nbsp;" 
        + label + "</p>";

        console.log(htmlStr);

        $('#locator_panel').html(htmlStr);
    }

    var drop_added = false;
    var locator_listed = false;

    function markLocators() {                               
            //clear locators
            locators = [];

            <%
            org.springframework.context.ApplicationContext ctx = 
            org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());

            String centeredPoint=null;

            if (request.getParameter("imeiCode")!=null);
            centeredPoint = request.getParameter("imeiCode");

            LocatorService locatorService = (LocatorService)ctx.getBean("locatorService");
            UserService userService = (UserService)ctx.getBean("userService");

            String companyId = userService.findByUsername(request.getUserPrincipal().getName()).getCompany().getId().toString();
                //out.print("var req = \"/CoordSafePortalApp/api/locator/company/"+companyName+"?key=test-1234qwer\"");
                out.print("var req = \"/fleet-link/vehicle/company/" + companyId + "\"");
                %>

                $.getJSON(req, function(data) {     
                   $.each(data, function(key, value){
                    $.each(value, function(key, vehicles){
                        //console.log(key, vehicles);

                        // populate vehicle group
                        if (!drop_added){
                            $("#sel_group").append($("<option/>", {value: key, text: key}));
                        }

                        // enumerate vehicles
                        $.each(vehicles, function (i, item) {    
                            //console.log(i, item.latLng.latitude);

                            var point =  new google.maps.LatLng(item.latLng.latitude, item.latLng.longitude);
                            var title = item.licensePalette;
                            var info = "<b>" + item.vehicleName +  "</b>"  + " - <i>" + item.licensePalette + "</i><br />GPS: " + 
                            item.status + "<br />Last update: " + new Date(item.lastUpdate); 
                            
                            locators.push(item);

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
                                
                                if (item.status){
                                    marker.setIcon(locatorImage);
                                }
                                else{
                                    marker.setIcon(locatorImage_Inactive);
                                }
                                //console.log("update marker");
                            }
                            else{
                                createLocatorMarker(point, title, info, item.status);
                                //console.log("create marker");
                            }

                            if (!drop_added){
                                // add only first group
                                $("#sel_locator").append($("<option/>", {value: item.locatorId, text: item.licensePalette}));
                            }
                        });
});
});
});

    drop_added = true;

    window.setTimeout("markLocators()", 5000);
}

function createLocatorMarker(point, title, info, active) {
  var marker;

  if (active == true)
     marker = new google.maps.Marker({position: point, title: title, icon: locatorImage, map: map}); 
 else 
     marker = new google.maps.Marker({position: point, title: title, icon: locatorImage_Inactive, map: map}); 

 addInfowindowToMarker(info, marker);			
 locator_markers.push(marker);

 return marker;
}

function addInfowindowToMarker(info, marker){
    google.maps.event.addListener(marker,'click',
        function() {
            geocoder.geocode({'latLng': marker.getPosition()}, function(results, status) {
                if (status == google.maps.GeocoderStatus.OK) {                            
                    if (results[0]) {
                        format_addr = results[0].formatted_address ;
                        formatted_address = "<br /><b>Address : " + format_addr + "</b>";

                        infoWindow.setContent(info+formatted_address);  
                        infoWindow.open(map,marker);

                        fillLocatorData (marker.title, false);
                    }
                }
            });
        }
        );
}

function fillLocatorData (value, moveMap){              
            //console.log(value, moveMap);
            for (var i = 0; i < locators.length; i ++){
                if (locators[i].licensePalette === value){
                    // update cpanel with locator info
                    var curLocator = locators[i];

                    $("#sel_locator").val(curLocator.locatorId);

                    var lat = curLocator.latLng.latitude;
                    var lng = curLocator.latLng.longitude;
                    /*$("#i_lat").val(lat);
                    $("#i_lng").val(lng);
                    $("#i_bat").val(locators[i].deviceStatus.batteryLeft);
                    $("#i_gps").val(locators[i].deviceStatus.isGpsOn);
                    $("#i_spd").val(locators[i].gpsLocation.speed);
                    $("#i_alt").val(locators[i].gpsLocation.altitude);
                    $("#i_loc").val(formatted_address);   */                       

                    $("#i_lat").html(lat.toFixed(5));
                    $("#i_lng").html(lng.toFixed(5));
                    $("#i_spd").html(curLocator.speed);  
                    $("#i_alt").html(curLocator.altitude);
                    $("#i_loc").html(format_addr);
                    $("#vh_type").html(curLocator.vehicleType);
                    $("#vh_model").html(curLocator.vehicleModel);
                    //$("#vh_dt").html(curLocator.);
                    //$("#vh_maintain").html(curLocator.);

                    if (moveMap)
                        zoomToObject(lat, lng);
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
            
            clearHistory();

            var loc = $("#sel_locator").val(); 
            var stime = $("#start_time").val().replace(/\//g, '-'); 
            var etime = $("#end_time").val().replace(/\//g, '-');
            
            //var req = "http://www.coordsafe.com.sg:8070/CoordSafePortalApp/locators/lc/history/" + loc + "/" + stime + "," + etime;
            //var req = "/CoordSafePortalApp/api/history/" + loc + "/" + stime + "," + etime + "?key=test-1234qwer"
            var req = "/fleet-link/trip/detail/" + loc + "/" + stime + "," + etime + "\"";
            
            $.getJSON(req, function(data) {
                $("#btn_search").removeAttr('disabled');
                
                tripData = data;                    
                drawPath(tripData);
                //drawChart(0);
            });
        }

        function populateHistory (index){
            //populate history summary
            $("#_locator").val($("#sel_locator option:selected").text());
            var timeStartInMilli = tripData[index].history[0].location_time;
            var timeEndInMilli = tripData[index].history[tripData[index].history.length - 1].location_time;
            
            console.log(timeStartInMilli, timeEndInMilli);
            
            var temp = new Date(timeStartInMilli);
            var mth = temp.getMonth() + 1;
            $("#_start_time").val(mth + "/" + temp.getDate() + "/" + temp.getFullYear() + " " + temp.getHours() + ":" + temp.getMinutes());
            
            var temp2 = new Date(timeEndInMilli);
            var mth2 = temp2.getMonth() + 1;                    
            $("#_end_time").val(mth2 + "/" + temp2.getDate() + "/" + temp2.getFullYear() + " " + temp2.getHours() + ":" + temp2.getMinutes());

            $("#_duration").val(((timeEndInMilli - timeStartInMilli)/(3.6*1E6)).toFixed(3) + "h");
            $("#_mileage").val((poly[index].Distance()/1000).toFixed(3) + " Km");
            $("#_avg_speed").val((parseFloat($("#_mileage").val())/parseFloat($("#_duration").val())).toFixed(3) + " Km/h");

            //active history tab
            //$('#info_tabs').tabs('select', 1);
        }

        function drawPath(data){
            var lng_MAX=-180,lng_MIN=180,lat_MAX=-90,lat_MIN=90;
            
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

                for(var j=0;j<tempData.length;j++){
                    pathList[i]=poly[i].getPath();
                    pathList[i].push(new google.maps.LatLng(tempData[j].lat,tempData[j].lng));
                    
                    if(tempData[j].lng>lng_MAX) lng_MAX=tempData[i].lng;
                    if(tempData[j].lng<lng_MIN) lng_MIN=tempData[i].lng;
                    if(tempData[j].lat>lat_MAX) lat_MAX=tempData[i].lat;
                    if(tempData[j].lat<lng_MIN) lat_MIN=tempData[i].lat;
                }
                
                //Add click/hover event to polyline
                createPolylineEvent(poly,i);
                
                //Set zoom and center of map
                var points=[];
                points.push(new google.maps.LatLng(lat_MIN,lng_MIN));
                points.push(new google.maps.LatLng(lat_MAX,lng_MAX));
                
                setMapZoomCenter(map,points);
            }
            
            //$("#trip-option").show();
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

            function markMilestone() {
                if (milestone_markers.length && (milestone_markers.length > 0)) {
                    for (var i=0; i<milestone_markers.length; i++) {
                        milestone_markers[i].setMap(map);
                    }
                } 
                else {
                    for (var i=0; i<path.Distance(); i+=5000) {
                        var milestone = path.GetPointAtDistance(i);

                        if (milestone) {
                            var infoWindowContent = "marker "+i/5000+" of "+Math.floor(path.Distance()/5000)+"<br>kilometer "+i/1000+" of "+(path.Distance()/1000).toFixed(2);
                            var milestone_marker = createMilestoneMarker(milestone, infoWindowContent);

                            milestone_markers.push(milestone_marker);
                        }
                    }
                }
            }

            function createMilestoneMarker (point, info){               
                return new google.maps.Marker({position: point, icon: flagImage_Milestone, map: map}); 
            }

            function clearHistory(){
                for (var i = 0; i < poly.length; i++){
                    poly[i].setMap(null);
                }

                for (var i = 0; i < flag_markers.length; i ++){
                    flag_markers[i].setMap(null);
                }

                pathList = []; 

            //new code
            clearChart();
            //$("#line-chart").hide();
            //tracking_marker.setVisible(false);
            //r.remove();
        }

        function getSelectedLocator(sel){
            var loc_name = sel.options[sel.selectedIndex].label;  
            fillLocatorData(loc_name, true);
        }
        
        function zoomToObject(lat, lng){
            map.panTo(new google.maps.LatLng(lat, lng));
            map.setZoom(14);
        }
        </script>



        <span><a id="resize-btn-2"><i class="icon-chevron-right"></i></a></span>
        <div id="cpanel" class="row-fluid">
          <div id="info_tabs">
             <form>
                <fieldset>
                   <legend>
                      Vehicle & Group <a id="resize-btn" class="pull-right"
                      style="margin-top: 10px"> <i id="resize-icon"
                      class="icon-chevron-left"></i>
                  </a>
              </legend>

              <div class="input-prepend" style="margin-top: 10px">
                  <span class="add-on span4">Group</span>
              </td> <select id="sel_group">
          </select>
      </div>

      <div class="input-prepend">
          <span class="add-on span4">Vehicle</span>
      </td> <select id="sel_locator" onchange="getSelectedLocator(this)">
  </select>
</div>
</fieldset>
</form>


<div class="tabbable">
    <ul class="nav nav-tabs" id="myTab">
        <li class="active"><a data-toggle="tab" href="#overview"> <i
            class="blue icon-truck bigger-110"></i> Overview
        </a></li>

        <li><a data-toggle="tab" href="#vehicle"> <i
         class="blue icon-truck bigger-110"></i> Vehicle
     </a></li>
 </ul>

 <div class="tab-content">
    <div id="overview" class="tab-pane in active">
        <div class="infobox infobox-green  ">
            <div class="infobox-icon">
                <i class="icon-comments"></i>
            </div>

            <div class="infobox-data">
                <span class="infobox-data-number">32</span>
                <div class="infobox-content">Total Vehicles</div>
            </div>
        </div>

        <div class="infobox infobox-blue  ">
            <div class="infobox-icon">
                <i class="icon-twitter"></i>
            </div>

            <div class="infobox-data">
                <span class="infobox-data-number">11</span>
                <div class="infobox-content">Active Vehicles</div>
            </div>
        </div>
        <div class="infobox infobox-pink  ">
            <div class="infobox-icon">
                <i class="icon-shopping-cart"></i>
            </div>

            <div class="infobox-data">
                <span class="infobox-data-number">8</span>
                <div class="infobox-content">Paused Vehicles</div>
            </div>
        </div>
        <div class="infobox infobox-red  ">
            <div class="infobox-icon">
                <i class="icon-beaker"></i>
            </div>

            <div class="infobox-data">
                <span class="infobox-data-number">7</span>
                <div class="infobox-content">Stopped Vehicles</div>
            </div>
        </div>

        <div>
            <marquee behavior="scroll" direction="up" scrollamount="3"
            id="marquee">
            <div>
                <span>9:30p.m.</span> <span>I have arrived</span>
            </div>

            <div>
                <span>9:30p.m.</span> <span>I have arrived</span>
            </div>

        </marquee>
    </div>

</div>

<div id="vehicle" class="tab-pane">

  <h4>- Vehicle info</h4>

  <table class="table table-striped table-condensed table-hover">
     <tr>
        <td>Type</td>
        <td id="vh_type">Truck</td>
        <td>Model</td>
        <td id="vh_model">Toyota</td>
    </tr>

    <tr>
        <td>Date</td>
        <td id="vh_dt">11/5/2013</td>
        <td>Driver</td>
        <td id="vh_driver">Mr Who</td>
    </tr>
    <tr>
        <td colspan="2" >Last Maintenance</td>
        <td colspan="2" id="vh_maintain">Jul 12, 2012</td>
    </tr>
</table>

<h4>- Status</h4>

<table class="table table-striped table-condensed table-hover">
 <tr>
    <td>Latitude</td>
    <td id="i_lat"></td>
    <td>Longitude</td>
    <td id="i_lng"></td>
</tr>

<tr>
    <td>Altitude</td>
    <td id="i_alt"></td>
    <td>Speed</td>
    <td id="i_spd"></td>
</tr>
<tr>
    <td>Location</td>
    <td id="i_loc" colspan="3"></td>
</tr>
</table>

<h4>- History</h4>
<form>
 <fieldset>
    <span >Start Time</span>
    <div class="input-append">
        <input id="start_time" type="text" value="" name="start_time"></input>
        <span class="add-on"><i class="icon-calendar"></i></span>
    </div>
	<br/>
    <span style="padding-right: 1.5%">End Time</span>
    <div class="input-append">
        <input id="end_time" type="text" value="" name="end_time"></input>
        <span class="add-on"><i class="icon-calendar"></i></span>
    </div>
    
    <div class="pull-right">
        <div class="btn-group dropup ">
            <button class="btn btn-primary btn-small dropdown-toggle" data-toggle="dropdown">
              Past
              <span class="caret"></span></button>
              <ul class="dropdown-menu dropdown-icon-only">
                 <li><a href="#" id="past-12h">12 hours</a></li>
                 <li><a href="#" id="past-24h">24 hours</a></li>
                 <li><a href="#" id="past-3d">3 days</a></li>
                 <li><a href="#" id="past-7d">7 days</a></li>
             </ul>
         </div>

         <button id="btn_search " type="submit"
         class="btn btn-primary no-border btn-small"
         onClick="queryHistory()">
         Search&nbsp<i class="icon-search"></i>
     </button>
 </div>
</fieldset>
</form>
</div>

</div>
</div>
</div>
</div>
<div id="line-chart"></div>
<!-- <script src="resources/assets/js/jquery.min.js"></script> -->
<script src="resources/scripts/jquery-ui-1.10.3.custom.min.withDTpicker.js"></script>
<script src="resources/scripts/jquery-ui-timepicker-addon.js"></script>

<script src="resources/assets/js/bootstrap.min.js"></script>

<!--page specific plugin scripts-->

<script src="resources/assets/js/jquery.dataTables.min.js"></script>
<script src="resources/assets/js/jquery.dataTables.bootstrap.js"></script>

<!--ace scripts-->
<script src="resources/assets/js/ace-elements.min.js"></script>
<script src="resources/assets/js/ace.min.js"></script>
<script type="text/javascript" src="resources/scripts/v3_epoly.js"></script>
<!--
<script src="resources/scripts/bootstrap-datetimepicker.min.js"></script>
-->


<script>
$(document).ready(function() {
    var end = $("#end_time");
    var start =$('#start_time');

    start.datetimepicker({
        dateFormat: 'dd-mm-yy',
    });
    end.datetimepicker({
        dateFormat: 'dd-mm-yy',
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
   })


    $("#past-12h").click(function() {
        end.datetimepicker('setDate', (new Date()));
        var currentdate = end.datetimepicker("getDate");

        var date = currentdate.getDate();
        var month = currentdate.getMonth();
        var year =  currentdate.getFullYear();
        var hour =  currentdate.getHours();
        var min =  currentdate.getMinutes();
        start.datetimepicker("setDate", (new Date(year, month, date, hour-12, min)));

        $(".dropup").removeClass("open");
        return false;  
    })


    $("#past-24h").click(function() {
        end.datetimepicker('setDate', (new Date()));
        var currentdate = end.datetimepicker("getDate");

        var date = currentdate.getDate();
        var month = currentdate.getMonth();
        var year =  currentdate.getFullYear();
        var hour =  currentdate.getHours();
        var min =  currentdate.getMinutes();
        start.datetimepicker("setDate", (new Date(year, month, date, hour-24, min)));

        $(".dropup").removeClass("open");
        return false;  
    })



    $("#past-3d").click(function() {
        end.datetimepicker('setDate', (new Date()));
        var currentdate = end.datetimepicker("getDate");

        var date = currentdate.getDate();
        var month = currentdate.getMonth();
        var year =  currentdate.getFullYear();
        var hour =  currentdate.getHours();
        var min =  currentdate.getMinutes();
        start.datetimepicker("setDate", (new Date(year, month, date-3, hour, min)));

        $(".dropup").removeClass("open");
        return false;  
    })


    $("#past-7d").click(function() {
        end.datetimepicker('setDate', (new Date()));
        var currentdate = end.datetimepicker("getDate");

        var date = currentdate.getDate();
        var month = currentdate.getMonth();
        var year =  currentdate.getFullYear();
        var hour =  currentdate.getHours();
        var min =  currentdate.getMinutes();
        start.datetimepicker("setDate", (new Date(year, month, date-7, hour, min)));

        $(".dropup").removeClass("open");
        return false;  
    })


/*
    var picker_start = $('#start_time').data('datetimepicker');
    var picker_end = $('#end_time').data('datetimepicker');

    $("#past-12h").click(function() {
        var currentdate = new Date();
        var date = currentdate.getDate();
        var month = currentdate.getMonth();
        var year =  currentdate.getFullYear();
        var hour =  currentdate.getHours();
        var min =  currentdate.getMinutes();

        picker_end.setLocalDate(new Date(year, month, date, hour, min));
        picker_start.setLocalDate(new Date(year, month, date, hour-12, min));
        return false;
    })

    $("#past-24h").click(function() {
        var currentdate = new Date();
        var date = currentdate.getDate();
        var month = currentdate.getMonth();
        var year =  currentdate.getFullYear();
        var hour =  currentdate.getHours();
        var min =  currentdate.getMinutes();

        picker_end.setLocalDate(new Date(year, month, date, hour, min));
        picker_start.setLocalDate(new Date(year, month, date-1, hour, min));
        return false;
    })

    $("#past-3d").click(function() {
        var currentdate = new Date();
        var date = currentdate.getDate();
        var month = currentdate.getMonth();
        var year =  currentdate.getFullYear();
        var hour =  currentdate.getHours();
        var min =  currentdate.getMinutes();

        picker_end.setLocalDate(new Date(year, month, date, hour, min));
        picker_start.setLocalDate(new Date(year, month, date-3, hour, min));
        return false;
    })

    $("#past-7d").click(function() {
        var currentdate = new Date();
        var date = currentdate.getDate();
        var month = currentdate.getMonth();
        var year =  currentdate.getFullYear();
        var hour =  currentdate.getHours();
        var min =  currentdate.getMinutes();

        picker_end.setLocalDate(new Date(year, month, date, hour, min));
        picker_start.setLocalDate(new Date(year, month, date-7, hour, min));
        return false;
    })
    */

    $("#marquee").mouseover(function() {
        //alert("here");
        $(this).attr("scrollamount", "0");
    });

    $("#marquee").mouseout(function() {
        $(this).attr("scrollamount", "3");
    })
});

    </script>
    <script src="resources/assets/js/bootstrap.min.js"></script>

</body>
</html>
