package com.takeoff.iot.modbus.common.entity;

import lombok.Data;

@Data
public class DeviceData {

    /**
     * 设备IP
     */
    private String deviceIp;

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
