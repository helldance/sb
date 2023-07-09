<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<div class="container-fluid" id="main-container">
	<a id="menu-toggler" href="#"> <span></span>
	</a>

	<div id="sidebar">

		<ul class="nav nav-list">

			<li><a class="dropdown-toggle" href="#"> <i
				class="icon-truck"></i> <span>Vehicle Management</span> <b
				class="arrow icon-angle-down"></b>
			</a>

			<ul class="submenu" id="vehicle-submenu">
				<li><a id="db-vehicle"
					href="<s:url value="/vehicle/search"/>?companyid=<sec:authentication property="principal.company.id"/>"
					class="pageFetcher"> <i class="icon-double-angle-right"></i>
					Vehicle Overview
				</a></li>

				<li><a id="db-vgroup"
					href="<s:url value="/vgroup/search"/>?companyid=<sec:authentication property="principal.company.id"/>"
					class="pageFetcher"> <i class="icon-double-angle-right"></i>
					Group Management
				</a></li>
			</ul></li>
			
			<authorize:authorize resourceName="MNU-ADMIN-ROLE-001">
			<li><a class="dropdown-toggle" href="#"> <i
				class="icon-road"></i> <span>Zone &amp; Route</span> <b
				class="arrow icon-angle-down"></b>
			</a>

			<ul class="submenu" id="zone-submenu">
				<li><a id="db-zone"
					href="<s:url value="/geofence/search"/>?companyid=<sec:authentication property="principal.company.id"/>&type=fence"
					class="pageFetcher"> <i class="icon-double-angle-right"></i>
					Geozone Management
				</a></li>

				<li><a id="db-route"
					href="<s:url value="/geofence/search"/>?companyid=<sec:authentication property="principal.company.id"/>&type=route"
					class="pageFetcher"> <i class="icon-double-angle-right"></i>
					Route Planning
				</a></li>
			</ul></li>
			</authorize:authorize>

			<li><a class="dropdown-toggle" href="#"> <i
				class="icon-bar-chart"></i> <span>Statistic &amp; Report</span> <b
				class="arrow icon-angle-down"></b>
			</a>
			
			<ul class="submenu" id="report-submenu">
				<li><a href="<s:url value="/web/report-overview" />"
					class="pageFetcher"> <i class="icon-double-angle-right"></i>
					Report Overview
				</a></li>

				<li><a
					href="<s:url value="/trip/company/"/><sec:authentication property="principal.company.id"/>"
					class="pageFetcher"> <i class="icon-double-angle-right"></i>
					Trip History
				</a></li>
<!--
				<li><a
					href="<s:url value="/event/company/"/><sec:authentication property="principal.company.id"/>/page?timeRange=18-05-2013,18-06-2013"
					class="pageFetcher"> <i class="icon-double-angle-right"></i>
					Event Log
				</a></li>
-->
				<li><a id="eventlog-a"
					href="<s:url value="/event/company/"/><sec:authentication property="principal.company.id"/>/page?timeRange="
					class="pageFetcher"> <i class="icon-double-angle-right"></i>
					Event Log
				</a></li>
				
				<li><a href="<s:url value="/web/vehicle-summary"/>" class="pageFetcher"> <i
					class="icon-double-angle-right"></i> Vehicle Summary
				</a></li>

			</ul></li>
			
			<authorize:authorize resourceName="MNU-ADMIN-ROLE-001">
			<li><a class="dropdown-toggle" href="#"> <i
				class="icon-road"></i> <span>Work Order</span> <b
				class="arrow icon-angle-down"></b>
			</a>

			<ul class="submenu" id="order-submenu">
				<li><a id="wo-list"
					href="<s:url value="/workorder/search"/>?companyid=<sec:authentication property="principal.company.id"/>"
					class="pageFetcher"> <i class="icon-double-angle-right"></i>
					Order List
				</a></li>
				
				<li><a id="task-list"
					href="<s:url value="/orderlist/search"/>?companyid=<sec:authentication property="principal.company.id"/>"
					class="pageFetcher"> <i class="icon-double-angle-right"></i>
					Task List
				</a></li>
			</ul></li>
			</authorize:authorize>


			<li><a class="dropdown-toggle" href="#"> <i
				class="icon-user"></i> <span>Setting &amp; Profile</span> <b
				class="arrow icon-angle-down"></b>
			</a>

			<ul class="submenu" id="profile-submenu">
				<li><a href="<s:url value="/user/profile/" /><sec:authentication property="principal.username"/>"
					class="pageFetcher"> <i class="icon-double-angle-right"></i>
					Update Profile
				</a></li>
				<authorize:authorize resourceName="MNU-ADMIN-ROLE-001">
						<li><a class="pageFetcher" href="#"  style=" pointer-events: none; cursor: default">
							<i class="icon-double-angle-right"></i> Preference
						</a></li>


				<li><a
					href="<s:url value="/notification/set" />?companyid=<sec:authentication property="principal.company.id"/>"
					class="pageFetcher"> <i class="icon-double-angle-right"></i>
					Notification Setting
				</a></li>
				
				</authorize:authorize>

				<%-- <authorize:authorize resourceName="MNU-GR-USR"> --%>
					<li><a href="<s:url value="/web/api-statistic" />"
						class="pageFetcher">API Usage</a></li>
					<%-- </authorize:authorize> --%>
				</ul>


				<li id="admin-li"><authorize:authorize 	resourceName="MNU-ADMIN-ROLE-001">
					<a class="dropdown-toggle" href="#"> <i class="icon-edit icon-3x"></i>
						<span>Admin</span> <b class="arrow icon-angle-down"></b>
					</a>

					<ul class="submenu" id="admin-submenu">
						<li><a class="pageFetcher"
							href="/fleet-link/codetable/search"> <i
							class="icon-double-angle-right"></i> Codetable Management
						</a></li>

						<li><a class="pageFetcher" href="/fleet-link/user/search">
							<i class="icon-double-angle-right"></i> User Management
						</a></li>
						
						<li><a class="pageFetcher" href="#" style=" pointer-events: none; cursor: default">
							<i class="icon-double-angle-right"></i> User Group Management
						</a></li>

						<li><a class="pageFetcher" href="/fleet-link/resource/search">
							<i class="icon-double-angle-right"></i> Resource Management
						</a></li>

						<li><a class="pageFetcher" href="/fleet-link/role/search">
							<i class="icon-double-angle-right"></i> Role Management
						</a></li>

						<li><a class="pageFetcher" href="/fleet-link/company/search">
							<i class="icon-double-angle-right"></i> Company Management
						</a></li>

						<li><a class="pageFetcher" href="/fleet-link/locator/search">
							<i class="icon-double-angle-right"></i> Locator Management
						</a></li>



					</ul>
				</authorize:authorize></li>
			</ul>
			<!--/.nav-list-->

			<div id="sidebar-collapse">
				<i class="icon-double-angle-left blue icon-2x"></i>
			</div>

			<div id="left-bottom" class="muted" style="margin: 10px; position:fixed; left:0px; bottom:0px;">
			<hr>
				&copy; 2014 CoordSafe Pte Ltd. &nbsp;<a
				href="http://www.coordsafe.com.sg" target="_blank"> <i class="icon-globe"></i></a>
				 |<a
				href="#" target="_blank"> Terms</a> | <a
				href="mailto:info@coordsafe.com.sg" target="_blank"> Contact Us</a>
				&nbsp;
			</div>
		</div>
	</div>

	<script type="text/javascript">

	function add0(p) {
		if (p<10) return ('0'+p);
			else return p;
	}

	$(document).ready(function() {
		$("a.pageFetcher").click(function() {
			$("#loading").show();
		});
		var currentdate = new Date();
		var date = currentdate.getDate();
		var month = currentdate.getMonth();
		var year =  currentdate.getFullYear();
        
        var date30 = new Date(year, month, date-1);
        var date2 = date30.getDate();
        var month2 = date30.getMonth();
		var year2 =  date30.getFullYear();

		var href = $("#eventlog-a").attr("href");
		href = href + add0(date2) +'-'+ add0(month2+1) +'-'+ add0(year2) +','+ add0(date) +'-' +add0(month+1) +'-'+ add0(year);
		$("#eventlog-a").attr("href", href);
	});

	</script>