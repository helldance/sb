<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<div id="main-content" class="clearfix">
	<h2 class="contentHeader">Notification Running Status</h2>

	<form:form modelAttribute="vEvent">
		<table class="table">
			<thead>
				<th>EventType</th>
				<th>Company</th>
				<th>Status</th>

			</thead>
			<tr>
			
			<td>${vEvent.type}</td>
			<td>${vEvent.company.name }</td>
			<td>${vEvent.notifSent}</td>
<%-- 			
				<td><form:input path="${vEvent.type }"          value="${vEvent.type }" /></td>
				<td><form:input path="${vEvent.company.name }"	value="${vEvent.company.name }" /></td>
				<td><form:checkbox path="${vEvent.NotifSent}"		value="${vEvent.NotifSent}" /></td>
 --%>
			</tr>
		</table>
	</form:form>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$("#profile-submenu").css("display","block");
	})
</script>