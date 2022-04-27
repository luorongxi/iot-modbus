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
	 * @param ip 设备IP
	 * @param deviceGroup 设备组号
	 */
	void registerGroup(String ip, String deviceGroup);

	/**
	 * 上传控制单锁指令.
	 * @param ip 设备IP
	 * @param device 设备号
	 * @param status 锁状态（0：上锁；1：开锁）
	 */
	void unlock(String ip, int device, int status);


}
