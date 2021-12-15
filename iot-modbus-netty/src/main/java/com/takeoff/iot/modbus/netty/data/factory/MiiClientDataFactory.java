package com.takeoff.iot.modbus.netty.data.factory;

import com.takeoff.iot.modbus.netty.data.MiiLockData;
import com.takeoff.iot.modbus.netty.data.base.MiiData;
import com.takeoff.iot.modbus.netty.data.base.MiiInData;
import com.takeoff.iot.modbus.netty.message.MiiMessage;

public class MiiClientDataFactory implements MiiDataFactory {

	@Override
	public MiiData buildData(int command, byte[] datas) {
		MiiData data = null;
		switch (command) {
		case MiiMessage.LOCK:
			data = new MiiLockData(datas);
			break;
		default: data = new MiiInData(datas);
			break;
		}
		return data;
	}

}
