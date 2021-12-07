package com.takeoff.iot.modbus.netty.data.base;

/**
 * 类功能说明：包含数组类型的指令数据处理<br/>
 * 公司名称：takeoff开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiByteArrayData implements MiiData {
	
	protected byte[] datas;
	
	public MiiByteArrayData(byte[] datas) {
		this.datas = datas;
	}
	
	public byte[] toBytes() {
		return datas;
	}
	
	public int length() {
		return datas.length;
	}

}
