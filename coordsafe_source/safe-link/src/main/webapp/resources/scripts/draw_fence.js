var map;
var poly, poly2;//poly is used to draw the map when load, poly2 is used to save the image of the map
var polyOptions = {strokeColor: '#0000b3', strokeOpacity: 0.5, strokeWeight: 4, editable: true, suppressUndo: true, fillColor:"transparent"};
var start_marker = new google.maps.Marker();
var end_marker = new google.maps.Marker();
var save_data;
var load_data;
var polyLastState=[];
var coordinates=[];
var undoCheck=false;
var changes=[];
var gfId;
var editEnable = false;
var ZoneType;
var zoneName;
//Draw the map
function initialize2(){
	console.log("In the initialize2...");
    mapOption={
        center: new google.maps.LatLng(1.32219, 103.827152),
        zoom: 12,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map  = new google.maps.Map(document.getElementById("map"),mapOption);
    google.maps.event.trigger(map, "resize");
    poly = new google.maps.Polygon(polyOptions);
    poly2 = new google.maps.Polyline(polyOptions);
    google.maps.event.addListener(map,"click",addDirection);

    $('#myTooltip').tooltip({
        //use 'of' to link the tooltip to your specified input
        position: { of: "#zone"}
    });
    $("#display-image").click(function(){
        load();
    });
    console.log("editEnable=" + editEnable);
    if (editEnable){
    	edit();
    }
};

//add new points to the polygon
function addDirection(event){
    if(poly.getMap()== null){
        poly.setMap(map);
        pathChangeEvent(poly.getPath());
    }
    var path = poly.getPath();
    path.push(event.latLng);
    //When there is only one point (first click)
    if(path.b.length==1){
        start_marker.setMap(map);
        start_marker.setPosition(path.b[0]);
        start_marker.setVisible(true);
        $("#undo").removeAttr("disabled","true");
        $("#reset").removeAttr("disabled","true");
        $("#close-route").attr("disabled","true");
        return;
    }else{//When there are more than one point
        $("#undo").removeAttr("disabled","true");
        $("#reset").removeAttr("disabled","true");
        $("#close-route").removeAttr("disabled","true");
        calculateDistanceArea();
        start_marker.setMap(map);
        start_marker.setPosition(path.b[0]);
        start_marker.setVisible(true);
        end_marker.setMap(map);           
        end_marker.setPosition(path.b[path.b.length-1]);
        end_marker.setVisible(true);
    }
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
    //var points = pathOptimize(coordinates);//reduce the number of points to meet the requirement of Google Static Maps API V2(url must not have more than 2048 characters)
    for(var i=0, l=coordinates.length;i<l;i++){
    	var point = new google.maps.LatLng(coordinates[i][0],coordinates[i][1]);
        path.push(point);
        bounds.extend(point);
    }
    if(coordinates.length>0){
        map.fitBounds(bounds);
    }
    var center = map.getCenter();
    var center_lat = (center.lat()).toFixed(6);
    var center_lng = (center.lng()).toFixed(6);
    var zoom = map.getZoom()-2;
    var strokeColor = "blue";
    var url = "";
    url = prefix + "center="+center_lat+","+center_lng;
    url+="&zoom="+zoom+"&path=color:"+strokeColor+"|weight:4|";
    var encodeString = google.maps.geometry.encoding.encodePath(path);
    url+="enc:"+encodeString;
    url+="&sensor=false";

    //generate json and send to the server
    var geometry = JSON.stringify({"type":"Polygon","coordinates":[coordinates]});
    //drawing_type 0 is fence; 1 is route;zoneType means dangerous or safe zone;
    //save_data = {"drawing_type":0,"zoneType":zoneType,"geometry":geometry,"img_url":url}; 
    save_data = {"drawing_type":0,"zoneType":zoneType,"zoneName":zoneName,"geometry":geometry,"img_url":url}; 
}

//Implementing undo functionality for drawing by doing a logical reverse of the previous action
//All actions done before are stored in changes[] with respective types ("insert_at" or "set_at")
function undoRoute(){
    undoCheck=true;
    if(changes.length==0){
        //do nothing
    }else if(changes.length==1){
        //remove start_marker and end_marker
        if(start_marker.getMap()!=null){
            start_marker.setMap(null);    
        }
        if(end_marker.getMap()!=null){
            end_marker.setMap(null);    
        }
        changes.pop();
        poly.setPath([]);
        poly.setMap(null);
        $("#undo").attr("disabled","true");
        $("#reset").attr("disabled","true");
        $("#close-route").attr("disabled","true");
        /*$("#distance").empty();
        $("#distance").append('<p>Distance: 0.00 km</p>');*/
        $("#dist").html("0.00");
        $("#area").html("0.00");
    }else{
        var path=poly.getPath();
        var lastChange = changes.pop();
        if(lastChange.type=="insert_at"){
            path.removeAt(lastChange.index);
        }else if(lastChange.type=="set_at"){
            path.setAt(lastChange.index,lastChange.coordinates);
        }
        start_marker.setPosition(path.b[0]);
        end_marker.setPosition(path.b[path.b.length-1]);
        calculateDistanceArea();
    }
}

//remove polygon from the map, clear path, clear polyLastState
function resetRoute(){
    var confirmBox = window.confirm("Do you really want to reset?\nAll unsaved change will be lost.");
    if(confirmBox==false){
        return;
    }else{
        clearMap();
    }  
};

function closeRoute(){
    var path = poly.getPath();
    path.push(path.b[0]);
    calculateDistanceArea();
    end_marker.setMap(map);           
    end_marker.setPosition(path.b[0]);
    end_marker.setVisible(true);
};

//set stroke color and fill color for polygon. Red is for dangerous zone; green is for safe zone
function chooseZone(){
    setTimeout(function(){
        $("#myTooltip").tooltip("close");    
    },250);
    
    zoneType = $("#zone input[name=zone]:checked").val();
    var path=poly.getPath();
    if(path.b[0]!=path.b[path.b.length-1]){
        closeRoute();    
    }
    if(zoneType=="safe"){
        poly.setOptions({fillColor:"green",strokeColor:"green"});
    }else if(zoneType=="dangerous"){
        poly.setOptions({fillColor:"red",strokeColor:"red"});
    }
}

function save(){
    if(!$("#zone input[name=zone]").is(":checked")){
        $("#myTooltip").tooltip("open");
        return;
    }
    
    if( $("#zone input[name=zoneName]").val() == ""){
        $("#myTooltip").tooltip("open");
        return;
    }
    
    zoneName = $("#zone input[name=zoneName]").val();
    console.log("zone Name = " + zoneName);
       
    var path = poly.getPath();
    
	var vts = path.getArray();
	
    if(path.b[0]!=vts[vts.length-1]){
        closeRoute();    
    } 

    for(var i=0, l=path.length;i<l;i++){
        //coordinates[i]=[path.b[i].kb,path.b[i].jb];
    	coordinates[i]=[vts[i].lat(),vts[i].lng()];
    }
    
    genImageUrl();
    
    //save_data = {"type":"Polygon","coordinates":[coordinates]};
    var str = JSON.stringify(save_data);
    var req;
    
    if (editEnable){
    	req = "/safe-link/geofence/edit/" + gfId;    		
    }
    else{
    	req = "/safe-link/geofence/zone/add";
    }
    
    $.ajax({
        type:"POST",
        url:req,
        data:str,
        beforeSend: function (request){
            request.setRequestHeader("Content-Type", "text/plain");
        },
        success: function(){
            //clearMap();
            alert("Drawing saved!");
            $("#edit-modal").modal("hide");
            loadPrimaryModal("#geofence_link");
        },
        error: function(jqXHR, textStatus, errorThrown) {
        	  alert(textStatus + ": failed to save update");
        },
        dataType:"text"
    });
};

function load(){
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
    //save map image and display as a screenshot of the map
    
    $.getJSON("http://localhost:8080/geoready/api/zone/40?key=test-1234qwer",function(data){
        load_data=data.geometry;
        if(load_data.type=="Polygon"){
            poly.setMap(map);
            var path = poly.getPath();
            var bounds = new google.maps.LatLngBounds();
            for(var i=0, l=load_data.coordinates[0].length;i<l;i++){
                var point = new google.maps.LatLng(load_data.coordinates[0][i][1],load_data.coordinates[0][i][0]);
                path.push(point);
                bounds.extend(point);
            }
            if(load_data.coordinates[0].length>1){
                map.fitBounds(bounds);
            }
            var zoneType = $("#zone input[name=zone]:checked").val();
            if(zoneType=="dangerous"){
                poly.setOptions({fillColor:"red",strokeColor:"red"});
            }else if(zoneType=="safe"){
                poly.setOptions({fillColor:"green",strokeColor:"green"});
            }
        }
        poly.setEditable(false);
    });
};

//Using Google Static Maps API for display displaying thumbnail of saved map
function saveMapImage(){
    $("#display-image").show();
    $("#display-image").empty();
    var org_Zoom = map.getZoom(), org_center=map.getCenter();
    var prefix = "http://maps.googleapis.com/maps/api/staticmap?";
    var path = poly.getPath();
    var bounds = new google.maps.LatLngBounds();
    for(var i=0, l=path.b.length;i<l;i++){
        bounds.extend(path.b[i]);
    }
    if(path.b.length>1){
        map.fitBounds(bounds);
    }
    var center = map.getCenter(), center_lat = (center.jb).toFixed(6), center_lng = (center.kb).toFixed(6);
    var zoom = map.getZoom()-2;
    var fillColor = poly.get("fillColor");
    var strokeColor = poly.get("strokeColor");
    if(fillColor.indexOf('#')!=-1){
        fillColor=fillColor.substring(1);
        fillColor="0x"+fillColor;
    }
    if(strokeColor.indexOf('#')!=-1){
        strokeColor=strokeColor.substring(1);
        strokeColor="0x"+strokeColor;
    }
    var strokeWeight = poly.get("strokeWeight");
    var url = "";
    url = prefix + "center="+center_lat+","+center_lng+"&size=150x100&zoom="+zoom+"&path=color:"+strokeColor+"|weight:"+strokeWeight+"|fillcolor:"+fillColor+"|";
    
    for(var i=0, l=path.length;i<l;i++){
        url+=path.b[i].jb.toFixed(5)+","+path.b[i].kb.toFixed(5)+"|";
    }
    if(path.b[0]!=path.b[path.b.length-1]){
        url+=path.b[0].jb.toFixed(5)+","+path.b[0].kb.toFixed(5)+"|"
    }
    url=url.substring(0,url.length-1);
    url+="&sensor=false";
    $("#display-image").append('<p>Saved map</p>')
    $("#display-image").append('<img src='+url+'>');
    map.setCenter(org_center);
    map.setZoom(org_Zoom);
};

//for user to edit saved map
function edit(){
	console.log("in the map edit...");
    clearMap();

    console.log("edit poly type=" + typeof poly);
	    	if (typeof poly === "undefined"){
	    		poly = new google.maps.Polygon(polyOptions);
	    	}
	    	
            poly.setMap(map);
            var path = poly.getPath();
            console.log("zoneType = " + zoneType);
            var bounds = new google.maps.LatLngBounds();
            for(var i=0, l=load_data.coordinates[0].length;i<l;i++){
                var point = new google.maps.LatLng(load_data.coordinates[0][i][0],load_data.coordinates[0][i][1]);
                path.push(point);
                bounds.extend(point);
            }
            if(load_data.coordinates[0].length>1){
                map.fitBounds(bounds);
            }
            pathChangeEvent(path);
            //chooseZone();
            calculateDistanceArea();
            start_marker.setPosition(path.b[0]);
            end_marker.setPosition(path.b[path.b.length-1]);
            poly.setEditable(true);
            
            if(zoneType=="dangerous"){
                poly.setOptions({fillColor:"red",strokeColor:"red"});
            }else if(zoneType=="safe"){
                poly.setOptions({fillColor:"green",strokeColor:"green"});
            }

};

function clearMap(){
	$("#dist").html("0.00");
    $("#area").html("0.00");
    if (typeof poly != "undefined"){
    	var path=poly.getPath();
    	
        for(var i=0, l=path.length;i<l;i++){
            path.pop();
        }
        
        poly.setMap(null);
    }
    	
    
    for(var i=0, l=polyLastState.length; i<l; i++){
        polyLastState.pop();
    }
    
    start_marker.setMap(null);
    end_marker.setMap(null);
    $("#undo").attr("disabled","true");
    $("#reset").attr("disabled","true");
    $("#close-route").attr("disabled","true");
}

//save the current path of the poly into polyLastState
function savePolyState(){
    var path = poly.getPath();
    //Clear polyLastState if array is not null
    if(polyLastState.length!=0){
        for(var i =0, l=polyLastState.length;i<l;i++){
            polyLastState.pop();
        }
    }
    for(var i=0,l=path.b.length;i<l;i++){
        polyLastState[i]=path.b[i];
    }
};

//calculate total distance of drawing and display the result on "#distance" div
function calculateDistanceArea(){
    var distance =0,area=0;
    var path=poly.getPath();
    var points=[];
    for(var i=0, l=path.b.length; i<l; i++){
        points[i]=path.b[i];
    }
    if(points[0]!=points[points.length-1]){
        points.push(points[0]);
    }
    distance+= google.maps.geometry.spherical.computeLength(points)/1000;
    area+= google.maps.geometry.spherical.computeArea(points)/1000000;
    distance = distance.toFixed(2);
    area = area.toFixed(2);
    /*$("#distance").empty();
    $("#distance").append('<p>Distance: '+ distance +' km</p>');*/
    $("#dist").html(distance);
    $("#area").html(area);
};

//set start_marker and end_makrer to the first and last LatLng of polygon
function setMarker(){
    var path=poly.getPath();
    start_marker.setPosition(path.b[0]);
    end_marker.setPosition(path.b[path.b.length-1]);
};

//add event to the path of polygon
//event is used to record changes in polygon and support undo function
//there are two events: "set_at" and "insert_at"
//If the event is fired by undo, there will no change recorded
function pathChangeEvent(path){
    google.maps.event.addListener(path,"set_at",function(event){
        if(!undoCheck){
            changes.push({"type":"set_at","index":event,"coordinates":polyLastState[event]});
            savePolyState();
        }else{
            undoCheck=false;   
        } 
        setMarker();
        calculateDistanceArea();
    });
    google.maps.event.addListener(path,"insert_at",function(event){
        changes.push({"type":"insert_at","index":event});
        savePolyState();
        setMarker();
        calculateDistanceArea();
    });
};