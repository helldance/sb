/**
 * ElderTrackerMsgHeader.java
 * xinhong
 * Dec 19, 2012
 */
package com.coordsafe.gateway.entity;

/**
 * @author yangwei
 *
 */
public class SzMsgHeader {
	public String start;
	public String runningNo; // 12 bytes
	public String cmd;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SzMsgHeader [start=" + start + ", runningNo=" + runningNo
				+ ", cmd=" + cmd + "]";
	}
}
