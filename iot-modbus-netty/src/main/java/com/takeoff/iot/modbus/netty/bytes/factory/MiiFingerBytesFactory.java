package com.takeoff.iot.modbus.netty.bytes.factory;

public class MiiFingerBytesFactory extends MiiFingerBytesCombinedFactory<Integer> implements MiiBytesFactory<Integer> {
	
	private static final int FINGER_BYTES = 1,FINGER_COUNT = 11,FINGER_STARTPOS = 0;
	
	public MiiFingerBytesFactory() {
		super(new MiiInteger2BytesFactory<Integer>(FINGER_BYTES, FINGER_COUNT, FINGER_STARTPOS));
	}
}
