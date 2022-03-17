package com.takeoff.iot.modbus.common.bytes.factory;

/**
 * 类功能说明：指静脉指令工厂<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiFingerBytesFactory extends MiiInteger2BytesFactory<Integer> implements MiiBytesFactory<Integer> {

	private static final int FINGER_BYTES = 1,FINGER_COUNT = 10;

	public MiiFingerBytesFactory() {
		this(0);
	}

	public MiiFingerBytesFactory(int startPos) {
		super(FINGER_BYTES, FINGER_COUNT, startPos);
	}
}
