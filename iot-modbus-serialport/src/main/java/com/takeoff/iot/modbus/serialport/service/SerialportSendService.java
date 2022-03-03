package com.takeoff.iot.modbus.serialport.service;

import com.takeoff.iot.modbus.common.entity.AlarmLampData;
import com.takeoff.iot.modbus.common.entity.LcdData;

import java.util.List;

/**
 * 类功能说明：串口发送指令接口<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public interface SerialportSendService {

    /**
     * 发送控制单锁指令.
     * @param deviceGroup 设备组编码
     * @param device 设备号
     */
    void unlock(String deviceGroup, int device);

    /**
     * 发送控制多开锁指令.
     * @param deviceGroup 设备组编码
     * @param device 设备号
     * @param arr 数组（门锁号、门锁状态、门锁号、门锁状态...）
     */
    void unlock(String deviceGroup, int device, Integer... arr);

    /**
     * 发送设置扫码模式指令.
     * @param deviceGroup 设备组编码
     * @param device 设备号
     * @param mode  扫码模式 {@link com.takeoff.iot.modbus.common.data.MiiData} ONCE ALWAYS NEVER
     */
    void barcode(String deviceGroup, int device, int mode);

    /**
     * 发送背光灯指令.
     * @param deviceGroup 设备组编码
     * @param device 设备号
     * @param status 开关状态 {@link com.takeoff.iot.modbus.common.data.MiiData} ON OFF
     */
    void backlight(String deviceGroup, int device, int status);

    /**
     * 按柜体批量发送lCD数据
     * @param lcdDataList 下发lcd显示内容
     */
    void lcdBatch(List<LcdData> lcdDataList);

    /**
     * 下发控制三色报警灯数据
     * 参数：@param alarmLampData 下发报警灯数据
     */
    void alarmLamp(AlarmLampData alarmLampData);

}
