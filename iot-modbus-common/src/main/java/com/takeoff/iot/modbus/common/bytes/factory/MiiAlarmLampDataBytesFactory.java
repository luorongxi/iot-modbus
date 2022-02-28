package com.takeoff.iot.modbus.common.bytes.factory;

public class MiiAlarmLampDataBytesFactory extends MiiInteger2BytesFactory<Integer> implements MiiBytesFactory<Integer> {

    private static final int ALARM_LAMP_BATCH_BYTES = 1, ALARM_LAMP_BATCH_COUNT = 4;
	
	public MiiAlarmLampDataBytesFactory() {
		this(0);
	}
	
	public MiiAlarmLampDataBytesFactory(int startPos) {
		super(ALARM_LAMP_BATCH_BYTES, ALARM_LAMP_BATCH_COUNT, startPos);
	}
}
