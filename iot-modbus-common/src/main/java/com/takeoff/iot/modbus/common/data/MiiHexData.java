package com.takeoff.iot.modbus.common.data;
import org.bouncycastle.util.encoders.Hex;

/**
 * 类功能说明：包含反馈的十六进制信息内容的指令数据处理<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiHexData extends MiiStringData {

	public MiiHexData(byte[] datas) {
		super(datas);
		content = Hex.toHexString(datas, CONTENT_INDEX, length() - CONTENT_INDEX);
	}
	
}
