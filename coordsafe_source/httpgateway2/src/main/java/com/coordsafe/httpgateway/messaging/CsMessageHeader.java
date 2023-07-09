package com.coordsafe.httpgateway.messaging;

public class CsMessageHeader {
	private CsMessageType msg_type;
	private String dest;
	/**
	 * @return the msg_type
	 */
	public CsMessageType getMsgType() {
		return msg_type;
	}
	/**
	 * @param msg_type the msg_type to set
	 */
	public void setMsgType(CsMessageType msg_type) {
		this.msg_type = msg_type;
	}
	/**
	 * @return the dest
	 */
	public String getDest() {
		return dest;
	}
	/**
	 * @param dest the dest to set
	 */
	public void setDest(String dest) {
		this.dest = dest;
	}
	
	
}
