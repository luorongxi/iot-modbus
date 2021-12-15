package com.takeoff.iot.modbus.server;

import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.device.MiiControlCentre;

import java.util.Arrays;
import java.util.List;

public abstract class TestControlCentre extends TestChannel implements MiiControlCentre {

	@Override
	public boolean add(MiiChannel group) {
		return false;
	}

	@Override
	public MiiChannel remove(String address) {
		return this;
	}

	@Override
	public MiiChannel get(String address) {
		return this;
	}

	@Override
	public List<MiiChannel> groups() {
		return Arrays.asList(this);
	}

}
