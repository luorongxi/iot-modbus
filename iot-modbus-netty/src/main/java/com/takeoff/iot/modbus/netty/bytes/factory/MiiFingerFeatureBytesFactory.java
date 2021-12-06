package com.takeoff.iot.modbus.netty.bytes.factory;

public class MiiFingerFeatureBytesFactory extends MiiInteger2BytesFactory<Integer> implements MiiBytesFactory<Integer> {

	private static final int FINGER_BYTES = 1,FINGER_COUNT = 11;
	
	public MiiFingerFeatureBytesFactory() {
		this(0);
	}
	
	public MiiFingerFeatureBytesFactory(int startPos) {
		super(FINGER_BYTES, FINGER_COUNT, startPos);
	}
}
