package com.takeoff.iot.modbus.common.entity;

import lombok.Data;

@Data
public class LampColorData {

	/**
	 * 类型（0：红色灯；1：绿色灯；2：黄色灯；3：蜂鸣器）
	 */
	private int tyte;
	
	/**
	 * 开关（0：关；1：开）
	 */
	private int onOff;
	
	/**
	 * 亮灯时间（毫秒数）
	 */
	private int onTime;
	
	/**
	 * 灭灯时间（毫秒数）
	 */
	private int offTime;
}
