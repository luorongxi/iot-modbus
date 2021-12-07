package com.takeoff.iot.modbus.netty.data.base;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 类功能说明：包含反馈内容的指令数据处理<br/>
 * 公司名称：takeoff开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiStringData extends MiiSlotData {
	
	protected String content;
	
	public MiiStringData(byte[] datas) {
		super(datas);
		if(length() > CONTENT_INDEX){
			content = new String(ArrayUtils.subarray(datas, CONTENT_INDEX, length()));
		}else{
			content = null;
		}
	}
	
	/**
	 * 返回柜体的反馈内容信息
	 * @return 反馈内容信息
	 */
	public String content() {
		return content;
	}
}
