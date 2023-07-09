package com.coordsafe.mqtt.publisher;

/**
 * @author Yang Wei
 * @Date Dec 14, 2013
 */
public class VehicleEventPublisher extends MqttPublisher {
	private static VehicleEventPublisher vePublisher;
	
	private VehicleEventPublisher () {}
	
	public static VehicleEventPublisher getPublisher (){
		if (vePublisher == null){
			vePublisher = new VehicleEventPublisher();
		}
		
		return vePublisher;
	}
}
