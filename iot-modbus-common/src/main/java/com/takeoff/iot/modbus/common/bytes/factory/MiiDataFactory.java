package com.takeoff.iot.modbus.common.bytes.factory;

import com.takeoff.iot.modbus.common.data.MiiData;

public interface MiiDataFactory {
	
	MiiData buildData(int command, byte[] datas);
	
}
