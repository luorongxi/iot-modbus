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
@ConfigurationProperties(prefix = "iot.netty.client")
public class IotModbusClientProperties {

    /**
     * 是否开启Socket服务
     */
    private Boolean open;

    /**
     * 服务IP
     */
    private String ip;

    /**
     * 服务端口
     */
    private Integer port;

    /**
     * Socket服务执行线程数
     */
    private Integer thread;

    /**
     * 设备组编码
     */
    private String deviceGroup;
}
