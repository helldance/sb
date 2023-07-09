package com.coordsafe.httpgateway.messaging;

public abstract class CsMessage {
	private CsMessageHeader header;
	private String payLoad;
	/**
	 * @return the header
	 */
	public CsMessageHeader getHeader() {
		return header;
	}
	/**
	 * @param header the header to set
	 */
	public void setHeader(CsMessageHeader header) {
		this.header = header;
	}
	/**
	 * @return the payLoad
	 */
	public String getPayLoad() {
		return payLoad;
	}
	/**
	 * @param payLoad the payLoad to set
	 */
	public void setPayLoad(String payLoad) {
		this.payLoad = payLoad;
	}

}
