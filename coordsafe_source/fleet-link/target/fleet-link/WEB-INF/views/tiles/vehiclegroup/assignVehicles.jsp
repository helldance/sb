<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<div class="clearfix">
	<div class="widget-box">
		<div class="widget-header  header-color-blue">
			<h2 class="contentHeader">'${vGroup.groupName}' Assignment</h2>
		</div>
		<div class="widget-body">
			<form:form name="assignVehicles" method="POST" action="/fleet-link/vgroup/assign" modelAttribute="vGroup" id="myForm" 
				class="form-horizontal" style="margin-bottom: 0px">


				<div class="widget-main">
					<table>
						<tr>
							<td>Available Vehicles</td>
							<td></td>
							<td>Assigned Vehicles</td>
						</tr>
						<tr>
							<td><form:select id="available" name="available" path=""
									items="${vehicles}" itemValue="id" itemLabel="name"
									multiple="multiple"></form:select></td>
							<!-- <td><a href="">Assiging></a>
								<hr> <a href=""><UnAssigning</a></td> -->
								
								<td><input type="button" id="btnLeft" value="&lt;&lt;" /><br><br>
								<input type="button" id="btnRight" value="&gt;&gt;" /></td>
								
							<td><form:select id="assigned" name="assigned" path=""
									items="${vGroup.vehicles}" itemValue="id" itemLabel="name"
									multiple="multiple"></form:select></td>
						</tr>
					</table>
				</div>
				<div class="widget-toolbox  padding-8 clearfix">
					<div class="pull-right">
							<button class="btn  btn-success no-border" type="submit" onclick="submitMyForm();" >
								<i class="icon-save bigger-125"></i> &nbsp;Save
							</button>
						</div>
						<div class="pull-left">
							<input id="cancel" class="btn  btn-primary no-border"
								type="button" value="Cancel" /> <!-- <input
								class="btn  btn-primary no-border" type="reset"
								value="Reset Form" /> -->
						</div>
				</div>
			</form:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$("#btnLeft").click(function() {
			var selectedItem = $("#assigned option:selected");
			$("#available").append(selectedItem);
		});

		$("#btnRight").click(function() {
			var selectedItem = $("#available option:selected");
			$("#assigned").append(selectedItem);
		});

		/* $("#assigned").change(function() {
			var selectedItem = $("#assigned option:selected");
		}); */
		$("#cancel").click(function() {
			$("#edit-modal").modal("hide");
		});
	});

    function submitMyForm(){  
        var form = document.getElementById("myForm");  
        for(i=0;i<form.assigned.length;i++){    
            form.assigned[i].selected=true;  
            
        }     
        for(i=0;i<form.available.length;i++){    
            form.available[i].selected=true;  
            
        }    
    }  
</script>