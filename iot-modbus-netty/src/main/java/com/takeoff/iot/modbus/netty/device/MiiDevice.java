package com.takeoff.iot.modbus.netty.device;

public class MiiDevice {
	
	/**
	 * 设备组编码
	 */
	private String deviceGroup;
	
	/**
	 * 设备号
	 */
	private int device;
	
	public MiiDevice(String deviceGroup, int device){
		this.deviceGroup = deviceGroup;
		this.device = device;
	}
	
	public String deviceGroup(){
		return deviceGroup;
	}
	
	public int device(){
		return device;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result 
				+ ((deviceGroup() == null) ? 0 : deviceGroup().hashCode());
		result = prime * result + device();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MiiDevice){
			MiiDevice cab = (MiiDevice) obj;
			return deviceGroup().equals(cab.deviceGroup())
					&& device() == cab.device();
		} else {
			return false;
		}
	}

}
