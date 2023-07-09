var map;
var poly,poly2;//poly is used to draw the map when load, poly2 is used to save the image of the map
var directionsService = new google.maps.DirectionsService();
var display = []; //array storing routes or lines being drawn on the map
var polyOptions = {strokeColor: '#0000b3', strokeOpacity: 0.5, strokeWeight: 5};
var displayOptions = {suppressMarkers: true, preserveViewport:true, polylineOptions: polyOptions};
var start_marker = new google.maps.Marker();
var end_marker = new google.maps.Marker();
var points = [];
var save_data;
var gfId;
var editEnable = false;
var coordinates = [];

//Draw the map
function initialize(){
    mapOption={
        center: new google.maps.LatLng(1.366667, 103.8),
        zoom: 11,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map= new google.maps.Map(document.getElementById("map"),mapOption);
    poly = new google.maps.Polyline(polyOptions);
    poly2 = new google.maps.Polyline(polyOptions);
    poly.setMap(map);
    google.maps.event.addListener(map,"click",addDirection);
    
    if (editEnable){
    	edit();
    }
};

function addDirection(event){
    //Populate click point into an array
    points.push(event.latLng);
    start_marker.setMap(map);
    start_marker.setPosition(points[0]);
    start_marker.setVisible(true);
    if(points.length==0){
        $("#undo").removeAttr("disabled","true");
        $("#reset").removeAttr("disabled","true");
        $("#close-route").attr("disabled","true");
        return;
    }else{
        $("#undo").removeAttr("disabled","true");
        $("#reset").removeAttr("disabled","true");
        $("#close-route").removeAttr("disabled","true");
    }
    //Check whether snap-to-road checkbox checked and draw accordingly
    if($("#snap-to-road").is(':checked')){
        addRoute();    
    }else{
        addLine();
    }
    end_marker.setMap(map);           
    end_marker.setPosition(points[points.length-1]);
    end_marker.setVisible(true);
};
//Add polyline to the map
function addLine(){
    var coordinates = [points[points.length-2],points[points.length-1]];
    var path = new google.maps.Polyline({path: coordinates});
    display.push({"type":"line","drawing":path});
    display[display.length-1].drawing.setMap(map);
    display[display.length-1].drawing.setOptions(polyOptions);
    calculateDistance();
};

//Add snap-to-road route to the map
function addRoute(){
    var request = {
        origin: points[points.length-2],
        destination: points[points.length-1],
        travelMode: google.maps.TravelMode.DRIVING
    }
    directionsService.route(request,function(response,status){
        if(status == google.maps.DirectionsStatus.OK){
        	var directionsDisplay = new google.maps.DirectionsRenderer(displayOptions);
            display.push({"type":"route","drawing":directionsDisplay});
        	display[display.length-1].drawing.setMap(map);
            display[display.length-1].drawing.setDirections(response);
            calculateDistance();
        }
    });
};

function undoRoute(){
    if(display.length==0&&points.length==0){
        //disable undo route
    }else if(display.length==0&&points.length!=0){
        //remove start marker and end marker
        if(start_marker.getMap()!=null){
            start_marker.setMap(null);    
        }
        if(end_marker.getMap()!=null){
            end_marker.setMap(null);    
        }
        points.pop();
        clearMap();
        $("#undo").attr("disabled","true");
        $("#reset").attr("disabled","true");
        $("#close-route").attr("disabled","true");
        var distance =0;
        /*$("#distance").empty();
        $("#distance").append('<p>Distance: '+ distance.toFixed(2) +' km</p>');*/
        $("#distance").html(distance.toFixed(2));
    }else{
        if(display[display.length-1].type=="route"){
            display[display.length-1].drawing.setDirections({routes: []});
        }else if(display[display.length-1].type=="line"){
            display[display.length-1].drawing.setMap(null);
        }
        display.pop();
        points.pop();
        end_marker.setPosition(points[points.length-1]);
        calculateDistance();
    }
};

//prompt user to confirm box before deleting all the map
function resetRoute(){
    var confirmBox = window.confirm("Do you really want to reset?\nAll unsaved change will be lost.");
    if(confirmBox==false){
        return;
    }else{
        clearMap();
    }  
};

function closeRoute(){
    if($("#snap-to-road").is(':checked')){        
        var request = {
            origin: points[points.length-1],
            destination: points[0],
            travelMode: google.maps.TravelMode.DRIVING
        }
        points.push(points[0]);
        directionsService.route(request,function(response,status){
            if(status == google.maps.DirectionsStatus.OK){
                var directionsDisplay = new google.maps.DirectionsRenderer(displayOptions);
                display.push({"type":"route","drawing":directionsDisplay});
                display[display.length-1].drawing.setMap(map);
                display[display.length-1].drawing.setDirections(response);
                calculateDistance();            
            }
        });
    }else{
        var coordinates = [points[points.length-1],points[0]];
        var path = new google.maps.Polyline({path: coordinates});
        display.push({"type":"line","drawing":path});
        display[display.length-1].drawing.setMap(map);
        display[display.length-1].drawing.setOptions(polylineOptions);
        calculateDistance();
    }
    end_marker.setMap(map);           
    end_marker.setPosition(points[0]);
    end_marker.setVisible(true);
};

function genImageUrl (){
    //generate Google Maps Static image url
    var prefix = "http://maps.googleapis.com/maps/api/staticmap?";
    var org_zoom = map.getZoom(), org_center=map.getCenter();
    if(poly2.getPath()!=null){
        poly2.setPath([]);
    }
    var path = poly2.getPath();
    var bounds = new google.maps.LatLngBounds();
    var points = pathOptimize(coordinates);//reduce the number of points to meet the requirement of Google Static Maps API V2(url must not have more than 2048 characters)
    for(var i=0, l=points.length;i<l;i++){
        path.push(points[i]);
        bounds.extend(points[i]);
    }
    if(coordinates.length>0){
        map.fitBounds(bounds);
    }
    var center = map.getCenter(), center_lat = (center.jb).toFixed(6), center_lng = (center.kb).toFixed(6);
    var zoom = map.getZoom()-2;
    var strokeColor = "blue";
    var url = "";
    url = prefix + "center="+center_lat+","+center_lng;
    url+="&zoom="+zoom+"&path=color:"+strokeColor+"|weight:4|";
    var encodeString = google.maps.geometry.encoding.encodePath(path);
    url+="enc:"+encodeString;
    url+="&sensor=false";

    $("#display-image").show();
    $("#display-image").empty();
    $("#display-image").append('<p>Saved map</p>')
    $("#display-image").append('<img src='+url+'>');
    map.setCenter(org_center);
    map.setZoom(org_zoom);

    //generate json and send to the server
    var geometry = JSON.stringify({"type":"LineString","coordinates":coordinates});

    save_data = {"drawing_type":0,"geometry":geometry,"img_url":url};
}

//Save map under geojson specification, {"type":"LineString",coordinates:[Position]}
function save(){
    for(var i=0, l=display.length;i<l;i++){
        if(display[i].type=="route"){
            var path=display[i].drawing.directions.routes[0].overview_path;
            for(var j=0, l2=path.length;j<l2;j++){
                coordinates.push([path[j].kb,path[j].jb]);
            }
        }else if(display[i].type="line"){
            var path=display[i].drawing.getPath();
            if(coordinates.length==0){
                coordinates.push([path.b[0].kb,path.b[0].jb]);
            }
            coordinates.push([path.b[path.b.length-1].kb,path.b[path.b.length-1].jb]);
        }
    }
    
    genImageUrl();
    
    //save_data = {"type":"LineString","coordinates":coordinates};
    
    var str = JSON.stringify(save_data);
        
    $.ajax({
        type:"POST",
        url:"/fleet-link/geofence/zone/add",
        data:str,
        beforeSend: function (request){
            request.setRequestHeader("Content-Type", "text/plain");
        },
        success: function(){
            alert("Drawing saved!");
        },
        dataType:"text"
    });
};

//load data from the server under GeoJSON format and display it on the map
function edit(){
    //$.getJSON("http://192.168.100.12:8070/geoready/api/zone/60?key=test-1234qwer",function(data){
        clearMap();
        //var load_data=data.geometry;
        if(load_data.type=="LineString"){
            poly.setMap(map);
            var path = poly.getPath();
            var bounds = new google.maps.LatLngBounds();
            for(var i=0, l=load_data.coordinates.length;i<l;i++){
                var point = new google.maps.LatLng(load_data.coordinates[i][1],load_data.coordinates[i][0]);
                path.push(point);
                bounds.extend(point);
            }
            if(load_data.coordinates.length>1){
                map.fitBounds(bounds);
            }
            var distance =google.maps.geometry.spherical.computeLength(path)/1000;
            distance = distance.toFixed(2);
            /*$("#distance").empty();
            $("#distance").append('<p>Distance: '+ distance +' km</p>');*/
            $("#distance").html(distance);
        }
        poly.setEditable(false);
    //});
};

function saveMapImage(){
    $("#display-image").show();
    $("#display-image").empty();
    var prefix = "http://maps.googleapis.com/maps/api/staticmap?";
    $.getJSON("http://192.168.100.12:8070/geoready/api/zone/60?key=test-1234qwer",function(data){
        var load_data=data.geometry;
        if(load_data.type=="LineString"){
            var bounds = new google.maps.LatLngBounds();
            if(poly2.getPath()!=null){
                poly2.setPath([]);
            }
            var path = poly2.getPath();
            var points = pathOptimize(load_data.coordinates);//reduce the number of points to meet the requirement of Google Static Maps API V2(url must not have more than 2048 characters)
            for(var i=0, l=points.length;i<l;i++){
                path.push(points[i]);
                bounds.extend(points[i]);
            }
            var zoom = getBoundsZoomLevel(bounds,{"height":150,"width":100});//Calculate zoom basing the bounds, height and width of the image
            var center = bounds.getCenter(), center_lat = (center.jb).toFixed(6), center_lng = (center.kb).toFixed(6);
            var strokeColor = "blue";
            var url = "";
            url = prefix + "center="+center_lat+","+center_lng+"&size=150x100&zoom="+zoom+"&path=color:"+strokeColor+"|weight:4|";
            var encodeString = google.maps.geometry.encoding.encodePath(path);
            url+="enc:"+encodeString;
            url+="&sensor=false&key=AIzaSyAM2GxecuxmLMeUc21w3-QuAD_9d2CQj4k";
            $("#display-image").append('<p>Saved map</p>')
            $("#display-image").append('<img src='+url+'>');
        }
    });
};

//Input an array of coordinates and and value (in metre) in order to optimize the path for redrawing path for the polyline
function pathOptimize(coordinates){
    var points = [], optimized_points = [], list = [];
    var value;
    var total=0;
    for(var i=0, l=coordinates.length;i<l;i++){
        points.push(new google.maps.LatLng(coordinates[i][1],coordinates[i][0]));
        if(i!=0){
            list.push(google.maps.geometry.spherical.computeLength([points[i-1],points[i]]));
            total+=list[i-1];
        }
    }
    value=total/250;
    for(var i=0, l=points.length; i<l;i++){
        if(i==0){
            optimized_points[0]=points[0];
        }else{
            var dist= google.maps.geometry.spherical.computeLength([optimized_points[optimized_points.length-1],points[i]]);
            if(dist>value){
                optimized_points.push(points[i]);
            }
        }
    }
    return optimized_points;
};

//calculate total distance of drawing
function calculateDistance(){
    var distance =0;
    for(i=0, l=display.length;i<l;i++){
        if(display[i].type=="route"){
            //Getting distance information of a route
            distance+= display[i].drawing.directions.routes[0].legs[0].distance.value/1000;    
        }else if(display[i].type=="line"){
            //calculate distance of polyline using Google Geometry library
            distance+= google.maps.geometry.spherical.computeLength(display[i].drawing.getPath())/1000;
        }
    }
    distance = distance.toFixed(2);
    /*$("#distance").empty();
    $("#distance").append('<p>Distance: '+ distance +' km</p>');*/
    $("#distance").html(distance);
};

function getBoundsZoomLevel(bounds, mapDim) {
    var WORLD_DIM = { height: 256, width: 256 };
    var ZOOM_MAX = 21;

    function latRad(lat) {
        var sin = Math.sin(lat * Math.PI / 180);
        var radX2 = Math.log((1 + sin) / (1 - sin)) / 2;
        return Math.max(Math.min(radX2, Math.PI), -Math.PI) / 2;
    }

    function zoom(mapPx, worldPx, fraction) {
        return Math.floor(Math.log(mapPx / worldPx / fraction) / Math.LN2);
    }

    var ne = bounds.getNorthEast();
    var sw = bounds.getSouthWest();

    var latFraction = (latRad(ne.lat()) - latRad(sw.lat())) / Math.PI;

    var lngDiff = ne.lng() - sw.lng();
    var lngFraction = ((lngDiff < 0) ? (lngDiff + 360) : lngDiff) / 360;

    var latZoom = zoom(mapDim.height, WORLD_DIM.height, latFraction);
    var lngZoom = zoom(mapDim.width, WORLD_DIM.width, lngFraction);

    return Math.min(latZoom, lngZoom, ZOOM_MAX);
};

function clearMap(){
    $("#distance").empty();
    $("#distance").append('<p>Distance: 0.00 km</p>');
    for(var i=display.length-1; i>=0;i--){
        if(display[i].type=="route"){
            display[i].drawing.setDirections({routes: []});
        }else if(display[i].type=="line"){
            display[i].drawing.setMap(null);
        }
        display.pop();
    }
    if(poly.getMap()!=null){
        var path = poly.getPath();
        for(var i=0, l=path.length;i<l;i++){
            path.pop();
        }
        poly.setMap(null);
    }
    if(start_marker.getMap()!=null){
        start_marker.setMap(null);
    }
    if(end_marker.getMap()!=null){
        end_marker.setMap(null);    
    }
    $("#undo").attr("disabled","true");
    $("#reset").attr("disabled","true");
    $("#close-route").attr("disabled","true");
};