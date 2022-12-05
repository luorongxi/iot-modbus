package com.takeoff.iot.modbus.common.entity;

import com.takeoff.iot.modbus.common.enums.DeviceConnectEnum;
import com.takeoff.iot.modbus.common.utils.JudgeEmptyUtils;
import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class ChannelConnectData extends ApplicationEvent {

	/** 
	 * 描述: TODO <br/>
	 * Fields  serialVersionUID : TODO <br/>
	 */
	private static final long serialVersionUID = 2111432846029949421L;

	private String deviceAddress = null;
	
	private Integer deviceConnect = null;
	
	private String connectMsg = null;
	
	public ChannelConnectData(Object source, Integer deviceConnect, String deviceAddress, int count) {
		super(source);
		if(!JudgeEmptyUtils.isEmpty(deviceAddress)){
			this.deviceConnect = deviceConnect;
			this.deviceAddress = deviceAddress;
			this.connectMsg = "设备："+ deviceAddress + DeviceConnectEnum.getName(deviceConnect) + "，累计："+ count + "次";
		}
	}

}
