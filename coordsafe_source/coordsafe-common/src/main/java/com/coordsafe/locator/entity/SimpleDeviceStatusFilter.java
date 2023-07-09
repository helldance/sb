/**
 * SimpleDeviceStatusFilter.java
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
		"imei",
		"isGsmOn",
		"networkAvailability",
		"ip",
		"batteryLeft"
})
public interface SimpleDeviceStatusFilter {

}
