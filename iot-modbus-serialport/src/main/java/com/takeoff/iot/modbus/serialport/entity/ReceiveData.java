package com.takeoff.iot.modbus.serialport.entity;

import java.util.List;

import lombok.Data;

@Data
public class ReceiveData {

	/**
	 * 获取起始符下标
	 */
	private int beginIndex;
	
	/**
	 * 获取结束符下标
	 */
	private int endIndex;
	
	/**
	 * 接收到的数据
	 */
	private List buffList;
	
	/**
	 * 校验标识
	 */
	private boolean flag;
}
