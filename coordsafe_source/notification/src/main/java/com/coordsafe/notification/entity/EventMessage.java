package com.coordsafe.notification.entity;

import com.coordsafe.event.entity.GenericEvent;

public class EventMessage implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GenericEvent enven;
	private AbstractNotificationSetting ns;
	
	public EventMessage(GenericEvent enven, AbstractNotificationSetting ns) {
		super();
		this.enven = enven;
		this.ns = ns;
	}
	public GenericEvent getEnven() {
		return enven;
	}

	public void setEnven(GenericEvent enven) {
		this.enven = enven;
	}
	public AbstractNotificationSetting getNs() {
		return ns;
	}
	public void setNs(AbstractNotificationSetting ns) {
		this.ns = ns;
	}
	
}
