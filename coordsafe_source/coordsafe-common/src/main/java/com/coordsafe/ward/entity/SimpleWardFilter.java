package com.coordsafe.ward.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Yang Wei
 * @Date Dec 30, 2013
 */
@JsonIgnoreProperties(value=
{	"locator", 
	"create", 
	"birthDate",
	"gender",
	"email",
	"nric",
	"notificationDate",
	"fenceStatus",
	"notified",
	"wardEvents", 
	"guardians",
	"geofences", 
	"panicAlarms"}) 
public interface SimpleWardFilter {

}
