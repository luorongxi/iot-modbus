package com.takeoff.iot.modbus.common.data;

/**
 * 类功能说明：设备信息数据<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiSlotData extends MiiByteArrayData {

	public MiiSlotData(byte[] datas) {
		super(datas);
	}
	
	/**
	 * 返回设备号
	 * @return 设备号
	 */
	public int device() {
		return toBytes()[DEVICE_INDEX];
	}

	/**
	 * 返回层号
	 * @return 层号
	 */
	public int shelf() {
		return toBytes()[SHELF_INDEX];
	}
	
	/**
	 * 返回槽位号
	 * @return 槽位号
	 */
	public int slot() {
		return toBytes()[SLOT_INDEX];
	}
	
}
