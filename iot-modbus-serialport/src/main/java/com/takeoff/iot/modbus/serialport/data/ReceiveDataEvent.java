package com.takeoff.iot.modbus.serialport.data;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

/**
 * 类功能说明：接收数据<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Getter
public class ReceiveDataEvent extends ApplicationEvent {

	/**
	 * 指令
	 */
	private Integer command;

	/**
	 * 设备号
	 */
	private Integer device;

	/**
	 * 层号
	 */
	private Integer shelf;

	/**
	 * 槽位号
	 */
	private Integer slot;
	
	public ReceiveDataEvent(Object source, Integer command, Integer device, Integer shelf, Integer slot) {
		super(source);
		this.command=command;
		this.device=device;
		this.shelf=shelf;
		this.slot=slot;
	}
	
}
