package com.takeoff.iot.modbus.serialport.data;

import com.takeoff.iot.modbus.netty.data.MiiHumitureData;
import lombok.Getter;

/**
 * 类功能说明：温湿度数据<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Getter
public class HumitureData extends ReceiveDataEvent{

    /**
     * 温度
     */
    private double temperature;

    /**
     * 湿度
     */
    private double humidity;

    public HumitureData(Object source, int command, MiiHumitureData data) {
        super(source, command, data.device(), data.shelf(), data.slot());
        this.temperature = data.temperature();
        this.humidity = data.humidity();
    }
}
