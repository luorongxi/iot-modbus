package com.takeoff.iot.modbus.netty.bytes.factory;

/**
 * 类功能说明：指静脉特征指令工厂<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiFingerFeatureBytesFactory extends MiiInteger2BytesFactory<Integer> implements MiiBytesFactory<Integer> {

	private static final int FINGER_BYTES = 1,FINGER_COUNT = 11;
	
	public MiiFingerFeatureBytesFactory() {
		this(0);
	}
	
	public MiiFingerFeatureBytesFactory(int startPos) {
		super(FINGER_BYTES, FINGER_COUNT, startPos);
	}
}
