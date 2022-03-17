package com.takeoff.iot.modbus.common.bytes.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.takeoff.iot.modbus.common.utils.IntegerToByteUtil;

/**
 * 类功能说明：指静脉特征组合指令工厂<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiFingerFeatureBytesCombinedFactory <E> implements MiiBytesFactory<E> {

	private MiiBytesFactory<E>[] factorys;

	@SafeVarargs
	public MiiFingerFeatureBytesCombinedFactory(MiiBytesFactory<E>... factorys) {
		this.factorys = factorys;
	}

	@Override
	public byte[] toBytes(E... contents) {
		ArrayList<Byte> list = new ArrayList<>();
		for(MiiBytesFactory<E> factory : factorys){
			list.addAll(Arrays.asList(ArrayUtils.toObject(factory.toBytes(contents))));
		}
		byte[] bytes = ArrayUtils.toPrimitive(list.toArray(new Byte[list.size()]));
		byte[] cabinetBytes = ArrayUtils.subarray(bytes, 0, 5);
		byte[] cmdBytes = ArrayUtils.subarray(bytes, 5, 8);
		byte[] gidBytes = ArrayUtils.subarray(bytes, 8, 9);
		byte[] endBytes = ArrayUtils.subarray(bytes, 9, 10);
		byte[] fingerIdBytes = ArrayUtils.subarray(bytes, 11, 13);
		byte[] featureBytes = ArrayUtils.subarray(bytes, 13, bytes.length);
		byte[] dataBytes = new byte[cmdBytes.length + fingerIdBytes.length + gidBytes.length];
		System.arraycopy(cmdBytes, 0, dataBytes, 0, cmdBytes.length);
		System.arraycopy(fingerIdBytes, 0, dataBytes, dataBytes.length - 3, fingerIdBytes.length);
		System.arraycopy(gidBytes, 0, dataBytes, dataBytes.length - 1, gidBytes.length);
		byte[] allBytes = new byte[cabinetBytes.length + dataBytes.length + 2 + cmdBytes.length + featureBytes.length + 2];
		System.arraycopy(cabinetBytes, 0, allBytes, 0, cabinetBytes.length);
		System.arraycopy(dataBytes, 0, allBytes, cabinetBytes.length, dataBytes.length);
		//最后赋值，以免干扰crc校验，切勿移到最前方
		allBytes[cabinetBytes.length + dataBytes.length] = IntegerToByteUtil.checkout(dataBytes, 0);
		System.arraycopy(endBytes, 0, allBytes, cabinetBytes.length + dataBytes.length + 1, endBytes.length);
		//模板指令数据
		byte[] templateBytes = new byte[cmdBytes.length + featureBytes.length + 2];
		byte[] statusCodeExBytes = ArrayUtils.subarray(bytes, 10, 11);
		System.arraycopy(statusCodeExBytes, 0, templateBytes, 0, statusCodeExBytes.length);
		System.arraycopy(cmdBytes, 1, templateBytes, 1, cmdBytes.length - 1);
		System.arraycopy(featureBytes, 0, templateBytes, cmdBytes.length, featureBytes.length);
		//最后赋值，以免干扰crc校验，切勿移到最前方
		templateBytes[templateBytes.length - 2] = IntegerToByteUtil.checkout(templateBytes, 0);
		System.arraycopy(endBytes, 0, templateBytes, templateBytes.length - 1, endBytes.length);
		System.arraycopy(templateBytes, 0, allBytes, cabinetBytes.length + dataBytes.length + 2, templateBytes.length);
		return allBytes;
	}

}
