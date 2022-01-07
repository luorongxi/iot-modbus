package com.takeoff.iot.modbus.serialport.data.factory;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.takeoff.iot.modbus.common.data.MiiBackLightData;
import com.takeoff.iot.modbus.common.data.MiiBarcodeData;
import com.takeoff.iot.modbus.common.data.MiiCardData;
import com.takeoff.iot.modbus.common.data.MiiFingerData;
import com.takeoff.iot.modbus.common.data.MiiHeartBeatData;
import com.takeoff.iot.modbus.common.data.MiiHumitureData;
import com.takeoff.iot.modbus.common.data.MiiLockData;
import com.takeoff.iot.modbus.common.message.MiiMessage;
import com.takeoff.iot.modbus.common.utils.IntegerToByteUtil;
import com.takeoff.iot.modbus.common.utils.ModbusCrc16Utils;
import com.takeoff.iot.modbus.serialport.data.BackLightData;
import com.takeoff.iot.modbus.serialport.data.BarCodeData;
import com.takeoff.iot.modbus.serialport.data.CardData;
import com.takeoff.iot.modbus.serialport.data.FingerData;
import com.takeoff.iot.modbus.serialport.data.HeartBeatData;
import com.takeoff.iot.modbus.serialport.data.HumitureData;
import com.takeoff.iot.modbus.serialport.data.LockData;
import com.takeoff.iot.modbus.serialport.data.ReceiveDataEvent;
import com.takeoff.iot.modbus.common.utils.JudgeEmptyUtils;
import com.takeoff.iot.modbus.serialport.utils.SpringContextUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 类功能说明：接收指令处理工厂<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
@Service
public class SerialportDataReceiveFactory implements SerialportDataFactory {

    @Override
    public void buildData(byte[] msg) {
        byte[] headBytes = {msg[MiiMessage.BEGIN_INDEX]};
        if(!Arrays.equals(MiiMessage.BEGIN_BYTES, headBytes)){
            log.error(String.format("报文头异常:%s", Hex.toHexString(msg)));
            return;
        }
        byte[] datas = ArrayUtils.subarray(msg, MiiMessage.COMMAND_INDEX, msg.length - 3);
        byte[] dataLength = ArrayUtils.subarray(msg, MiiMessage.DATA_INDEX, MiiMessage.COMMAND_INDEX);
        if(datas.length != IntegerToByteUtil.bytesToInt(dataLength)){
            log.error(String.format("报文长短异常:%s", Hex.toHexString(msg)));
            return;
        }
        byte[] checkcode = {msg[msg.length - 3],msg[msg.length - 2]};
        byte[] checkData = ArrayUtils.subarray(msg, MiiMessage.DATA_INDEX, msg.length - 3);
        if(!ModbusCrc16Utils.getCrcString(checkData).equals(Hex.toHexString(checkcode))){
            log.error(String.format("报文校验码校验错误:%s", Hex.toHexString(msg)));
            return;
        }
        int command = msg[MiiMessage.COMMAND_INDEX] & 0x7F;
        ReceiveDataEvent receiveDataEvent = handleData(command, datas);
        ApplicationContext getApplicationContext = SpringContextUtils.applicationContext;
        if(!JudgeEmptyUtils.isEmpty(receiveDataEvent) && !JudgeEmptyUtils.isEmpty(getApplicationContext)){
            log.info("将数据发送给对接的指令监听器："+ JSON.toJSONString(receiveDataEvent));
            getApplicationContext.publishEvent(receiveDataEvent);
        }
    }

    /**
     * 数据处理工厂
     * @param command
     * @param datas
     * @return
     */
    private ReceiveDataEvent handleData(int command, byte[] datas) {
        ReceiveDataEvent handleData = null;
        switch (command) {
            case MiiMessage.HEARTBEAT:
                handleData = new HeartBeatData(this, command, new MiiHeartBeatData(datas));
                break;
            case MiiMessage.LOCK:
                handleData = new LockData(this, command, new MiiLockData(datas));
                break;
            case MiiMessage.CARD:
                handleData = new CardData(this, command, new MiiCardData(datas));
                break;
            case MiiMessage.BARCODE:
                handleData = new BarCodeData(this, command, new MiiBarcodeData(datas));
                break;
            case MiiMessage.BACKLIGHT:
                handleData = new BackLightData(this, command, new MiiBackLightData(datas));
                break;
            case MiiMessage.FINGER:
                handleData = new FingerData(this, command, new MiiFingerData(datas));
                break;
            case MiiMessage.HM:
                handleData = new HumitureData(this, command, new MiiHumitureData(datas));
                break;
            default:
                break;
        }
        return handleData;
    }

}
