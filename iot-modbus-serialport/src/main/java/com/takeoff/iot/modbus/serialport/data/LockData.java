package com.takeoff.iot.modbus.serialport.data;

import java.util.List;

import com.takeoff.iot.modbus.common.data.MiiLockData;
import com.takeoff.iot.modbus.common.entity.LockStatus;

import lombok.Getter;

/**
 * 类功能说明：门锁数据<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Getter
public class LockData extends ReceiveDataEvent {

    /**
     * 锁状态
     */
    private List<LockStatus> list;

    public LockData(Object source, int command, MiiLockData data) {
        super(source, command, data.device(), data.shelf(), data.slot());
        this.list = data.list();
    }

}
