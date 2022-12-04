package com.takeoff.iot.modbus.common.enums;

public enum DeviceConnectEnum {

	ON_LINE(0, "已连接"),
	BREAK_OFF(1, "已断开"),
	ABNORMAL(2, "处理业务异常"),
	;
	
	// 成员变量
	private Integer key;
	private String value;

	// 构造方法
	private DeviceConnectEnum(Integer key, String value) {
		this.key = key;
		this.value = value;
	}

	// 普通方法
	public static String getName(Integer key) {
		for (DeviceConnectEnum c : DeviceConnectEnum.values()) {
			if (c.getKey().equals(key)) {
				return c.value;
			}
		}
		return null;
	}

	public Integer getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
