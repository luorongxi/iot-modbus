package com.takeoff.iot.modbus.netty.bytes.factory;

public interface MiiBytesFactory<E> {
	
	byte[] toBytes(E... contents);
	
}
