
<!DOCTYPE html>
<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>

<html>
<head>

<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,300|Rambla|Calligraffitti'
	rel='stylesheet' type='text/css'>
<script src="/fleet-link/resources/scripts/modernizr.js"></script>
<script src="/fleet-link/resources/scripts/mqttws31.js"></script>
<script type="text/javascript"
  	src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
	
</head>

<body class="navbar-fixed">
	<script type="text/javascript">
		// mqtt block, 16/12/2013
		$(document).ready(
				/* function() {
					var client = new Messaging.Client("192.168.1.131", 61614,
							"948jren-lsnig404984");

					//client.onConnect = onConnect;        

					client.connect({
						onSuccess : function() {
							client.subscribe("/topic/vehicle-event");

							console.log("connected and subscribe to MQTT");
						},
						onFailure : onFailure
					});
					
					client.onMessageArrived = onMessageArrived;
					client.onConnectionLost = onConnectionLost;

					function onFailure(failure) {
					}

					function onMessageArrived(message) {
						console.log("+: " + message.payloadString);
					}

					function onConnectionLost(responseObject) {
						if (responseObject.errorCode !== 0) {
							console.log(client.clientId + ": "
									+ responseObject.errorCode + "\n");
						}
					} 
				}*/
				);
	</script>
	<div class="header navbar-fixed-top" style="background: #f3f3f3">
		<!-- <div class="header-inner container"> -->
		<div class="container-fluid">
			<div class="span8">
				<a class="brand" title="Home" style="text-decoration: none">
					<h1>
						<c:choose>
							<c:when test="${pageContext.request.userPrincipal.name eq 'shamir' || pageContext.request.userPrincipal.name eq 'shroad' || pageContext.request.userPrincipal.name eq 'hope' || pageContext.request.userPrincipal.name eq 'skyir' || pageContext.request.userPrincipal.name eq 'alkadri'}">
								<img
									src="${pageContext.request.contextPath}/resources/images/versa.png">
							</c:when>
							<c:otherwise>
								<img
									src="${pageContext.request.contextPath}/resources/img/logo.png">
								<span> Coord</span>Safe<span>.</span><span style="font-size: 24px">
									FleetLink System</span>
							</c:otherwise>
						</c:choose>

					</h1>
				</a>
			</div>


			<div class="span4">
				<div class="navbar navbar-inverse" style="margin-top: 10px">
					<div class="navbar-inner">
						<div class="container-fluid">
							<ul class="nav ace-nav pull-right">


								<li><a href="/fleet-link/"> <i
										class="icon-home  icon-only"></i> Map
								</a></li>

								<%-- <li>
										<a href="/fleet-link/"> <i class="icon-home icon-only"></i> Map
										</a>
										<li><a data-toggle="dropdown" href="#"
											class="user-menu dropdown-toggle"> <i class="icon-caret-down"></i>
										</a>
										
										<ul class="dropdown-menu dropdown-blue dropdown-caret dropdown-closer">
											<li><a href="<s:url value="/onemap" />">

												<i class="icon-cog"></i> One Map

										</a></li>
										</ul>
										<a href="/fleet-link/onemap"> <i
										class="icon-globe icon-only"></i> OneMap
								</a>
										
								</li> --%>


								<li><a
									href="<s:url value="/vehicle/search"/>?companyid=<sec:authentication property="principal.company.id"/>">
										<i class="icon-dashboard  icon-only"></i> Dashboard
								</a></li>

								<li class="user-profile"><a data-toggle="dropdown" href="#"
									class="user-menu dropdown-toggle"> <span id="user_info">
											Welcome!<br /><sec:authentication property="principal.username"/>
									</span> <i class="icon-caret-down"></i>
								</a>

									<ul
										class="pull-right dropdown-menu dropdown-blue dropdown-caret dropdown-closer"
										id="user_menu">
										<authorize:authorize resourceName="MNU-ADMIN-ROLE-001">
										<li><a
											href="<s:url value="/notification/set" />?companyid=<sec:authentication property="principal.company.id"/>">

												<i class="icon-cog"></i> Settings

										</a></li>
										</authorize:authorize>

										<li><a
											href="/fleet-link/user/profile/<sec:authentication property="principal.username"/>">
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
</body>

</html>

