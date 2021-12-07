package com.takeoff.iot.modbus.netty.device;

import com.takeoff.iot.modbus.netty.channel.MiiChannel;

/**
 * 类功能说明：设备信息<br/>
 * 公司名称：takeoff开源 <br/>
 * 作者：luorongxi <br/>
 */
public interface MiiDeviceGroup extends MiiChannel {
	
	/**
	 * 返回设备组地址.
	 * @return 设备组地址
	 */
	String address();
	
	/**
	 * 返回设备组名称
	 * @return 设备组名称
	 */
	String name();
	
	/**
	 * 返回设备组端口
	 * @return 设备组端口
	 */
	int port();
}
