package com.takeoff.iot.modbus.common.enums;

/**
 * 类功能说明：Bytes与Hex转换工具类<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public enum MitModbusCacheKeyEnum {

	CLIENT_CHANNEL("CLIENT_CHANNEL", "客户端通讯管道"),
	;

	// 成员变量
	private String key;
	private String value;

	// 构造方法
	private MitModbusCacheKeyEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	// 普通方法
	public static String getName(String key) {
		for (MitModbusCacheKeyEnum c : MitModbusCacheKeyEnum.values()) {
			if (c.getKey().equals(key)) {
				return c.value;
			}
		}
		return null;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
	
}
