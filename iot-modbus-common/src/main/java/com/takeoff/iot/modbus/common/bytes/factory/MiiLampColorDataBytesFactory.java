package com.takeoff.iot.modbus.common.bytes.factory;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.takeoff.iot.modbus.common.data.MiiData;
import com.takeoff.iot.modbus.common.entity.AlarmLampData;
import com.takeoff.iot.modbus.common.entity.LampColorData;
import com.takeoff.iot.modbus.common.utils.IntegerToByteUtil;
import com.takeoff.iot.modbus.common.utils.JudgeEmptyUtils;
import org.apache.commons.lang3.ArrayUtils;

public class MiiLampColorDataBytesFactory implements MiiEntityBytesFactory<AlarmLampData> {

private static final Charset DEFAULT_CHARSET = Charset.forName("GBK");
	
	private Charset charset;
	
	public MiiLampColorDataBytesFactory(Charset charset){
		this.charset = charset;
	}
	
	public MiiLampColorDataBytesFactory(){
		this(DEFAULT_CHARSET);
	}
	
	/**
	 * 描述: 字符串转16进制byte[] <br/>
	 * @see com.takeoff.iot.modbus.common.bytes.factory.MiiBytesFactory#toBytes(java.lang.Object[]) <br/>
	 */
	@Override
	public byte[] toBytes(String... contents) {
		List<Byte> resList = new ArrayList<>();
		for(int i = 0; i < contents.length;i++){
			String content = contents[i];
			Collections.addAll(resList, ArrayUtils.toObject(content.getBytes(charset)));
		}
		return ArrayUtils.toPrimitive(
				resList.toArray(new Byte[resList.size()]));
	}

	@Override
	public byte[] toBytes(AlarmLampData alarmLampData) {
		byte[] alarmLampDataBytes = new byte[0]; 
		if(!JudgeEmptyUtils.isEmpty(alarmLampData.getLampColorDataList()) && alarmLampData.getLampColorDataList().size() == 4){
			int sumLength = 0;
			Map<Integer, byte[]> map = new HashMap<>();
			for(int i=0; i<alarmLampData.getLampColorDataList().size(); i++){
				LampColorData lampColorData = alarmLampData.getLampColorDataList().get(i);
				//开关
				byte[] onOffByte = new byte[0];
				if(JudgeEmptyUtils.isEmpty(lampColorData.getOnOff())){
					onOffByte = IntegerToByteUtil.intToByteArray(lampColorData.getOnOff(), 1);
				}
				//亮灯时间
				byte[] onTimeByte = new byte[0];
				if(JudgeEmptyUtils.isEmpty(lampColorData.getOnTime())){
					onTimeByte = IntegerToByteUtil.intToBytes(lampColorData.getOnTime());
				}
				//灭灯时间
				byte[] offTimeByte = new byte[0];
				if(JudgeEmptyUtils.isEmpty(lampColorData.getOffTime())){
					offTimeByte = IntegerToByteUtil.intToBytes(lampColorData.getOffTime());
				}
				sumLength = sumLength + onOffByte.length + onTimeByte.length + offTimeByte.length;
				byte[] colorDataBytes = new byte[onOffByte.length + onTimeByte.length + offTimeByte.length];
				System.arraycopy(onOffByte, 0, colorDataBytes, 0, onOffByte.length);
				System.arraycopy(onTimeByte, 0, colorDataBytes, onOffByte.length, onTimeByte.length);
				System.arraycopy(offTimeByte, 0, colorDataBytes, onOffByte.length + onTimeByte.length, offTimeByte.length);
				map.put(lampColorData.getTyte(), colorDataBytes);
			}
			alarmLampDataBytes = alarmLampDataHandle(sumLength, map, alarmLampData.getAlarmTime());
		}
		return alarmLampDataBytes;
	}

	private byte[] alarmLampDataHandle(int sumLength, Map<Integer, byte[]> map, int alarmTime) {
		byte[] alarmLampDataBytes = new byte[0]; 
		if(sumLength > 0){
			alarmLampDataBytes = new byte[sumLength + 2];
			for(Map.Entry<Integer, byte[]> entry : map.entrySet()){
				if(MiiData.LAMP_RED == entry.getKey()){
					System.arraycopy(entry.getValue(), 0, alarmLampDataBytes, 0, entry.getValue().length);
				}else if(MiiData.LAMP_GREEN == entry.getKey()){
					System.arraycopy(entry.getValue(), 0, alarmLampDataBytes, 5, entry.getValue().length);
				}else if(MiiData.LAMP_YELLOW == entry.getKey()){
					System.arraycopy(entry.getValue(), 0, alarmLampDataBytes, 10, entry.getValue().length);
				}else if(MiiData.BUZZER == entry.getKey()){
					System.arraycopy(entry.getValue(), 0, alarmLampDataBytes, 15, entry.getValue().length);
				}
			}
			//亮灯时间
			byte[] alarmTimeByte = new byte[0];
			if(JudgeEmptyUtils.isEmpty(alarmTime)){
				alarmTimeByte = IntegerToByteUtil.intToBytes(alarmTime);
			}
			System.arraycopy(alarmTimeByte, 0, alarmLampDataBytes, alarmLampDataBytes.length - 2, alarmTimeByte.length);
		}
		return alarmLampDataBytes;
	}

}
