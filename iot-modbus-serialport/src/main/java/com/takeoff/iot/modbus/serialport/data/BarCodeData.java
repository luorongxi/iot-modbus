package com.takeoff.iot.modbus.serialport.data;

import com.takeoff.iot.modbus.netty.data.MiiBarcodeData;
import lombok.Getter;

/**
 * 类功能说明：扫码数据<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Getter
public class BarCodeData extends ReceiveDataEvent {
	
	private String barCode;

	public BarCodeData(Object source, int command, MiiBarcodeData data) {
		super(source, command, data.device(), data.shelf(), data.slot());
		this.barCode = data.content();
	}

}
