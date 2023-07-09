/**
 * 
 */
package com.coordsafe.gateway.entity;

/**
 * @author Yang Wei
 *
 */
public class SzOneMsg extends SzUpMsg{
	// total 77 bytes exclude header
	public String deviceId; // 15 bytes
	public String date; // 6 bytes
	public String valid;
	public String latLng; // 21 bytes
	public String speed; // 5 bytes, km/h
	public String time; // 6 bytes
	public String direction; // 6 bytes
	public String power; 
	public String info; // 7 bytes
	public String mileage; // 8 bytes
	public String endMark;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SzOneMsg [deviceId=" + deviceId + ", date=" + date + ", valid="
				+ valid + ", latLng=" + latLng + ", speed=" + speed + ", time="
				+ time + ", direction=" + direction + ", power=" + power
				+ ", info=" + info + ", mileage=" + mileage + ", endMark="
				+ endMark + ", header=" + header + "]";
	}
	
}
