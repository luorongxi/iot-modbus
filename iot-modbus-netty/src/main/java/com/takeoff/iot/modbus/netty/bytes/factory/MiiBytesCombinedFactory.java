package com.takeoff.iot.modbus.netty.bytes.factory;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;


public class MiiBytesCombinedFactory<E> implements MiiBytesFactory<E> {
	
	private MiiBytesFactory<E>[] factorys;
	
	@SafeVarargs
	public MiiBytesCombinedFactory(MiiBytesFactory<E>... factorys) {
		this.factorys = factorys;
	}

	@Override
	public byte[] toBytes(E... contents) {
		ArrayList<Byte> list = new ArrayList<>();
		for(MiiBytesFactory<E> factory : factorys){
			list.addAll(Arrays.asList(
					ArrayUtils.toObject(factory.toBytes(contents))));
		}
		return ArrayUtils.toPrimitive(list.toArray(new Byte[list.size()]));
	}
	
}
