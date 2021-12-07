package com.takeoff.iot.modbus.netty.service;

public interface NameValue {
	
	boolean name(String name);
	
	String name();
	
	void map(Mappings<? extends NameValue> mapping);
	
	Mappings<NameValue> map();
	
}
