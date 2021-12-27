package com.takeoff.iot.modbus.common.bytes.factory;

public interface MiiBytesFactory<E> {
	
	byte[] toBytes(E... contents);
	
}
