var r = {"counter":0,"resource":0};
var bars = {"counter":0,"resource":0};
var pies;
var raphael;
var b_path = {"counter":[],"resource":[]};
var b_text = {"counter":[],"resource":[]};
var b_label = {"counter":[],"resource":[]};
var month=['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
var fullMonth=['January','February','March','April','May','June','July','August','September','October','November','December'];
var prefix="/fleet-link/web/api-statistic/";
var usageData;
var api_key_data;
var durVal;
//var suffix="?key=test-1234qwer";

function createQuery(){
	var type=$("#type-option option:selected").val();
	var firstDate,lastDate;
	//var value=$("#trip-option option:selected").val();
	var value=durVal;
	if(typeof value=="undefined"){
		if(type=="weekly"){
			value=(new Date()).getWeek();
		}else if(type=="monthly"){
			value=(new Date()).getMonth();
		}
	}else{
		value=parseInt(value);
	}    
	if(type=="weekly"){
		firstDate = (new Date()).setWeek(value);
		lastDate  = new Date(firstDate.getTime()+6*86400000);
	}else if(type=="monthly"){
		firstDate = new Date(2014,value,1);
		lastDate = new Date(2014,value+1,0);
	}
	var req = prefix + keyId +  "?timeRange=" 
		+("0" + firstDate.getDate()).slice(-2)+"-"+("0" + (firstDate.getMonth()+1)).slice(-2)+"-"+firstDate.getFullYear()
		+","+("0" + lastDate.getDate()).slice(-2)+"-"+("0" + (lastDate.getMonth()+1)).slice(-2)+"-"+lastDate.getFullYear();

	return req;
};

function retrieveUsageData(){
	//populateDurationOption("weekly",new Date().getWeek());
	
	$.getJSON(createQuery(), function(data) {	
		//var key_id = [];
		api_key_data = usageData = data;
		drawAPIKeyChart();

		/*for(var i=0, l=api_key_data.length; i<l; i++){
			var key=api_key_data[i].key.id;
			if(key_id.indexOf(key)==-1){
				key_id.push(key);
			}
		}
		populateAPIKeyIdOptions(key_id);*/
  });
}

function initiate(){
	var key_id = [];
//	var api_key_data = getAPIKeyData();
	api_key_data = usageData;

	for(var i=0, l=api_key_data.length; i<l; i++){
		var key=api_key_data[i].key.id;
		if(key_id.indexOf(key)==-1){
			key_id.push(key);
		}
	}
	populateAPIKeyIdOptions(key_id);
};

//populate dropdown for selecting API key
function populateAPIKeyIdOptions(key_id){
	$("#key-option").append('<option value="-1">------</option>');
	for(var i=0, l=key_id.length; i<l; i++){
		$("#key-option").append('<option value="'+i+'">'+key_id[i]+'</option>');
	}
	currentWeek= (new Date()).getWeek();
	$("#trip-option").append('<option value="'+currentWeek+'" selected></option>');
	durVal = currentWeek;
	populateDurationOption("weekly",currentWeek);
};

function selectType(){
	var type=$("#type-option option:selected").val();
	if(type=="weekly"){
		var value = (new Date()).getWeek();
		populateDurationOption("weekly",value);   
		$("#trip-option option:selected").val(""+value);
		durVal = value;
	}else if(type=="monthly"){
		var value = (new Date()).getMonth();
		populateDurationOption("monthly",value);
		$("#trip-option option:selected").val(""+value);
		durVal = value;
	}
	//selectKeyOption();
	//retrieveUsageData();
	//drawAPIKeyChart();
	retrieveUsageData();
};

function selectKeyOption(){
	var keyOption = $("#key-option option:selected").val();
	if(keyOption=="-1"){
		return;
	}else{
		//drawAPIKeyChart();
		retrieveUsageData();
	}
};

function drawAPIKeyChart(){
	//var api_key_data = retrieveUsageData(), processed_data =[];
	//retrieveUsageData();
	var processed_data =[];
	api_key_data.sort(function(x,y){
		return x.reqDt-y.reqDt;
	});
	var type = $("#type-option option:selected").val();
	//var value = $("#trip-option option:selected").val();
	value = durVal;
	if(type=="monthly"){
		if(typeof value=="undefined"){
			value = (new Date()).getMonth();
		}
		for(var i=0, l=api_key_data.length; i<l; i++){
			var date = new Date(api_key_data[i].reqDt);
			if(date.getMonth()==value){
				processed_data.push(api_key_data[i]);
			}else if(date.getMonth()>value){
				break;
			}
		}
	}else if(type=="weekly"){
		if(typeof value=="undefined"){
			value = (new Date()).getWeek();
		}
		for(var i=0, l=api_key_data.length; i<l; i++){
			var date = new Date(api_key_data[i].reqDt);
			if(date.getWeek()==value){
				processed_data.push(api_key_data[i]);
			}else if(date.getWeek()>value){
				break;
			}
		}
	}
	$("#counter").empty();
	$("#resource").empty();
	$("#summary-table").empty();
	if(processed_data.length!=0){
		drawAPIKeyCounterChart(processed_data);
		drawAPIKeyResourceChart(processed_data);	
	}else{
		$("#counter").append('<p align="center">Data is not avaialble for this time period</p>');
	}
};

function drawAPIKeyCounterChart(data){
	var k_counter = [], k_date = [], k_labels = [], k_colors = [];
	var type = $("#type-option option:selected").val();
	//var value = $("#trip-option option:selected").val();
	var value = durVal;
	value = parseInt(value);

	if(type=="monthly"){
		var firstDate = new Date(data[0].reqDt);
		firstDate.setMonth(value);
		firstDate.setDate(1);
		var tempDate=firstDate;
		var i=0;
		while(tempDate.getMonth()==value){
			k_counter[i] = 0;
			tempDate = new Date(firstDate.getTime()+i*86400000);
			k_date[i] = tempDate;
			i++;
		}
		k_counter.pop();
		k_date.pop();
		for(var i=0, l=data.length;i<l;i++){
			var date = new Date(data[i].reqDt);
			if(date.getMonth()==value){
				k_counter[date.getDate()-1]++;
			}else if(date.getMonth()>value){
				break;
			}
		}
	}else if(type=="weekly"){
		var firstDate = new Date(data[0].reqDt);
		firstDate = firstDate.setWeek(value);

		for(var i=0;i<7;i++){
			k_counter[i] = 0;
			k_date[i] = new Date(firstDate.getTime()+i*86400000);
		}
		for(var i=0, l=data.length;i<l;i++){
			var date = new Date(data[i].reqDt);
			if(date.getWeek()==value){
				k_counter[date.getDay()]++;
			}else if(date.getWeek>value){
				break;
			}
		}
	}

	var max_counter = 0;
	var avg_counter = 0;
	for(var i=0;i<k_date.length;i++){
		if(parseInt(k_counter[i])>max_counter){
			max_counter=parseInt(k_counter[i]);
		}
		var d = k_date[i].getDate();
		var m = k_date[i].getMonth();
		k_labels[i]=d+"/"+(m+1);
		k_colors[i]="#c71a23";
    }
    
    //Adding max and min value for drawing gridlines
    k_counter.push(0);
    k_counter.push(maxValue(max_counter));
    k_colors.push("transparent");
    k_colors.push("transparent");

    if(r.counter!=null&&r.counter!=0) r.counter.remove();
	//draw barchart
    var div=$("#counter");
    var w = div.width();
    var h = div.height();
	r.counter = Raphael("counter",w,h);
	//Drawing chart title
	r.counter.text(w/8,6,"# of Request").attr("font-size",16);
	
    drawBarchart(k_counter,k_labels,k_colors,avg_counter,"counter");
};

function drawAPIKeyResourceChart(data){
	var k_usage = [], k_labels = [], k_colors = [];

	for(var i=0, l=data.length; i<l; i++){
		var pattern = /api\/(.+?)\//g;
		var matches = pattern.exec(data[i].resource);
		if (matches === null){
			continue;
		}
		var match = matches[1];
		if(k_labels.indexOf(match)==-1){
			k_labels.push(match);
			k_usage[k_labels.indexOf(match)] = 1;
		}else{
			k_usage[k_labels.indexOf(match)]++;
		}
	}
	for(var i=0, l=k_labels.length; i<l; i++){
		k_labels[i] = k_labels[i].charAt(0).toUpperCase()+k_labels[i].slice(1);
	}
	//Populate resource usage table
    populateResourceUsageTable(k_usage,k_labels);
	//draw piechart
    if(r.resource!=null&&r.resource!=0) r.resource.remove();
    var div=$("#resource");
	r.resource = Raphael("resource",div.width(),div.height());
	//Drawing chart title
	r.resource.text(div.width()*1/3,20,"Resource Usage").attr("font-size",16);
    drawPiechart(k_usage,k_labels,"resource");    
};

//draw summary table of usage for a certain period of time
function populateResourceUsageTable(usage,labels){
	var div = $("#summary-table");
	var r_text = Raphael("summary-table", div.width(), div.height());
	r_text.text(div.width()*1/2,320,"Summary").attr("font-size",16);
	$("#summary-table").append('<table id="hor-minimalist-a" summary="Summary">');
	$("#summary-table table").append('<thead><tr></tr></thead>');
	for(var i=0, l=labels.length; i<l; i++){
		$("#summary-table table thead tr").append('<th scope="col">'+labels[i]+'</th>');
	}
	$("#summary-table table thead tr").append('<th scope="col">Total</th>');
	$("#summary-table table thead tr").append('<th scope="col">Most Requested</th>');
	$("#summary-table table").append('<tbody><tr></tr></tbody>');

	var highestUsageResources = [];
	var max_usage = 0;
	var total_usage = 0;
	for(var i=0, l=usage.length; i<l; i++){
		total_usage+=usage[i];
		if(max_usage<usage[i]){
			max_usage = usage[i];
			for(var j=0, l1=highestUsageResources.length; j<l1; j++){
				highestUsageResources.pop();
			}
			highestUsageResources.push(labels[i]);
		}else if(max_usage == usage[i]){
			highestUsageResources.push(labels[i]);
		}
		$("#summary-table table tbody tr").append('<td>'+usage[i]+'</th>');
	}
	var str = "";
	for(var i=0, l=highestUsageResources.length; i<l; i++){
		str+=highestUsageResources[i]+", ";
	}
	str = str.slice(0,-2);
	$("#summary-table table tbody tr").append('<td>'+total_usage+'</th>');
	$("#summary-table table tbody tr").append('<td>'+str+'</th>');
};

function drawPiechart(g_value,g_label,title){
	var div=$("#"+title);
	if(pies!=null){
		pies.remove();
	}

	for(var i=0, l=g_label.length; i<l; i++){
		g_label[i]+=":  %%.%%";
	}
	var options = {legend:g_label, legendpos: "east"};
	pies = r[title].piechart(160,170,120,g_value,options);
	pies.each(function(){
		var a =this.sector;
		//scale each sector to 0
		this.sector.scale(0.25, 0.25, this.cx, this.cy);
		//animate from 0 to default size
		this.sector.animate({ transform: 's1 1 ' + this.cx + ' ' + this.cy }, 400);
	});
	pies.hover(function () {
		this.sector.stop();
		if(this.sector.matrix.a==1&&this.sector.matrix.d==1){
			var test = this.sector.scale(1.1, 1.1, this.cx, this.cy);	
		}
		if (this.label) {
			this.label[0].stop();
			this.label[0].attr({ r: 7.5 });
			this.label[1].attr({ "font-weight": 800 });
		}
	}, function () {
		this.sector.animate({ transform: 's1 1 ' + this.cx + ' ' + this.cy }, 250);	
		if (this.label) {
			this.label[0].animate({ r: 5 }, 400);
			this.label[1].attr({ "font-weight": 400 });
		}
	});
	//hover in and out function for popup window
	pies.hover(function(){
		this.popups = r[title].set();
		this.popups.push(r[title].popup(this.mx, this.my, this.label[1].attrs.text || "0","right"));
	},function(){
		this.popups && this.popups.remove();
	});
};

function drawBarchart(g_value,g_label,colors,avg,title){	
	var div=$("#"+title);
	if(bars[title]!=null&&bars[title]!=0){
		bars[title].remove();
		for(var i=0;i<5;i++){
			b_path[title][i].remove();
			b_text[title][i].remove();
		}
		for(var j=0;j<b_label[title].length;j++){
			b_label[title][j].remove();
		}
	}
	var bar_attr={"x":40,"y":25,"width":div.width(),"height":div.height()-50};
	var options={colors:colors,gutter:"50%"};

    temp_m=r[title].barchart(bar_attr.x,bar_attr.y,bar_attr.width,bar_attr.height,g_value,options);
    temp_m.hide();

    //The last bar is the max (smallest y), the second last bar is the min (0, largest y)
    //y4 the highest gridline, y0 the lowest gridline
	y0=temp_m.bars[g_value.length-2].y;
	y4=temp_m.bars[g_value.length-1].y;
	y1=(y0-y4)*3/4+y4;
	y2=(y0-y4)/2+y4;
	y3=(y0-y4)*1/4+y4;
	//y5=(y0-y4)*(1-avg/g_value[g_value.length-1])+y4;
	//Getting x value of the last and second last bar and preventing them from displaying on the chart
    x_max=temp_m.bars[g_value.length-1].x;
    x_min=temp_m.bars[g_value.length-2].x;

	b_path[title][0]=r[title].path("M"+(bar_attr.x)+" "+y0+"l"+(x_max-70)+" 0").attr({"stroke-opacity":1});
	b_path[title][1]=r[title].path("M"+(bar_attr.x)+" "+y1+"l"+(x_max-70)+" 0").attr({"stroke-opacity":1,"stroke":"#c0c0c0"});
	b_path[title][2]=r[title].path("M"+(bar_attr.x)+" "+y2+"l"+(x_max-70)+" 0").attr({"stroke-opacity":1,"stroke":"#c0c0c0"});
	b_path[title][3]=r[title].path("M"+(bar_attr.x)+" "+y3+"l"+(x_max-70)+" 0").attr({"stroke-opacity":1,"stroke":"#c0c0c0"});
	b_path[title][4]=r[title].path("M"+(bar_attr.x)+" "+y4+"l"+(x_max-70)+" 0").attr({"stroke-opacity":1,"stroke":"#c0c0c0"});
	
	//Labeling for y axis
	//txt4 is the value for the highest gridline, txt1 is the value for the lowest gridline
	var txt0,txt1,txt2,txt3,txt4;
	txt4=g_value[g_value.length-1];
	txt3=""+txt4*3/4;
	txt2=""+txt4/2;
	txt1=""+txt4*1/4;
	txt0="0";

    b_text[title][0] = r[title].text(25,(y0-4),txt0).attr({"font-size":14,"opacity":0});
	b_text[title][1] = r[title].text(25,(y1-4),txt1).attr({"font-size":14,"opacity":0});
	b_text[title][2] = r[title].text(25,(y2-4),txt2).attr({"font-size":14,"opacity":0});
	b_text[title][3] = r[title].text(25,(y3-4),txt3).attr({"font-size":14,"opacity":0});
	b_text[title][4] = r[title].text(25,(y4-4),txt4).attr({"font-size":14,"opacity":0});
	//Animate gridlines and label
	for(var i=0;i<5;i++){
		b_text[title][i].animate({"opacity":1},400);
	}

	var zoom=5;
	for(var i=0;i<g_value.length-2;i++){
		g_value[i]/=zoom;
	}
	bars[title]=r[title].barchart(bar_attr.x,bar_attr.y,bar_attr.width,bar_attr.height,g_value,options);
	//Putting label for each bar (x axis) and animate them
    for(var i=0;i<g_value.length-2;i++){
    	b_label[title].push(r[title].text(temp_m.bars[i].x,y0+20,g_label[i]).attr({"font-size":12,"opacity":1}));
    }
    
    //Animate barchart: growing bars    
    for(var i=0;i<bars[title].bars.length-2;i++){
    	bars[title].bars[i].animate({transform:"s1,"+zoom+","+bars[title].bars[i].x+","+y0},600);
    }

    for(var i=0;i<bars[title].bars.length-2;i++){
    	bars[title].bars[i].value*=zoom;
    }

    //Hover function for barchart
    bars[title].hover(function(){
    	this.popups = r[title].set();
    	if(title=="counter"){
    		if(this.bar.x!=x_min&&this.bar.x!=x_max){
    			this.popups.push(r[title].popup(this.bar.x, 5*this.bar.y-4*y0, "request : "+this.bar.value || "0","right").insertBefore(this));	
    		}
    	}else if(title=="resource"){
    		if(this.bar.x!=x_min&&this.bar.x!=x_max){
    			this.popups.push(r[title].popup(this.bar.x, 5*this.bar.y-4*y0, title+": "+this.bar.value || "0","right").insertBefore(this));	
    		}
    	}
    },function(){
    	this.popups && this.popups.remove();
    });
};

//determine max for drawing chart purpose
function maxValue(num){
	if(num<10){		
		return (Math.floor(num/2)+1)*2;
	}else{
		power=(Math.floor(num)+"").length-2;
		denominator=4*Math.pow(10,power);
		temp=Math.floor(num/denominator)+1;
		return temp*denominator;
	}
};

Date.prototype.getWeek = function() {
	var onejan = new Date(this.getFullYear(),0,1);
	return Math.ceil((((this - onejan) / 86400000) + onejan.getDay()+1)/7);
};

Date.prototype.setWeek = function(num){
	var onejan = new Date(this.getFullYear(),0,1);
	thisDate = onejan.getTime()+(num*7-7-onejan.getDay())*86400000;
	return (thisDate>onejan.getTime()) ? new Date(thisDate) : new Date(onejan.getTime());
};

function populateDurationOption(type,value){
	var d_div=$("#duration");
	if(raphael!=null){
		raphael.remove();
	}
	raphael= Raphael("duration",d_div.width(),d_div.height());
	//draw next and previous button
	rightc = raphael.circle(d_div.width()-20, d_div.height()-15, 10).attr({fill: "#e0e0e0", stroke: "none"}),
    right = raphael.path("M"+(d_div.width()-23)+","+(d_div.height()-20)+"l10,5 -10,5z").attr({fill: "#000000"}),
    leftc = raphael.circle(20, 15, 10).attr({fill: "#e0e0e0", stroke: "none"}),
    left = raphael.path("M23,10l-10,5 10,5z").attr({fill: "#000000"});

    var text="";

	if(type=="weekly"){
		start=new Date();
		start=start.setWeek(value);
		end=new Date(start.getTime()+6*86400000);
		text="Week "+value+": from "+start.getDate()+"/"+(start.getMonth()+1)+"/"+start.getFullYear()+
		                      " to "+end.getDate()+"/"+(end.getMonth()+1)+"/"+end.getFullYear();
	}else if(type=="monthly"){
		text=fullMonth[value]+" "+(new Date).getFullYear();
	}
	d_text = raphael.text(d_div.width()/2,15,text).attr({"font-size":15,"font-weight":"bold","fill-opacity":0.75});

	//hover effects for next and previous buttons
    function f_in_right(){
    	rightc.attr({"fill":"#408000"});
    	right.attr({"fill":"#ffffff","stroke":"#ffffff"});
    	d_div.css("cursor","pointer");
    };
    function f_out_right(){
    	rightc.attr({"fill":"#e0e0e0"});
    	right.attr({"fill":"#000000","stroke":"#000000"});
    	d_div.css("cursor","default");
    };
    function f_in_left(){
    	leftc.attr({"fill":"#408000"});
    	left.attr({"fill":"#ffffff","stroke":"#ffffff"});
    	d_div.css("cursor","pointer");
    };
    function f_out_left(){
    	leftc.attr({"fill":"#e0e0e0"});
    	left.attr({"fill":"#000000","stroke":"#000000"});
    	d_div.css("cursor","default");
    };
    rightc.hover(f_in_right,f_out_right);
    leftc.hover(f_in_left,f_out_left	);
    right.hover(f_in_right,f_out_right);
    left.hover(f_in_left,f_out_left	);

    //adding click functions for next and previous button
    function f_click_week_next(){
    	populateDurationOption("weekly",value+1);
    	$("#trip-option option:selected").val(""+(value+1));
    	durVal = value + 1;
    	//drawAPIKeyChart();
    	retrieveUsageData();
    };
    function f_click_week_prev(){
    	populateDurationOption("weekly",value-1);
    	$("#trip-option option:selected").val(""+(value-1));
    	durVal = value - 1;
    	//drawAPIKeyChart();
    	retrieveUsageData();
    };
    function f_click_month_next(){
    	if(value==11){
    		populateDurationOption("monthly",0);
    		$("#trip-option option:selected").val(""+0);
    		durVal = 0;
    	}else{
    		populateDurationOption("monthly",value+1);
    		$("#trip-option option:selected").val(""+(value+1));
    		durVal = value + 1;
    	}
    	//drawAPIKeyChart();
    	retrieveUsageData();
    }
    function f_click_month_prev(){
    	if(value==0){
    		populateDurationOption("monthly",11);
    		$("#trip-option option:selected").val(""+11);
    		durVal = 11;
    	}else{
    		populateDurationOption("monthly",value-1);
    		$("#trip-option option:selected").val(""+(value-1));
    		durVal = value - 1;
    	}
    	//drawAPIKeyChart();
    	retrieveUsageData();
    }
    //assign click functions for next and previous buttons
    if(type=="weekly"){
    	rightc.click(f_click_week_next);
    	right.click(f_click_week_next);
    	leftc.click(f_click_week_prev);
    	left.click(f_click_week_prev);	
    }else if(type=="monthly"){
    	rightc.click(f_click_month_next);
    	right.click(f_click_month_next);
    	leftc.click(f_click_month_prev);
    	left.click(f_click_month_prev);	
    } 
}