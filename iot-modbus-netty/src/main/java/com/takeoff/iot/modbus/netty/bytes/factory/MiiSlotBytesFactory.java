package com.takeoff.iot.modbus.netty.bytes.factory;

/**
 * 类功能说明：短整型指令工厂<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiSlotBytesFactory extends MiiInteger2BytesFactory<Integer> implements MiiBytesFactory<Integer> {
	
	private static final int INTEGER_BYTES = 1,INTEGER_COUNT = 5;
	
	public MiiSlotBytesFactory() {
		this(0);
	}
	
	public MiiSlotBytesFactory(int startPos) {
		super(INTEGER_BYTES, INTEGER_COUNT, startPos);
	}
}
