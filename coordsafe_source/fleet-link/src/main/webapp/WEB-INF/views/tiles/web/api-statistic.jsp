<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Cache-control" content="private">
	<link rel="stylesheet" href="/fleet-link/resources/css/api_key_charts.css" type="text/css" media="screen" charset="utf-8"/>
    <link rel="stylesheet" href="/fleet-link/resources/css/hor_table.css" type="text/css" media="screen" charset="utf-8"/>

    <script type="text/javascript" src="/fleet-link/resources/assets/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="/fleet-link/resources/scripts/chart/raphael.js"></script>
    <script type="text/javascript" src="/fleet-link/resources/scripts/chart/g.raphael-min.js"></script>
    <script type="text/javascript" src="/fleet-link/resources/scripts/chart/g.bar-min.js"></script>
    <script type="text/javascript" src="/fleet-link/resources/scripts/chart/g.line-min.js"></script>
    <script type="text/javascript" src="/fleet-link/resources/scripts/chart/g.pie-min.js"></script>

    <script type="text/javascript" src="/fleet-link/resources/scripts/api_key_charts.js"></script>
    
    <script>
	    $(document).ready(function(){
	    	//initiate();
	    	populateDurationOption("weekly",new Date().getWeek());
	    	retrieveUsageData();
	    });
    </script>

</head>
<body>
	<script type="text/javascript">
    
	</script>
	<div id="main-content" class="clearfix">
		<h2 class="contentHeader">
			<i class="icon-double-angle-right"></i> API Usage - <span
				class="muted" style="font-size: 14px">Key: <c:out
					value="${key.key}" /></span>
					<script type="text/javascript">var keyId = ${key.id}</script>
		</h2>
		
		<c:if test="${ empty(key)}">
			<div class="alert alert-error">
				<button data-dismiss="alert" class="close" type="button">
					<i class="icon-remove"></i>
				</button>

				<i class="icon-remove"></i> <span><c:out
						value="You do not have a key, only GeoReady users are allocated API keys."></c:out></span>
			</div>
		</c:if>

		<%-- <h6>Data: <c:out value="${usage.count}" /></h6> --%>
		<%-- <div class="span12" style="margin-bottom: 24px; margin-left: 0px">
			<div class="infobox infobox-green infobox-small infobox-dark">
				<div class="infobox-progress">
					<div data-size="39" data-percent="61"
						class="easy-pie-chart percentage easyPieChart"
						style="width: 39px; height: 39px; line-height: 39px;">
						<span class="percent">61</span>%
						<canvas height="39" width="39"></canvas>
					</div>
				</div>

				<div class="infobox-data">
					<div class="infobox-content">Quota</div>
					<div class="infobox-content">Used</div>
				</div>
			</div>
			<div class="infobox infobox-blue infobox-small infobox-dark">
				<div class="infobox-chart">
					<span data-values="3,4,2,3,4,4,2,2" class="sparkline"><canvas
							style="display: inline-block; width: 39px; height: 20px; vertical-align: top;"
							width="39" height="20"></canvas></span>
				</div>

				<div class="infobox-data">
					<div class="infobox-content">Usage</div>
					<div class="infobox-content">
						<c:out value="${key.count}" />
					</div>
				</div>
			</div>
		</div> --%>

		<div class="row-fluid">			
			<div id="options-header" class="span12">
				<div class="span6">
					<!-- <select id="key-option" onchange="selectKeyOption()" class="span2">
					</select> --> 
					<!-- <div class="span1"></div> -->
					<select id="type-option" onchange="selectType()" class="span5">
						<option value="weekly">Weekly Report</option>
						<option value="monthly">Monthly Report</option>
					</select>

					<!-- <div class="control-group span5">
						<label class="control-label">View</label>
						<div class="controls btn switch switch-three">
							<input id="week3" name="view" type="radio" checked> 
							<label for="week3" onclick="">Week</label> 
							<input id="month3" name="view" type="radio"> 
							<label for="month3" onclick="">Month</label>
							<input id="month4" name="view" type="radio"> 
							<label for="month4" onclick="">Month</label> 
							<span class="slide-button btn btn-success"></span>
						</div>
					</div> -->

					<!-- <select id="trip-option">
					</select> -->
				</div>
				<div id="duration" class="span6"></div>
			</div>
			<div id="counter" class="span12"></div>
			<div class="span12">
				<div id="resource" class="span5"></div>
				<div id="summary-table" class="span6"></div>
			</div>
		</div>
	</div>
</body>
</html>