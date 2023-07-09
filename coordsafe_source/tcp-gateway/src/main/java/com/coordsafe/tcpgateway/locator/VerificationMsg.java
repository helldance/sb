package com.coordsafe.tcpgateway.locator;

import com.coordsafe.tcpgateway.util.CommonConstants;

public class VerificationMsg extends LocatorMessage{
	private VerificationPayload verPayload;

	public VerificationMsg() {
		super();
		if (locatorMsgHeader != null)
			locatorMsgHeader.setMsgTypeId(CommonConstants.VER_MSG);
	}

	/**
	 * @return the verPayload
	 */
	public VerificationPayload getVerPayload() {
		return verPayload;
	}

	/**
	 * @param verPayload
	 *            the verPayload to set
	 */
	public void setVerPayload(VerificationPayload verPayload) {
		this.verPayload = verPayload;
	}
	
}
