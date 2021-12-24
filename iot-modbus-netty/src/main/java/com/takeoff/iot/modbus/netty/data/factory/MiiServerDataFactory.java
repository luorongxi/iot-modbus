package com.takeoff.iot.modbus.netty.data.factory;

import com.takeoff.iot.modbus.netty.data.*;
import com.takeoff.iot.modbus.netty.data.base.MiiData;
import com.takeoff.iot.modbus.netty.data.base.MiiInData;
import com.takeoff.iot.modbus.netty.message.MiiMessage;

/**
 * 类功能说明：指令数据处理工厂<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiServerDataFactory implements MiiDataFactory {

	@Override
	public MiiData buildData(int command, byte[] datas) {
		MiiData data = null;
		switch (command) {
		case MiiMessage.HEARTBEAT:
			data = new MiiHeartBeatData(datas);
			break;
		case MiiMessage.LOCK:
			data = new MiiLockData(datas);
			break;
		case MiiMessage.CARD:
			data = new MiiCardData(datas);
			break;
		case MiiMessage.BARCODE:
			data = new MiiBarcodeData(datas);
			break;
		case MiiMessage.BACKLIGHT:
			data = new MiiBackLightData(datas);
			break;
		case MiiMessage.FINGER:
			data = new MiiFingerData(datas);
			break;
		case MiiMessage.HM:
			data = new MiiHumitureData(datas);
			break;
		case MiiMessage.LED:
		default: data = new MiiInData(datas);
			break;
		}
		return data;
	}

}
