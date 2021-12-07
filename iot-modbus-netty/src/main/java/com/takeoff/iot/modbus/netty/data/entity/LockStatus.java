package com.takeoff.iot.modbus.netty.data.entity;

import lombok.Data;

/**
 * 类功能说明：门锁状态实体类<br/>
 * 公司名称：takeoff开源 <br/>
 * 作者：luorongxi <br/>
 */
@Data
public class LockStatus {

	/**
	 * 返回门锁号
	 * @return 门锁号
	 */
	private int lockNo;

	/**
	 * 返回门锁状态码
	 * @return 门锁状态码
	 */
	private int lockStatus;
	
	/**
	 * 返回传感器状态码
	 * @return 传感器状态码
	 */
	private int sensorStatus;
	
}
