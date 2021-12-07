package com.takeoff.iot.modbus.netty.service;

import java.util.List;

public interface Mappings<NV> {
	
	boolean add(NV nv);
	
	NV get(String name);
	
	NV remove(String name);
	
	List<NV> list();
}
