package com.coordsafe.tcpgateway.locator;

import java.util.Date;

import com.coordsafe.tcpgateway.locator.MessageHeader;
import com.coordsafe.tcpgateway.util.CommonConstants;

public class LocatorMsgHeader extends MessageHeader {

	private String imeiCode;

	public LocatorMsgHeader() {
		this.dirIndicator = CommonConstants.DIR_L2S;
	}

	/**
	 * @return the imeiCode
	 */
	public String getImeiCode() {
		return imeiCode;
	}

	/**
	 * @param imeiCode
	 *            the imeiCode to set
	 */
	public void setImeiCode(String imeiCode) {
		this.imeiCode = imeiCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LocatorMsgHeader [imeiCode=" + imeiCode + ", serviceId="
				+ serviceId + ", dirIndicator=" + dirIndicator + ", msgTypeId="
				+ msgTypeId + ", serverTime=" + new Date(serverTime) + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((imeiCode == null) ? 0 : imeiCode.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof LocatorMsgHeader)) {
			return false;
		}
		LocatorMsgHeader other = (LocatorMsgHeader) obj;
		if (imeiCode == null) {
			if (other.imeiCode != null) {
				return false;
			}
		} else if (!imeiCode.equals(other.imeiCode)) {
			return false;
		}
		return true;
	}

}
