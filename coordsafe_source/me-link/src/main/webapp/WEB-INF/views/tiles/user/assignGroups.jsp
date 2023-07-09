<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>

<div class="clearfix">
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<h2 class="contentHeader">'${user.username}' Groups Assignment</h2>
		</div>


		<div class="widget-body">
		<%-- 		<form:form name="assignRoles" method="POST" modelAttribute="user">
			<display:table name="${groups}" defaultsort="2" class="displaytag"
				defaultorder="ascending" pagesize="15" requestURI="" excludedParams="_chk"
				decorator="userAssignGroupsCheckboxDecorator">
				<display:column property="checkbox" title="" style="width: 1px" />
				<display:column property="name" title="Group Name" sortable="true" />
				<display:column property="description" title="Description" />
			</display:table>
			<div class="actionButtons">
				<input type="submit" value="Submit" /> 
				<input type="button" value="Back" onclick="javascript:history.back()">
			</div>
		</form:form> --%>
		<form:form name="assignGroup" method="POST" modelAttribute="user" class="form-horizontal"  style="margin-bottom: 0px" id="myForm">



		<div class="widget-main">
			<fieldset>

				<table id="table_report" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>Select Group</th>
							<th>Group Name</th>
							<th>Description</th>

						</tr>
					</thead>
					<c:forEach var="group" items="${groups}" varStatus="status">

					<tr>
						<td><form:input name = "_checked" path="" type="checkbox"/></td>
						<td><c:out value="${group.name}"></c:out></td>
						<td><c:out value="${group.description}"></c:out></td>


					</tr>

				</c:forEach>
			</table>
		</fieldset>
	</div>


	<div class="widget-toolbox  padding-8 clearfix">
		<div class="pull-right">
			<button class="btn  btn-success no-border" type="submit">
				<i class="icon-save bigger-125"></i> &nbsp;Save
			</button>
		</div>
		<div class="pull-left">
			<input id="cancel" class= "btn btn-primary  no-border" type="button" value="Back"/>
			<input type="reset" class= "btn  btn-primary no-border" value="Reset Form" />
		</div>
	</div>
</form:form>
</div>
</div>
</div>

<script type="text/javascript">
$(function() {
	$("#cancel").click(function() {
		$("#edit-modal").modal("hide");
	})
})
</script>