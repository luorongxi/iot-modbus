package com.takeoff.iot.modbus.netty.data;

import com.takeoff.iot.modbus.netty.data.base.MiiSlotData;

/**
 * 类功能说明：温湿度指令数据<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiHumitureData extends MiiSlotData {
	
	private double temperature;
	private double humidity;
	
	
	public MiiHumitureData(byte[] datas) {
		super(datas);
		short humiture = (short)(((datas[8] & 0x00FF) << 8) | (0x00FF & datas[7]));
        double td = ((humiture * 175.72) / 65536.0 - 46.85);
        temperature = (double)Math.round(td*100)/100;
        humiture =(short)(((datas[10] & 0x00FF) << 8) | (0x00FF & datas[9]));
        double hd = ((humiture * 125.0) / 65536.0 - 6);
        humidity = (double)Math.round(hd*100)/100;
	}

	public double temperature(){
		return temperature;
	}
	
	public double humidity(){
		return humidity;
	}

}
