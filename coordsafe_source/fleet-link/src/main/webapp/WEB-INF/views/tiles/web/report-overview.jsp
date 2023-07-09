<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- <meta http-equiv="Cache-control" content="private"> -->
<script type="text/javascript" src="/fleet-link/resources/scripts/filesaver.js"></script>
<script type="text/javascript" src="/fleet-link/resources/scripts/canvas-toBlob.js"></script>
<link rel="stylesheet" href="/fleet-link/resources/css/chart/barchart.css" type="text/css" media="screen" charset="utf-8" />
<style type="text/css">
	#options-header {
		position: relative;
		top: 10px;
		width: 100%;
		z-index: 1;
		vertical-align:middle;
		margin-bottom: 36px;
		/* background: #AAA; */
	}

	/* #trip-option {
		display: none;
	} */

	#chart-option {
		display: none;
	}

	#saveAsPNG {
		display: none;
		float: right;
	}

	#saveAsCSV {
		display: none;
		float: right;
	}
	
	/* .a {
		display:none;
	} */
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
    var grp2;
    var wm;

	$(document).ready(function() {
		var op = window.location.hash;
		
		console.log(op);
		
		if (op){
			//saveImage(op);
		}
		
		$("#report-submenu").css("display","block");
		retrieveGroup();
		selectType();
		//retrieveTrip();
		
		$('#exp_opt').on('click', 'li a', function(e) {
		    //alert($(this).text());
		    var opt = $(this).text();
		    if (opt === "Duration")
		    	opt = "Time";
		    else if (opt === "Trip")
	    		opt = "Frequency";
		    
		    saveImage(opt);
		});
	});

	function retrieveGroup(){
		var req = "/fleet-link/vgroup/company/" + <sec:authentication property="principal.company.id"/> ;
		$.getJSON(req, function(data) {
			group_data = data;
			
			populateGroupDrop();
		});
	}
	
	function populateGroupDrop() {
		var keys = [];
		var values = [];
		
		$("#group-option").empty();
		$("#group-option").append('<option value="-1">----Select Group----</option>');
		
		for (i=0; i<group_data.length; i++) {
		    for (key in group_data[i]) {	      
			  $("#group-option").append('<option value="'+key+'">' + group_data[i][key] + '</option>');
		    }
		}
	}
	
	function retrieveTrip() {
		$.getJSON(createQuery(), function(data) {
			trip_data = data;
			populateVehicleDrop(trip_data);
		});
	}

	function populateVehicleDrop(trip_data) {
		var v_name = [];

		for ( var i = 0; i < trip_data.length; i++) {
			for ( var k in trip_data[i])
				key = k;
			v_name.push(key);
		}
		
		$("#vehicle-option").empty();

		$("#vehicle-option").append('<option value="-1">----Select Vehicle----</option>');
		for ( var i = 0; i < trip_data.length; i++) {
			$("#vehicle-option").append(
					'<option value="'+i+'">' + v_name[i] + '</option>');
		}
		$("#vehicle-option").append('<option value="all">All vehicles</option>');
		currentWeek = (new Date()).getWeek();
		/* $("#trip-option").append(
				'<option value="'+currentWeek+'" selected></option>'); */
		wm = currentWeek;
		populateDurationOption("weekly", currentWeek);
	}

	function selectGroupOption() {
		var groupOption = $("#group-option option:selected").val();
		grp2 = groupOption;
		// requery group trip
		retrieveTrip();
	}

	function selectVehicleOption() {
		var vehicleOption = $("#vehicle-option option:selected").val();
		var type = $("#type-option option:selected").val();
		//var value = $("#trip-option option:selected").val();
		//value = parseInt(value);
		var value = parseInt(wm);

		$("#Mileage").empty();
		$("#Time").empty();
		$("#Frequency").empty();

		if (vehicleOption == "-1") {
			return;
		} else if (vehicleOption == "all") {
			if (typeof value == "undefined") {
				value = (new Date()).getWeek();
			}
			//get group data of all vehicles for a particular week or month
			getAllVehiclesData(value);
		} else {
			if (typeof value == "undefined") {
				value = (new Date()).getWeek();
			}
			//get group data of a single vehicle for a particular week or month
			getSingleVehicleData(value);
		}
	}

	function selectType() {
		type = $("#type-option option:selected").val();

		if (type == "weekly") {
			populateDurationOption("weekly", (new Date()).getWeek());
		} else if (type == "monthly") {
			populateDurationOption("monthly", (new Date()).getMonth());
		}

		selectVehicleOption();
	}

	function savePNG() {
		chartOption = $("#chart-option option:selected").val();
		saveImage(chartOption);
	}

	function saveCSV() {
		vehicleOption = $("#vehicle-option option:selected").val();

		if (vehicleOption != "all" && vehicleOption != "-1") {
			vehicleOption = parseInt(vehicleOption);
			$.getJSON(createQuery(), function(data) {
				trip_data = data;
				var key;
				for ( var k in trip_data[vehicleOption])
					key = k;
				var vehicle_data = trip_data[vehicleOption][key];
				vehicle_data.sort(function(x, y) {
					return x.tripStartTime - y.tripStartTime;
				});
				var csv = JSON2CSV(vehicle_data);
				//window.open("data:text/csv;charset=utf-8," + escape(csv));
				var linktofile = "data:text/csv;charset=uft-8," + escape(csv);
				//$('<a href="' + linktofile + '"download=\"export.csv\" >')[0].click();
				//<a href="data:text/plain,Test" download="test.txt">
				$("a#_hidden").attr("href", linktofile);
				$("a#_hidden").attr("download", "export.csv");
				$('a#_hidden')[0].click();
			});
		}
	}
</script>
<div id="main-content" class="clearfix">
	<h2 class="contentHeader"><i class="icon-double-angle-right"></i>
	    Report Overview	
	</h2>
	<div id="options-header">
		<select id="group-option" onchange="selectGroupOption()"></select>
		<select id="vehicle-option" onchange="selectVehicleOption()">
		</select> <select id="chart-option">
			<option value="Mileage">Mileage</option>
			<option value="Time">Time</option>
			<option value="Frequency">Frequency</option>
		</select> 

		<select id="type-option" onchange="selectType()">
			<option value="weekly">Weekly Report</option>
			<option value="monthly">Monthly Report</option>
		</select>
		
		<!-- <button id="saveAsPNG" class="btn btn-small btn-purple" onClick="savePNG()">
			<i class="icon-picture"></i> Export PNG
		</button>&nbsp;&nbsp; -->
			<div id="saveAsPNG" class="btn-group">
				<button type="button" class="btn btn-small btn-purple dropdown-toggle"
					data-toggle="dropdown">
					<i class="icon-picture"></i>Export PNG<span class="caret"></span>
				</button>
				<ul id="exp_opt" class="dropdown-menu">
					<li><a href="#Mileage">Mileage</a></li>
					<li><a href="#Time">Duration</a></li>
					<li><a href="#Frequency">Trip</a></li>
				</ul>
			</div>
			<button id="saveAsCSV" class="btn btn-small btn-purple" onClick="saveCSV()">
			<i class="icon-cloud-download"></i> Export CSV
		</button>
		<!-- <input id="saveAsPNG" type="submit" value="Export PNG"	onClick="savePNG()" /> --> 
		<!-- <input id="saveAsCSV" type="submit"	value="Export CSV" onClick="saveCSV()" /> -->
	</div>
	<div id="duration" class="well"></div>
	<div id="Mileage"></div>
	<div id="Time"></div>
	<div id="Frequency"></div>
	<!-- myCanvas is used for exporting png -->
	<canvas id="myCanvas"></canvas>
	<a id="_hidden" hidden>
</div>
</body>
</html>