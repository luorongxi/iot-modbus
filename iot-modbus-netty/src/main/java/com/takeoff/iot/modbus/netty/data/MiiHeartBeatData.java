package com.takeoff.iot.modbus.netty.data;

import com.takeoff.iot.modbus.netty.data.base.MiiStringData;
import org.apache.commons.lang3.StringUtils;

/**
 * 类功能说明：心跳指令数据<br/>
 * 公司名称：takeoff开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiHeartBeatData extends MiiStringData {
	
	static final int DEVICE_GROUPNO_INDEX = 1;

	public MiiHeartBeatData(byte[] datas) {
		super(datas);
	}
	
	/**
	 * 返回设备组编码
	 * @return 设备编码
	 */
	public String deviceGroup() {
		return StringUtils.isBlank(content()) ? 
				String.valueOf(toBytes()[DEVICE_GROUPNO_INDEX]) : content();
	}
}
