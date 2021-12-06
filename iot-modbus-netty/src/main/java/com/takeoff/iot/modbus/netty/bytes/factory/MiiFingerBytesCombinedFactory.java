package com.takeoff.iot.modbus.netty.bytes.factory;

import com.takeoff.iot.modbus.netty.utils.IntegerByteTransform;
import org.apache.commons.lang3.ArrayUtils;

public class MiiFingerBytesCombinedFactory<E> implements MiiBytesFactory<E> {
	
	private MiiBytesFactory<E> factory;
	
	public MiiFingerBytesCombinedFactory(MiiBytesFactory<E> factory) {
		this.factory = factory;
	}

	@Override
	public byte[] toBytes(E... contents) {
		byte[] bytes = factory.toBytes(contents);
		byte[] cabinetBytes = ArrayUtils.subarray(bytes, 0, bytes.length - 7);
		byte[] cmdBytes = ArrayUtils.subarray(bytes, bytes.length - 7, bytes.length);
		byte[] dataBytes = new byte[cmdBytes.length + 2];
		System.arraycopy(cmdBytes, 1, dataBytes, 1, cmdBytes.length - 4);
		byte[] fingerBytes = IntegerByteTransform.intToBytes(cmdBytes[cmdBytes.length - 3]);
		System.arraycopy(fingerBytes, 0, dataBytes, dataBytes.length - 5, fingerBytes.length);
		System.arraycopy(cmdBytes, cmdBytes.length -2, dataBytes, dataBytes.length - 3, 1);
		dataBytes[dataBytes.length - 2] = IntegerByteTransform.checkout(dataBytes, 0);
		System.arraycopy(cmdBytes, cmdBytes.length -1, dataBytes, dataBytes.length - 1, 1);
		//最后赋值，以免干扰crc校验，切勿移到最前方
		System.arraycopy(cmdBytes, 0, dataBytes, 0, 1);
		byte[] allBytes = new byte[cabinetBytes.length + dataBytes.length];
		System.arraycopy(cabinetBytes, 0, allBytes, 0, cabinetBytes.length);
		System.arraycopy(dataBytes, 0, allBytes, cabinetBytes.length, dataBytes.length);
		return allBytes;
	}
	
}
