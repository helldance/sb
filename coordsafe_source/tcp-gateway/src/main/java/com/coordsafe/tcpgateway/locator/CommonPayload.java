package com.coordsafe.tcpgateway.locator;

public class CommonPayload {
	private GpsLocation gpsLocation;
	//private short status;
	private byte gps;
	private byte gprs;
	private byte power;
	private byte other;

	public GpsLocation getGpsLocation() {
		return gpsLocation;
	}	

	public void setGpsLocation(GpsLocation gpsLocation) {
		this.gpsLocation = gpsLocation;
	}

	/*public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((gpsLocation == null) ? 0 : gpsLocation.hashCode());
		//result = prime * result + status;
		return result;
	}

	/**
	 * @return the gps
	 */
	public byte getGps() {
		return gps;
	}

	/**
	 * @param gps the gps to set
	 */
	public void setGps(byte gps) {
		this.gps = gps;
	}

	/**
	 * @return the gprs
	 */
	public byte getGprs() {
		return gprs;
	}

	/**
	 * @param gprs the gprs to set
	 */
	public void setGprs(byte gprs) {
		this.gprs = gprs;
	}

	/**
	 * @return the power
	 */
	public byte getPower() {
		return power;
	}

	/**
	 * @param power the power to set
	 */
	public void setPower(byte power) {
		this.power = power;
	}

	/**
	 * @return the other
	 */
	public byte getOther() {
		return other;
	}

	/**
	 * @param other the other to set
	 */
	public void setOther(byte other) {
		this.other = other;
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
		if (!(obj instanceof CommonPayload)) {
			return false;
		}
		CommonPayload other = (CommonPayload) obj;
		if (gpsLocation == null) {
			if (other.gpsLocation != null) {
				return false;
			}
		} else if (!gpsLocation.equals(other.gpsLocation)) {
			return false;
		}
		/*if (status != other.status) {
			return false;
		}*/
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CommonPayload [gpsLocation=" + gpsLocation + ", gps="
				 + gps + ", gprs=" + gprs + ", power=" + power + ", other=" + other + "]";
	}

	
}
