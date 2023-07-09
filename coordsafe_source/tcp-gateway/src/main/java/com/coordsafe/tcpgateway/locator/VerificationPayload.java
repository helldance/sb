package com.coordsafe.tcpgateway.locator;

public class VerificationPayload {
	private Version firmwareVersion;
	private Version appsVersion;
	private Version configVersion;
	
	public VerificationPayload() {
		
	}

	public VerificationPayload(Version firmwareVersion, Version appsVersion,Version configVersion) {
		this.appsVersion = appsVersion;
		this.firmwareVersion = firmwareVersion;
		this.configVersion = configVersion;
	}
	
	/**
	 * @return the firmwareVersion
	 */
	public Version getFirmwareVersion() {
		return firmwareVersion;
	}

	/**
	 * @param firmwareVersion
	 *            the firmwareVersion to set
	 */
	public void setFirmwareVersion(Version firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	/**
	 * @return the appsVersion
	 */
	public Version getAppsVersion() {
		return appsVersion;
	}

	/**
	 * @param appsVersion
	 *            the appsVersion to set
	 */
	public void setAppsVersion(Version appsVersion) {
		this.appsVersion = appsVersion;
	}

	/**
	 * @return the configVersion
	 */
	public Version getConfigVersion() {
		return configVersion;
	}

	/**
	 * @param configVersion
	 *            the configVersion to set
	 */
	public void setConfigVersion(Version configVersion) {
		this.configVersion = configVersion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((appsVersion == null) ? 0 : appsVersion.hashCode());
		result = prime * result
				+ ((configVersion == null) ? 0 : configVersion.hashCode());
		result = prime * result
				+ ((firmwareVersion == null) ? 0 : firmwareVersion.hashCode());
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
		if (!(obj instanceof VerificationPayload)) {
			return false;
		}
		VerificationPayload other = (VerificationPayload) obj;
		if (appsVersion == null) {
			if (other.appsVersion != null) {
				return false;
			}
		} else if (!appsVersion.equals(other.appsVersion)) {
			return false;
		}
		if (configVersion == null) {
			if (other.configVersion != null) {
				return false;
			}
		} else if (!configVersion.equals(other.configVersion)) {
			return false;
		}
		if (firmwareVersion == null) {
			if (other.firmwareVersion != null) {
				return false;
			}
		} else if (!firmwareVersion.equals(other.firmwareVersion)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VerificationPayload [firmwareVersion=" + firmwareVersion
				+ ", appsVersion=" + appsVersion + ", configVersion="
				+ configVersion + "]";
	}
	
	
}
