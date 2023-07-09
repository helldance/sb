/**
 * SimpleGpsLocationFilter.java
 * May 9, 2013
 * Yang Wei
 */
package com.coordsafe.locator.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Yang Wei
 *
 */
@JsonIgnoreProperties(value=
{
		"time",
		"accuracy",
		"altitude",
		"bearing",
		"distance",
		"hasAccuracy",
		"hasAltitude",
		"hasBearing",
		"hasSpeed",
		"initialBearing"
})
public interface SimpleGpsLocationFilter {

}
