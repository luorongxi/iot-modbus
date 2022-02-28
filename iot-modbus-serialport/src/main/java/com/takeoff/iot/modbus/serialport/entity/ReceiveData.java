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
	 * 指令总长度
	 */
	private int instructLength;
	
	/**
	 * 接收到的数据
	 */
	private List buffList;
	
	/**
	 * 校验标识
	 */
	private boolean flag;
}
