package com.takeoff.iot.modbus.serialport.data;

import com.takeoff.iot.modbus.netty.data.MiiFingerData;
import lombok.Getter;

import java.util.List;

/**
 * 类功能说明：指静脉数据<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Getter
public class FingerData extends ReceiveDataEvent {

    private int fingerType;
    private int fingerCmd;
    private int fingerId;
    private List fingerIdList;
    private byte[] fingerTemplate;

    public FingerData(Object source, int command, MiiFingerData data) {
        super(source, command, data.device(), data.shelf(), data.slot());
        this.fingerType = data.fingerType();
        this.fingerCmd = data.fingerCmd();
        this.fingerId = data.fingerId();
        this.fingerIdList = data.fingerIdList();
        this.fingerTemplate = data.fingerTemplate();
    }

}
