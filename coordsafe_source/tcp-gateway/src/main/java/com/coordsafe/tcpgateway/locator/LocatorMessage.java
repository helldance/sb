package com.coordsafe.tcpgateway.locator;

public abstract class LocatorMessage {
	protected LocatorMsgHeader locatorMsgHeader;
	protected CommonPayload commonPayload;

	public LocatorMessage() {
		locatorMsgHeader = new LocatorMsgHeader();
	}
	/**
	 * @return the locatorMsgHeader
	 */
	public LocatorMsgHeader getLocatorMsgHeader() {
		return locatorMsgHeader;
	}

	/**
	 * @param locatorMsgHeader
	 *            the locatorMsgHeader to set
	 */
	public void setLocatorMsgHeader(LocatorMsgHeader locatorMsgHeader) {
		this.locatorMsgHeader = locatorMsgHeader;
	}

	/**
	 * @return the commonPayload
	 */
	public CommonPayload getCommonPayload() {
		return commonPayload;
	}

	/**
	 * @param commonPayload
	 *            the commonPayload to set
	 */
	public void setCommonPayload(CommonPayload commonPayload) {
		this.commonPayload = commonPayload;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((commonPayload == null) ? 0 : commonPayload.hashCode());
		result = prime
				* result
				+ ((locatorMsgHeader == null) ? 0 : locatorMsgHeader.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		if (!(obj instanceof LocatorMessage)) {
			return false;
		}
		LocatorMessage other = (LocatorMessage) obj;
		if (commonPayload == null) {
			if (other.commonPayload != null) {
				return false;
			}
		} else if (!commonPayload.equals(other.commonPayload)) {
			return false;
		}
		if (locatorMsgHeader == null) {
			if (other.locatorMsgHeader != null) {
				return false;
			}
		} else if (!locatorMsgHeader.equals(other.locatorMsgHeader)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LocatorMessage [locatorMsgHeader=" + locatorMsgHeader
				+ ", commonPayload=" + commonPayload + "]";
	}
	
}
