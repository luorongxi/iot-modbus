package com.takeoff.iot.modbus.common.data;

import com.takeoff.iot.modbus.common.utils.BytesToHexUtil;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 类功能说明：扫码指令处理<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiBarcodeData extends MiiHexData {

	public MiiBarcodeData(byte[] datas) {
		super(datas);
		byte[] barcodeByte = ArrayUtils.subarray(datas, CONTENT_INDEX, datas.length);
		content = BytesToHexUtil.asciiToString(barcodeByte);
	}

}
