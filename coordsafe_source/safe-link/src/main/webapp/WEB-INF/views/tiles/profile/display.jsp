<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<div id="main-content" class="clearfix">
	<h2 class="contentHeader">Profile Management</h2>

    <div class="row-fluid">
      <div class="span5">
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

                      <div class="form-actions center">
						<button onclick="return false;" class="btn btn-small btn-success">
						      Submit
							  <i class="icon-arrow-right icon-on-right bigger-110"></i>
						</button>
					   </div>
                    </form>	
        	    </div>
             </div>		
         </div>
       </div>

 <div class="span7">
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

                      <div class="form-actions center">
						<button onclick="return false;" class="btn btn-small btn-success">
						      Submit
							  <i class="icon-arrow-right icon-on-right bigger-110"></i>
						</button>
					   </div>
                    </form>	
        	    </div>
             </div>		
         </div>
       </div>

      </div>

</div>


<script type="text/javascript">
$(function() {
	
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


