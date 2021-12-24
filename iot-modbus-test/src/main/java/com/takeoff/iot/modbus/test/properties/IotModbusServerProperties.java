package com.takeoff.iot.modbus.test.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 类功能说明：netty通讯服务端参数配置<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Data
@Component
@ConfigurationProperties(prefix = "iot.netty.server")
public class IotModbusServerProperties {
	
	/**
	 * 是否开启Socket服务
	 */
	private Boolean open;

	/**
	 * Socket服务端口
	 */
	private Integer port;
	
	/**
	 * Socket服务执行线程数
	 */
    private Integer thread;
}
