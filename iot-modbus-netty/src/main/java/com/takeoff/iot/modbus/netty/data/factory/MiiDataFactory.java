package com.takeoff.iot.modbus.netty.data.factory;

import com.takeoff.iot.modbus.netty.data.base.MiiData;

public interface MiiDataFactory {
	
	MiiData buildData(int command, byte[] datas);
	
}
