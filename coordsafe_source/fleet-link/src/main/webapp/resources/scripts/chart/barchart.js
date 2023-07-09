var r = {"Mileage":0,"Time":0,"Frequency":0};
var raphael;
var bars = {"Mileage":0,"Time":0,"Frequency":0};
var b_path={"Mileage":[],"Time":[],"Frequency":[]};
var b_text={"Mileage":[],"Time":[],"Frequency":[]};
var b_label={"Mileage":[],"Time":[],"Frequency":[]};
var month=['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
var fullMonth=['January','February','March','April','May','June','July','August','September','October','November','December'];
var prefix="/fleet-link/trip/group/";
//var suffix="?key=test-1234qwer";

function createQuery(){
	var type=$("#type-option option:selected").val();
	var firstDate,lastDate;
	//var value=$("#trip-option option:selected").val();
	value = wm;
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
	var req = prefix + grp2 + "/" 
		+("0" + firstDate.getDate()).slice(-2)+"-"+("0" + (firstDate.getMonth()+1)).slice(-2)+"-"+firstDate.getFullYear()
		+","+("0" + lastDate.getDate()).slice(-2)+"-"+("0" + (lastDate.getMonth()+1)).slice(-2)+"-"+lastDate.getFullYear();

	return req;
};

function getSingleVehicleData(value){
	$.getJSON(createQuery(), function(data) {
		var vehicleOption=$("#vehicle-option option:selected").val();
		vehicleOption=parseInt(vehicleOption);
    	var key;
    	for( var k in trip_data[vehicleOption]){
    		key=k;	
    	}
    	var vehicle_data=data[vehicleOption][key];
    	vehicle_data.sort(function(x,y){
    	return x.tripStartTime-y.tripStartTime;
    	});
    	if(vehicle_data.length!=0){
    		drawOneVehicle(vehicle_data,value);
    		$("#saveAsCSV").show();
            $("#saveAsPNG").show();
            /*$("#chart-option").show();*/
    	}else{
    		$("#Mileage").append('<p align="center">Data is not avaialble for selected period</p>');
    		$("#Time").append('<p align="center">Data is not avaialble for selected period</p>');
    		$("#Frequency").append('<p align="center">Data is not avaialble for selected period</p>');
            $("#saveAsCSV").hide();
            $("#saveAsPNG").hide();
            /*$("#chart-option").hide();*/
    	}
    });
}

function drawOneVehicle(vehicle_data,value){
	var firstDate = new Date(vehicle_data[0].tripStartTime);
	var type=$("#type-option option:selected").val();
	if(type=="weekly"){
		if(typeof value=="undefined"){
			value = firstDate.getWeek();
		}
		drawOneVehicleMileAgeWeek(vehicle_data,value);
		drawOneVehicleTimeWeek(vehicle_data,value);
		drawOneVehicleFrequencyWeek(vehicle_data,value);
	}else if(type=="monthly"){
		if(typeof value=="undefined"){
			value = firstDate.getMonth();
		}
		drawOneVehicleMileAgeMonth(vehicle_data,value);
		drawOneVehicleTimeMonth(vehicle_data,value);
		drawOneVehicleFrequencyMonth(vehicle_data,value);
	}
}

function drawOneVehicleMileAgeWeek(vehicle_data,weekNo){
	var v_mileage=[], v_date=[], v_labels=[], v_colors=[];
	
	firstDate = new Date(vehicle_data[0].tripStartTime);
	firstDate = firstDate.setWeek(weekNo);

	for(var i=0;i<7;i++){
		v_mileage[i]=0;
		v_date[i]=new Date(firstDate.getTime()+i*86400000);
	}

	for(var i=0;i<vehicle_data.length;i++){
		date=new Date(vehicle_data[i].tripStartTime);
		if(date.getWeek()==weekNo){
			v_mileage[date.getDay()]+=vehicle_data[i].mileage;
		}else if(date.getWeek()>weekNo){
			break;
		}
	}

	if(r.Mileage!=null&&r.Mileage!=0) r.Mileage.remove();
	if(bars.Mileage!=null&&bars.Mileage!=0) bars.Mileage.remove();
	//draw barchart
    var div=$("#Mileage");
	r.Mileage = Raphael("Mileage",div.width(),div.height());
	//Drawing chart title
	r.Mileage.text(div.width()/10,20,"Mileage").attr({"font-size":15,"font-weight":"bold","fill-opacity":0.75});

	var max_mileage=0;
	var avg_mileage=0;
	for(var i=0;i<v_date.length;i++){
		avg_mileage+=v_mileage[i];
		// g_mileage[i]=g_mileage[i];
		if(parseInt(v_mileage[i])>max_mileage){
			max_mileage=parseInt(v_mileage[i])+1;
		}
		d=v_date[i].getDate();
		m=v_date[i].getMonth();
		v_labels[i]=d+"/"+(m+1);
		v_colors[i]="#008cd2";
    }
    avg_mileage/=(v_date.length);
    /*for(var i=0;i<v_date.length;i++){
		if(v_mileage[i]>avg_mileage){
			v_colors[i]="#008cd2";	
		}else{
			v_colors[i]="#666666";
		}
	}*/
    
    //Adding max and min value for drawing gridlines
    v_mileage.push(0);
    v_mileage.push(maxValue(max_mileage));
    v_colors.push("transparent");
    v_colors.push("transparent");

    drawBarchart(v_mileage,v_labels,v_colors,avg_mileage,"Mileage");
};

function drawOneVehicleTimeWeek(vehicle_data,weekNo){
	var v_time=[], v_date=[], v_labels=[], v_colors=[];
	
	firstDate = new Date(vehicle_data[0].tripStartTime);
	firstDate = firstDate.setWeek(weekNo);

	for(var i=0;i<7;i++){
		v_time[i]=0;
		v_date[i]=new Date(firstDate.getTime()+i*86400000);
	}

	for(var i=0;i<vehicle_data.length;i++){
		date=new Date(vehicle_data[i].tripStartTime);
		if(date.getWeek()==weekNo){
			tripTime=(vehicle_data[i].tripEndTime-vehicle_data[i].tripStartTime)/3600000;
			v_time[date.getDay()]+=tripTime;
		}else if(date.getWeek()>weekNo){
			break;
		}
	}

	if(r.Time!=null&&r.Time!=0) r.Time.remove();
	if(bars.Time!=null&&bars.Time!=0) bars.Time.remove();
	//draw barchart
    var div=$("#Time");
	r.Time = Raphael("Time",div.width(),div.height());
	//Drawing chart title
	r.Time.text(div.width()/10,20,"Time").attr({"font-size":15,"font-weight":"bold","fill-opacity":0.75});

	var max_time=0;
	var avg_time=0;
	for(var i=0;i<v_date.length;i++){
		avg_time+=v_time[i];
		if(parseInt(v_time[i])>max_time){
			max_time=parseInt(v_time[i])+1;
		}
		d=v_date[i].getDate();
		m=v_date[i].getMonth();
		v_labels[i]=d+"/"+(m+1);
		v_colors.push("#c71a23");
    }
    avg_time/=(v_date.length);
    /*for(var i=0;i<v_date.length;i++){
		if(v_time[i]>avg_time){
			v_colors[i]="#c71a23";	
		}else{
			v_colors[i]="#666666";
		}
	}*/
    
    //Adding max and min value for drawing gridlines
    v_time.push(0);
    v_time.push(maxValue(max_time));
    v_colors.push("transparent");
    v_colors.push("transparent");

    drawBarchart(v_time,v_labels,v_colors,avg_time,"Time");
};

function drawOneVehicleFrequencyWeek(vehicle_data,weekNo){
	var v_frequency=[], v_date=[], v_labels=[], v_colors=[];
	
	firstDate = new Date(vehicle_data[0].tripStartTime);
	firstDate = firstDate.setWeek(weekNo);

	for(var i=0;i<7;i++){
		v_frequency[i]=0;
		v_date[i]=new Date(firstDate.getTime()+i*86400000);
	}

	for(var i=0;i<vehicle_data.length;i++){
		date=new Date(vehicle_data[i].tripStartTime);
		if(date.getWeek()==weekNo){
			v_frequency[date.getDay()]++;
		}else if(date.getWeek()>weekNo){
			break;
		}
	}

	if(r.Frequency!=null&&r.Frequency!=0) r.Frequency.remove();
	if(bars.Frequency!=null&&bars.Frequency!=0) bars.Frequency.remove();
	//draw barchart
    var div=$("#Frequency");
	r.Frequency = Raphael("Frequency",div.width(),div.height());
	//Drawing chart title
	r.Frequency.text(div.width()/10,20,"Trips").attr({"font-size":15,"font-weight":"bold","fill-opacity":0.75});

	var max_frequency=0;
	var avg_frequency=0;
	for(var i=0;i<v_date.length;i++){
		avg_frequency+=v_frequency[i];
		// g_mileage[i]=g_mileage[i];
		if(parseInt(v_frequency[i])>max_frequency){
			max_frequency=parseInt(v_frequency[i]);
		}
		d=v_date[i].getDate();
		m=v_date[i].getMonth();
		v_labels[i]=d+"/"+(m+1);
		v_colors.push("#02e04a");
    }
    avg_frequency/=(v_date.length);
    /*for(var i=0;i<v_date.length;i++){
		if(v_frequency[i]>avg_frequency){
			v_colors[i]="#02e04a";	
		}else{
			v_colors[i]="#666666";
		}
	}*/
    
    //Adding max and min value for drawing gridlines
    v_frequency.push(0);
    v_frequency.push(maxValue(max_frequency));
    v_colors.push("transparent");
    v_colors.push("transparent");

    drawBarchart(v_frequency,v_labels,v_colors,avg_frequency,"Frequency");
};

function drawOneVehicleMileAgeMonth(vehicle_data,monthNo){
	var v_mileage=[], v_date=[], v_labels=[], v_colors=[];
	
	firstDate = new Date(vehicle_data[0].tripStartTime);
	firstDate.setDate(1);
	firstDate.setMonth(monthNo);

	var tempDate=firstDate;
	var i=0;
	while(tempDate.getMonth()==monthNo){
		v_mileage[i]=0;
		tempDate=new Date(firstDate.getTime()+i*86400000);
		v_date[i]=tempDate;
		i++;
	}
	v_mileage.pop();
	v_date.pop();

	for(var i=0;i<vehicle_data.length;i++){
		date=new Date(vehicle_data[i].tripStartTime);
		if(date.getMonth()==monthNo){
			v_mileage[date.getDate()-1]+=vehicle_data[i].mileage;
		}else if(date.getMonth()>monthNo){
			break;
		}
	}

	if(r.Mileage!=null&&r.Mileage!=0) r.Mileage.remove();
	if(bars.Mileage!=null&&bars.Mileage!=0) bars.Mileage.remove();
	//draw barchart
    var div=$("#Mileage");
	r.Mileage = Raphael("Mileage",div.width(),div.height());
	//Drawing chart title
	r.Mileage.text(div.width()/10,20,"Mileage").attr({"font-size":15,"font-weight":"bold","fill-opacity":0.75});

	var max_mileage=0;
	var avg_mileage=0;
	for(var i=0;i<v_date.length;i++){
		avg_mileage+=v_mileage[i];
		// g_mileage[i]=g_mileage[i];
		if(parseInt(v_mileage[i])>max_mileage){
			max_mileage=parseInt(v_mileage[i])+1;
		}
		d=v_date[i].getDate();
		m=v_date[i].getMonth();
		v_labels[i]=d+"/"+(m+1);
		v_colors[i]="#008cd2";
    }
    avg_mileage/=(v_date.length);
    /*for(var i=0;i<v_date.length;i++){
		if(v_mileage[i]>avg_mileage){
			v_colors[i]="#008cd2";	
		}else{
			v_colors[i]="#666666";
		}
	}*/
    
    //Adding max and min value for drawing gridlines
    v_mileage.push(0);
    v_mileage.push(maxValue(max_mileage));
    v_colors.push("transparent");
    v_colors.push("transparent");

    drawBarchart(v_mileage,v_labels,v_colors,avg_mileage,"Mileage");
};

function drawOneVehicleTimeMonth(vehicle_data,monthNo){
	var v_time=[], v_date=[], v_labels=[], v_colors=[];
	
	firstDate = new Date(vehicle_data[0].tripStartTime);
	firstDate.setDate(1);
	firstDate.setMonth(monthNo);

	var tempDate=firstDate;
	var i=0;
	while(tempDate.getMonth()==monthNo){
		v_time[i]=0;
		tempDate=new Date(firstDate.getTime()+i*86400000);
		v_date[i]=tempDate;
		i++;
	}
	v_time.pop();
	v_date.pop();
	
	for(var i=0;i<vehicle_data.length;i++){
		date=new Date(vehicle_data[i].tripStartTime);
		if(date.getMonth()==monthNo){
			tripTime=(vehicle_data[i].tripEndTime-vehicle_data[i].tripStartTime)/3600000;
			v_time[date.getDate()-1]+=tripTime;
		}else if(date.getMonth()>monthNo){
			break;
		}
	}

	if(r.Time!=null&&r.Time!=0) r.Time.remove();
	if(bars.Time!=null&&bars.Time!=0) bars.Time.remove();
	//draw barchart
    var div=$("#Time");
	r.Time = Raphael("Time",div.width(),div.height());
	//Drawing chart title
	r.Time.text(div.width()/10,20,"Time").attr({"font-size":15,"font-weight":"bold","fill-opacity":0.75});

	var max_time=0;
	var avg_time=0;
	for(var i=0;i<v_date.length;i++){
		avg_time+=v_time[i];
		if(parseInt(v_time[i])>max_time){
			max_time=parseInt(v_time[i])+1;
		}
		d=v_date[i].getDate();
		m=v_date[i].getMonth();
		v_labels[i]=d+"/"+(m+1);
		v_colors[i]="#c71a23";
    }
    avg_time/=(v_date.length);
    /*for(var i=0;i<v_date.length;i++){
		if(v_time[i]>avg_time){
			v_colors[i]="#c71a23";	
		}else{
			v_colors[i]="#666666";
		}
	}*/
    
    //Adding max and min value for drawing gridlines
    v_time.push(0);
    v_time.push(maxValue(max_time));
    v_colors.push("transparent");
    v_colors.push("transparent");

    drawBarchart(v_time,v_labels,v_colors,avg_time,"Time");
};

function drawOneVehicleFrequencyMonth(vehicle_data,monthNo){
	var v_frequency=[], v_date=[], v_labels=[], v_colors=[];
	
	firstDate = new Date(vehicle_data[0].tripStartTime);
	firstDate.setDate(1);
	firstDate.setMonth(monthNo);

	var tempDate=firstDate;
	var i=0;
	while(tempDate.getMonth()==monthNo){
		v_frequency[i]=0;
		tempDate=new Date(firstDate.getTime()+i*86400000);
		v_date[i]=tempDate;
		i++;
	}
	v_frequency.pop();
	v_date.pop();

	for(var i=0;i<vehicle_data.length;i++){
		date=new Date(vehicle_data[i].tripStartTime);
		if(date.getMonth()==monthNo){
			v_frequency[date.getDate()-1]++;
		}else if(date.getMonth()>monthNo){
			break;
		}
	}

	if(r.Frequency!=null&&r.Frequency!=0) r.Frequency.remove();
	if(bars.Frequency!=null&&bars.Frequency!=0) bars.Frequency.remove();
	//draw barchart
    var div=$("#Frequency");
	r.Frequency = Raphael("Frequency",div.width(),div.height());
	//Drawing chart title
	r.Frequency.text(div.width()/10,20,"Trips").attr({"font-size":15,"font-weight":"bold","fill-opacity":0.75});

	var max_frequency=0;
	var avg_frequency=0;
	for(var i=0;i<v_date.length;i++){
		avg_frequency+=v_frequency[i];
		// g_mileage[i]=g_mileage[i];
		if(parseInt(v_frequency[i])>max_frequency){
			max_frequency=parseInt(v_frequency[i]);
		}
		d=v_date[i].getDate();
		m=v_date[i].getMonth();
		v_labels[i]=d+"/"+(m+1);
		v_colors[i]="#02e04a";
    }
    avg_frequency/=(v_date.length);
    /*for(var i=0;i<v_date.length;i++){
		if(v_frequency[i]>avg_frequency){
			v_colors[i]="#02e04a";	
		}else{
			v_colors[i]="#666666";
		}
	}*/
    
    //Adding max and min value for drawing gridlines
    v_frequency.push(0);
    v_frequency.push(maxValue(max_frequency));
    v_colors.push("transparent");
    v_colors.push("transparent");

    drawBarchart(v_frequency,v_labels,v_colors,avg_frequency,"Frequency");
};

function getAllVehiclesData(value){
	$.getJSON(createQuery(), function(data) {
		var vehicles=[],vehicles_name=[];
		var count=0;
		for(var i=0;i<data.length;i++){
			for(var k in trip_data[i]){
				vehicles_name[i] = k;
			}
			vehicles[i] = data[i][vehicles_name[i]];
			count+= (vehicles[i].length==0)?0:1;
		}
    	if(count!=0){
    		drawAllVehicles(data,value);
    		$("#saveAsCSV").hide();
            $("#saveAsPNG").show();
            /*$("#chart-option").show();*/
    	}else{
    		$("#Mileage").append('<p align="center">Vehicle data is not avaialble for this time period</p>');
    		$("#Time").append('<p align="center">Vehicle data is not avaialble for this time period</p>');
    		$("#Frequency").append('<p align="center">Vehicle data is not avaialble for this time period</p>');
            $("#saveAsCSV").hide();
            $("#saveAsPNG").hide();
            /*$("#chart-option").hide();*/
    	}
    });
};

function drawAllVehicles(trip_data,weekNo){
	var vehicles=[], vehicles_name=[];
	var firstDate = 0, lastDate = 0;

	for(var i=0;i<trip_data.length;i++){
		for( var k in trip_data[i]) vehicles_name[i]=k;
		vehicles[i]=trip_data[i][vehicles_name[i]];
    }
    
    drawAllVehiclesMileage(vehicles,vehicles_name);
    drawAllVehiclesTime(vehicles,vehicles_name);
    drawAllVehiclesFrequency(vehicles,vehicles_name);
};

function drawAllVehiclesMileage(vehicles,vehicles_name){
	var vehicles_mileage=[],vehicles_colors=[];

	if(r.Mileage!=null&&r.Mileage!=0) r.Mileage.remove();
	if(bars.Mileage!=null&&bars.Mileage!=0) bars.Mileage.remove();
	//draw barchart
    var div=$("#Mileage");
	r.Mileage = Raphael("Mileage",div.width(),div.height());
	//Drawing chart title
	r.Mileage.text(div.width()/10,20,"Mileage").attr({"font-size":15,"font-weight":"bold","fill-opacity":0.75});
	var max_mileage=0;
	var avg_mileage=0;
	for(var i=0;i<vehicles_name.length;i++){
		vehicles_mileage[i]=0;
		for(var j=0;j<vehicles[i].length;j++){
			vehicles_mileage[i]+=vehicles[i][j].mileage;
		}
		avg_mileage+=vehicles_mileage[i];
		if(max_mileage<vehicles_mileage[i]){
			max_mileage=parseInt(vehicles_mileage[i])+1;	
		} 
	}
	avg_mileage/=(vehicles_name.length);

	for(var i=0;i<vehicles_name.length;i++){
		if(vehicles_mileage[i]>avg_mileage){
			vehicles_colors[i]="#008cd2";	
		}else{
			vehicles_colors[i]="#666666";
		}
	}
	vehicles_mileage.push(0);
    vehicles_mileage.push(maxValue(max_mileage));
    vehicles_colors.push("transparent");
    vehicles_colors.push("transparent");

    drawBarchart(vehicles_mileage,vehicles_name,vehicles_colors,avg_mileage,"Mileage");
};

function drawAllVehiclesTime(vehicles,vehicles_name){
	var vehicles_time=[], vehicles_colors=[];

	if(r.Time!=null&&r.Time!=0) r.Time.remove();
	if(bars.Time!=null&&r.Time!=0) bars.Time.remove();
	//draw barchart
    var div=$("#Time");
	r.Time = Raphael("Time",div.width(),div.height());
	//Drawing chart title
	r.Time.text(div.width()/10,20,"Time").attr({"font-size":15,"font-weight":"bold","fill-opacity":0.75});
	var max_time=0;
	var avg_time=0;
	for(var i=0;i<vehicles_name.length;i++){
		vehicles_time[i]=0;
		for(var j=0;j<vehicles[i].length;j++){
			tripTime=(vehicles[i][j].tripEndTime-vehicles[i][j].tripStartTime)/3600000;
			vehicles_time[i]+=tripTime;
		}
		avg_time+=vehicles_time[i];
		if(max_time<vehicles_time[i]){
			max_time=parseInt(vehicles_time[i])+1;	
		} 
	}
	avg_time/=(vehicles_name.length);

	for(var i=0;i<vehicles_name.length;i++){
		if(vehicles_time[i]>avg_time){
			vehicles_colors[i]="#c71a23";	
		}else{
			vehicles_colors[i]="#666666";
		}
	}
	vehicles_time.push(0);
    vehicles_time.push(maxValue(max_time));
    vehicles_colors.push("transparent");
    vehicles_colors.push("transparent");

    drawBarchart(vehicles_time,vehicles_name,vehicles_colors,avg_time,"Time");
};

function drawAllVehiclesFrequency(vehicles,vehicles_name){
	var vehicles_frequency=[], vehicles_colors=[];

	if(r.Frequency!=null&&r.Frequency!=0) r.Frequency.remove();
	if(bars.Frequency!=null&&r.Frequency!=0) bars.Frequency.remove();
	//draw barchart
    var div=$("#Frequency");
	r.Frequency = Raphael("Frequency",div.width(),div.height());
	//Drawing chart title
	r.Frequency.text(div.width()/10,20,"Trips").attr({"font-size":15,"font-weight":"bold","fill-opacity":0.75});
	var max_frequency=0;
	var avg_frequency=0;
	for(var i=0;i<vehicles_name.length;i++){
		vehicles_frequency[i]=0;
		for(var j=0;j<vehicles[i].length;j++){
			vehicles_frequency[i]++;
		}
		avg_frequency+=vehicles_frequency[i];
		if(max_frequency<vehicles_frequency[i]){
			max_frequency=parseInt(vehicles_frequency[i]);	
		} 
	}

	avg_frequency/=(vehicles_name.length);
	for(var i=0;i<vehicles_name.length;i++){
		if(vehicles_frequency[i]>avg_frequency){
			vehicles_colors[i]="#02e04a";	
		}else{
			vehicles_colors[i]="#666666";
		}
	}
	vehicles_frequency.push(0);
    vehicles_frequency.push(maxValue(max_frequency));
    vehicles_colors.push("transparent");
    vehicles_colors.push("transparent");

    drawBarchart(vehicles_frequency,vehicles_name,vehicles_colors,avg_frequency,"Frequency");
};

function drawBarchart(g_value,g_label,colors,avg,title){	
	var div=$("#"+title);
	if(bars[title]!=null&&bars[title]!=0){
		bars[title].remove();
		for(var i=0;i<6;i++){
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
	y5=(y0-y4)*(1-avg/g_value[g_value.length-1])+y4;
	//Getting x value of the last and second last bar and preventing them from displaying on the chart
    x_max=temp_m.bars[g_value.length-1].x;
    x_min=temp_m.bars[g_value.length-2].x;

	b_path[title][0]=r[title].path("M"+(bar_attr.x)+" "+y0+"l"+(x_max-70)+" 0").attr({"stroke-opacity":1});
	b_path[title][1]=r[title].path("M"+(bar_attr.x)+" "+y1+"l"+(x_max-70)+" 0").attr({"stroke-opacity":1,"stroke":"#c0c0c0"});
	b_path[title][2]=r[title].path("M"+(bar_attr.x)+" "+y2+"l"+(x_max-70)+" 0").attr({"stroke-opacity":1,"stroke":"#c0c0c0"});
	b_path[title][3]=r[title].path("M"+(bar_attr.x)+" "+y3+"l"+(x_max-70)+" 0").attr({"stroke-opacity":1,"stroke":"#c0c0c0"});
	b_path[title][4]=r[title].path("M"+(bar_attr.x)+" "+y4+"l"+(x_max-70)+" 0").attr({"stroke-opacity":1,"stroke":"#c0c0c0"});
	b_path[title][5]=r[title].path("M"+(bar_attr.x)+" "+y5+"l"+(x_max-70)+" 0").attr({"stroke-opacity":0,"stroke-dasharray":"-","stroke-width":2});

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
	if(title=="Time"){
		avg_minute=Math.round((avg-Math.floor(avg))*60);
    	avg_time=Math.floor(avg)+"h "+avg_minute+"m";
		b_text[title][5] = r[title].text(div.width()-60,20,"Average: "+avg_time).attr({"font-size":14,"fill":"black","opacity":0,"font-weight":"bold","text-anchor":"end"});
	}else if(title=="Mileage"){
		b_text[title][5] = r[title].text(div.width()-60,20,"Average: "+avg.toFixed(2)+"km").attr({"font-size":14,"black":"blue","opacity":0,"font-weight":"bold","text-anchor":"end"});	
	}else if(title=="Frequency"){
		b_text[title][5] = r[title].text(div.width()-60,20,"Average: "+avg.toFixed(2)).attr({"font-size":14,"black":"blue","opacity":0,"font-weight":"bold","text-anchor":"end"});
	}
	//Animate gridlines and label
	for(var i=0;i<5;i++){
		b_text[title][i].animate({"opacity":1},400);
	}
	b_text[title][i].animate({"opacity":0.8},400);
	b_path[title][5].animate({"stroke-opacity":0.7},400);

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
    	if(title=="Mileage"){
    		if(this.bar.x!=x_min&&this.bar.x!=x_max){
    			this.popups.push(r[title].popup(this.bar.x, 5*this.bar.y-4*y0, title+": "+this.bar.value.toFixed(2)+" km" || "0","right").insertBefore(this));	
    		}
    	}else if(title=="Time"){
    		if(this.bar.x!=x_min&&this.bar.x!=x_max){
    			minute=Math.round((this.bar.value-Math.floor(this.bar.value))*60);
    			time_popup=Math.floor(this.bar.value)+"h "+minute+"m";
    			this.popups.push(r[title].popup(this.bar.x, 5*this.bar.y-4*y0, title+": "+time_popup || "0h","right").insertBefore(this));	
    		}
    	}else if(title=="Frequency"){
    		if(this.bar.x!=x_min&&this.bar.x!=x_max){
    			this.popups.push(r[title].popup(this.bar.x, 5*this.bar.y-4*y0, "Trips"+": "+this.bar.value || "0","right").insertBefore(this));	
    		}
    	}
    },function(){
    	this.popups && this.popups.remove();
    });
    
};

function clearBarChart(){
	if(bars!=null){
		bars.remove();
	}
	if(r!=null){
		r.remove();
	}
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

function saveImage(chartOption){
	var svg = r[chartOption].toSVG();

	var fix = svgfix(svg);
	var canvasID = "myCanvas";
	//var canvas = document.createElement('canvas');
	
	var canvas = document.getElementById(canvasID), ctx = canvas.getContext("2d");
	// draw to canvas...	
	
	canvg(document.getElementById(canvasID),fix,{renderCallback:function(){
		//Canvas2Image.saveAsPNG(document.getElementById(canvasID), "test.png");
		canvas.toBlob(function(blob) {
		    saveAs(blob, chartOption + ".png");
		});
	}});
};

function JSON2CSV(objArray) {
    var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;

    var str = '';
    var line = '';

    var head = array[0];
    for (var index in array[0]) {
        var value = index + "";
        line += '"' + value.replace(/"/g, '""') + '",';
    }
        line = line.slice(0, -1);
        str += line + '\r\n';    

    for (var i = 0; i < array.length; i++) {
        var line = '';
    	for (var index in array[i]) {
        	var value = array[i][index] + "";
        	line += '"' + value.replace(/"/g, '""') + '",';
    	}       
        line = line.slice(0, -1);
        str += line + '\r\n';
    }
    return str;
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
	
	// to ensure query date is updated.
	wm = value;
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
    	wm = value+1;
    	selectVehicleOption();
    };
    function f_click_week_prev(){
    	populateDurationOption("weekly",value-1);
    	wm = value-1;
    	selectVehicleOption();
    };
    function f_click_month_next(){
    	if(value==11){
    		populateDurationOption("monthly",0);
    		wm = 0;
    	}else{
    		populateDurationOption("monthly",value+1);
    		wm = value + 1;
    	}
    	selectVehicleOption();	
    }
    function f_click_month_prev(){
    	if(value==0){
    		populateDurationOption("monthly",11);
    		wm = 11;
    	}else{
    		populateDurationOption("monthly",value-1);
    		wm = value -1;
    	}
    	selectVehicleOption();
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