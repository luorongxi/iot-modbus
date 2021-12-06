package com.takeoff.iot.modbus.netty.bytes.factory;

public class MiiSlotBytesFactory extends MiiInteger2BytesFactory<Integer> implements MiiBytesFactory<Integer> {
	
	private static final int INTEGER_BYTES = 1,INTEGER_COUNT = 5;
	
	public MiiSlotBytesFactory() {
		this(0);
	}
	
	public MiiSlotBytesFactory(int startPos) {
		super(INTEGER_BYTES, INTEGER_COUNT, startPos);
	}
}
