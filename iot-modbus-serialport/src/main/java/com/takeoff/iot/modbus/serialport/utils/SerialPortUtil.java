package com.takeoff.iot.modbus.serialport.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import com.takeoff.iot.modbus.common.utils.JudgeEmptyUtils;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SerialPortUtil {

	private static SerialPortUtil serialPortUtil = null;

    static {
        //在该类被ClassLoader加载时就初始化一个SerialTool对象
        if (serialPortUtil == null) {
            serialPortUtil = new SerialPortUtil();
        }
    }

    //私有化SerialTool类的构造方法，不允许其他类生成SerialTool对象
    private SerialPortUtil() {
    	
    }

    /**
     * 获取提供服务的SerialTool对象
     * @return serialPortUtil
     */
    public static SerialPortUtil getSerialPortUtil() {
        if (serialPortUtil == null) {
            serialPortUtil = new SerialPortUtil();
        }
        return serialPortUtil;
    }


    /**
     * 查找所有可用端口
     * @return 可用端口名称列表
     */
    public static final ArrayList<String> findPort() {
        //获得当前所有可用串口
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        ArrayList<String> portNameList = new ArrayList<>();
        //将可用串口名添加到List并返回该List
        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();
            portNameList.add(portName);
        }
        return portNameList;
    }

    /**
     * 打开串口
     * @param portName 端口名称
     * @param baudrate 波特率
     * @param databits 数据位
     * @param stopbits 停止位
     * @param parity   校验位（奇偶位）
     * @return 串口对象
     */
    public static final SerialPort openPort(String portName, int timeout, int baudrate, int databits, int stopbits, int parity){
    	SerialPort serialPort = null;
        try {
            //通过端口名识别端口
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            //打开端口，并给端口名字和一个timeout（打开操作的超时时间）
            CommPort commPort = portIdentifier.open(portName, timeout);
            //判断是不是串口
            if (commPort instanceof SerialPort) {
                try {
                	serialPort = (SerialPort) commPort;
                    //设置一下串口的波特率等参数
                    serialPort.setSerialPortParams(baudrate, databits, stopbits, parity);
                    log.info("创建串口：{}", serialPort);
                } catch (UnsupportedCommOperationException e) {
                    log.error("端口："+portName+"设置串口参数失败："+e.getMessage());
                }
            } else {
                //不是串口
                log.error("端口："+portName+"指向设备不是串口类型");
            }
        } catch (NoSuchPortException e) {
            log.error("端口："+portName+"没有对应的串口设备："+e.getMessage());
        } catch (PortInUseException e) {
        	log.error("端口："+portName+"口已被占用："+e.getMessage());
        }
        return serialPort;
    }

    /**
     * 关闭串口
     * @param serialPort 待关闭的串口对象
     */
    public static void closePort(SerialPort serialPort) {
        if (!JudgeEmptyUtils.isEmpty(serialPort)) {
            serialPort.close();
            serialPort = null;
        }
    }

    /**
     * 往串口发送数据
     * @param serialPort 串口对象
     * @param bytes      待发送数据
     */
    public static void sendToPort(SerialPort serialPort, byte[] bytes){
        OutputStream out = null;
        try {
            out = serialPort.getOutputStream();
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            log.error("向串口："+serialPort+"发送数据失败："+e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException e) {
            	log.error("关闭串口："+serialPort+"对象的输出流出错："+e.getMessage());
            }
        }
    }

    /**
     * 从串口读取数据
     * @param serialPort 当前已建立连接的SerialPort对象
     * @return 读取到的数据
     */
    public static byte[] readFromPort(SerialPort serialPort){
        InputStream in = null;
        byte[] bytes = null;
        try {
            in = serialPort.getInputStream();
            int bufflenth = in.available();        //获取buffer里的数据长度
            while (bufflenth != 0) {
                bytes = new byte[bufflenth];    //初始化byte数组为buffer中数据的长度
                in.read(bytes);
                bufflenth = in.available();
            }
        } catch (IOException e) {
        	log.error("从串口："+serialPort+"读取数据时出错："+e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
            	log.error("关闭串口："+serialPort+"对象输入流出错："+e.getMessage());
            }
        }
        return bytes;
    }

    /**
     * 添加监听器
     * @param serialPort 串口对象
     * @param listener 串口监听器
     */
    public static void addListener(SerialPort serialPort, SerialPortEventListener listener){
        try {
            //给串口添加监听器
        	serialPort.addEventListener(listener);
            //设置当有数据到达时唤醒监听接收线程
        	serialPort.notifyOnDataAvailable(true);
            //设置当通信中断时唤醒中断线程
        	serialPort.notifyOnBreakInterrupt(true);
        } catch (TooManyListenersException e) {
        	log.error("串口："+serialPort+"监听类对象过多："+e.getMessage());
        }
    }

    /**
     * 删除监听器
     * @param serialPort 串口对象
     * @param listener 串口监听器
     */
    public static void removeListener(SerialPort serialPort, SerialPortEventListener listener) {
        //删除串口监听器
    	serialPort.removeEventListener();
    }
}
