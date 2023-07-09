<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<div class="clearfix">

	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<h2 class="contentHeader">Notification Setting for ${ward.name}</h2>
		</div>

		<div class="widget-body">
			<form:form id="myForm" method="post" modelAttribute="nsForm" action="/safe-link/notification/wardNotification">
			<input type='hidden' id='ward' name='ward' value='${ward}'/>


			<div class="widget-main">
				<fieldset>

					<table class="table table-hover">
						<thead>
							<th>ID</th>
							<th>EventType</th>
							<th>SMS</th>
							<th>Email</th>
							<th>Emails</th>
							<th>SMSs</th>
						</thead>
						<c:if test="${not empty nsForm.wardNotificationSettings}">
						<c:forEach items="${nsForm.wardNotificationSettings}"	var="notificationSetting" varStatus="status">

						<tr>
							<td align="center">${status.count}</td>

							<td><form:input path="wardNotificationSettings[${status.index}].eventType" value="${notificationSetting.eventType}"  readonly="true" /></td>
							<td><form:checkbox class="ace-switch ae-switch-4" path="wardNotificationSettings[${status.index}].smsEnable" 	value="${notificationSetting.smsEnable}" /></td>
							<td><form:checkbox class="ace-checkbox" path="wardNotificationSettings[${status.index}].emailEnable"	value="${notificationSetting.emailEnable}" /></td>
							<td><form:input path="wardNotificationSettings[${status.index}].emailaddress" value="${notificationSetting.emailaddress}" class="emails" /></td>
							<td><form:input path="wardNotificationSettings[${status.index}].mobile"	value="${notificationSetting.mobile}" class="mobile" /></td>								
							<td><form:hidden path="wardNotificationSettings[${status.index}].ward.id"	/></td>	

							<td><form:hidden path="wardNotificationSettings[${status.index}].id"/></td>



						</tr>

					</c:forEach>
				</c:if>


				<c:if test="${empty nsForm.notificationSettings}">
				<c:forEach items="${nsForm.wardEventTypes}" var="notificationSetting"	varStatus="status">		   
				<tr>
					<td align="center">${status.count}</td>

					<td><form:input path="wardEventTypes[${status.index}].eventType" value="${notificationSetting.eventType}" readonly="true" /></td>
					<td><form:checkbox  path="wardEventTypes[${status.index}].smsEnable" 	value="${notificationSetting.smsEnable}" /></td>
					<td><form:checkbox  path="wardEventTypes[${status.index}].emailEnable"	value="${notificationSetting.emailEnable}" /></td>
					<td><form:input path="wardEventTypes[${status.index}].emailaddress"	class="emails"	value="${notificationSetting.emailaddress}" /></td>
					<td><form:input path="wardEventTypes[${status.index}].mobile"	value="${notificationSetting.mobile}" class="mobile" /></td>	
					<td><form:hidden path="wardEventTypes[${status.index}].ward.id"	/></td>	
<%-- 						<td><form:hidden path="wardEventTypes[${status.index}].id"/></td>
--%>					</tr>
</c:forEach>
</c:if>
</table>
</fieldset>

</div>  <!--End of widget main -->

	<div class="widget-toolbox  padding-8 clearfix">
		<div class="pull-left">
			<input class= "btn  btn-cancel no-border" type="button" id="edit-cancel" onclick="cancelEditModal(this);" value="Back" />	
		</div>
		<div class="pull-right">	
			<button class="btn  btn-success btn-small no-border" type="submit">
				<i class="icon-save bigger-125"></i> &nbsp;Save
			</button>
		</div>
	</div>

 <!-- End of Widget toolbox -->
</form:form>

</div>  <!-- End of widget body -->

</div>  <!-- End of widget box -->
</div>





<script type="text/javascript">

		$.validator.addMethod("email_multiple", function(value, element) {
			/*
		return this.optional(element) || /^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,3})$/.test(value);
		*/

		}); 




		$("#myForm").submit(function(e) {
			e.preventDefault();
		})


		$("#myForm").validate({
			submitHandler: function(form) {
				$.ajax({
					type : 'POST',
					url: $("#myForm").attr("action"),
					data : $("#myForm").serialize(),
					success: function() {
							humane.log("Notification is set successfully");
							$("#edit-modal").modal("hide");
							loadPrimaryModal("#wards_link");
					}
		     	});
			}

		});


    $('.emails').each(function() {
        $(this).rules('add', {
            email: true,
            messages: {
                //email:  "Emails shall be separated by comma"
            }
        });
    });

    $('.mobile').each(function() {
        $(this).rules('add', {
            
            number: true,
            messages: {
                number:  "Please enter a valid number"
            }
        });
    });





</script>