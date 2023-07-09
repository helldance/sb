<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta charset="UTF-8"/>
    <link rel="stylesheet" href="/fleet-link/resources/css/driver_performance.css" type="text/css" media="screen" charset="utf-8"/>
    <link rel="stylesheet" href="/fleet-link/resources/css/table.css" type="text/css" media="screen" charset="utf-8"/>

    <script type="text/javascript" src="/fleet-link/resources/assets/js/jquery-1.9.1.min.js"></script>

    <script type="text/javascript" src="/fleet-link/resources/scripts/chart/raphael-min.js"></script>
    <script type="text/javascript" src="/fleet-link/resources/scripts/chart/g.raphael-min.js"></script>
    <script type="text/javascript" src="/fleet-link/resources/scripts/chart/g.bar-min.js"></script>
    <script type="text/javascript" src="/fleet-link/resources/scripts/chart/g.pie-min.js"></script>

    <script type="text/javascript" src="/fleet-link/resources/scripts/driver_performance.js"></script>
    <script type="text/javascript" src="/fleet-link/resources/scripts/trip-with-speeding_data.js"></script>
    
    <script>
    $(document).ready(function(){
    	initiateDriverPerformanceReport();
    });
    </script>
</head>

<body>
    <div id="speeding"></div>
    <div id="pause"></div>
    <div id="duration"></div>
    <div id="summary-table"></div>
    
    <form>                
        <select id="driver-option" onchange="selectDriverOption()">
        </select>
    </form>
    <form>
        <select id="type-option"  onchange="selectType()">
            <option value="weekly">Weekly Report</option>
            <option value="monthly">Monthly Report</option>
        </select>
    </form>
    <form>
        <select id="trip-option">
        </select>
    </form>
</body>
</html>