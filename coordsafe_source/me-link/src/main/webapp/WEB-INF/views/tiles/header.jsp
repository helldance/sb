
<!DOCTYPE html>
<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>


<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,300|Rambla|Calligraffitti'
	rel='stylesheet' type='text/css'>
<script src="/me-link/resources/scripts/modernizr.js"></script>

</head>

<body class="navbar-fixed">

	<div class="header navbar-fixed-top" style="background: #f3f3f3">
		<!-- <div class="header-inner container"> -->
		<div class="row-fluid">
			<div class="span6">
				<a class="brand" title="Home" style="text-decoration: none">
					<h1>
						<img
							src="${pageContext.request.contextPath}/resources/img/logo.png">
						<span> Coord</span>Safe<span>.</span><span style="font-size: 24px">
							SafeLink System</span>
					</h1>
				</a>
			</div>


			<div class="span6">
				<div class="navbar navbar-inverse" style="margin-top: 10px">
					<div class="navbar-inner">
						<div class="container-fluid">
							<ul class="nav ace-nav pull-right">
								<sec:authorize access="hasRole('ROLE_ADMIN')">
									<li><a href="/me-link/guardian/search" id="guardians_link"
										onclick="loadPrimaryModal(this); return false;"> <i
											class="icon-eye-open  icon-only"></i> Guardians
									</a></li>
									
									<li><a href="/me-link/locator/search" id="locators_link"
										onclick="loadPrimaryModal(this); return false;"> <i
											class="icon-globe  icon-only"></i> Locators
									</a></li>

								</sec:authorize>

								<sec:authorize access="hasAnyRole('ROLE_SERVICE','ROLE_ADMIN')">
									<li><a
										href="/me-link/retailer/search?retailer=<sec:authentication property="principal.username"/>"
										id="sims_link" onclick="loadPrimaryModal(this); return false;">
											<i class="icon-mobile-phone  icon-only"></i> SIMs
									</a></li>
								</sec:authorize>

								<sec:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
									<li><a
										href="/me-link/wards/search?guardian=<sec:authentication property="principal.username"/>"
										id="wards_link" onclick="loadPrimaryModal(this); return false;">
											<i class="icon-smile  icon-only"></i> Wards
									</a></li>

									<li class="user-profile"><a data-toggle="dropdown"
										href="#" class="user-menu dropdown-toggle"> <span>
												Setting </span> <i class="icon-caret-down"></i>
									</a>

										<ul
											class="pull-right dropdown-menu dropdown-blue dropdown-caret dropdown-closer"
											id="user_menu">



											<li><a
												href="<s:url value="/geofence/search"/>?createby=<sec:authentication property="principal.username"/>&type=fence"
												onclick="loadPrimaryModal(this); return false;"
												id="geofence_link"> <i class="icon-globe icon-only"></i> Geofence
											</a></li>
										</ul></li>
								</sec:authorize>



								<li class="user-profile"><a data-toggle="dropdown" href="#"
									class="user-menu dropdown-toggle"> <span id="user_info">
											Welcome!<br />
										<sec:authentication property="principal.username" />
									</span> <i class="icon-caret-down"></i>
								</a>

									<ul
										class="pull-right dropdown-menu dropdown-blue dropdown-caret dropdown-closer"
										id="user_menu">


										<li><a
											href="<s:url value="/guardian/profile/"/><sec:authentication property="principal.username"/>" onclick="loadPrimaryModal(this); return false;" id="profile_link">
												<i class="icon-user"></i> Profile
										</a></li>
										<li class="divider"></li>
										<li><a href="<s:url value="/" />j_spring_security_logout">
												<i class="icon-off"></i> Logout
										</a></li>
									</ul></li>
							</ul>
						</div>
					</div>
				</div>

			</div>
		</div>
		<!--  </div> -->
	</div>



