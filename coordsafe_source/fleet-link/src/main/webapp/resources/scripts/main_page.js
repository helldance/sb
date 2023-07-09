/**
 * 
 */
function addInfowindowToMarker2(obj, marker){
	google.maps.event.clearListeners(marker, 'click');
	google.maps.event.clearListeners(marker, 'mouseover');
	
	var lat = marker.getPosition().lat();
	var lng = marker.getPosition().lng();
	
	/*if (obj instanceof Trip)*/
	// assume is trip object
	
	google.maps.event.addListener(marker,'mouseover',
		function() {				
			// get trip event data
			getTripEvents(obj.id);
			// build infowindow
			var content = '<div id="iwin2" style="overflow: auto"><b>Trip</b> - <i>' +  dateFormat(new Date(obj.tripStartTime), "dd/mm/yyyy") + '</i><hr>';
			content += '<div id="trip_info" style="margin-bottom: 6px"><i class="icon-time pull-left"> ' + dateFormat(new Date(obj.tripStartTime), "h:MM TT");
			
			if (obj.tripEndTime === null){
				content += ' - ?';
				content += '</i> <i class="icon-road pull-left"> &nbsp;-- KM </i></div>'; 
			}
			else{
				content += ' - ' + dateFormat(new Date(obj.tripEndTime), "h:MM TT");
				content += '</i> <i class="icon-road pull-left"> ' + obj.mileage + ' KM </i></div>'; 
			}
			
			content += '<br><hr><div><i class="icon-flag-checked pull-left">';
			content += '<label><input id="spd_chk" type="checkbox" onclick="showSpeeding()"><span style="margin-left: 24px">Speeding</span></label></i></div>';
			content += '<label><input id="pse_chk" type="checkbox" onclick="showPause()"><span style="margin-left: 24px">Pause</span></label></i></div><br>';
			/*content += '<hr><div id="action_btn2" class="pull-right"><input class="btn btn-purple btn-small no-border" type="button" value="Speeding" onclick="getSpeeding(' + obj.id + ')" />';*/
			content += '</div>';
			
			//console.log(content);

			infoWindow.setContent(content);  
			infoWindow.open(map,marker);
		}
	);
}

var speeding_markers = [];
var pause_markers  = [];
var speeding_event = [];
var pause_event = [];
var spd_img = "resources/images/mm_red.png";
var pse_img = "resources/images/mm_purple.png";

function getTripEvents(tripId){
	/*for (var i = 0; i < speeding_markers.length; i ++){
		speeding_markers[i].setMap(null);
    }  
	for (var i = 0; i < pause_markers.length; i ++){
		pause_markers[i].setMap(null);
    }*/ 
	
	speeding_event = [];
	pause_event = [];
	
	var req = "/fleet-link/event/trip/" + tripId;
	
	 $.getJSON(req, function(data) { 
		 $.each(data, function(index, item){
			 if (item.type === "VHC_SPEEDING"){
				 //var mkr = createPlainMarker(item.location.latitude, item.location.longitude, spd_img);
				 //speeding_markers.push(mkr);
				 speeding_event.push(item);
			 }
			 else if (item.type === "VHC_PAUSE"){
				 //var mkr = createPlainMarker(item.location.latitude, item.location.longitude, pse_img);
				 //pause_markers.push(mkr);
				 pause_event.push(item);
			 }
		 });
	 });
}

function showSpeeding(){
	for (var i = 0; i < speeding_markers.length; i ++){
		speeding_markers[i].setMap(null);
    }  	
	
	speeding_markers = [];
	
	if ($("#spd_chk").is(':checked')){			
		$.each(speeding_event, function(index, item){
			 var mkr = createPlainMarker(item.location.latitude, item.location.longitude, spd_img);
			 speeding_markers.push(mkr);
		});
	}
}

function showPause(){
	for (var i = 0; i < pause_markers.length; i ++){
		pause_markers[i].setMap(null);
    }  	
	
	pause_markers = [];
	
	if ($("#pse_chk").is(':checked')){			
		$.each(pause_event, function(index, item){
			 var mkr = createPlainMarker(item.location.latitude, item.location.longitude, pse_img);
			 pause_markers.push(mkr);
		});
	}
}

// function to drop a marker, no infowindow, but customized icon
function createPlainMarker(lat, lng, img){
	var pos = new google.maps.LatLng(lat, lng);
	var mkr = new google.maps.Marker({position: pos, icon: img, map: map}); 
	
	return mkr;
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

function gr_locator(){
	var key = "?key=master-1234qwr";
	var req_loc = "/geoready/api/locator/company/" + companyId + key; 
	
	console.log(req_loc);
	
	$.getJSON(req_loc, function(data){
		$.each(data, function(key, activity){
			//activities.push(activity);

			//
		});
	});
}

function gr_history(locator, s, e){
	var key = "?key=master-1234qwr";
	var req_his = "/geoready/api/history/" + locator + "/" + s + "," + e + key; 
	
	console.log(req_his);
	
	$.getJSON(req_his, function(data){
		$.each(data, function(key, activity){
			//activities.push(activity);

			//
		});
	});
}

/**
 * performs list of action command on infowindow
 */

function viewCurrentOrLastTrip (){
	clearHistory();
	
	//var loc = $("#sel_locator").val();
	var loc = curSelectedLocator.locatorId;
	
	var req = "/fleet-link/trip/detail/" + loc + "/curOrLast";
	
	$("#loading").show();
    
    $.getJSON(req, function(data) {
        $("#loading").fadeOut();
    	$("#btn_search").removeAttr('disabled');
    	        
        tripData = data;         
        
        // if no trip data, alert
        if ($.isEmptyObject(tripData)){
        	alert("No trips performed within chosen period");
        	//loadModal("No trips are found");
        }                
        else {
        	drawPath(tripData);
        }
        //drawChart(0);
    });
}

function addInfowindowToMarker3(order, marker){
	google.maps.event.clearListeners(marker, 'click');
	google.maps.event.clearListeners(marker, 'mouseover');
	
	var lat = marker.getPosition().lat();
	var lng = marker.getPosition().lng();
    var title = order.orderNumber;
    var info = "<div> Order No: <b>" + title +  "</b>"  + "</i><span class='pull-right' style='margin-right: 24px'>" + order.status 
    	+ "</span></div><hr><div style='width: 100%'><span><i class='icon-time icon-large'></i> &nbsp; " + dateFormat(new Date(order.targetCompletionDt), "ddd, mm/dd, yyyy, h:MM:ss TT") + "</span>"; 
    var orderId = order.id;
	
	/*if (obj instanceof Trip)*/
	// assume is trip object
	
	google.maps.event.addListener(marker,'click',			
		function() {								
			geocoder.geocode({'latLng': marker.getPosition()}, function(results, status) {
				if (status == google.maps.GeocoderStatus.OK) {                            
					if (results[0]) {
						format_addr = results[0].formatted_address ;
						formatted_address = "<br><span><i class='icon-map-marker icon-large'></i> &nbsp; " + format_addr + "</span></div>";
						
						// build infowindow
						var content = '<div id="iwin" style="width: 480px;">' +  info+formatted_address;
						content += '<i class="icon-comment"></i> &nbsp; ' + order.remark + '<hr>';
						content += '<div id="action_btn" class="pull-right"><input class="btn btn-purple btn-small no-border" type="button" value="Assign" onclick="viewCurrentOrLastTrip()"/>';
						/* content += '&nbsp;<input class="btn btn-success btn-small no-border" type="button" value="Send Message" onclick="viewCurTrip()"/>'
						content += '&nbsp;<input class="btn btn-danger btn-small no-border" type="button" value="Take Picture" onclick="viewCurTrip()"/>';*/
						content += '&nbsp;<input class="btn btn-info btn-small no-border" type="button" value="Mark Done" onclick="markDone(' + orderId + ')" />'; 
						content += '<br></div>'; 
						content += '</div>';
						
						//console.log(content);

						infoWindow.setContent(content);  
						infoWindow.open(map,marker);
					}
				}
			});
		});
	}

function addInfowindowToMarker(info, marker){
	// clear event listener
	google.maps.event.clearListeners(marker, 'click');
	google.maps.event.clearListeners(marker, 'mouseover');
	
	var lat = marker.getPosition().lat();
	var lng = marker.getPosition().lng();
	
	google.maps.event.addListener(marker,'click',
		function() {								
			geocoder.geocode({'latLng': marker.getPosition()}, function(results, status) {
				if (status == google.maps.GeocoderStatus.OK) {                            
					if (results[0]) {
						format_addr = results[0].formatted_address ;
						formatted_address = "<br><span><i class='icon-map-marker icon-large'></i> &nbsp; " + format_addr + "</span></div>";
						
						// build infowindow
						var content = '<div id="iwin" style="width: 480px;">' +  info+formatted_address+ '<hr>';
						content += '<div id="action_btn" class="pull-right"><input class="btn btn-purple btn-small no-border" type="button" value="View Trip" onclick="viewCurrentOrLastTrip()"/>';
						/* content += '&nbsp;<input class="btn btn-success btn-small no-border" type="button" value="Send Message" onclick="viewCurTrip()"/>'
						content += '&nbsp;<input class="btn btn-danger btn-small no-border" type="button" value="Take Picture" onclick="viewCurTrip()"/>';*/
						content += '&nbsp;<input class="btn btn-info btn-small no-border" type="button" value="Zoom to" onclick="zoomToObject(' + lat + ',' + lng + ', 20)" />'; 
						content += '<br></div>'; 
						content += '</div>';
						
						//console.log(content);

						infoWindow.setContent(content);  
						infoWindow.open(map,marker);

						$(".active").removeClass("active");
						$("#vehicle-li").addClass("active");
						$("#vehicle").addClass("active");

						fillLocatorData (marker.title, false);
					}
				}
			});
		});
}

function markDone(orderId){
	var req = "/fleet-link/workorder/done?orderId=" + orderId;
	
	 $.getJSON(req, function(data) { 
		 console.log("done: " + data);
	 });
}