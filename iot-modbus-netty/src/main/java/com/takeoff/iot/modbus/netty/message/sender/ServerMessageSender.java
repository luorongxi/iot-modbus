package com.takeoff.iot.modbus.netty.message.sender;

/**
 * 类功能说明：指令下发接口<br/>
 * 公司名称：takeoff开源 <br/>
 * 作者：luorongxi <br/>
 */
public interface ServerMessageSender {
	
	/**
	 * 发送控制单锁指令.
	 * @param deviceGroup 设备组编码
	 * @param device 设备号
	 */
	void unlock(String deviceGroup, int device);
	
	/**
	 * 发送控制多开锁指令.
	 * @param deviceGroup 设备组编码
	 * @param device 设备号
	 * @param arr 数组（门锁号、门锁状态、门锁号、门锁状态...）
	 */
	void unlock(String deviceGroup, int device, Integer... arr);
	
	/**
	 * 发送设置扫码模式指令.
	 * @param deviceGroup 设备组编码
	 * @param device 设备号
	 * @param mode  扫码模式 {@link com.takeoff.iot.modbus.netty.data.base.MiiData} ONCE ALWAYS NEVER
	 */
	void barcode(String deviceGroup, int device, int mode);
	
	/**
	 * 发送背光灯指令.
	 * @param deviceGroup 设备组编码
	 * @param device 设备号
	 * @param status 开关状态 {@link com.takeoff.iot.modbus.netty.data.base.MiiData} ON OFF
	 */
	void backlight(String deviceGroup, int device, int status);
	
	/**
	 * 注册指静脉.
	 * @param deviceGroup 设备组编码
	 * @param device 设备号
	 * @param fingerId 手指ID
	 */
	void registerFinger(String deviceGroup, int device, int fingerId);
	
	/**
	 * 单条删除指静脉.
	 * @param deviceGroup 设备组编码
	 * @param device 设备号
	 * @param fingerId 手指ID
	 */
	void deleteFinger(String deviceGroup, int device, int fingerId);
	
	/**
	 * 批量删除指静脉.
	 * @param deviceGroup 设备组编码
	 * @param device 设备号
	 */
	void deleteAllFinger(String deviceGroup, int device);
	
	/**
	 * 查询所有指静脉.
	 * @param deviceGroup 设备组编码
	 * @param device 设备号
	 */
	void getFingerList(String deviceGroup, int device);

	/**
	 * 获取手指ID指静脉特征.
	 * @param deviceGroup 设备组编码
	 * @param device 设备号
	 * @param fingerId 手指ID
	 */
	void getFingerFeature(String deviceGroup, int device, int fingerId);
	
	/**
	 * 写入指静脉特征.
	 * @param deviceGroup 设备组编码
	 * @param device 设备号
	 * @param fingerId 手指ID
	 */
	void writeFingerFeature(String deviceGroup, int device, int fingerId, byte[] feature);
	
}
