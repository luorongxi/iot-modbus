package com.takeoff.iot.modbus.netty.listener;

import com.takeoff.iot.modbus.common.message.MiiMessage;
import com.takeoff.iot.modbus.netty.channel.MiiChannel;

/**
 * 类功能说明：设备回传信息监听器接口,处理设备返回信息需实现这个接口,并使用 <code>addListener()</code>添加到{@link MiiServer}的实例中<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@FunctionalInterface
public interface MiiListener {
	
	/**
	 * 处理接收到的设备信息
	 * @param channel 消息通道
	 * @param message 接收到的设备信息
	 */
	void receive(MiiChannel channel, MiiMessage message);
}
