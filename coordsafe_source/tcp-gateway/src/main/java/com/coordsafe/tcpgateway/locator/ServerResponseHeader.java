package com.coordsafe.tcpgateway.locator;

public class ServerResponseHeader {
	public byte service_id;
	public byte msg_dir;
	public byte msg_type;
	public String serv_ip;
	public long serv_time;
	/**
	 * @param service_id
	 * @param msg_dir
	 * @param msg_type
	 * @param serv_ip
	 * @param serv_time
	 */
	public ServerResponseHeader(byte service_id, byte msg_dir, byte msg_type,
			String serv_ip, long serv_time) {
		super();
		this.service_id = service_id;
		this.msg_dir = msg_dir;
		this.msg_type = msg_type;
		this.serv_ip = serv_ip;
		this.serv_time = serv_time;
	}
	
	
}
