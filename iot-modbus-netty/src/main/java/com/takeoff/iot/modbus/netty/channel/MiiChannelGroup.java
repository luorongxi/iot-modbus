package com.takeoff.iot.modbus.netty.channel;

import com.takeoff.iot.modbus.netty.device.MiiControlCentre;
import com.takeoff.iot.modbus.netty.service.MappingsImpl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MiiChannelGroup extends MappingsImpl<MiiChannel> implements MiiControlCentre {
	
	public MiiChannelGroup(){
		super(new ConcurrentHashMap<>());
	}

	@Override
	public List<MiiChannel> groups() {
		return list();
	}
}
