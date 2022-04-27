package com.takeoff.iot.modbus.client.entity;

import lombok.Data;

@Data
public class OpenLock {

    /**
     * 服务IP
     */
    private String ip;

    /**
     * 设备号
     */
    private int device;

    /**
     * 锁状态（0：上锁；1：开锁）
     */
    private int status;
}
