package com.takeoff.iot.modbus.common.bytes.factory;

public interface MiiEntityBytesFactory<T> extends MiiBytesFactory<String> {

	byte[] toBytes(T t);
	
}
