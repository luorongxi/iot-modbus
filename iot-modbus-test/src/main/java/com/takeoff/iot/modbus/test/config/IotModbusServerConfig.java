package com.takeoff.iot.modbus.test.config;
import com.takeoff.iot.modbus.common.message.MiiMessage;
import com.takeoff.iot.modbus.server.MiiServer;
import com.takeoff.iot.modbus.test.listener.*;
import com.takeoff.iot.modbus.test.properties.IotModbusServerProperties;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 类功能说明：通讯协议服务端配置注册<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
@Configuration
public class IotModbusServerConfig implements ApplicationRunner {

	@Resource
	private IotModbusServerProperties iotModbusServerProperties;

	@Resource
	private CardListener cardListener;

	@Resource
	private BarCodeListener barCodeListener;

	@Resource
	private BackLightListener backLightListener;

	@Resource
	private LockListener lockListener;

	@Resource
	private FingerListener fingerListener;

	@Resource
	private HumitureListener humitureListener;
	
	@Getter
	private MiiServer miiServer;

	@Override
    public void run(ApplicationArguments args) throws Exception {
		if(iotModbusServerProperties.getOpen()){
			miiServer = new MiiServer(iotModbusServerProperties.getPort(), iotModbusServerProperties.getThread(), iotModbusServerProperties.getHeartBeatTime());
			miiServer.addListener(MiiMessage.BACKLIGHT, backLightListener);
			miiServer.addListener(MiiMessage.LOCK, lockListener);
			miiServer.addListener(MiiMessage.CARD, cardListener);
			miiServer.addListener(MiiMessage.BARCODE, barCodeListener);
			miiServer.addListener(MiiMessage.FINGER, fingerListener);
			miiServer.addListener(MiiMessage.HM, humitureListener);
			log.info("IOT通讯协议已开启Socket服务，占用端口： " + iotModbusServerProperties.getPort() + ",执行线程池线程数:" + iotModbusServerProperties.getThread());
			miiServer.start();
		}else{
			log.info("IOT通讯协议未开启Socket服务");
		}
	}
}
