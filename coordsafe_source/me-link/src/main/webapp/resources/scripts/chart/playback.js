var r,lines;
var tracking_marker= new google.maps.Marker({
    position:new google.maps.LatLng(1.312177,103.796241),
    draggable:true,
    visible:false
});

function playback(data,map,option) {
    if(!(typeof lines=="undefined")){
    	lines.remove();
    	r.remove();
    }
    var div=$("#line-chart");
    r = Raphael("line-chart",div.width(),div.height());
    var txt_attr= {font: "16px 'Fontin Sans', Fontin-Sans, sans-serif"};
	var txt_speed ={font: "14px 'Fontin Sans', Fontin-Sans, sans-serif", fill:"#B0171F" };
	var txt_altitude ={font: "14px 'Fontin Sans', Fontin-Sans, sans-serif", fill:"#4682B4" };

	var x = [], y = [], y1= [], y2 = [],points=[];

    var lng_MAX=-180,lng_MIN=180,lat_MAX=-90,lat_MIN=90;
    
	for (var i = 0; i <data.length; i++) {
        x[i] = data[i].location_time;
        y[i] = data[i].location_time/1e12;
        y1[i] = (data[i].speed*1.2852).toFixed(2);
        y2[i] = data[i].altitude.toFixed(2);
        
        if(data[i].lng>lng_MAX) lng_MAX=data[i].lng;
        if(data[i].lng<lng_MIN) lng_MIN=data[i].lng;
        if(data[i].lat>lat_MAX) lat_MAX=data[i].lat;
        if(data[i].lat<lng_MIN) lat_MIN=data[i].lat;
	}

    points.push(new google.maps.LatLng(lat_MIN,lng_MIN));
    points.push(new google.maps.LatLng(lat_MAX,lng_MAX));    

    //r.text(div.width()*2/5,20, "Trip "+(option+1)).attr(txt_attr);
    r.text(div.width()*1/8-50,20, "-- Speed").attr(txt_speed);
    r.text(div.width()*1/8+50,20,"-- Altitude").attr(txt_altitude);

    //r.path("M"+(div.width()*3/4-80)+" 30l60 0").attr({"stroke-width":3,stroke:"#B0171F"});
    //r.path("M"+(div.width()*3/4+20)+" 30l60 0").attr({"stroke-width":3,stroke:"#4682B4"});

    div.css('visibility', 'visible');

    var line_attr={"x":20,"y":30,"width":div.width()-20,"height":div.height()-50};

    lines = r.linechart(line_attr.x, line_attr.y, line_attr.width, line_attr.height, [x,x,x], [y,y1,y2], { nostroke: false, axis: "0 0 1 1", symbol: "", colors:["transparent","#B0171F","#4682B4"] });

    div.css('visibility', 'visible');
    //Draw a moving line
    var movingLine=r.path("M"+line_attr.x+" "+line_attr.y+" l 0 "+line_attr.height+"");
    movingLine.hide();

    //Display information when the mouse hovers the line chart
    tracking_marker.setVisible(false);//Hide previous marker
    tracking_marker.setMap(map);

    setMapZoomCenter(map,points);        
    
    lines.hoverColumn(function () {
        this.popups = r.set();
        var t=this.x-30; //location for transformation
        movingLine.show();
        var _transformedPath = Raphael.transformPath("M"+(line_attr.x+10)+" "+(line_attr.y+10)+" l 0 "+(line_attr.height-20)+"", 'T'+t+',0');

        movingLine.animate({path: _transformedPath});

        if(y.indexOf(this.values[0])!=-1){
            //Change position the tracking_marker on the map
            var i=y.indexOf(this.values[0]);
            pos=new google.maps.LatLng(data[i].lat,data[i].lng);
            tracking_marker.setPosition(pos);
            tracking_marker.setVisible(true);
            map.setCenter(pos);

            //Draw the info window
            if(this.x>line_attr.width/2+line_attr.x){
                var time1=new Date(data[i].location_time);
                this.popups.push(r.popup(this.x, line_attr.height/2+20,""+dateFormat(time1,"dd/mm/yyyy, HH:MM")+"\nSpeed: "+((data[i].speed*1.2852).toFixed(2))+" km/h\n Altitude: "+(data[i].altitude.toFixed(2))+" m", 'left', 20).insertBefore(this));
            }else{
                var time2=new Date(data[i].location_time);
            	this.popups.push(r.popup(this.x, line_attr.height/2+20,""+dateFormat(time2,"dd/mm/yyyy, HH:MM")+"\nSpeed: "+((data[i].speed*1.2852).toFixed(2))+" km/h\n Altitude: "+(data[i].altitude.toFixed(2))+" m", 'right', 20).insertBefore(this));
            }
			this.popups.attr("opacity",0.8);
		}
	}, function () {
        this.popups && this.popups.remove();
        movingLine.hide();
    });

    var xAxisItems = lines.axis[0].text.items;
    for(var i=0, ii=xAxisItems.length;i<ii;i++){
        var date= new Date(parseInt(xAxisItems[i].attr("text")));
        xAxisItems[i].attr("text", dateFormat(date, "dd/mm, HH:MM"));
    }
    lines.symbols.attr({ r: 6 });
};

function clearChart(){
    $("#line-chart").css("visibility","hidden");
    tracking_marker.setVisible(false);
    if(!(typeof r=="undefined")){
        r.remove();    
    }
    if(!(typeof lines=="undefined")){
        lines.clear();
    }
};

function getColors(num){
    var colors=[];
    var golden_ratio=0.618033988749895;
    h=0.151;
    for(var i=0;i<num;i++){
        h+=golden_ratio;
        h%=1;
        var hex= HSVtoHEX(h,0.99,0.90);
        colors.push(hex);
    }
    return colors;
};

function HSVtoHEX(h,s,v){
    //hsv values=0-1; rgb values=0-255
    var h_i = Math.floor(h*6),            
        f   = h*6 - h_i,
        p   = v * (1-s),
        q   = v * (1-f*s),
        t   = v * (1-(1-f)*s),
        r   = 255,
        g   = 255,
        b   = 255;
    switch(h_i) {
        case 0: r = v, g = t, b = p;    break;
        case 1: r = q, g = v, b = p;    break;
        case 2: r = p, g = v, b = t;    break;
        case 3: r = p, g = q, b = v;    break;
        case 4: r = t, g = p, b = v;    break;
        case 5: r = v, g = p, b = q;    break;
    }
    return '#' + toHex(r) +""+ toHex(g) +""+ toHex(b);
}

function toHex(dec){
    if(Math.floor(dec*256)<16){
        return "0"+Math.floor(dec*256).toString(16);
    }else{
        return Math.floor(dec*256).toString(16);        
    }
};

function createPolylineEvent(polyline,i){
    google.maps.event.addListener(polyline[i], 'click', function(event) {
        drawChart(i);
        //change colors for other polilines
        changePolylineColor(polyline,i);
        populateHistory(i);
    });
    
    // highlight trip when mouseover
    google.maps.event.addListener(polyline[i], 'mouseover', function() {
    	polyline[i].setOptions({
    		strokeWeight:12
    	}); 
    });
    	
    // reset highlight
	google.maps.event.addListener(polyline[i], 'mouseout', function() {
		polyline[i].setOptions({
    		strokeWeight:6
    	}); 
    });
};

function changePolylineColor(polyline,i){
    for(var j=0;j<polyline.length;j++){
        if(j!=i){
            polyline[j].setOptions({strokeOpacity:0.6});
        }else{
            polyline[j].setOptions({strokeOpacity:1});
        }
    }
};

//Set zoom and center for map
//Input map and arrays of points which are to determine bound
function setMapZoomCenter(map,points){
    var bounds=new google.maps.LatLngBounds();
    for(var i=0;i<points.length;i++){
        bounds.extend(points[i]);
    }
    map.fitBounds(bounds);
    map.setCenter(bounds.getCenter());
};