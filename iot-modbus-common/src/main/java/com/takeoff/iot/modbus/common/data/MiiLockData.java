package com.takeoff.iot.modbus.common.data;

import java.util.ArrayList;
import java.util.List;

import com.takeoff.iot.modbus.common.entity.LockStatus;
import com.takeoff.iot.modbus.common.utils.IntegerToByteUtil;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 类功能说明：门锁指令数据<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiLockData extends MiiSlotData implements MiiData {
	
	private List<LockStatus> list;
	
	public MiiLockData(byte[] datas) {
		super(datas);
		byte[] dataByte = ArrayUtils.subarray(datas, CONTENT_INDEX, datas.length);
		if(dataByte.length > 3){
			dataByte = ArrayUtils.subarray(dataByte, CONTENT_INDEX - 1, dataByte.length);
		}
		list = new ArrayList<LockStatus>();
		LockStatus lockStatus = null;
		for(int i = 0; i < dataByte.length; i++){
			byte[] bytes = new byte[1];
			bytes[0] = dataByte[i];
			Integer content = IntegerToByteUtil.bytesToInt(bytes);
			if (i % 3 == 0){
				lockStatus = new LockStatus();
				lockStatus.setLockNo(content);
				if(dataByte.length == 1){
					list.add(lockStatus);
				}
			}else if ((i+1)%3 == 0){
				lockStatus.setSensorStatus(content);
				list.add(lockStatus);
			}else{
				lockStatus.setLockStatus(content);
			}
		}
	}
	
	public List<LockStatus> list(){
		return list;
	}
}
