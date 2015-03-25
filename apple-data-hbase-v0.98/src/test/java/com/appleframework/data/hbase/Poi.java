package com.appleframework.data.hbase;

import java.io.Serializable;


public class Poi implements Serializable {

	private static final long serialVersionUID = -522626071707351371L;

	protected String sn;
	protected String type;
	protected String longitude;
	protected String latitude;
	protected String altitude;
	protected String speed;
	protected String gpsTime;

	protected String accessTime;
	protected String receiveTime;
	protected String storageTime;

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return this.type + "_" + this.sn;
	}

	public String getGpsTime() {
		return gpsTime;
	}

	public void setGpsTime(String gpsTime) {
		this.gpsTime = gpsTime;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

	public String getStorageTime() {
		return storageTime;
	}

	public void setStorageTime(String storageTime) {
		this.storageTime = storageTime;
	}

	
	public void setData(Object key, Object value) {
		
	}
	@Override
	public String toString() {
		return "Poi [sn=" + sn + ", type=" + type + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", gpsTime=" + gpsTime + ", accessTime="
				+ accessTime + ", receiveTime=" + receiveTime + ", speed=" + speed + ", altitude=" + altitude + "]";
	}

}
