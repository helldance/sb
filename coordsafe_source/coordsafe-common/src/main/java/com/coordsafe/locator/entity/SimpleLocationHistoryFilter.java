/**
 * SimpleLocatorHistoryFilter.java
 * Jun 5, 2013
 * Yang Wei
 */
package com.coordsafe.locator.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Yang Wei
 *
 */
@JsonIgnoreProperties(value=
{	"accuracy", 
	"bearing", 
	"distance" 
}) 
public interface SimpleLocationHistoryFilter {

}
