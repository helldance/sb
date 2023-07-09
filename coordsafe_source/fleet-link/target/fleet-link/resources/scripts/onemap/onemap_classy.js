//constructor for marker which takes 3 arguments: (E,N,options)
//E: Easting & N: Northing of a point accordingt to SVY21 system
//options is a JSON object which contains url, width and height of the marker
function Marker(E,N, options){
	this.type = "marker";
	var point = new esri.geometry.Point(E,N,new esri.SpatialReference({ wkid:3414}));
	var pointSymbol = new esri.symbol.PictureMarkerSymbol(options.url,options.width,options.height);
	this.graphic = new esri.Graphic(point, pointSymbol);
	this.map = null;
};

Marker.prototype.setMap = function(map) {
	this.map = map;
	if(map) map.graphics.add(this.graphic);
};

//Return position of a marker as esri.Point format
Marker.prototype.getPosition = function(){
	return this.graphic.geometry;
};

//set position for a marker by inputting the easting and northing of a point (SVY21 system)
Marker.prototype.setPosition = function(E,N){
	var point = this.graphic.geometry.update(E,N);
	this.graphic.setGeometry(point);		
};

Marker.prototype.setInfoTemplate = function(infoTemplate){
	this.graphic.setInfoTemplate(infoTemplate);
}

Marker.prototype.setOptions = function(options){
	var pointSymbol = this.graphic.symbol;
	if(options.url) pointSymbol.setUrl(options.url);
	if(options.width) pointSymbol.setWidth(options.width);
	if(options.height) pointSymbol.setHeight(options.height);
	this.graphic.setSymbol(pointSymbol);
};

Marker.prototype.show = function(){
	this.graphic.show();
};

Marker.prototype.hide = function(){
	this.graphic.hide();
};

// Constructor for polyline which only takes 2 argument: (path,options)
// path: an array of coordinates [easting, northing] which are in SVY21 system. This array represents the path of polyline
// options: a JSON object which contains lineStyle, lineColor and lineWidth of the polyline
// lineStyle and lineColor will follow ArcGIS API specifications
function Polyline(path,options){
	this.type = "polyline";
	var polyline = new esri.geometry.Polyline({"paths":[path],"spatialReference":{"wkid":3414}});
	var lineSymbol = new esri.symbol.SimpleLineSymbol(options.lineStyle,options.lineColor,options.lineWidth);
	this.graphic = new esri.Graphic(polyline, lineSymbol);
	this.map = null;
};

Polyline.prototype.setMap = function(map){
	this.map = map;
	if(map) map.graphics.add(this.graphic);
};

Polyline.prototype.setId = function(id){
	this.graphic.setAttributes({"id": id});
}

//insert a point at a specific index. If there is no index, insert that point at the end od the path
Polyline.prototype.insertPoint = function(N,E,index){
	var addingPoint = new esri.geometry.Point(E,N,new esri.SpatialReference({ wkid:3414}));
	var polyline = this.graphic.geometry;
	if(index){
		polyline.insertPoint(0,index,addingPoint);
	}else{
		polyline.insertPoint(0,polyline.paths[0].length,addingPoint);
	}
	this.graphic.setGeometry(polyline);
};

//remove a point at a specific index
Polyline.prototype.removePoint = function(index){
	if(index){
		var polyline = this.graphic.geometry;
		polyline.removePoint(0,index);
		this.graphic.setGeometry(polyline);
	}
};

Polyline.prototype.getPath = function(){
	return this.graphic.geometry.paths[0];
};

// path: an array of coordinates [easting, northing] which are in SVY21 system. This array represents the path of polyline
Polyline.prototype.setPath = function(path){
	var polyline = this.graphic.geometry;
	polyline.removePath(0);
	polyline.addPath(path);
	this.graphic.setGeometry(polyline);
};

// options: a JSON object which contains style, color and width of the polyline
// style and color will follow ArcGIS API specifications
Polyline.prototype.setOptions = function(options){
	var lineSymbol = this.graphic.symbol;
	if(options.lineColor) lineSymbol.setColor(options.lineColor);
	if(options.lineStyle) lineSymbol.setStyle(options.lineStyle);
	if(options.lineWidth) lineSymbol.setWidth(options.lineWidth);
	this.graphic.setSymbol(lineSymbol);
};

Polyline.prototype.show = function(){
	this.graphic.show();
};

Polyline.prototype.hide = function(){
	this.graphic.hide();
};

// Constructor for polygon which only takes 2 argument: (rings,options)
// rings: array of rings where each ring is an array of coordinates [easting, northing] which are in SVY21 system. This array represents all the rings of polygon
// options: a JSON object which contains fillStyle, fillColor, lineStyle, lineColor, lineWidth of the polygon
// fillStyle, fillColor, lineStyle, lineColor will follow ArcGIS API specifications
function Polygon(rings, options){
	this.type = "polyline";
	var polygon = new esri.geometry.Polygon({"rings":rings,"spatialReference":{"wkid":3414}});
	var lineSymbol = new esri.symbol.SimpleLineSymbol(options.lineStyle,options.lineColor,options.lineWidth);
	var fillSymbol = new esri.symbol.SimpleFillSymbol(options.fillStyle, lineSymbol, options.fillColor);
	this.graphic = new esri.Graphic(polygon, fillSymbol);
	this.map = null;
};

Polygon.prototype.setMap = function(map){
	this.map = map;
	if(map) map.graphics.add(this.graphic);
};

//insert a point at a specific index of a specific ring.
//If there is no ringIndex, point will be inserted at the first ring
//If there is no pointIndex, point will be inserted at the end of the ring
Polygon.prototype.insertPoint = function(N,E,ringIndex, pointIndex){
	var addingPoint = new esri.geometry.Point(E,N,new esri.SpatialReference({ wkid:3414}));
	var polygon = this.graphic.geometry;
	if(ringIndex){
		if(pointIndex){
			polygon.insertPoint(ringIndex,pointIndex,addingPoint);
		}else{
			polygon.insertPoint(ringIndex,polygon.rings[ringIndex].length,addingPoint);
		}
	}else{
		if(pointIndex){
			polygon.insertPoint(0,pointIndex,addingPoint);
		}else{
			polygon.insertPoint(0,polygon.rings[ringIndex].length,addingPoint);
		}
	}
	this.graphic.setGeometry(polygon);
};

//remove a point at a specific index of a specifix ring
Polygon.prototype.removePoint = function(ringIndex, pointIndex){
	if(ringIndex && pointIndex){
		var polygon = this.graphic.geometry;
		polygon.removePoint(0,index);
		this.graphic.setGeometry(polygon);
	}
};

Polygon.prototype.getRings = function(){
	return this.graphic.geometry.rings;
};

// ring: an array of coordinates [easting, northing] which are in SVY21 system. This array represents the ring of polygon
Polygon.prototype.addRing = function(ring){
	var polygon = this.graphic.geometry;
	polygon.addRing(ring);
	this.graphic.setGeometry(polygon);
};

Polygon.prototype.removeRing = function(ringIndex){
	var polygon = this.graphic.geometry;
	polygon.removeRing(ring);
	this.graphic.setGeometry(polygon);
};

// options: a JSON object which contains fillStyle, fillColor, lineStyle, lineColor, lineWidth of the polygon
// fillStyle, fillColor, lineStyle, lineColor will follow ArcGIS API specifications
Polygon.prototype.setOptions = function(options){
	var fillSymbol = this.graphic.symbol;
	var outline = fillSymbol.outline;

	if(options.fillColor) fillSymbol.setColor(options.fillColor);
	if(options.fillStyle) fillSymbol.setStyle(options.fillStyle);
	if(options.lineStyle) outline.setStyle(options.lineStyle);
	if(options.lineColor) outline.setColor(options.lineColor);
	if(options.lineWidth) outline.setWidth(options.lineWidth);

	fillSymbol.setOutline(outline);
	this.graphic.setSymbol(fillSymbol);
};

Polygon.prototype.show = function(){
	this.graphic.show();
};

Polygon.prototype.hide = function(){
	this.graphic.hide();
};