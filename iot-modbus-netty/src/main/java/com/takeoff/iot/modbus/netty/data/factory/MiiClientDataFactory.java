package com.takeoff.iot.modbus.netty.data.factory;

import com.takeoff.iot.modbus.common.bytes.factory.MiiDataFactory;
import com.takeoff.iot.modbus.common.data.MiiData;
import com.takeoff.iot.modbus.common.data.MiiInData;
import com.takeoff.iot.modbus.common.data.MiiLockData;
import com.takeoff.iot.modbus.common.message.MiiMessage;

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
