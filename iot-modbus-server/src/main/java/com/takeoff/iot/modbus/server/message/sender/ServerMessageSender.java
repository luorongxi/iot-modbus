package com.takeoff.iot.modbus.server.message.sender;

import com.takeoff.iot.modbus.common.entity.AlarmLampData;
import com.takeoff.iot.modbus.common.entity.LcdData;

import java.util.List;

/**
 * 类功能说明：指令下发接口<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public interface ServerMessageSender {
	
	/**
	 * 发送控制单锁指令.
	 * @param deviceIp 设备IP
	 * @param device 设备号
	 */
	void unlock(String deviceIp, int device);
	
	/**
	 * 发送控制多开锁指令.
	 * @param deviceIp 设备IP
	 * @param device 设备号
	 * @param arr 数组（门锁号、门锁状态、门锁号、门锁状态...）
	 */
	void unlock(String deviceIp, int device, Integer... arr);
	
	/**
	 * 发送设置扫码模式指令.
	 * @param deviceIp 设备IP
	 * @param device 设备号
	 * @param mode  扫码模式 {@link com.takeoff.iot.modbus.common.data.MiiData} ONCE ALWAYS NEVER
	 */
	void barcode(String deviceIp, int device, int mode);
	
	/**
	 * 发送背光灯指令.
	 * @param deviceIp 设备IP
	 * @param device 设备号
	 * @param status 开关状态 {@link com.takeoff.iot.modbus.common.data.MiiData} ON OFF
	 */
	void backlight(String deviceIp, int device, int status);
	
	/**
	 * 注册指静脉.
	 * @param deviceIp 设备IP
	 * @param device 设备号
	 * @param fingerId 手指ID
	 */
	void registerFinger(String deviceIp, int device, int fingerId);
	
	/**
	 * 单条删除指静脉.
	 * @param deviceIp 设备IP
	 * @param device 设备号
	 * @param fingerId 手指ID
	 */
	void deleteFinger(String deviceIp, int device, int fingerId);
	
	/**
	 * 批量删除指静脉.
	 * @param deviceIp 设备IP
	 * @param device 设备号
	 */
	void deleteAllFinger(String deviceIp, int device);
	
	/**
	 * 查询所有指静脉.
	 * @param deviceIp 设备IP
	 * @param device 设备号
	 */
	void getFingerList(String deviceIp, int device);

	/**
	 * 获取手指ID指静脉特征.
	 * @param deviceIp 设备IP
	 * @param device 设备号
	 * @param fingerId 手指ID
	 */
	void getFingerFeature(String deviceIp, int device, int fingerId);
	
	/**
	 * 写入指静脉特征.
	 * @param deviceIp 设备IP
	 * @param device 设备号
	 * @param fingerId 手指ID
	 */
	void writeFingerFeature(String deviceIp, int device, int fingerId, byte[] feature);

	/**
	 * 按柜体批量发送lCD数据
	 * 参数：@param lcdDataList 下发lcd显示内容
	 */
	void lcdBatch(List<LcdData> lcdDataList);

	/**
	 * 下发控制三色报警灯数据
	 * 参数：@param alarmLampData 下发报警灯数据
	 */
	void alarmLamp(AlarmLampData alarmLampData);
}
