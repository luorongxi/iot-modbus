package com.takeoff.iot.modbus.serialport.data;

import com.takeoff.iot.modbus.netty.data.MiiCardData;
import lombok.Getter;

/**
 * 类功能说明：刷卡数据<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Getter
public class CardData extends ReceiveDataEvent {

	private String cardCode;

	public CardData(Object source, int command, MiiCardData data) {
		super(source, command, data.device(), data.shelf(), data.slot());
		this.cardCode = data.content();
	}

}
