package com.takeoff.iot.modbus.client.message.sender;

import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.service.Mappings;
import com.takeoff.iot.modbus.netty.service.NameValue;

public abstract class TestChannel implements MiiChannel {

	@Override
	public boolean name(String name) {
		return false;
	}

	@Override
	public String name() {
		return null;
	}

	@Override
	public void map(Mappings<? extends NameValue> mapping) {
	}

	@Override
	public Mappings<NameValue> map() {
		return null;
	}
}
