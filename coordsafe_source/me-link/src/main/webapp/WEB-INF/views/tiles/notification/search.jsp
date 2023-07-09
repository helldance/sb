<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<div id="main-content" class="clearfix">

	<h2 class="contentHeader">Notification Setting </h2>
	<form:form id="myForm" method="post" modelAttribute="nsForm" action="/fleet-link/notification/set">

	<table class="table table-hover">
		<thead>
			<th>ID</th>
			<th>EventType</th>
			<!-- <th>EventType</th> -->
			<th>SMS</th>
			<th>Email</th>
			<th>Emails</th>
			<th>SMSs</th>
		</thead>
		<c:if test="${not empty nsForm.notificationSettings}">
		<c:forEach items="${nsForm.notificationSettings}"	var="notificationSetting" varStatus="status">

		<tr>
			<td align="center">${status.count}</td>
<%-- 						<td><input	name="notificationSettings[${status.index}].eventType"	value="${notificationSetting.eventType}" /></td>
						<td><input name="notificationSettings[${status.index}].smsEnable"	value="${notificationSetting.smsEnable}" type="checkbox"/></td>
						<td><input name="notificationSettings[${status.index}].emailEnable"	value="${notificationSetting.emailEnable}" type="checkbox"/></td>
						<td><input name="notificationSettings[${status.index}].emailaddress" value="${notificationSetting.emailaddress}" /></td>
						<td><input name="notificationSettings[${status.index}].mobile"		value="${notificationSetting.mobile}" /></td> --%>
						<td><form:input path="notificationSettings[${status.index}].eventType" value="${notificationSetting.eventType}"  readonly="true" /></td>
						<%-- <td><form:label path="notificationSettings[${status.index}].eventType" value="${notificationSetting.eventType}" /></td> --%>
						<td><form:checkbox class="ace-switch ae-switch-4" path="notificationSettings[${status.index}].smsEnable" 	value="${notificationSetting.smsEnable}" /></td>
						<td><form:checkbox class="ace-checkbox" path="notificationSettings[${status.index}].emailEnable"	value="${notificationSetting.emailEnable}" /></td>
						<td><form:input path="notificationSettings[${status.index}].emailaddress"		value="${notificationSetting.emailaddress}" /></td>
						<td><form:input path="notificationSettings[${status.index}].mobile"	value="${notificationSetting.mobile}" /></td>								
						<td><form:hidden path="notificationSettings[${status.index}].company.id"	/></td>	
						<td><form:hidden path="notificationSettings[${status.index}].id"/></td>
						
						
						
					</tr>

				</c:forEach>
			</c:if>


			<c:if test="${empty nsForm.notificationSettings}">
			<c:forEach items="${nsForm.eventTypes}" var="notificationSetting"
			varStatus="status">
			<tr>
				<td align="center">${status.count}</td>
<%-- 						<td><input name="eventTypes[${status.index}].eventType" value="${notificationSetting.eventType}" /></td>
						<td><input name="eventTypes[${status.index}].smsEnable" 	value="${notificationSetting.smsEnable}"  type="checkbox"/></td>
						<td><input name="eventTypes[${status.index}].emailEnable"	value="${notificationSetting.emailEnable}" type="checkbox"/></td>
						<td><input name="eventTypes[${status.index}].emailaddress"		value="${notificationSetting.emailaddress}" /></td>
						<td><input name="eventTypes[${status.index}].mobile"	value="${notificationSetting.mobile}" /></td> --%>
						<td><form:input path="eventTypes[${status.index}].eventType" value="${notificationSetting.eventType}" readonly="true" /></td>
						<%-- <td><form:label path="notificationSettings[${status.index}].eventType" value="${notificationSetting.eventType}" /></td> --%>
						<td><form:checkbox  path="eventTypes[${status.index}].smsEnable" 	value="${notificationSetting.smsEnable}" /></td>
						<td><form:checkbox  path="eventTypes[${status.index}].emailEnable"	value="${notificationSetting.emailEnable}" /></td>
						<td><form:input path="eventTypes[${status.index}].emailaddress"		value="${notificationSetting.emailaddress}" /></td>
						<td><form:input path="eventTypes[${status.index}].mobile"	value="${notificationSetting.mobile}" /></td>	
						<td><form:hidden path="eventTypes[${status.index}].company.id"	/></td>		


					</tr>
				</c:forEach>
			</c:if>
		</table>
		
		<div class="hr hr8 hr-double hr-dotted"></div>
		
		<button class="btn btn-small btn-success pull-right" type="submit">
			Submit <i class="icon-arrow-right icon-on-right bigger-110"></i>
		</button>
		
		
		
	</form:form>
</div>

<script type="text/javascript">
$(document).ready(function() {
	$("#profile-submenu").css("display","block");

	$("#myForm").validate({
		rules : {
		},

		messages : {
		},

		submitHandler : function(form) {
						//$("#checkbox_agree").click();
						$.ajax({
							type : 'POST',
							url: $("#myForm").attr("action"),
							data : $("#myForm").serialize(),
							success: function() {
								alert("success");
								window.location.reload(false); // true if want to get from server
							}
						});
					}
				});

	$("#myForm").submit(function(e) {
		e.preventDefault();
	})
})
</script>