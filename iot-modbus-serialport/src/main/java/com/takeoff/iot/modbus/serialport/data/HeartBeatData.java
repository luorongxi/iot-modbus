package com.takeoff.iot.modbus.serialport.data;

import com.takeoff.iot.modbus.netty.data.MiiHeartBeatData;
import lombok.Getter;

/**
 * 类功能说明：心跳数据<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Getter
public class HeartBeatData extends ReceiveDataEvent {

	private String deviceGroup;

	public HeartBeatData(Object source, int command, MiiHeartBeatData data) {
		super(source, command, data.device(), data.shelf(), data.slot());
		this.deviceGroup = data.deviceGroup();
	}

}
