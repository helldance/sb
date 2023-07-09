<%@ include file="/WEB-INF/views/tiles/includes.jsp" %>
<div class="clearfix">

	<h2 class="contentHeader">'${vGroup.groupName}'  Assignment</h2>

	<div class="form">
		<form:form name="assignVehicles" method="POST" modelAttribute="vGroup">
<%-- 			<display:table name="${roleList.roles}" defaultsort="2" class="displaytag"
				defaultorder="ascending" pagesize="15" requestURI="" excludedParams="_chk"
				decorator="userAssignRolesCheckboxDecorator">
				<display:column property="checkbox" title="" style="width: 1px" />
				<display:column property="name" title="Role Name" sortable="true" />
				<display:column property="description" title="Description" />
			</display:table> --%>
			
			<table>
				<tr>
					<td>All Available Vehicles</td><td>Assigned Vehicles</td>
				</tr>
				<tr>
					<td><form:select name="available" path="" items="${vehicles}" itemValue="id" itemLabel="name" multiple="multiple"></form:select></td>
					<td>
						<a href="">Assiging---></a>
						<hr>
						<a href=""><---UnAssigning</a>
					</td>
					<td><form:select name="assigned" path="" items="${vGroup.vehicles}" itemValue="id" itemLabel="name" multiple="multiple"></form:select></td>
				</tr>
			</table>
			
			<div class="actionButtons">
				<input type="submit" value="Submit" /> 
				<input type="button" value="Back" onclick="javascript:history.back()">
			</div>
		</form:form>
	</div>
</div>