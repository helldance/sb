var centerPoint="20860.597,32718.861";
var levelNumber=1;
var OneMap = new GetOneMap('map','SM',{level:levelNumber,center:centerPoint});
var map;
var moving_marker, moving_point;
var r,lines;
var load_data = {type:"Polygon",coordinates:[[103.759391773492,1.36835440189283],[103.740165699273,1.33231552437556],[103.833549488336,1.32476445418671],[103.873031605035,1.36114666925072],[103.808143604547,1.38311302483772],[103.763854969293,1.40576561603765],[103.852775562555,1.32030244734016],[103.759391773492,1.36835440189283]]};

function initializeMap(){
    map = OneMap.map;
    //map.hideZoomSlider();
    
    //var playbackData = getData();
    //addMarker();
    //addPolyline(playbackData);
    //drawLineChart(playbackData);
    //drawPolygon();
    console.log("map loaded? " + map.loaded);
    
    // by now map is loaded already
    dojo.connect(map.graphics, "onClick", myGraphicsClickHandler);
};

function myGraphicsClickHandler(evt) {
	var gra = evt.graphic;
	
	  console.log(gra.geometry);
	  
	  var playbackData =  tripData[gra.attributes.id].history;
	  
	  drawLineChart(playbackData);
}

function addPolyline(data){
    var path = [];
    var cv = new SVY21();
    for(var i=0, l=data.length; i<l; i++){
        var svy21_result = cv.computeSVY21(data[i].lat,data[i].lng);
        path.push([svy21_result.E,svy21_result.N]);
    }
    var polyOptions = {"lineStyle":esri.symbol.SimpleLineSymbol.STYLE_SOLID, "lineColor": new dojo.Color([51,102,153]), "lineWidth": 4};
    var poly = new Polyline(path,polyOptions);
    poly.setMap(map);    
};

function addMarker(){
    var lat = 1.320989;
    var lng = 103.882942;
    var cv = new SVY21();//conversion helper
    var svy21_result = cv.computeSVY21(lat,lng);
    var url = "images/truck_42.png";
    var markerOptions = {"url":url,"width":42,"height":34};
    var testing_marker = new Marker(svy21_result.E,svy21_result.N,markerOptions);
    testing_marker.setMap(map);

    var title = 'This is an info template';
    var content = "This is the content of the info template.";

    var infoTemplate = new esri.InfoTemplate(title,content);
    testing_marker.setInfoTemplate(infoTemplate);
};

//draw polygon from geojson format
function drawPolygon(){
    if(load_data.type=="Polygon"){
        var cv = new SVY21();
        var coordinates = [];
        for(var i=0, l=load_data.coordinates.length; i<l; i++){
            var svy21_result = cv.computeSVY21(load_data.coordinates[i][1],load_data.coordinates[i][0]);
            coordinates.push([svy21_result.E,svy21_result.N]);
        }
        var polygonOptions= {
            "lineStyle": esri.symbol.SimpleLineSymbol.STYLE_SOLID, 
            "lineColor": new dojo.Color([51,102,153]), 
            "lineWidth": 4, 
            "fillColor": new dojo.Color([51,102,153,0.25]),
            "fillStyle": esri.symbol.SimpleFillSymbol.STYLE_SOLID
        };
        var polygon = new Polygon([coordinates],polygonOptions);
        polygon.setMap(map);
    }
};

function drawLineChart(data){
    var cv = new SVY21();
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

    r.text(div.width()*1/8-50,20, "-- Speed").attr(txt_speed);
    r.text(div.width()*1/8+50,20,"-- Altitude").attr(txt_altitude);

    var line_attr={"x":20,"y":30,"width":div.width()-20,"height":div.height()-50};

    lines = r.linechart(line_attr.x, line_attr.y, line_attr.width, line_attr.height, [x,x,x], [y,y1,y2], { nostroke: false, axis: "0 0 1 1", symbol: "", colors:["transparent","#B0171F","#4682B4"] });

    div.css('visibility', 'visible');
    //Draw a moving line
    var movingLine=r.path("M"+line_attr.x+" "+line_attr.y+" l 0 "+line_attr.height+"");
    movingLine.hide();

    //Display information when the mouse hovers the line chart
    //Add marker to the map
    //var url = "images/truck_42.png";
    var markerOptions = {"url":"/fleet-link/resources/images/pin_red.png","width":32,"height":32};
    moving_marker = new Marker(23860.597,32718.861,markerOptions);
    moving_marker.setMap(map);
    moving_marker.hide();
    
    lines.hoverColumn(function () {
        this.popups = r.set();
        var t=this.x-30; //location for transformation
        movingLine.show();
        var _transformedPath = Raphael.transformPath("M"+(line_attr.x+10)+" "+(line_attr.y+10)+" l 0 "+(line_attr.height-20)+"", 'T'+t+',0');
        movingLine.animate({path: _transformedPath});

        //making moving_marker visible
        moving_marker.show();
        
        if(y.indexOf(this.values[0])!=-1){
            //Change position the tracking_marker on the map            
            var i=y.indexOf(this.values[0]);            
            var svy21_result = cv.computeSVY21(data[i].lat,data[i].lng);
            moving_marker.setPosition(svy21_result.E,svy21_result.N);
        }
        //Draw the info window of linechart
        if(this.x>line_attr.width/2+line_attr.x){
            var time1=new Date(data[i].location_time);
            this.popups.push(r.popup(this.x, line_attr.height/2+20,""+dateFormat(time1,"dd/mm/yyyy, HH:MM")+"\nSpeed: "+((data[i].speed*1.2852).toFixed(2))+" km/h\n Altitude: "+(data[i].altitude.toFixed(2))+" m", 'left', 20).insertBefore(this));
        }else{
            var time2=new Date(data[i].location_time);
            this.popups.push(r.popup(this.x, line_attr.height/2+20,""+dateFormat(time2,"dd/mm/yyyy, HH:MM")+"\nSpeed: "+((data[i].speed*1.2852).toFixed(2))+" km/h\n Altitude: "+(data[i].altitude.toFixed(2))+" m", 'right', 20).insertBefore(this));
        }
        this.popups.attr("opacity",0.8);
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