package com.coordsafe.tcpgateway.locator;

import com.coordsafe.tcpgateway.util.CommonConstants;

public class MessageHeader {
	protected byte serviceId = CommonConstants.ELDER_SERVICE_ID;
	protected byte dirIndicator;
	protected byte msgTypeId;
	protected byte serialNo;
	//protected short payloadLength;
	/**
	 * @return the payloadLength
	 */
	/*public short getPayloadLength() {
		return payloadLength;
	}
	*//**
	 * @param payloadLength the payloadLength to set
	 *//*
	public void setPayloadLength(short payloadLength) {
		this.payloadLength = payloadLength;
	}*/
	/**
	 * @return the serialNo
	 */
	public byte getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(byte serialNo) {
		this.serialNo = serialNo;
	}

	protected long serverTime;

	public MessageHeader() {
	}
	/**
	 * @return the serviceId
	 */
	public byte getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId
	 *            the serviceId to set
	 */
	public void setServiceId(byte serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return the dirIndicator
	 */
	public byte getDirIndicator() {
		return dirIndicator;
	}

	/**
	 * @param dirIndicator
	 *            the dirIndicator to set
	 */
	public void setDirIndicator(byte dirIndicator) {
		this.dirIndicator = dirIndicator;
	}

	/**
	 * @return the msgTypeId
	 */
	public byte getMsgTypeId() {
		return msgTypeId;
	}

	/**
	 * @param msgTypeId
	 *            the msgTypeId to set
	 */
	public void setMsgTypeId(byte msgTypeId) {
		this.msgTypeId = msgTypeId;
	}

	/**
	 * @return the serverTime
	 */
	public long getServerTime() {
		return serverTime;
	}

	/**
	 * @param serverTime
	 *            the serverTime to set
	 */
	public void setServerTime(long serverTime) {
		this.serverTime = serverTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dirIndicator;
		result = prime * result + msgTypeId;
		result = prime * result + (int) (serverTime ^ (serverTime >>> 32));
		result = prime * result + serviceId;
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
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MessageHeader)) {
			return false;
		}
		MessageHeader other = (MessageHeader) obj;
		if (dirIndicator != other.dirIndicator) {
			return false;
		}
		if (msgTypeId != other.msgTypeId) {
			return false;
		}
		if (serverTime != other.serverTime) {
			return false;
		}
		if (serviceId != other.serviceId) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MessageHeader [serviceId=" + serviceId + ", dirIndicator="
				+ dirIndicator + ", msgTypeId=" + msgTypeId + ", serverTime="
				+ serverTime + "]";
	}
}
