package com.takeoff.iot.modbus.serialport.service.impl;

import com.google.common.primitives.Bytes;
import com.takeoff.iot.modbus.common.message.MiiMessage;
import com.takeoff.iot.modbus.common.utils.IntegerToByteUtil;
import com.takeoff.iot.modbus.serialport.data.factory.SerialportDataFactory;
import com.takeoff.iot.modbus.serialport.entity.ReceiveData;
import com.takeoff.iot.modbus.serialport.enums.DatebitsEnum;
import com.takeoff.iot.modbus.serialport.enums.ParityEnum;
import com.takeoff.iot.modbus.serialport.enums.StopbitsEnum;
import com.takeoff.iot.modbus.serialport.service.SerialportService;
import com.takeoff.iot.modbus.common.utils.BytesToHexUtil;
import com.takeoff.iot.modbus.common.utils.JudgeEmptyUtils;
import com.takeoff.iot.modbus.serialport.utils.SerialPortUtil;
import gnu.io.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * 类功能说明：串口通讯接口实现类<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
@Service
public class SerialportServiceImpl implements SerialportService {

    // 串口信息
    private static SerialPort serialPort = null;

    @Resource
    private SerialportDataFactory serialportDataFactory;

    /**
     * 连接串口
     * @param port
     * @param baudrate
     * @param timeout
     * @return
     */
    @Override
    public void openComPort(String port, Integer baudrate, Integer timeout) {
        //确保串口已被关闭，未关闭会导致重新监听串口失败
        if (!JudgeEmptyUtils.isEmpty(serialPort)) {
            SerialPortUtil.closePort(serialPort);
            serialPort = null;
        }
        if (JudgeEmptyUtils.isEmpty(serialPort)) {
            serialPort = SerialPortUtil.openPort(port, timeout, baudrate, DatebitsEnum.EIGHT.getKey(), StopbitsEnum.ONE.getKey(), ParityEnum.ZERO.getKey());
            //设置串口监听
            SerialPortUtil.addListener(serialPort, new SerialPortEventListener() {
                @Override
                public void serialEvent(SerialPortEvent serialPortEvent) {
                    if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                        //读取串口数据
                        byte[] bytes = SerialPortUtil.readFromPort(serialPort);
                        log.info("接收到的原始数据：" + BytesToHexUtil.bytesToHexString(bytes));
                        //数据拆包处理
                        unpackHandle(bytes);
                    }
                }
            });
        }
    }

    /**
     * 读取串口数据缓存
     */
    private static List cacheBuffs = new ArrayList();

    /**
     * 数据拆包处理
     * @param readByte
     */
    private void unpackHandle(byte[] readByte) {
        if(readByte.length == 0){
            return;
        }
        log.info("原缓存中的数据：cacheBuffs-->" +cacheBuffs+"");
        //将缓存数据优先处理
        List buffList = addBuffList();
        buffList.addAll(Bytes.asList(readByte));
        log.info("待处理的数据：buffList-->" +buffList+"");
        //校验标识
        boolean flag = true;
        while (flag == true) {
            if(buffList.size() > 0){
                //校验指令数据
                ReceiveData data = checkData(buffList);
                flag = data.isFlag();
                if(data.getBeginIndex() >= 0 && data.getInstructLength() > data.getBeginIndex()){
                    //截取指令数据(从起始符到结束符)
                    List<Byte> dataBuff = data.getBuffList().subList(data.getBeginIndex(), data.getInstructLength() + 1);
                    //剩余的指令数据
                    buffList = data.getBuffList().subList(data.getInstructLength() + 1, buffList.size());
                    byte[] msg = Bytes.toArray(dataBuff);
                    if(msg.length > 0){
                        log.info("待处理的指令："+ BytesToHexUtil.bytesToHexString(msg));
                        serialportDataFactory.buildData(msg);
                    }
                }
            }else{
                flag = false;
            }
        }
    }

    /**
     * 函数功能说明 ：校验指令数据 <br/>
     * 修改者名字： <br/>
     * 修改日期： <br/>
     * 修改内容：<br/>
     * 作者：Lion <br/>
     * 参数：@param buffList
     * 参数：@return <br/>
     * return：ReceiveData <br/>
     */
    private ReceiveData checkData(List buffList) {
        ReceiveData data = new ReceiveData();
        data.setFlag(true);
        //获取第一个起始符下标
        int beginIndex = buffList.indexOf(MiiMessage.BEGIN_BYTES[0]);
        //获取第一个结束符下标
        int endIndex = buffList.indexOf(MiiMessage.END_BYTES[0]);
        //计算指令数据长度
        byte[] buffListByte = Bytes.toArray(buffList);
        byte[] dataLength = getDataLength(buffListByte, beginIndex);
        //指令总长度
        int instructLength = getInstructLength(dataLength);
        //获取指令结束符
        byte[] endByte = getEndByte(buffListByte, beginIndex, instructLength);
        //获取最后一个起始符下标
        int lastBeginIndex = buffList.lastIndexOf(MiiMessage.BEGIN_BYTES[0]);
        //如果是异常指令直接丢弃
        if(beginIndex == -1){
            log.info("接收到不合法指令，直接丢弃不处理：buffList-->" +buffList+"");
            buffList.clear();
            buffList = null;
            buffList = new ArrayList<>();
            data.setFlag(false);
        }else if(beginIndex != -1 && !BytesToHexUtil.bytesToHexString(endByte).equals(BytesToHexUtil.bytesToHexString(MiiMessage.END_BYTES))){
            //去掉不合法指令
            buffList = buffList.subList(lastBeginIndex, buffList.size());
            buffListByte = Bytes.toArray(buffList);
            beginIndex = buffList.indexOf(MiiMessage.BEGIN_BYTES[0]);
            dataLength = getDataLength(buffListByte, beginIndex);
            instructLength = getInstructLength(dataLength);
            if(buffList.size() != instructLength + 1){
                cacheBuffs.addAll(buffList);
                log.info("接收到有起始符没有结束符的指令，暂不处理，放入缓存：cacheBuffs-->" +cacheBuffs+"");
                data.setFlag(false);
            }
        }else if(endIndex != -1 && beginIndex > endIndex){
            buffList = buffList.subList(lastBeginIndex, buffList.size());
            buffListByte = Bytes.toArray(buffList);
            //确保起始符下标小于结束符下标
            beginIndex = buffList.indexOf(MiiMessage.BEGIN_BYTES[0]);
            dataLength = getDataLength(buffListByte, beginIndex);
            instructLength = getInstructLength(dataLength);
            endIndex = buffList.indexOf(MiiMessage.END_BYTES[0]);
            if(endIndex == -1){
                cacheBuffs.addAll(buffList);
                log.info("处理后的指令有起始符没有结束符，暂不处理，放入缓存：cacheBuffs-->" +cacheBuffs+"");
                data.setFlag(false);
            }
        }
        data.setBeginIndex(beginIndex);
        data.setInstructLength(instructLength);
        data.setBuffList(buffList);
        return data;
    }

    /**
     * 函数功能说明 ： 获取指令结束符 <br/>
     * 修改者名字： <br/>
     * 修改日期： <br/>
     * 修改内容：<br/>
     * 作者：Lion <br/>
     * 参数：@param buffListByte
     * 参数：@param beginIndex
     * 参数：@param instructLength
     * 参数：@return <br/>
     * return：byte[] <br/>
     */
    private byte[] getEndByte(byte[] buffListByte, int beginIndex, int instructLength) {
        byte[] endByte = new byte[0];
        if(beginIndex > 0){
            endByte = ArrayUtils.subarray(buffListByte, beginIndex + instructLength, beginIndex + instructLength + 1);
        }else{
            endByte = ArrayUtils.subarray(buffListByte, instructLength, instructLength + 1);
        }
        return endByte;
    }

    /**
     * 函数功能说明 ： 计算指令数据长度<br/>
     * 修改者名字： <br/>
     * 修改日期： <br/>
     * 修改内容：<br/>
     * 作者：Lion <br/>
     * 参数：@param buffListByte
     * 参数：@param beginIndex
     * 参数：@return <br/>
     * return：byte[] <br/>
     */
    private byte[] getDataLength(byte[] buffListByte, int beginIndex) {
        return ArrayUtils.subarray(buffListByte, beginIndex + MiiMessage.BEGIN_SIZE, beginIndex + MiiMessage.DATA_SIZE);
    }

    /**
     * 函数功能说明 ： 指令总长度 <br/>
     * 修改者名字： <br/>
     * 修改日期： <br/>
     * 修改内容：<br/>
     * 作者：Lion <br/>
     * 参数：@param dataLength
     * 参数：@return <br/>
     * return：int <br/>
     */
    private int getInstructLength(byte[] dataLength) {
        int instructLength = 0;
        if(dataLength.length > 0){
            //指令数据下标从0开始，总长度需要减1
            instructLength = MiiMessage.BEGIN_SIZE + MiiMessage.DATA_SIZE + IntegerToByteUtil.bytesToInt(dataLength) + MiiMessage.CHECKCODE_SIZE + MiiMessage.END_SIZE - 1;
        }
        return instructLength;
    }

    /**
     * 函数功能说明 ： 将缓存数据优先处理 <br/>
     * 修改者名字： <br/>
     * 修改日期： <br/>
     * 修改内容：<br/>
     * 作者：Lion <br/>
     * 参数：@return <br/>
     * return：List <br/>
     */
    private List addBuffList() {
        List buffList = new ArrayList<>();
        if(!JudgeEmptyUtils.isEmpty(cacheBuffs)){
            buffList.addAll(cacheBuffs);
            //清空静态变量数据，释放内存
            cacheBuffs.clear();
            cacheBuffs = null;
            cacheBuffs = new ArrayList();
        }
        return buffList;
    }

    /**
     * 关闭串口
     */
    @Override
    public void closeSerialPort() {
        if (!JudgeEmptyUtils.isEmpty(serialPort)) {
            SerialPortUtil.closePort(serialPort);
            serialPort = null;
        }
    }

    /**
     * 发送数据到串口
     * @param bytes
     */
    @Override
    public void serialportSendData(byte[] bytes) {
        if (!JudgeEmptyUtils.isEmpty(serialPort)) {
            SerialPortUtil.sendToPort(serialPort, bytes);
        }
    }
}
