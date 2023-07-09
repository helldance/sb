package com.coordsafe.tcpgateway.locator;

import com.coordsafe.tcpgateway.util.CommonConstants;

public class AuthenticationMsg extends LocatorMessage{
	public AuthenticationMsg() {
		super();
		if (locatorMsgHeader != null) {
			locatorMsgHeader.setMsgTypeId(CommonConstants.AUTH_MSG);
		}
		//this.header = header;
	}

/*	public LocatorMsgHeader getHeader() {
		return header;
	}

	public void setHeader(LocatorMsgHeader header) {
		this.header = header;
	}

	public CommonPayload getCommonPayload() {
		return commonPayload;
	}

	public void setCommonPayload(CommonPayload commonPayload) {
		this.commonPayload = commonPayload;
	}
*/
}
