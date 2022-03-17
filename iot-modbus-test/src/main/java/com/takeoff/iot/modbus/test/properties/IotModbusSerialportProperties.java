package com.takeoff.iot.modbus.test.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 类功能说明：netty通讯客户端参数配置<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Data
@Component
@ConfigurationProperties(prefix = "iot.serialport")
public class IotModbusSerialportProperties {

    /**
     * 是否开启串口服务
     */
    private Boolean open;

    /**
     * 串口号
     */
    private String port;

    /**
     * 是否使用netty对数据进行拆包处理
     */
    private Boolean netty;

    /**
     * 链接超时时间，不使用netty对数据进行拆包处理时必填
     */
    private Integer timeout;

    /**
     * 波特率
     */
    private Integer baudrate;

    /**
     * 设置通讯服务执行线程数
     */
    private Integer thread;
}
