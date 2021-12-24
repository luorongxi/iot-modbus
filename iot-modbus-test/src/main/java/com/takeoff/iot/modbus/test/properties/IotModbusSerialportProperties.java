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
     * 超时时间
     */
    private Integer timeout;

    /**
     * 波特率
     */
    private Integer baudrate;
}
