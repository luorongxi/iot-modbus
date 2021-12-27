package com.takeoff.iot.modbus.client.message.sender;

import java.util.List;

import com.takeoff.iot.modbus.common.data.Finger;

/**
 * 类功能说明：上传指令接口<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public interface ClientMessageSender {

	/**
	 * 上传设备组指令.
	 * @param deviceGroup 设备组号
	 */
	void registerGroup(String deviceGroup);

	/**
	 * 上传控制单锁指令.
	 * @param device 设备号
	 * @param status 锁状态
	 */
	void unlock(int device, int status);


}
