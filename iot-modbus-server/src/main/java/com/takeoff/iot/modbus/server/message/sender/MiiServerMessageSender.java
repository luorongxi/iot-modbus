package com.takeoff.iot.modbus.server.message.sender;

import java.util.Arrays;

import com.takeoff.iot.modbus.netty.bytes.factory.*;
import com.takeoff.iot.modbus.netty.device.MiiControlCentre;
import com.takeoff.iot.modbus.netty.data.base.MiiData;
import com.takeoff.iot.modbus.netty.message.MiiMessage;
import com.takeoff.iot.modbus.netty.message.factory.MiiMessageFactory;
import com.takeoff.iot.modbus.netty.message.factory.MiiOutMessageFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.bouncycastle.util.encoders.Hex;

/**
 * 类功能说明：指令下发接口实现<br/>
 * 公司名称：takeoff开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
public class MiiServerMessageSender implements ServerMessageSender {
	
	private static final MiiBytesFactory<Integer> BYTESFACTORY_SLOT = new MiiSlotBytesFactory();
	
	private static final MiiBytesFactory<String> BYTESFACTORY_STRING = new MiiStrings2BytesFactory();
	
	private static final MiiBytesFactory<Integer> BYTESFACTORY_FINGER = new MiiFingerBytesFactory();
	
	private static final MiiBytesFactory<Integer> BYTESFACTORY_FINGER_FEATURE = new MiiFingerFeatureBytesFactory();
	
	private static final MiiBytesFactory<Integer> BYTESFACTORY_MULTI_LOCK = new MiiMultiLockBytesFactory();

	private static final MiiMessageFactory<Integer> SINGLE_LOCK = new MiiOutMessageFactory<>(BYTESFACTORY_SLOT);
	
	private static final MiiMessageFactory<Object> MULTI_LOCK = new MiiOutMessageFactory<>(
			new MiiBytesCombinedFactory<Object>(
					new MiiBytesFactorySubWrapper<Integer, Object>(BYTESFACTORY_MULTI_LOCK, 0, 4)
					,new MiiBytesFactorySubWrapper<Integer, Object>(new MiiMultiLockDataBytesFactory(), 4, -1)
					));
	
	private static final MiiMessageFactory<Integer> BARCODE = new MiiOutMessageFactory<>(BYTESFACTORY_SLOT);
	
	private static final MiiMessageFactory<Integer> BACKLIGHT = new MiiOutMessageFactory<>(BYTESFACTORY_SLOT);
	
	private static final MiiMessageFactory<Integer> FINGER = new MiiOutMessageFactory<>(BYTESFACTORY_FINGER);
	
	private static final MiiMessageFactory<Object> FINGER_FEATURE = new MiiOutMessageFactory<>(
			new MiiFingerFeatureBytesCombinedFactory<Object>(
					new MiiBytesFactorySubWrapper<Integer, Object>(BYTESFACTORY_FINGER_FEATURE, 0, 11)
					,new MiiBytesFactorySubWrapper<String, Object>(BYTESFACTORY_STRING, 11, -1)
					));

	
	private MiiControlCentre centre;
	
	public MiiServerMessageSender(MiiControlCentre centre){
		this.centre = centre;
	}
	
	private <E> void sendMessage(MiiMessageFactory<E> factory,String deviceGroup, E... datas){
		MiiMessage message = factory.buildMessage(deviceGroup, datas);
		log.info("待下发指令数据："+Hex.toHexString(message.toBytes()));
		centre.get(deviceGroup).send(message);
	}
	
	@Override
	public void unlock(String deviceGroup, int device) {
		sendMessage(SINGLE_LOCK, deviceGroup, MiiMessage.LOCK, device, MiiData.NULL, MiiData.NULL, MiiData.ONELOCK);
	}
	
	@Override
	public void unlock(String deviceGroup, int device, Integer... arr) {
		sendMessage(MULTI_LOCK, deviceGroup,
				ListUtils.union(Arrays.asList(MiiMessage.LOCK, MiiData.ALL, MiiData.ALL, MiiData.ALL), Arrays.asList(arr)).toArray());
	}
	
	@Override
	public void barcode(String deviceGroup, int device, int mode) {
		sendMessage(BARCODE, deviceGroup, MiiMessage.BARCODE, device, MiiData.NULL, MiiData.NULL, mode);
	}
	
	@Override
	public void backlight(String deviceGroup, int device, int status) {
		sendMessage(BACKLIGHT, deviceGroup, MiiMessage.BACKLIGHT, device, MiiData.NULL, MiiData.NULL, status);
	}
	
	@Override
	public void registerFinger(String deviceGroup, int device, int fingerId) {
		sendMessage(FINGER, deviceGroup, MiiMessage.FINGER, device, MiiData.NULL, MiiData.NULL,
				MiiMessage.USER_CODE, MiiMessage.STATUS_CODE, MiiData.CMD_REGISTER, MiiMessage.DEVID, fingerId, MiiMessage.GID, MiiMessage.END_CODE);
	}
	
	@Override
	public void deleteFinger(String deviceGroup, int device, int fingerId) {
		sendMessage(FINGER, deviceGroup, MiiMessage.FINGER, device, MiiData.NULL, MiiData.NULL,
				MiiMessage.USER_CODE, MiiMessage.STATUS_CODE, MiiData.CMD_DELETE_ONE, MiiMessage.DEVID, fingerId, MiiMessage.GID, MiiMessage.END_CODE);
	}
	
	@Override
	public void deleteAllFinger(String deviceGroup, int device) {
		sendMessage(FINGER, deviceGroup, MiiMessage.FINGER, device, MiiData.NULL, MiiData.NULL,
				MiiMessage.USER_CODE, MiiMessage.STATUS_CODE, MiiData.CMD_DELETE_ALL, MiiMessage.DEVID, MiiData.NULL, MiiMessage.GID, MiiMessage.END_CODE);
	}
	
	@Override
	public void getFingerList(String deviceGroup, int device) {
		sendMessage(FINGER, deviceGroup, MiiMessage.FINGER, device, MiiData.NULL, MiiData.NULL,
				MiiData.WDH320S_USER_Tran, MiiMessage.STATUS_CODE, MiiData.CMD_UPLOAD_ALL_ID, MiiMessage.DEVID, MiiData.NULL, MiiMessage.GID, MiiMessage.END_CODE);
	}
	
	@Override
	public void getFingerFeature(String deviceGroup, int device, int fingerId) {
		sendMessage(FINGER, deviceGroup, MiiMessage.FINGER, device, MiiData.ONE, MiiData.ONE,
				MiiData.WDH320S_USER_Tran, MiiMessage.STATUS_CODE, MiiData.CMD_UPLOAD_INFOR_TEMPLATES, MiiMessage.DEVID, fingerId, MiiMessage.GID, MiiMessage.END_CODE);
	}
	
	@Override
	public void writeFingerFeature(String deviceGroup, int device, int fingerId, byte[] feature) {
		String featureStr = Hex.toHexString(feature);
		sendMessage(FINGER_FEATURE, deviceGroup, MiiMessage.FINGER, device, MiiData.ONE, MiiData.ONE,
				MiiData.WDH320S_USER_Tran, MiiMessage.STATUS_CODE, MiiData.CMD_DOWNLOAD_INFOR_TEMPLATES, MiiMessage.DEVID, fingerId, MiiMessage.GID, MiiMessage.END_CODE, featureStr);
	}


}
