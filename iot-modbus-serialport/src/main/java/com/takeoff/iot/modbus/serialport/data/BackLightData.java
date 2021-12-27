package com.takeoff.iot.modbus.serialport.data;

import com.takeoff.iot.modbus.common.data.MiiBackLightData;

import lombok.Getter;

/**
 * 类功能说明：背光灯数据<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Getter
public class BackLightData extends ReceiveDataEvent {

    private Integer statusCode;

    public BackLightData(Object source, Integer command, MiiBackLightData data) {
        super(source, command, data.device(), data.shelf(), data.slot());
        this.statusCode = data.statusCode();
    }
}
