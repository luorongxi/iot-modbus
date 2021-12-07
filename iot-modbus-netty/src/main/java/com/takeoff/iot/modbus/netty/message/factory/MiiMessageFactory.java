package com.takeoff.iot.modbus.netty.message.factory;

import com.takeoff.iot.modbus.netty.message.MiiMessage;

public interface MiiMessageFactory<E> {
	
	MiiMessage buildMessage(String deviceGroup, E... datas);
	
}
