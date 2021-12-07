package com.takeoff.iot.modbus.netty.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappingsImpl<NV extends NameValue> implements Mappings<NV> {
	
	protected transient final Map<String, NV> namedMap;
	
	public MappingsImpl(){
		this(new HashMap<String, NV>());
	}
	
	public MappingsImpl(Map<String, NV> namedMap){
		this.namedMap = namedMap;
	}

	@Override
	public boolean add(NV nv) {
		boolean exists = false;
		
		if(nv.map() == null || nv.map() == this){
			exists = get(nv.name()) != null;
			if(exists){
				remove(nv.name());
			}
			namedMap.put(nv.name(), nv);
			nv.map(this);
		} else {
			throw new RuntimeException(String.format("%s已经存在其他映射组里!", nv.name()));
		}
		
		return ! exists;
	}

	@Override
	public NV get(String name) {
		return (NV) namedMap.get(name);
	}

	@Override
	public NV remove(String name) {
		NV nv = namedMap.remove(name);
		if(nv != null){
			nv.map(null);
		}
		return nv;
	}

	@Override
	public List<NV> list() {
		return new ArrayList<>(namedMap.values());
	}

}
