package com.takeoff.iot.modbus.serialport.service.impl;

import com.takeoff.iot.modbus.common.bytes.factory.MiiBytesCombinedFactory;
import com.takeoff.iot.modbus.common.bytes.factory.MiiBytesFactory;
import com.takeoff.iot.modbus.common.bytes.factory.MiiBytesFactorySubWrapper;
import com.takeoff.iot.modbus.common.bytes.factory.MiiMultiLockBytesFactory;
import com.takeoff.iot.modbus.common.bytes.factory.MiiMultiLockDataBytesFactory;
import com.takeoff.iot.modbus.common.bytes.factory.MiiSlotBytesFactory;
import com.takeoff.iot.modbus.common.data.MiiData;
import com.takeoff.iot.modbus.common.message.MiiMessage;
import com.takeoff.iot.modbus.common.message.factory.MiiMessageFactory;
import com.takeoff.iot.modbus.common.message.factory.MiiOutMessageFactory;
import com.takeoff.iot.modbus.serialport.service.SerialportSendService;
import com.takeoff.iot.modbus.serialport.service.SerialportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 类功能说明：串口发送指令接口实现类<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
@Service
public class SerialportSendServiceImpl implements SerialportSendService {

    @Resource
    private SerialportService serialportService;

    private static final MiiBytesFactory<Integer> BYTESFACTORY_SLOT = new MiiSlotBytesFactory();

    private static final MiiMessageFactory<Integer> SINGLE_LOCK = new MiiOutMessageFactory<>(BYTESFACTORY_SLOT);

    private static final MiiBytesFactory<Integer> BYTESFACTORY_MULTI_LOCK = new MiiMultiLockBytesFactory();

    private static final MiiMessageFactory<Integer> BARCODE = new MiiOutMessageFactory<>(BYTESFACTORY_SLOT);

    private static final MiiMessageFactory<Integer> BACKLIGHT = new MiiOutMessageFactory<>(BYTESFACTORY_SLOT);

    private static final MiiMessageFactory<Object> MULTI_LOCK = new MiiOutMessageFactory<>(
            new MiiBytesCombinedFactory<Object>(
                    new MiiBytesFactorySubWrapper<Integer, Object>(BYTESFACTORY_MULTI_LOCK, 0, 4)
                    ,new MiiBytesFactorySubWrapper<Integer, Object>(new MiiMultiLockDataBytesFactory(), 4, -1)
            ));


    private <E> void sendMessage(MiiMessageFactory<E> factory, String deviceGroup, E... datas){
        MiiMessage message = factory.buildMessage(deviceGroup, datas);
        log.info("待下发指令数据："+ Hex.toHexString(message.toBytes()));
        serialportService.serialportSendData(message.toBytes());
    }


    @Override
    public void unlock(String deviceGroup, int device) {
        sendMessage(SINGLE_LOCK, deviceGroup, MiiMessage.LOCK, device, MiiData.NULL, MiiData.NULL, MiiData.ONELOCK);
    }

    @Override
    public void unlock(String deviceGroup, int device, Integer... arr) {
        sendMessage(MULTI_LOCK, deviceGroup,
                ListUtils.union(Arrays.asList(MiiMessage.LOCK, MiiData.ALL, MiiData.ALL, MiiData.ALL), Arrays.asList(arr)).toArray());
    }

    @Override
    public void barcode(String deviceGroup, int device, int mode) {
        sendMessage(BARCODE, deviceGroup, MiiMessage.BARCODE, device, MiiData.NULL, MiiData.NULL, mode);
    }

    @Override
    public void backlight(String deviceGroup, int device, int status) {
        sendMessage(BACKLIGHT, deviceGroup, MiiMessage.BACKLIGHT, device, MiiData.NULL, MiiData.NULL, status);
    }
}
