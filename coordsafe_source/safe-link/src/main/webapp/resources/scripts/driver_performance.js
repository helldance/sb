var r = {"speeding":0,"pause":0};
var bars = {"speeding":0,"pause":0};
var pies;
var raphael;
var b_path = {"speeding":[],"pause":[]};
var b_text = {"speeding":[],"pause":[]};
var b_label = {"speeding":[],"pause":[]};
var month=['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
var fullMonth=['January','February','March','April','May','June','July','August','September','October','November','December'];

function initiateDriverPerformanceReport(){
	var trip_data = retrieveTripData();
	var vehicle_names = [];

	for(var i=0, l=trip_data.length; i<l; i++){
		var key;
		for( var k in trip_data[i]) key=k;
		vehicle_names.push(key);
	}
	populateDriverOptions(vehicle_names);
};

function populateDriverOptions(names){
	$("#driver-option").append('<option value="-1">------------</option>');
	for(var i=0, l=names.length; i<l; i++){
		$("#driver-option").append('<option value="'+i+'">'+names[i]+'</option>');
	}
	currentWeek= (new Date()).getWeek();
	$("#trip-option").append('<option value="'+currentWeek+'" selected></option>');
	populateDurationOption("weekly",currentWeek);
};

function selectType(){
    var type=$("#type-option option:selected").val();
    if(type=="weekly"){
        var value = (new Date()).getWeek();
        populateDurationOption("weekly",value);   
        $("#trip-option option:selected").val(""+value);
    }else if(type=="monthly"){
        var value = (new Date()).getMonth();
        populateDurationOption("monthly",value);
        $("#trip-option option:selected").val(""+value);
    }
    selectDriverOption();
};

function selectDriverOption(){
    var driverOption = $("#driver-option option:selected").val();
    if(driverOption=="-1"){
        return;
    }else{
        drawDriverPerformanceChart();
    }
};

function drawDriverPerformanceChart(){
    var driverOption = $("#driver-option option:selected").val();
    driverOption = parseInt(driverOption);
    var trip_data = retrieveTripData();
    var key;
    for( var k in trip_data[driverOption]) key=k;
    var driver_data = trip_data[driverOption][key];
    driver_data.sort(function(x,y){
        return x.tripStartTime - y.tripStartTime;
    });

    var type = $("#type-option option:selected").val();
    var value = $("#trip-option option:selected").val();
    value = parseInt(value);
    var processed_data = [];
    if(type=="monthly"){
        if(typeof value=="undefined"){
            value = (new Date()).getMonth();
        }
        for(var i=0, l=driver_data.length; i<l; i++){
            var date = new Date(driver_data[i].tripStartTime);
            if(date.getMonth()==value){
                processed_data.push(driver_data[i]);
            }else if(date.getMonth()>value){
                break;
            }
        }
    }else if(type=="weekly"){
        if(typeof value=="undefined"){
            value = (new Date()).getWeek();
        }
        for(var i=0, l=driver_data.length; i<l; i++){
            var date = new Date(driver_data[i].tripStartTime);
            if(date.getWeek()==value){
                processed_data.push(driver_data[i]);
            }else if(date.getWeek()>value){
                break;
            }
        }
    }
    $("#speeding").empty();
    $("#pause").empty();
    if(processed_data.length!=0){
        drawSpeedingChart(processed_data);
        drawPauseChart(processed_data);    
    }else{
        $("#speeding").append('<p align="center">Data is not avaialble for this time period</p>');
        $("#pause").append('<p align="center">Data is not avaialble for this time period</p>');
    }
};

function drawSpeedingChart(data){
    var d_speeding = [], d_date = [], d_labels = [], d_colors = [];
    var type = $("#type-option option:selected").val();
    var value = $("#trip-option option:selected").val();
    value = parseInt(value);

    if(type=="monthly"){
        var firstDate = new Date(data[0].tripStartTime);
        firstDate.setMonth(value);
        firstDate.setDate(1);
        var tempDate=firstDate;
        var i=0;
        while(tempDate.getMonth()==value){
            d_speeding[i] = 0;
            tempDate = new Date(firstDate.getTime()+i*86400000);
            d_date[i] = tempDate;
            i++;
        }
        d_speeding.pop();
        d_date.pop();
        for(var i=0, l=data.length;i<l;i++){
            var date = new Date(data[i].tripStartTime);
            if(date.getMonth()==value){
                d_speeding[date.getDate()-1]+=data[i].speeding;
            }else if(date.getMonth()>value){
                break;
            }
        }
    }else if(type=="weekly"){
        var firstDate = new Date(data[0].tripStartTime);
        firstDate = firstDate.setWeek(value);

        for(var i=0;i<7;i++){
            d_speeding[i] = 0;
            d_date[i] = new Date(firstDate.getTime()+i*86400000);
        }
        for(var i=0, l=data.length;i<l;i++){
            var date = new Date(data[i].tripStartTime);
            if(date.getWeek()==value){
                d_speeding[date.getDay()]+=data[i].speeding;
            }else if(date.getWeek>value){
                break;
            }
        }
    }

    var max_speeding = 0;
    var avg_speeding = 0;
    for(var i=0;i<d_date.length;i++){
        if(parseInt(d_speeding[i])>max_speeding){
            max_speeding=parseInt(d_speeding[i]);
        }
        var d = d_date[i].getDate();
        var m = d_date[i].getMonth();
        d_labels[i]=d+"/"+(m+1);
        d_colors[i]="#008cd2";
    }
    
    //Adding max and min value for drawing gridlines
    d_speeding.push(0);
    d_speeding.push(maxValue(max_speeding));
    d_colors.push("transparent");
    d_colors.push("transparent");

    if(r.speeding!=null&&r.speeding!=0) r.speeding.remove();
    //draw barchart
    var div=$("#speeding");
    r.speeding = Raphael("speeding",div.width(),div.height());
    //Drawing chart title
    r.speeding.text(div.width()/2,20,"Speeding").attr("font-size",16);
    drawBarchart(d_speeding,d_labels,d_colors,avg_speeding,"speeding");
};

function drawPauseChart(data){
    var d_pause = [], d_date = [], d_labels = [], d_colors = [];
    var type = $("#type-option option:selected").val();
    var value = $("#trip-option option:selected").val();
    value = parseInt(value);

    if(type=="monthly"){
        var firstDate = new Date(data[0].tripStartTime);
        firstDate.setMonth(value);
        firstDate.setDate(1);
        var tempDate=firstDate;
        var i=0;
        while(tempDate.getMonth()==value){
            d_pause[i] = 0;
            tempDate = new Date(firstDate.getTime()+i*86400000);
            d_date[i] = tempDate;
            i++;
        }
        d_pause.pop();
        d_date.pop();
        for(var i=0, l=data.length;i<l;i++){
            var date = new Date(data[i].tripStartTime);
            if(date.getMonth()==value){
                d_pause[date.getDate()-1]+=data[i].pause;
            }else if(date.getMonth()>value){
                break;
            }
        }
    }else if(type=="weekly"){
        var firstDate = new Date(data[0].tripStartTime);
        firstDate = firstDate.setWeek(value);

        for(var i=0;i<7;i++){
            d_pause[i] = 0;
            d_date[i] = new Date(firstDate.getTime()+i*86400000);
        }
        for(var i=0, l=data.length;i<l;i++){
            var date = new Date(data[i].tripStartTime);
            if(date.getWeek()==value){
                d_pause[date.getDay()]+=data[i].pause;
            }else if(date.getWeek>value){
                break;
            }
        }
    }

    var max_pause = 0;
    var avg_pause = 0;
    for(var i=0;i<d_date.length;i++){
        if(parseInt(d_pause[i])>max_pause){
            max_pause=parseInt(d_pause[i]);
        }
        var d = d_date[i].getDate();
        var m = d_date[i].getMonth();
        d_labels[i]=d+"/"+(m+1);
        d_colors[i]="#c71a23";
    }
    
    //Adding max and min value for drawing gridlines
    d_pause.push(0);
    d_pause.push(maxValue(max_pause));
    d_colors.push("transparent");
    d_colors.push("transparent");

    if(r.pause!=null&&r.pause!=0) r.pause.remove();
    //draw barchart
    var div=$("#pause");
    r.pause = Raphael("pause",div.width(),div.height());
    //Drawing chart title
    r.pause.text(div.width()/2,20,"Pause").attr("font-size",16);
    drawBarchart(d_pause,d_labels,d_colors,avg_pause,"pause");
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
        if(title=="speeding"){
            if(this.bar.x!=x_min&&this.bar.x!=x_max){
                this.popups.push(r[title].popup(this.bar.x, 5*this.bar.y-4*y0, "Speeding: "+this.bar.value || "0","right").insertBefore(this));   
            }
        }else if(title=="pause"){
            if(this.bar.x!=x_min&&this.bar.x!=x_max){
                this.popups.push(r[title].popup(this.bar.x, 5*this.bar.y-4*y0, "Pause: "+this.bar.value || "0","right").insertBefore(this));   
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
    	drawDriverPerformanceChart();
    };
    function f_click_week_prev(){
    	populateDurationOption("weekly",value-1);
    	$("#trip-option option:selected").val(""+(value-1));
    	drawDriverPerformanceChart();
    };
    function f_click_month_next(){
    	if(value==11){
    		populateDurationOption("monthly",0);
    		$("#trip-option option:selected").val(""+0);
    	}else{
    		populateDurationOption("monthly",value+1);
    		$("#trip-option option:selected").val(""+(value+1));
    	}
    	drawDriverPerformanceChart();	
    }
    function f_click_month_prev(){
    	if(value==0){
    		populateDurationOption("monthly",11);
    		$("#trip-option option:selected").val(""+11);
    	}else{
    		populateDurationOption("monthly",value-1);
    		$("#trip-option option:selected").val(""+(value-1));
    	}
    	drawDriverPerformanceChart();
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
};
