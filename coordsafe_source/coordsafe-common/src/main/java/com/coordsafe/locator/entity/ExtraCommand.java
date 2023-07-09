/**
 * 
 */
package com.coordsafe.locator.entity;

/**
 * @author Yang Wei
 *
 */
public class ExtraCommand {
	public boolean powerInternal;
	public boolean stopSignal;
	public boolean panicSignal;
	public boolean tripStartSignal;
	/**
	 * 
	 */
	public ExtraCommand() {
		super();
		// TODO Auto-generated constructor stub
		
		this.powerInternal = this.stopSignal = this.panicSignal = tripStartSignal = false;
	}
}
