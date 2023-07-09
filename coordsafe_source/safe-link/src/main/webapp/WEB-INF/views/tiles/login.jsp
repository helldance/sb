<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>Login - CoordSafe</title>

<meta name="description" content="User login page" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!--basic styles-->

<link href="resources/assets/css/bootstrap.min.css" rel="stylesheet" />
<link href="resources/assets/css/bootstrap-responsive.min.css"
	rel="stylesheet" />
<link rel="stylesheet" href="resources/assets/css/font-awesome.min.css" />

<!--[if IE 7]>
		  <link rel="stylesheet" href="resources/assets/css/font-awesome-ie7.min.css" />
		  <![endif]-->

<!--page specific plugin styles-->

<!--fonts-->

<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />

<!--ace styles-->

<link rel="stylesheet" href="resources/assets/css/ace.min.css" />
<link rel="stylesheet"
	href="resources/assets/css/ace-responsive.min.css" />

<!--[if lt IE 9]>
		  <link rel="stylesheet" href="resources/assets/css/ace-ie.min.css" />
		  <![endif]-->
<style>
.login-layout2 label {
	margin-bottom: 11px;
}

.login-layout2 .widget-box {
	visibility: hidden;
	position: absolute;
	overflow: hidden;
	width: 100%;
	border-bottom: none;
	box-shadow: none;
	padding: 6px;
	-moz-box-shadow: 0 0 3px #888;
	-webkit-box-shadow: 0 0 3px #888;
	box-shadow: 0 0 3px #888;
	filter: progid:DXImageTransform.Microsoft.gradient(  startColorstr='#f2f5f6',
		endColorstr='#c8d7dc', GradientType=0);
	-moz-transform: scale(0, 1) translate(-150px);
	-webkit-transform: scale(0, 1) translate(-150px);
	-o-transform: scale(0, 1) translate(-150px);
	-ms-transform: scale(0, 1) translate(-150px);
	transform: scale(0, 1) translate(-150px);
}

.login-layout2 .widget-box.visible {
	visibility: visible;
	-moz-transform: scale(1, 1) translate(0);
	-webkit-transform: scale(1, 1) translate(0);
	-o-transform: scale(1, 1) translate(0);
	-ms-transform: scale(1, 1) translate(0);
	transform: scale(1, 1) translate(0);
	-webkit-transition: all .3s ease;
	-moz-transition: all .3s ease;
	-o-transition: all .3s ease;
	transition: all .3s ease;
	-o-transition: none;
	/* too slow */
	-webkit-transition: none;
	/* works in chrome but not in safari, never scales back to 1! */
}

.login-layout2 .widget-box .widget-main {
	padding: 16px 36px 36px;
	background: #F7F7F7;
}

.login-layout2 .widget-box .widget-main form {
	margin: 0;
}

.login-layout2 .widget-box .widget-body .toolbar>div>a {
	font-size: 15px;
	font-weight: 400;
	text-shadow: 1px 0px 1px rgba(0, 0, 0, 0.25);
}
</style>
</head>

<body>
	<div class="container-fluid login-layout2" id="main-container">
		<!-- <div id="main-content"> -->
		<div class="row-fluid">
			<div class="span12" style="vertical-align: middle">
				<div class="login-container">
					<div class="row-fluid">
						<div class="center">

							<h1>
								<img src="resources/img/logo.png"><span> Coord</span>Safe<span>.</span>
							</h1>

						</div>
					</div>

					<div class="space-6"></div>

					<div class="row-fluid">
						<div class="position-relative">
							<div id="login-box" class="visible widget-box no-border">
								<div class="widget-body">
									<div class="widget-main" style="background-color: transparent">
										<h4 class="header blue lighter bigger">
											<i class="icon-signin"></i> Login to SafeLink
										</h4>

										<div class="space-6"></div>

										<form name="loginForm" action="j_spring_security_check"
											method="POST">
											<c:if test="${not empty param.login_error}">
												<h3 class="error light" style="font-size: 13px">
													Login Failed! (
													<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
													)
												</h3>
											</c:if>
											<fieldset>
												<label> <span
													class="block input-icon input-icon-right"> <input
														type="text" class="span12" placeholder="Username"
														name="j_username"
														value="<c:out value="${SPRING_SECURITY_LAST_USERNAME}" />" />

														<i class="icon-user"></i>
												</span>
												</label> <label> <span
													class="block input-icon input-icon-right"> <input
														type="password" class="span12" placeholder="Password"
														name="j_password" /> <i class="icon-lock"></i>
												</span>
												</label>

												<div class="space"></div>

												<div class="row-fluid">
													<label class="span8"> <input type="checkbox" /> <span
														class="lbl"> Remember Me</span>
													</label>

													<button type="submit" name="submit" value="Login"
														class="span4 btn btn-small btn-primary">
														<i class="icon-key"></i> Login
													</button>
												</div>
											</fieldset>
										</form>
									</div>
									<!--/widget-main-->

									<div class="toolbar clearfix">
										<div>
											<a href="#" onclick="show_box('forgot-box'); return false;"
												class="forgot-password-link"> <i class="icon-arrow-left"></i>
												I forgot my password
											</a>
										</div>


										<div>

											<a href="#" onclick="show_box('signup-box'); return false;"
												class="user-signup-link"> I want to register <i
												class="icon-arrow-right"></i>
											</a>
										</div>

									</div>
								</div>
								<!--/widget-body-->
							</div>
							<!--/login-box-->

							<div id="forgot-box" class="widget-box no-border">
								<div class="widget-body">
									<div class="widget-main" style="background-color: transparent">
										<h4 class="header red lighter bigger">
											<i class="icon-key"></i> Reset Password
										</h4>

										<div class="space-6"></div>
										<p>Enter your email and to receive instructions</p>

										<form method="POST" action="/safe-link/guardian/forgetpassword" >
											<fieldset>
												<label> <span
													class="block input-icon input-icon-right"> 
													<input type="email" name="email" class="span12" placeholder="Email" /> 
													<i class="icon-envelope"></i>
												</span>
												</label>

												<div class="row-fluid">
													<button  class="span5 offset7 btn btn-small btn-danger">
														<i class="icon-lightbulb"></i> Send Request
													</button>
												</div>
											</fieldset>
										</form>
									</div>
									<!--/widget-main-->

									<div class="toolbar center">
										<a href="#" onclick="show_box('login-box'); return false;"
											class="back-to-login-link"> Back to login <i
											class="icon-arrow-right"></i>
										</a>
									</div>
								</div>
								<!--/widget-body-->
							</div>
							<!--/forgot-box-->

							<div id="signup-box" class="widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header green lighter bigger">
											<i class="icon-group blue"></i> Guardian Registration
										</h4>

										<div class="space-6"></div>
										<p>Enter your details to begin:</p>

										<form method="POST" action="/safe-link/guardian/register_new">
											<fieldset>


												<label> <span
													class="block input-icon input-icon-right"> <input
														type="text" class="span12" placeholder="Username" name = "login" /> <i
														class="icon-user"></i>
												</span>
												</label> <label> <span
													class="block input-icon input-icon-right"> <input
														type="password" class="span12" placeholder="Password" name = "password"/>
														<i class="icon-lock"></i>
												</span>
												</label> <label> <span
													class="block input-icon input-icon-right"> <input
														type="password" class="span12"
														placeholder="Repeat password" name = "repassword"/> <i class="icon-retweet"></i>
												</span>
												</label> <label> <span
													class="block input-icon input-icon-right"> <input
														type="email" class="span12" placeholder="Email" name = "email"/> <i
														class="icon-envelope"></i>
												</span>
												</label> <label> <span
													class="block input-icon input-icon-right"> <input
														type="phone" class="span12"
														placeholder="Mobile Phone number" name = "phone" /> <i
														class="icon-phone"></i>
												</span>
												</label> <label> <input type="checkbox" /> <span
													class="lbl"> I accept the <a href="#">User
															Agreement</a>
												</span>
												</label>

												<div class="space-24"></div>

												<div class="row-fluid">
													<button type="reset" class="span6 btn btn-small">
														<i class="icon-refresh"></i> Reset
													</button>

													<button class="span6 btn btn-small btn-success">
														Register <i class="icon-arrow-right icon-on-right"></i>
													</button>
												</div>
											</fieldset>
										</form>
										
										
									</div>

									<div class="toolbar center">
										<a href="#" onclick="show_box('login-box'); return false;"
											class="back-to-login-link"> <i class="icon-arrow-left"></i>
											Back to login
										</a>
									</div>
								</div>
								<!--/widget-body-->
							</div>
							<!--/signup-box-->
						</div>
						<!--/position-relative-->
					</div>
				</div>
			</div>
			<!--/span-->
		</div>
		<!--/row-->
		<!-- </div> -->
	</div>
	<!--/.fluid-container-->

	<!--basic scripts-->

	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript">
		window.jQuery || document.write("<script src='resources/assets/js/jquery-1.9.1.min.js'>"+"<"+"/script>");
		</script>

	<!--page specific plugin scripts-->

	<!--inline scripts related to this page-->

	<script type="text/javascript">
		function show_box(id) {
			$('.widget-box.visible').removeClass('visible');
			$('#'+id).addClass('visible');
		}
		</script>
</body>
</html>
