package com.takeoff.iot.modbus.common.bytes.factory;

import org.apache.commons.lang3.ArrayUtils;

import com.takeoff.iot.modbus.common.utils.IntegerToByteUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 类功能说明：指静脉组合指令工厂<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiFingerBytesCombinedFactory<E> implements MiiBytesFactory<E> {

	private MiiBytesFactory<E>[] factorys;

	@SafeVarargs
	public MiiFingerBytesCombinedFactory(MiiBytesFactory<E>... factorys) {
		this.factorys = factorys;
	}

	@Override
	public byte[] toBytes(E... contents) {
		ArrayList<Byte> list = new ArrayList<>();
		for(MiiBytesFactory<E> factory : factorys){
			list.addAll(Arrays.asList(ArrayUtils.toObject(factory.toBytes(contents))));
		}
		byte[] bytes = ArrayUtils.toPrimitive(list.toArray(new Byte[list.size()]));
		byte[] cabinetBytes = ArrayUtils.subarray(bytes, 0, bytes.length - 7);
		byte[] cmdBytes = ArrayUtils.subarray(bytes, bytes.length - 7, bytes.length - 4);
		byte[] gidBytes = ArrayUtils.subarray(bytes, bytes.length - 4, bytes.length -3);
		byte[] endBytes = ArrayUtils.subarray(bytes, bytes.length - 3, bytes.length - 2);
		byte[] fingerIdBytes = ArrayUtils.subarray(bytes, bytes.length - 2, bytes.length);
		byte[] dataBytes = new byte[cmdBytes.length + fingerIdBytes.length + gidBytes.length];
		System.arraycopy(cmdBytes, 0, dataBytes, 0, cmdBytes.length);
		System.arraycopy(fingerIdBytes, 0, dataBytes, dataBytes.length - 3, fingerIdBytes.length);
		System.arraycopy(gidBytes, 0, dataBytes, dataBytes.length - 1, gidBytes.length);
		byte[] allBytes = new byte[cabinetBytes.length + dataBytes.length + 2];
		System.arraycopy(cabinetBytes, 0, allBytes, 0, cabinetBytes.length);
		System.arraycopy(dataBytes, 0, allBytes, cabinetBytes.length, dataBytes.length);
		//最后赋值，以免干扰crc校验，切勿移到最前方
		allBytes[allBytes.length - 2] = IntegerToByteUtil.checkout(dataBytes, 0);
		System.arraycopy(endBytes, 0, allBytes, allBytes.length - 1, endBytes.length);
		return allBytes;
	}
	
}
