package com.takeoff.iot.modbus.test.config;

import com.takeoff.iot.modbus.client.MiiClient;
import com.takeoff.iot.modbus.common.message.MiiMessage;
import com.takeoff.iot.modbus.test.listener.LockListener;
import com.takeoff.iot.modbus.test.properties.IotModbusClientProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 类功能说明：通讯协议客户端配置注册<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
@Configuration
public class IotModbusClientConfig implements ApplicationRunner {

    @Resource
    private IotModbusClientProperties iotModbusClientProperties;

    @Resource
    private LockListener lockListener;

    @Getter
    private MiiClient miiClient;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(iotModbusClientProperties.getOpen()){
            miiClient = new MiiClient(iotModbusClientProperties.getDeviceGroup(), iotModbusClientProperties.getThread(), iotModbusClientProperties.getHeartBeatTime());
            String[] ips = new String[1];
            if(iotModbusClientProperties.getIps().contains(",")){
                ips = iotModbusClientProperties.getIps().split(",");
            }else{
                ips[0] = iotModbusClientProperties.getIps();
            }
            miiClient.addListener(MiiMessage.LOCK, lockListener);
            for(int i=0;i<ips.length;i++){
                miiClient.connect(ips[i], iotModbusClientProperties.getPort(), iotModbusClientProperties.getReconnectTime());
                log.info("IOT通讯协议链接服务端，占用IP： " + ips[i] + ",链接端口:" + iotModbusClientProperties.getPort());
                //miiClient.disconnect(ips[i]);
            }

        }
    }
}
