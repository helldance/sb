<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />
  <style>
  .no-border-input {
    border: none;
    background: transparent;
  }

  </style>
</head>
<body>
<%-- 
  <display:table name="${organizations}" defaultsort="1"
    defaultorder="ascending" pagesize="15" requestURI=""
    class="displaytag"
    decorator="com.coordsafe.core.rbac.decorators.OrganizationSearchTableDecorator">
    <display:column property="name" title="Name" sortable="true"
      style="width: 200px;" />
    <display:column property="parentOrganization.name"
      title="Parent Organization" style="width: 200px;" />
    <display:column property="description" title="Description" />
    <display:column property="action" title="Actions" style="width: 15px;" />
  </display:table> --%>

  <script type="text/javascript">
  $(document).ready(function() {
    $(".no-border-input").focus(function() {
      $(this).removeClass("no-border-input");
    });
  });
  </script>

  <h2 class="page-header"><i class="icon-table blue"></i>
    Profile Management

  </h2>

  <div class="row-fluid">
    <div class="span6 offset3">
     <div class="widget-box">
      <div class="widget-header">
        <h4> My profile</h4>
      </div>

      <div class="widget-body">
       <div class="widget-main no-padding">
         <form class="form-horizontal">
           <fieldset>
            <div class="control-group">
              <label class="control-label" for="display-name">
               Display Name
             </label>
             <div class="controls">
              <input class="span8 no-border-input" id="display-name" value="Password"></input>
            </div>
          </div>

          <div class="control-group">
            <label class="control-label" for="email">
             Email
           </label>
           <div class="controls">
            <input class="span8 no-border-input" id="email" value="email"></input>
          </div>
        </div>



        <div class="control-group">
          <label class="control-label" for="phone">
           Phone number
         </label>
         <div class="controls">
          <input class="span8 no-border-input" id="phone" value="phone"></input>
        </div>
      </div>


      <div class="control-group">
        <label class="control-label" for="address">
         Address
       </label>
       <div class="controls">
        <textarea rows="5" cols="30">The cat was playing in the garden.
        </textarea>
      </div>
    </div>

  </fieldset>
</form> 
</div>

<div class="widget-toolbox padding-8 clearfix">
  <button class="btn btn-small btn-success pull-left" id="edit-btn">
    Edit Profile
  </button>
  <button class="btn btn-small btn-primary pull-right" id="setPW">
    Change Password
  </button>

</div>
</div>   
</div>
</div>
</div>


<div id="pw-modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-body" >
   <div class="widget-box">
    <div class="widget-header">
      <h4>Change Password</h4>
    </div>

    <div class="widget-body">
     <div class="widget-main no-padding">
       <form class="form-horizontal">
         <fieldset>
          <div class="control-group">
            <label class="control-label" for="old-password">
             Old Password
           </label>
           <div class="controls">
            <input class="span11" id="old-password" type="password" placeholder="Password"></input>
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="new-password-1">
           New Password
         </label>
         <div class="controls">
          <input class="span11" id="new-password-1" type="password" placeholder="Password"></input>
        </div>
      </div>



      <div class="control-group">
        <label class="control-label" for="new-password-2">
         Enter again to confirm
       </label>
       <div class="controls">
        <input class="span11" id="new-password-2" type="password" placeholder="Password"></input>
      </div>
    </div>
  </fieldset>
</div>


<div class="widget-toolbox padding-8 clearfix">
  <button onclick="return false;" class="btn btn-small btn-success pull-left">
    Submit
    <i class="icon-arrow-right icon-on-right bigger-110"></i>
  </button>

  <button class="btn btn-small btn-danger pull-right" id="cancel-pw-btn">
    <i class="icon-remove"></i>Cancel
  </button>

</div>



</form> 
</div>
</div>   
</div>
</div>
</div>


<div id="profile-modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-body" >
   <div class="widget-box">
    <div class="widget-header">
      <h4>Modify profile</h4>
    </div>

    <div class="widget-body">
     <div class="widget-main no-padding">
       <form class="form-horizontal">
         <fieldset>
          <div class="control-group">
            <label class="control-label" for="display-name">
             Display Name
           </label>
           <div class="controls">
            <input class="span8" id="display-name" value="Password"></input>
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="email">
           Email
         </label>
         <div class="controls">
          <input class="span8" id="email" value="email"></input>
        </div>
      </div>



      <div class="control-group">
        <label class="control-label" for="phone">
         Phone number
       </label>
       <div class="controls">
        <input class="span8" id="phone" value="phone"></input>
      </div>
    </div>


    <div class="control-group">
      <label class="control-label" for="address">
       Address
     </label>
     <div class="controls">
      <textarea rows="5" cols="30">The cat was playing in the garden.
      </textarea>
    </div>
  </div>

</fieldset>
</div>
<div class="widget-toolbox padding-8 clearfix">
  <button onclick="return false;" class="btn btn-small btn-success pull-left">
    Submit
    <i class="icon-arrow-right icon-on-right bigger-110"></i>
  </button>

  <button class="btn btn-small btn-danger pull-right" id="cancel-edit-btn">
    <i class="icon-remove"></i>Cancel
  </button>

</div>
</form> 
</div>
</div>   
</div>

</div>   
</div>




<script type="text/javascript">
$("#setPW").click(function() {
  $("#pw-modal").modal();
})
$("#edit-btn").click(function(){
  $("#profile-modal").modal();
})

$("#cancel-pw-btn").click(function() {
  $("#pw-modal").modal("hide");
})

$("#cancel-edit-btn").click(function() {
  $("#profile-modal").modal("hide");
  $("#contentCoordsafe").load($("#db-profile").attr("href"));
})
</script>



</body>
</html>
