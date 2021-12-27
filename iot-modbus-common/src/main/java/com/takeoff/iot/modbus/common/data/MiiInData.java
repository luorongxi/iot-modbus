package com.takeoff.iot.modbus.common.data;

public class MiiInData extends MiiSlotData {
	
	public MiiInData(byte[] datas){
		super(datas);
	}
	
	/**
	 * 返回状态码
	 * @return 状态码
	 */
	public int statusCode() {
		return toBytes()[CONTENT_INDEX] & 0xFF;
	}
}
