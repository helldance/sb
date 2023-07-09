/**
 * SimpleLocatorFilter.java
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
	{"location", 
		"create", 
		"status", 
		"create",
		"assignedTo",
		"id",
		"type",
		"status",
		"madeBy",
		"madeDate",
		"model",
		"ownerId"}) 
public interface SimpleLocatorFilter {

}
