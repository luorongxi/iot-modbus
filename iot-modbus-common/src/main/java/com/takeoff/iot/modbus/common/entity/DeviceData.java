package com.takeoff.iot.modbus.common.entity;

import lombok.Data;

@Data
public class DeviceData {

    /**
     * 设备组编码
     */
    private String deviceGroup;

    /**
     * 设备号
     */
    private int device;

    /**
     * 层号
     */
    private int shelf;

    /**
     * 槽位号
     */
    private int slot;
}
