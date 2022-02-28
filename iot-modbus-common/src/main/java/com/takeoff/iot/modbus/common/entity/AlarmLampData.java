package com.takeoff.iot.modbus.common.entity;

import java.util.List;

import lombok.Data;

@Data
public class AlarmLampData extends DeviceData {

	/**
	 * 灯的颜色控制数据
	 */
	private List<LampColorData> lampColorDataList;
	
	/**
	 * 报警时间（毫秒数）
	 */
	private int alarmTime;
}
