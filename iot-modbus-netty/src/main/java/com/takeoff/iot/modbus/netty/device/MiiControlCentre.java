package com.takeoff.iot.modbus.netty.device;

import com.takeoff.iot.modbus.netty.channel.MiiChannel;

import java.util.List;

public interface MiiControlCentre {
	
	boolean add(MiiChannel group);
	
	MiiChannel remove(String address);
	
	MiiChannel get(String address);
	
	List<MiiChannel> groups();
	
}
