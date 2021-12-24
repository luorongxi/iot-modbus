package com.takeoff.iot.modbus.netty.bytes.factory;

/**
 * 类功能说明：多锁指令工厂<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiMultiLockBytesFactory extends MiiInteger2BytesFactory<Integer> implements MiiBytesFactory<Integer> {

private static final int MULTI_LOCK_BYTES = 1,MULTI_LOCK_COUNT = 4;
	
	public MiiMultiLockBytesFactory() {
		this(0);
	}
	
	public MiiMultiLockBytesFactory(int startPos) {
		super(MULTI_LOCK_BYTES, MULTI_LOCK_COUNT, startPos);
	}

}
