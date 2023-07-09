<%@ include file="/WEB-INF/views/tiles/includes.jsp"%>
<div class="container-fluid" id="main-container">
	<a id="menu-toggler" href="#"> <span></span>
	</a>


	</div>

	<script type="text/javascript">

	function add0(p) {
		if (p<10) return ('0'+p)
			else return p;
	}


	$(document).ready(function() {
		$("a.pageFetcher").click(function() {
			$("#loading").show();
		})
		var currentdate = new Date();
		var date = currentdate.getDate();
		var month = currentdate.getMonth();
		var year =  currentdate.getFullYear();
        
        var date30 = new Date(year, month, date-3);
        var date2 = date30.getDate();
        var month2 = date30.getMonth();
		var year2 =  date30.getFullYear();

		var href = $("#eventlog-a").attr("href");
		href = href + add0(date2) +'-'+ add0(month2+1) +'-'+ add0(year2) +','+ add0(date) +'-' +add0(month+1) +'-'+ add0(year);
		$("#eventlog-a").attr("href", href);
	})

	</script>