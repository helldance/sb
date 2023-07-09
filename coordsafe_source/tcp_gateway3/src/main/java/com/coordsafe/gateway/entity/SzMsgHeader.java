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

//For SafeLink device of ShenZhen; 
public class SzMsgHeader {
	public String start;
	public String producer;
	public String runningNo; // 10 bytes
	public String cmdType;//V1 or V4
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
