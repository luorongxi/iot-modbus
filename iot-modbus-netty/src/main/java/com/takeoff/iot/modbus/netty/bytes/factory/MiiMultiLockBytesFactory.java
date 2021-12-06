package com.takeoff.iot.modbus.netty.bytes.factory;

public class MiiMultiLockBytesFactory extends MiiInteger2BytesFactory<Integer> implements MiiBytesFactory<Integer> {

private static final int MULTI_LOCK_BYTES = 1,MULTI_LOCK_COUNT = 4;
	
	public MiiMultiLockBytesFactory() {
		this(0);
	}
	
	public MiiMultiLockBytesFactory(int startPos) {
		super(MULTI_LOCK_BYTES, MULTI_LOCK_COUNT, startPos);
	}

}
