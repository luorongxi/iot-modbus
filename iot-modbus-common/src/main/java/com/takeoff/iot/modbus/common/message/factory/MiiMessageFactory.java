package com.takeoff.iot.modbus.common.message.factory;

import com.takeoff.iot.modbus.common.message.MiiMessage;

public interface MiiMessageFactory<E> {
	
	MiiMessage buildMessage(String deviceGroup, E... datas);
	
}
