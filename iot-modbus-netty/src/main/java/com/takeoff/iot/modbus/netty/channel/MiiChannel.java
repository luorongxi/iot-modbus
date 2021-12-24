package com.takeoff.iot.modbus.netty.channel;

import com.takeoff.iot.modbus.netty.message.MiiMessage;
import com.takeoff.iot.modbus.netty.service.NameValue;

/**
 * 类功能说明：通讯通道<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public interface MiiChannel extends NameValue {
	
	/**
	 * 发送指令信息
	 * @param msg 指令信息
	 */
	void send(MiiMessage msg);
	
}
