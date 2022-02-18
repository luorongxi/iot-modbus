package com.takeoff.iot.modbus.common.bytes.factory;

/**
 * 类功能说明：LCD指令工厂<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiLcdBatchBytesFactory extends MiiInteger2BytesFactory<Integer> implements MiiBytesFactory<Integer> {

    private static final int LCD_BATCH_BYTES = 1, LCD_BATCH_COUNT = 4;
	
	public MiiLcdBatchBytesFactory() {
		this(0);
	}
	
	public MiiLcdBatchBytesFactory(int startPos) {
		super(LCD_BATCH_BYTES, LCD_BATCH_COUNT, startPos);
	}

}
