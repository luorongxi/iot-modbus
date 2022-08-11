package com.takeoff.iot.modbus.serialport.service;

/**
 * 类功能说明：串口通讯接口<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public interface SerialportService {

    /**
     * 连接串口
     * @param port
     * @param baudrate
     * @param timeout
     * @param thread
     * @param sleepTime
     */
    void openComPort(String port, Integer baudrate, Integer timeout, Integer thread, int sleepTime);

    /**
     * netty连接串口
     * @param port
     * @param baudrate
     * @param thread
     */
    void openComPort(String port, Integer baudrate, Integer thread);

    /**
     * 关闭串口
     */
    void closeSerialPort();

    /**
     * 发送数据到串口
     * @param bytes
     */
    void serialportSendData(byte[] bytes);

}
