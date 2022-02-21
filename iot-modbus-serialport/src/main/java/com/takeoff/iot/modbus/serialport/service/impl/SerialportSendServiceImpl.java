package com.takeoff.iot.modbus.serialport.service.impl;

import com.takeoff.iot.modbus.common.bytes.factory.*;
import com.takeoff.iot.modbus.common.data.MiiData;
import com.takeoff.iot.modbus.common.entity.LcdData;
import com.takeoff.iot.modbus.common.message.MiiMessage;
import com.takeoff.iot.modbus.common.message.factory.MiiMessageFactory;
import com.takeoff.iot.modbus.common.message.factory.MiiOutMessageFactory;
import com.takeoff.iot.modbus.common.utils.JudgeEmptyUtils;
import com.takeoff.iot.modbus.serialport.service.SerialportSendService;
import com.takeoff.iot.modbus.serialport.service.SerialportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

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

    private static final MiiBytesFactory<String> BYTESFACTORY_STRING = new MiiStrings2BytesFactory();

    private static final MiiMessageFactory<Integer> SINGLE_LOCK = new MiiOutMessageFactory<>(BYTESFACTORY_SLOT);

    private static final MiiBytesFactory<Integer> BYTESFACTORY_MULTI_LOCK = new MiiMultiLockBytesFactory();

    private static final MiiBytesFactory<Integer> BYTESFACTORY_LCD = new MiiLcdBatchBytesFactory();

    private static final MiiMessageFactory<Integer> BARCODE = new MiiOutMessageFactory<>(BYTESFACTORY_SLOT);

    private static final MiiMessageFactory<Integer> BACKLIGHT = new MiiOutMessageFactory<>(BYTESFACTORY_SLOT);

    private static final MiiMessageFactory<Object> MULTI_LOCK = new MiiOutMessageFactory<>(
            new MiiBytesCombinedFactory<Object>(
                    new MiiBytesFactorySubWrapper<Integer, Object>(BYTESFACTORY_MULTI_LOCK, 0, 4)
                    ,new MiiBytesFactorySubWrapper<Integer, Object>(new MiiMultiLockDataBytesFactory(), 4, -1)
            ));

    private static final MiiMessageFactory<Object> LCD_BATCH = new MiiOutMessageFactory<>(
            new MiiBytesCombinedFactory<Object>(
                    new MiiBytesFactorySubWrapper<Integer, Object>(BYTESFACTORY_LCD, 0, 4)
                    ,new MiiBytesFactorySubWrapper<String, Object>(BYTESFACTORY_STRING, 4, -1)
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

    @Override
    public void lcdBatch(List<LcdData> lcdDataList) {
        if(!JudgeEmptyUtils.isEmpty(lcdDataList)){
            for(LcdData lcdData : lcdDataList){
                MiiLcdData2BytesFactory lcdData2BytesFactory = new MiiLcdData2BytesFactory();
                String lcdDataStr = Hex.toHexString(lcdData2BytesFactory.toBytes(lcdData));
                sendMessage(LCD_BATCH, lcdData.getDeviceGroup(), MiiMessage.LCD, lcdData.getDevice(), lcdData.getShelf(), lcdData.getSlot(), lcdDataStr);
            }
        }
    }
}
