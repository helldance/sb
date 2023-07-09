<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Cache-control" content="private">
<script type="text/javascript" src="/fleet-link/resources/assets/js/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" href="/fleet-link/resources/css/chart/barchart.css" type="text/css" media="screen" charset="utf-8" />
<style type="text/css">
	#options-header {
		position: relative;
		top: 10px;
		width: 100%;
		z-index: 1;
		vertical-align:middle;
		margin-bottom: 24px;
		/* background: #AAA; */
	}
	
	.col_head {
		/* text-transform: uppercase;  */
		font-weight: bold;
		/* color: rgb(167, 201, 66); */		
	}

</style>

<script type="text/javascript" src="/fleet-link/resources/scripts/chart/raphael-min.js"></script>
<script type="text/javascript" src="/fleet-link/resources/scripts/chart/g.raphael-min.js"></script>
<script type="text/javascript" src="/fleet-link/resources/scripts/chart/g.bar-min.js"></script>
<script type="text/javascript" src="/fleet-link/resources/scripts/chart/barchart.js"></script>
<script type="text/javascript" src="/fleet-link/resources/scripts/chart/raphael.export.js"></script>
<script type="text/javascript" src="/fleet-link/resources/scripts/chart/svgfix-0.2.js"></script>
<script type="text/javascript" src="/fleet-link/resources/scripts/chart/canvg.js"></script>
<script type="text/javascript" src="/fleet-link/resources/scripts/chart/canvas2image.js"></script>
<script type="text/javascript" src="/fleet-link/resources/scripts/chart/rgbcolor.js"></script>

</head>
<body>
	<script type="text/javascript">
    var trip_data;
    var group_data;
    var groupVehicleList = [];
    var grp2;
    var prefix="/fleet-link/trip/";
    var locatorId;
    var vehicleName;
    var first = true;

	$(document).ready(function() {
		$("#report-submenu").css("display","block");
		
		retrieveGroupVehicle();
	});
	
	function retrieveGroupVehicle(){
		var req = "/fleet-link/vgroup/company/" + <sec:authentication property="principal.company.id"/> + "/list";
		
		$.getJSON(req, function(data){
			groupVehicleList = data;
			
			$("#group-option").empty();
			$("#group-option").append($("<option/>", {value: '-1', text: 'Select Group'}));
			
			$.each(data, function (i, item) {    
        		$("#group-option").append($("<option/>", {value: item.group.groupId, text: item.group.groupName}));
        	});
		});
	}
	
	function selectGroupOption() {
		var groupOption = $("#group-option option:selected").val();

		$.each(groupVehicleList, function (i, item){		
			if (item.group.groupId === parseInt(groupOption)){
				$("#vehicle-option").empty();
				$("#vehicle-option").append($("<option/>", {value: '-1', text: 'Select Vehicle'}));
				
				var vehicleData = item.vehicles;
									
				$.each(vehicleData, function (i, item){
					$("#vehicle-option").append($("<option/>", {value: item.locatorId, text: item.vehicleName}));
				});
			}
		});
	}
	
	function retrieveTrip() {
		var req = prefix + "summary/" + locatorId;
		
		$.getJSON(req, function(data) {
			trip_data = data;
			
			//console.log(trip_data);
			
			// update caption
			if (first){
				$("#cp_vhc").append(vehicleName);
				$("#cp_vhc").css('display', '');
				first = false;
			}
			else 
				//$("#cp_vhc").append(" / " + vehicleName);
				$("#cp_vhc").append("<span class='tag'> | " + vehicleName + "<button class='close' type='button>×</button></span>");
			
			// populate table
						
			$("#w_mile").append("    " + trip_data[2].mileage + " Km");
			$("#w_dur").append("    " + msToTime(trip_data[2].duration));
			$("#w_trip").append("    " + trip_data[2].tripCount);
			$("#w_speed").append("    " + trip_data[2].speedCount);
			
			$("#m_mile").append("    " + trip_data[1].mileage + " Km");
			$("#m_dur").append("    " + msToTime(trip_data[1].duration));
			$("#m_trip").append("    " + trip_data[1].tripCount);
			$("#m_speed").append("    " + trip_data[1].speedCount);
			
			$("#y_mile").append("    " + trip_data[0].mileage + " Km");
			$("#y_dur").append("    " + msToTime(trip_data[0].duration));
			$("#y_trip").append("    " + trip_data[0].tripCount);
			$("#y_speed").append("    " + trip_data[0].speedCount);
		});
	}

	function selectVehicleOption() {
		if ($("#vehicle-option option:selected").val() === '-1'){
			return;
		}
		// limit vehicle to 5
		var ves = $("#cp_vhc").html();
		if (ves.split("|").length >= 5){
			// max 5 vehicle
			humane.log("You can add up to 5 vehicles");
			
			return;
		}
		
		locatorId = $("#vehicle-option option:selected").val();
		vehicleName =  $("#vehicle-option option:selected").text();
		
		retrieveTrip();
	}
	
	function msToTime(s) {
		  function addZ(n) {
		    return (n<10? '0':'') + n;
		  }

		  var ms = s % 1000;
		  s = (s - ms) / 1000;
		  var secs = s % 60;
		  s = (s - secs) / 60;
		  var mins = s % 60;
		  var hrs = (s - mins) / 60;

		  return addZ(hrs) + ':' + addZ(mins) + ':' + addZ(secs);
	}
	
	function clearTable(){
		// update caption
		$("#cp_vhc").html("");
		
		// populate table
		$("#m_mile").html("");
		$("#m_dur").html("");
		$("#m_trip").html("");
		$("#m_speed").html("");
		
		$("#y_mile").html("");
		$("#y_dur").html("");
		$("#y_trip").html("");
		$("#y_speed").html("");
		
		$("#w_mile").html("");
		$("#w_dur").html("");
		$("#w_trip").html("");
		$("#w_speed").html("");
	}
</script>
<div id="main-content" class="clearfix">
	<h2 class="contentHeader"><i class="icon-double-angle-right"></i>
	    Vehicle Summary	
	</h2>
	<div id="options-header" class="row-fluid">
		<div class="span8">
			<span style="font-size: 16px; line-height: 24px"><!--  <i class="icon-plus green"></i> Add Vehicle: -->  </span>
			<select id="group-option" onchange="selectGroupOption()"></select>
			<select id="vehicle-option" onchange="selectVehicleOption()"></select>  
		</div>
		
		<div class="span4">
			<div class="control-group pull-right">
				<button id="clr" class="btn btn-small btn-danger" onclick="clearTable()">
					<i class="icon-picture"></i> Clear
				</button>
				<!-- hide for no implementation 27/06/2014  -->
				<!-- <button id="ept" class="btn btn-small btn-purple" onclick="exportTable()">
					<i class="icon-cloud-download"></i> Export
				</button>
				<button id="eml" class="btn btn-small btn-info" onclick="emailTable()">
					<i class="icon-mail-forward"></i> Email
				</button> -->
			</div>
		</div>
		<!-- <input id="saveAsPNG" type="submit" value="Export PNG"	onClick="savePNG()" /> --> 
		<!-- <input id="saveAsCSV" type="submit"	value="Export CSV" onClick="saveCSV()" /> -->
	</div>

	<div class="row-fluid" >
			<div class="span12">
				<table id="tbl_summary" class="table table-bordered table-hover">
					<caption class="text-left" style="margin-bottom: 10px; font-size: 16px; line-height: 24px">
						<i class="icon-share-alt green"></i>
						<span>Vehicles:</span>
						<ul id="cp_vhc" class="inline muted">
						</ul>
					</caption>
					<tr>
						<th></th>
						<th>This Week</th>
						<th>This Month</th>
						<th>This Year</th>
					</tr>
					<tr>
						<td class="col_head"># of Trip</td>
						<td id="w_trip"></td>
						<td id="m_trip"></td>
						<td id="y_trip"></td>
					</tr>
					<tr>
						<td class="col_head">Duration (h:m:s)</td>
						<td id="w_dur"></td>
						<td id="m_dur"></td>
						<td id="y_dur"></td>
					</tr>
					<tr>
						<td class="col_head">Mileage</td>
						<td id="w_mile"></td>
						<td id="m_mile"></td>
						<td id="y_mile"></td>
					</tr>
					<tr>
						<td class="col_head">Speeding</td>
						<td id="w_speed"></td>
						<td id="m_speed"></td>
						<td id="y_speed"></td>
					</tr>
				</table>
			</div>
	</div>

		<!-- myCanvas is used for exporting png -->
	<canvas id="myCanvas"></canvas>
</div>
</body>
</html>