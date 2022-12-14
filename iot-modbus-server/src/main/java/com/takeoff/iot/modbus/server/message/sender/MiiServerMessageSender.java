package com.takeoff.iot.modbus.server.message.sender;

import java.util.Arrays;
import java.util.List;

import com.takeoff.iot.modbus.common.bytes.factory.*;
import com.takeoff.iot.modbus.common.entity.AlarmLampData;
import com.takeoff.iot.modbus.common.entity.LcdData;
import com.takeoff.iot.modbus.common.utils.CacheUtils;
import com.takeoff.iot.modbus.common.utils.IntegerToByteUtil;
import com.takeoff.iot.modbus.common.utils.JudgeEmptyUtils;
import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import org.apache.commons.collections.ListUtils;
import org.bouncycastle.util.encoders.Hex;

import com.takeoff.iot.modbus.common.data.MiiData;
import com.takeoff.iot.modbus.common.message.MiiMessage;
import com.takeoff.iot.modbus.common.message.factory.MiiMessageFactory;
import com.takeoff.iot.modbus.common.message.factory.MiiOutMessageFactory;
import com.takeoff.iot.modbus.netty.device.MiiControlCentre;

import lombok.extern.slf4j.Slf4j;

/**
 * 类功能说明：指令下发接口实现<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
public class MiiServerMessageSender implements ServerMessageSender {
	
	private static final MiiBytesFactory<Integer> BYTESFACTORY_SLOT = new MiiSlotBytesFactory();
	
	private static final MiiBytesFactory<String> BYTESFACTORY_STRING = new MiiStrings2BytesFactory();

	private static final MiiBytesFactory<Integer> BYTESFACTORY_MULTI_LOCK = new MiiMultiLockBytesFactory();

	private static final MiiBytesFactory<Integer> BYTESFACTORY_BARCODE = new MiiBarcodeBytesFactory();
	
	private static final MiiBytesFactory<Integer> BYTESFACTORY_FINGER = new MiiFingerBytesFactory();
	
	private static final MiiBytesFactory<Integer> BYTESFACTORY_FINGER_FEATURE = new MiiFingerFeatureBytesFactory();

	private static final MiiBytesFactory<Integer> BYTESFACTORY_LCD = new MiiLcdBatchBytesFactory();

	private static final MiiBytesFactory<Integer> BYTESFACTORY_ALARM_LAMP = new MiiAlarmLampDataBytesFactory();

	private static final MiiMessageFactory<Integer> SINGLE_LOCK = new MiiOutMessageFactory<>(BYTESFACTORY_SLOT);
	
	private static final MiiMessageFactory<Object> MULTI_LOCK = new MiiOutMessageFactory<>(
			new MiiBytesCombinedFactory<Object>(
					new MiiBytesFactorySubWrapper<Integer, Object>(BYTESFACTORY_MULTI_LOCK, 0, 4)
					,new MiiBytesFactorySubWrapper<Integer, Object>(new MiiMultiLockDataBytesFactory(), 4, -1)
					));
	
	private static final MiiMessageFactory<Integer> BARCODE = new MiiOutMessageFactory<>(BYTESFACTORY_BARCODE);
	
	private static final MiiMessageFactory<Integer> BACKLIGHT = new MiiOutMessageFactory<>(BYTESFACTORY_SLOT);

	private static final MiiMessageFactory<Object> FINGER = new MiiOutMessageFactory<>(
			new MiiFingerBytesCombinedFactory<Object>(
					new MiiBytesFactorySubWrapper<Integer, Object>(BYTESFACTORY_FINGER, 0, 10)
					,new MiiBytesFactorySubWrapper<String, Object>(BYTESFACTORY_STRING, 10, -1)
			));

	private static final MiiMessageFactory<Object> FINGER_FEATURE = new MiiOutMessageFactory<>(
			new MiiFingerFeatureBytesCombinedFactory<Object>(
					new MiiBytesFactorySubWrapper<Integer, Object>(BYTESFACTORY_FINGER_FEATURE, 0, 11)
					,new MiiBytesFactorySubWrapper<String, Object>(BYTESFACTORY_STRING, 11, 12)
					,new MiiBytesFactorySubWrapper<String, Object>(BYTESFACTORY_STRING, 12, -1)
			));

	private static final MiiMessageFactory<Object> LCD_BATCH = new MiiOutMessageFactory<>(
			new MiiBytesCombinedFactory<Object>(
					new MiiBytesFactorySubWrapper<Integer, Object>(BYTESFACTORY_LCD, 0, 4)
					,new MiiBytesFactorySubWrapper<String, Object>(BYTESFACTORY_STRING, 4, -1)
			));

	private static final MiiMessageFactory<Object> ALARM_LAMP = new MiiOutMessageFactory<>(
			new MiiBytesCombinedFactory<Object>(
					new MiiBytesFactorySubWrapper<Integer, Object>(BYTESFACTORY_ALARM_LAMP, 0, 4)
					,new MiiBytesFactorySubWrapper<String, Object>(BYTESFACTORY_STRING, 4, -1)
			));

	public MiiServerMessageSender(){

	}
	
	private <E> void sendMessage(MiiMessageFactory<E> factory, String deviceIp, E... datas){
		if(JudgeEmptyUtils.isEmpty(CacheUtils.get(deviceIp))){
			log.info("未找到对应的客户端："+ deviceIp +" 的通讯连接，下发指令失败");
		}else{
			MiiChannel channel = (MiiChannel) CacheUtils.get(deviceIp);
			MiiMessage message = factory.buildMessage(deviceIp, datas);
			log.info("待下发指令数据："+Hex.toHexString(message.toBytes()));
			channel.send(message);
		}
	}
	
	@Override
	public void unlock(String deviceIp, int device) {
		sendMessage(SINGLE_LOCK, deviceIp, MiiMessage.LOCK, device, MiiData.NULL, MiiData.NULL, MiiData.ONELOCK);
	}
	
	@Override
	public void unlock(String deviceIp, int device, Integer... arr) {
		sendMessage(MULTI_LOCK, deviceIp,
				ListUtils.union(Arrays.asList(MiiMessage.LOCK, MiiData.ALL, MiiData.ALL, MiiData.ALL), Arrays.asList(arr)).toArray());
	}
	
	@Override
	public void barcode(String deviceIp, int device, int mode) {
		sendMessage(BARCODE, deviceIp, MiiMessage.BARCODE, device, MiiData.NULL, MiiData.NULL, mode, MiiData.CODE_7E);
	}
	
	@Override
	public void backlight(String deviceIp, int device, int status) {
		sendMessage(BACKLIGHT, deviceIp, MiiMessage.BACKLIGHT, device, MiiData.NULL, MiiData.NULL, status);
	}

	@Override
	public void registerFinger(String cabinetGroup, int cabinet, int fingerId) {
		String fingerIdStr = Hex.toHexString(IntegerToByteUtil.intToBytes(fingerId));
		sendMessage(FINGER, cabinetGroup, MiiMessage.FINGER, cabinet, MiiData.NULL, MiiData.NULL,
				MiiMessage.USER_CODE, MiiMessage.STATUS_CODE, MiiData.CMD_REGISTER, MiiMessage.DEVID, MiiMessage.GID, MiiMessage.END_CODE, fingerIdStr);
	}

	@Override
	public void deleteFinger(String cabinetGroup, int cabinet, int fingerId) {
		String fingerIdStr = Hex.toHexString(IntegerToByteUtil.intToBytes(fingerId));
		sendMessage(FINGER, cabinetGroup, MiiMessage.FINGER, cabinet, MiiData.NULL, MiiData.NULL,
				MiiMessage.USER_CODE, MiiMessage.STATUS_CODE, MiiData.CMD_DELETE_ONE, MiiMessage.DEVID, MiiMessage.GID, MiiMessage.END_CODE, fingerIdStr);
	}

	@Override
	public void deleteAllFinger(String cabinetGroup, int cabinet) {
		String fingerIdStr = Hex.toHexString(IntegerToByteUtil.intToBytes(MiiData.NULL));
		sendMessage(FINGER, cabinetGroup, MiiMessage.FINGER, cabinet, MiiData.NULL, MiiData.NULL,
				MiiMessage.USER_CODE, MiiMessage.STATUS_CODE, MiiData.CMD_DELETE_ALL, MiiMessage.DEVID, MiiData.NULL, MiiMessage.GID, MiiMessage.END_CODE, fingerIdStr);
	}

	@Override
	public void getFingerList(String cabinetGroup, int cabinet) {
		String fingerIdStr = Hex.toHexString(IntegerToByteUtil.intToBytes(MiiData.NULL));
		sendMessage(FINGER, cabinetGroup, MiiMessage.FINGER, cabinet, MiiData.NULL, MiiData.NULL,
				MiiData.WDH320S_USER_Tran, MiiMessage.STATUS_CODE, MiiData.CMD_UPLOAD_ALL_ID, MiiMessage.DEVID, MiiData.NULL, MiiMessage.GID, MiiMessage.END_CODE, fingerIdStr);
	}

	@Override
	public void getFingerFeature(String cabinetGroup, int cabinet, int fingerId) {
		String fingerIdStr = Hex.toHexString(IntegerToByteUtil.intToBytes(fingerId));
		sendMessage(FINGER, cabinetGroup, MiiMessage.FINGER, cabinet, MiiData.ONE, MiiData.ONE,
				MiiData.WDH320S_USER_Tran, MiiMessage.STATUS_CODE, MiiData.CMD_UPLOAD_INFOR_TEMPLATES, MiiMessage.DEVID, MiiMessage.GID, MiiMessage.END_CODE, fingerIdStr);
	}

	@Override
	public void writeFingerFeature(String cabinetGroup, int cabinet, int fingerId, byte[] feature) {
		String fingerIdStr = Hex.toHexString(IntegerToByteUtil.intToBytes(fingerId));
		String featureStr = Hex.toHexString(feature);
		sendMessage(FINGER_FEATURE, cabinetGroup, MiiMessage.FINGER, cabinet, MiiData.ONE, MiiData.ONE,
				MiiData.WDH320S_USER_Tran, MiiMessage.STATUS_CODE, MiiData.CMD_DOWNLOAD_INFOR_TEMPLATES, MiiMessage.DEVID, MiiMessage.GID, MiiMessage.END_CODE, MiiMessage.STATUS_CODE_EX, fingerIdStr, featureStr);
	}

	@Override
	public void lcdBatch(List<LcdData> lcdDataList) {
		if(!JudgeEmptyUtils.isEmpty(lcdDataList)){
			for(LcdData lcdData : lcdDataList){
				MiiLcdDataBytesFactory lcdDataBytesFactory = new MiiLcdDataBytesFactory();
				String lcdDataStr = Hex.toHexString(lcdDataBytesFactory.toBytes(lcdData));
				sendMessage(LCD_BATCH, lcdData.getDeviceIp(), MiiMessage.LCD, lcdData.getDevice(), lcdData.getShelf(), lcdData.getSlot(), lcdDataStr);
			}
		}
	}

	@Override
	public void alarmLamp(AlarmLampData alarmLampData) {
		if(!JudgeEmptyUtils.isEmpty(alarmLampData)){
			MiiLampColorDataBytesFactory alarmLampDataBytesFactory = new MiiLampColorDataBytesFactory();
			String alarmLampDataStr = Hex.toHexString(alarmLampDataBytesFactory.toBytes(alarmLampData));
			sendMessage(ALARM_LAMP, alarmLampData.getDeviceIp(), MiiMessage.WLED, alarmLampData.getDevice(), alarmLampData.getShelf(), alarmLampData.getSlot(), alarmLampDataStr);
		}
	}


}
