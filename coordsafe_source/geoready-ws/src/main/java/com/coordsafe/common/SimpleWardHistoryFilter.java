package com.coordsafe.common;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Yang Wei
 * @Date Jan 20, 2014
 */
@JsonIgnoreProperties(value=
{	"accuracy", 
	"bearing", 
	"distance", 
	"altitude",
	"speed",
	"vehicleId",
	"wardId"
}) 
public interface SimpleWardHistoryFilter {

}
