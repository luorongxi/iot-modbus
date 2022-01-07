package com.takeoff.iot.modbus.common.data;

import org.apache.commons.lang3.ArrayUtils;

import com.takeoff.iot.modbus.common.utils.IntegerToByteUtil;

/**
 * 类功能说明：包含整型的指令数据处理<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiIntegerData extends MiiSlotData {

	protected Integer content;
	
	public MiiIntegerData(byte[] datas) {
		super(datas);
		if(length() > CONTENT_INDEX){
			byte[] countByte = ArrayUtils.subarray(datas, CONTENT_INDEX, datas.length);
			content = IntegerToByteUtil.bytesToInt(countByte);
		}else{
			content = null;
		}
	}

	public Integer content(){
		return content;
	}
}
