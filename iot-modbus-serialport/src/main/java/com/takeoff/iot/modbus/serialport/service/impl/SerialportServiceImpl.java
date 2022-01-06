package com.takeoff.iot.modbus.serialport.service.impl;

import com.google.common.primitives.Bytes;
import com.takeoff.iot.modbus.common.message.MiiMessage;
import com.takeoff.iot.modbus.serialport.data.factory.SerialportDataFactory;
import com.takeoff.iot.modbus.serialport.enums.DatebitsEnum;
import com.takeoff.iot.modbus.serialport.enums.ParityEnum;
import com.takeoff.iot.modbus.serialport.enums.StopbitsEnum;
import com.takeoff.iot.modbus.serialport.service.SerialportService;
import com.takeoff.iot.modbus.common.utils.BytesToHexUtil;
import com.takeoff.iot.modbus.common.utils.JudgeEmptyUtils;
import gnu.io.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

/**
 * 类功能说明：串口通讯接口实现类<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
@Service
public class SerialportServiceImpl implements SerialportService, SerialPortEventListener {

    // 检测系统中可用的通讯端口类
    private CommPortIdentifier commPortId;
    // 串口信息
    private SerialPort serialPort;
    // 输入流
    private InputStream inputStream;
    // 输出流
    private OutputStream outputStream;

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
        // 获取系统中所有的通讯端口
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        // 记录是否含有指定串口
        boolean isExsist = false;
        // 循环通讯端口
        while (portList.hasMoreElements()) {
            commPortId = portList.nextElement();
            // 判断是否是串口
            if (commPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                // 比较串口名称是否是指定串口
                if (port.equals(commPortId.getName())) {
                    // 串口存在
                    isExsist = true;
                    // 打开串口
                    try {
                        // open:（应用程序名【随意命名】，阻塞时等待的毫秒数）
                        serialPort = (SerialPort) commPortId.open(Object.class.getSimpleName(), 2000);
                        // 设置串口监听
                        serialPort.addEventListener(this);
                        // 设置串口数据时间有效(可监听)
                        serialPort.notifyOnDataAvailable(true);
                        // 设置串口通讯参数:波特率，数据位，停止位,校验方式
                        serialPort.setSerialPortParams(baudrate, DatebitsEnum.EIGHT.getKey(), StopbitsEnum.ONE.getKey(), ParityEnum.ZERO.getKey());
                    } catch (PortInUseException e) {
                        log.error("端口被占用:"+e.getMessage());
                    } catch (TooManyListenersException e) {
                        log.error("监听器过多:"+e.getMessage());
                    } catch (UnsupportedCommOperationException e) {
                        log.error("不支持的COMM端口操作异常:"+e.getMessage());
                    }
                    // 结束循环
                    break;
                }
            }
        }
        // 若不存在该串口则抛出异常
        if (!isExsist) {
            log.error("串口:"+port+"不存在！");
        }
    }

    /**
     * 实现接口SerialPortEventListener中的方法 读取从串口中接收的数据
     * @param ev
     */
    @Override
    public void serialEvent(SerialPortEvent ev) {
        switch (ev.getEventType()) {
            case SerialPortEvent.BI: // 通讯中断
                log.error("串口通讯：中断");
            case SerialPortEvent.OE: // 溢位错误
                log.error("串口通讯：溢位错误");
            case SerialPortEvent.FE: // 帧错误
                log.error("串口通讯：帧错误");
            case SerialPortEvent.PE: // 奇偶校验错误
                log.error("串口通讯：奇偶校验错误");
            case SerialPortEvent.CD: // 载波检测
                log.error("串口通讯：载波检测");
            case SerialPortEvent.CTS: // 清除发送
                log.error("串口通讯：清除发送");
            case SerialPortEvent.DSR: // 数据设备准备好
                log.error("串口通讯：数据设备准备好");
            case SerialPortEvent.RI: // 响铃侦测
                log.error("串口通讯：响铃侦测");
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 输出缓冲区已清空
                log.error("串口通讯：输出缓冲区已清空");
                break;
            case SerialPortEvent.DATA_AVAILABLE: // 有数据到达
                // 调用读取数据的方法
                readSerialportData();
                break;
            default:
                break;
        }
    }

    /**
     * 读取串口数据缓存
     */
    private static List cacheBuffs = new ArrayList();

    /**
     * 读取串口返回数据
     */
    private void readSerialportData() {
        try {
            inputStream = serialPort.getInputStream();
            // 通过输入流对象的available方法获取数组字节长度
            byte[] readByte = new byte[inputStream.available()];
            // 从线路上读取数据流
            int len = 0;
            // 直接获取到的数据
            while ((len = inputStream.read(readByte)) != -1) {
                // 读取后置空流对象
                log.info("接收到的原始数据：" + BytesToHexUtil.bytesToHexString(readByte));
                inputStream.close();
                inputStream = null;
                break;
            }
            //数据拆包处理
            unpackHandle(readByte);
        } catch (IOException e) {
            log.error("读取串口数据时发生IO异常:"+e.getMessage());
        }
    }

    /**
     * 数据拆包处理
     * @param readByte
     */
    private void unpackHandle(byte[] readByte) {
        if(readByte.length == 0){
            return;
        }
        log.info("原缓存中的数据：cacheBuffs-->" +cacheBuffs+"");
        List buffList = new ArrayList<>();
        if(!JudgeEmptyUtils.isEmpty(cacheBuffs)){
            buffList.addAll(cacheBuffs);
            //清空静态变量数据，释放内存
            cacheBuffs.clear();
            cacheBuffs = null;
            cacheBuffs = new ArrayList();
        }
        buffList.addAll(Bytes.asList(readByte));
        log.info("待处理的数据：buffList-->" +buffList+"");
        //获取起始符下标
        Integer beginIndex = buffList.indexOf(MiiMessage.BEGIN_BYTES[0]);
        //获取结束符下标
        Integer endIndex = buffList.indexOf(MiiMessage.END_BYTES[0]);
        //如果是异常指令直接丢弃
        if(beginIndex == -1){
            log.info("接收到不合法指令，直接丢弃不处理：buffList-->" +buffList+"");
            buffList.clear();
            buffList = null;
            buffList = new ArrayList<>();
            return;
        }else if(beginIndex != -1 && endIndex == -1){
            cacheBuffs.addAll(buffList);
            log.info("接收到有起始符没有结束符的指令，暂不处理，放入缓存：cacheBuffs-->" +cacheBuffs+"");
            return;
        }else if(endIndex != -1 && beginIndex > endIndex){
            buffList = buffList.subList(0, endIndex);
            log.info("截取不合法指令，直接丢弃不处理：buffList-->" +buffList+"");
            //确保起始符下标小于结束符下标
            beginIndex = buffList.indexOf(MiiMessage.BEGIN_BYTES[0]);
            endIndex = buffList.indexOf(MiiMessage.END_BYTES[0]);
            if(endIndex == -1){
                cacheBuffs.addAll(buffList);
                log.info("处理后的指令有起始符没有结束符，暂不处理，放入缓存：cacheBuffs-->" +cacheBuffs+"");
                return;
            }
        }
        if(beginIndex >= 0 &&  endIndex > beginIndex){
            //截取指令数据(从起始符到结束符)
            List<Byte> dataBuff = buffList.subList(beginIndex, endIndex + 1);
            byte[] msg = new byte[dataBuff.size()];
            int i = 0;
            for (Byte b : dataBuff) {
                msg[i] = b;
                i++;
            }
            if(msg.length > 0){
                serialportDataFactory.buildData(msg);
            }
        }
    }

    /**
     * 关闭串口
     */
    public void closeSerialPort() {
        if (serialPort != null) {
            serialPort.notifyOnDataAvailable(false);
            serialPort.removeEventListener();
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (IOException e) {
                    log.error("关闭输入流时发生IO异常:"+e.getMessage());
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (IOException e) {
                    log.error("关闭输出流时发生IO异常:"+e.getMessage());
                }
            }
            serialPort.close();
            serialPort = null;
        }
    }

    /**
     * 发送数据到串口
     * @param writerBuffer
     */
    public void serialportSendData(byte[] writerBuffer) {
        try {
            outputStream = serialPort.getOutputStream();
            outputStream.write(writerBuffer);
            outputStream.flush();
        } catch (NullPointerException e) {
            log.error("找不到串口:"+e.getMessage());
        } catch (IOException e) {
            log.error("发送信息到串口时发生IO异常:"+e.getMessage());
        }
    }
}
