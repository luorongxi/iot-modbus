package com.takeoff.iot.modbus.common.data;

public interface Finger {
	
	/**
	 * 返回枚举标识
	 * @return 枚举标识
	 */
	int fingerType();
	
	/**
	 * 返回状态码
	 * @return 状态码
	 */
	int fingerStatus();
	
	/**
	 * 返回指令码
	 * @return 指令码
	 */
	int fingerCmd();

	/**
	 * 返回手指ID
	 * @return 手指ID
	 */
	int fingerId();
	
}
