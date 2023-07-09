package com.coordsafe.messaging;

import java.nio.ByteBuffer;

/**
 * @author Yang Wei
 * @Date Mar 15, 2014
 */
public class ByteMessage {
	public byte [] header;	// 2 byte
	public byte [] length;	// 2 byte
	public byte [] id;		// 7 byte
	public byte [] command; // 2 byte - 1110
	public byte [] data;	// 0 -100 byte
	public byte [] checksum;// 2 byte - 0000
	public byte [] tailer; // 2 byte - \r\n
		
	/**
	 * 
	 */
	public ByteMessage() {
		super();
	}

	/**
	 * @param length
	 * @param id
	 * @param command
	 */
	public ByteMessage(byte[] length, byte[] id, byte[] command, byte[] data) {
		super();
		
		this.header = "&&".getBytes();
		
		this.length = length;
		this.id = id;
		this.command = command;		
		this.data = data;
		
		this.checksum =  new byte[]{(byte)0, (byte)0};
		this.tailer =  new byte[]{(byte)0x0d, (byte)0x0a};
	}

	/**
	 * @param header
	 * @param length
	 * @param id
	 * @param command
	 * @param data
	 * @param checksum
	 */
	public ByteMessage(byte[] header, byte[] length, byte[] id, byte[] command,
			byte[] data, byte[] checksum, byte [] tailer) {
		super();
		
		this.header = header;
		this.length = length;
		this.id = id;
		this.command = command;
		this.data = data;
		this.checksum = checksum;
		this.tailer = tailer;
	}

	public byte [] allBytes(){
		int dataLen = data == null? 0 : data.length;
		
		System.out.println(",,,,,,,,,,,,...........\n" + header.length + " " + length.length + " " 
				+ id.length + " " + command.length + " " + data.length + " " + checksum.length + " " + tailer.length);
		
		byte [] all = new byte [17 + dataLen];
		
		ByteBuffer all_ = ByteBuffer.wrap(all);
		
		//System.out.println(all_.capacity());
		
		all_.put(header);
		all_.put(length);
		all_.put(id);
		all_.put(command);
		
		if (dataLen > 0){
			all_.put(data);
		}
		
		all_.put(checksum);
		all_.put(tailer);
		
		for (byte b: all_.array()){
			System.out.print(b + " ");
		}
		
		System.out.println();
		
		return all_.array();
	}
}
