package com.takeoff.iot.modbus.netty.service;

public class NameValueImpl implements NameValue {
	
	private String name;
	private Mappings mapping;
	
	public NameValueImpl(){
	}
	
	public NameValueImpl(String name){
		this.name = name;
	}

	@Override
	public String name() {
		return name;
	}
	
	@Override
	public boolean name(String name) {
		Mappings<NameValue> map = map();
		if(map != null) map.remove(this.name);
		this.name = name;
		if(map != null) map.add(this);
		return map == null;
	}

	@Override
	public Mappings<NameValue> map() {
		return mapping;
	}

	@Override
	public void map(Mappings<? extends NameValue> mapping) {
		Mappings<NameValue> map = map();
		if(map != null && mapping != map) map.remove(this.name());
		this.mapping = mapping;
	}
}
