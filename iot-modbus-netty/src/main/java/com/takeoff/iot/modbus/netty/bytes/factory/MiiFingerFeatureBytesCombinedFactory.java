package com.takeoff.iot.modbus.netty.bytes.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.takeoff.iot.modbus.netty.utils.IntegerByteTransform;
import org.apache.commons.lang3.ArrayUtils;

public class MiiFingerFeatureBytesCombinedFactory <E> implements MiiBytesFactory<E> {
	
    private MiiBytesFactory<E>[] factorys;
	
	@SafeVarargs
	public MiiFingerFeatureBytesCombinedFactory(MiiBytesFactory<E>... factorys) {
		this.factorys = factorys;
	}

	@Override
	public byte[] toBytes(E... contents) {
		List<Byte> fingerList = new ArrayList<>();
		List<Byte> featureList = new ArrayList<>();
		for(int i=0;i<factorys.length;i++){
			MiiBytesFactory<E> factory = factorys[i];
			if(i == 0){
				fingerList.addAll(Arrays.asList(ArrayUtils.toObject(factory.toBytes(contents))));
			}else{
				featureList.addAll(Arrays.asList(ArrayUtils.toObject(factory.toBytes(contents))));
			}
		}
		byte[] fingerBytes = ArrayUtils.toPrimitive(fingerList.toArray(new Byte[fingerList.size()]));
		byte[] featureBytes = ArrayUtils.toPrimitive(featureList.toArray(new Byte[featureList.size()]));
		byte[] cabinetBytes = ArrayUtils.subarray(fingerBytes, 0, fingerBytes.length - 7);
		byte[] cmdBytes = ArrayUtils.subarray(fingerBytes, fingerBytes.length - 7, fingerBytes.length);
		byte[] dataBytes = new byte[cmdBytes.length + 2];
		System.arraycopy(cmdBytes, 1, dataBytes, 1, cmdBytes.length - 4);
		byte[] lengthBytes = IntegerByteTransform.intToBytes(featureBytes.length);
		System.arraycopy(lengthBytes, 0, dataBytes, dataBytes.length - 5, lengthBytes.length);
		System.arraycopy(cmdBytes, cmdBytes.length -2, dataBytes, dataBytes.length - 3, 1);
		dataBytes[dataBytes.length - 2] = IntegerByteTransform.checkout(dataBytes, 0);
		System.arraycopy(cmdBytes, cmdBytes.length -1, dataBytes, dataBytes.length - 1, 1);
		ArrayList<Byte> fingerFeatureList = new ArrayList<>();
        for (byte b : cmdBytes) {
        	fingerFeatureList.add(b);
        }
        List<Byte> subBuffs = getFingerData(featureBytes, cmdBytes);
        fingerFeatureList.addAll(subBuffs);
        byte[] fingerFeatureBytes = ArrayUtils.toPrimitive(fingerFeatureList.toArray(new Byte[fingerFeatureList.size()]));
        byte[] allBytes = new byte[cabinetBytes.length + dataBytes.length + fingerFeatureBytes.length];
		System.arraycopy(cabinetBytes, 0, allBytes, 0, cabinetBytes.length);
		System.arraycopy(dataBytes, 0, allBytes, cabinetBytes.length, dataBytes.length);
		System.arraycopy(fingerFeatureBytes, 0, allBytes, cabinetBytes.length + dataBytes.length, fingerFeatureBytes.length);
        return allBytes;
	}
	
	private List<Byte> getFingerData(byte[] featureBytes, byte[] cmdBytes) {
        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.add((byte) cmdBytes[cmdBytes.length - 3]);
        bytes.add((byte) 0xFF);
        for (byte b : featureBytes) {
        	bytes.add(b);
        }
        byte[] crcData = getList2byteArrary(bytes);
        bytes.add(IntegerByteTransform.checkout(crcData, 0));
        bytes.add(0, (byte) 0x3E);
        bytes.add((byte) 0x0D);
        return bytes;
    }
	
	public static byte[] getList2byteArrary(List<Byte> a) {
        byte[] buffs = new byte[a.size()];
        int i = 0;
        for (Byte b : a) {
            buffs[i] = b;
            i++;
        }
        return buffs;
    }

}
