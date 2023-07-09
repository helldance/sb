<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<%@page import="com.coordsafe.event.entity.EventType" %>

<div id="main-content" class="clearfix">
	<h2 class="contentHeader">Profile Management</h2>

  <div class="row-fluid">

   <div class="span6">
     <div class="widget-box">
      <div class="widget-header">
       <h3><i>Notification Setting</i></h3>
     </div>

     <div class="widget-body">
      <div class="widget-main no-padding">

        <form:form class="form-horizontal" modelAttribute="nsForm" >

        <table width="50%">
          <tr>
            <th>Event Type</th>
            <th>SMS</th>
            <th>Email</th>
          </tr>

          <c:forEach items="${nsForm.notificationSettings}" var="notificationSetting" varStatus="status">
          <tr>
            <td>${notificationSetting.eventType}</td>
            <td>${notificationSetting.isSMS}</td>
            <td>${notificationSetting.isEmail}</td>
          </tr>
        </c:forEach>
        <tr>

         <div class="form-actions center">
           <button onclick="return false;" class="btn btn-small btn-success">
             Submit
             <i class="icon-arrow-right icon-on-right bigger-110"></i>
           </button>
         </div>
       </tr>
     </table>
   </form:form>

 </div>
</div>		
</div>
</div>
</div>
</div>


<script type="text/javascript">
$(function() {

    $("#profile-submenu").css("display","block");
 
	
	var oTable1 = $('#table_report').dataTable( {
		"aoColumns": [
		{ "bSortable": false },
		null, null,
		{ "bSortable": false }
		] } );

 $(function(){
   $("a.form-op").click(function(){
     $('.modal-body').load($(this).attr('href'));
     $("#edit-modal").modal();	            
     return false;
   });
 });

})
</script>


