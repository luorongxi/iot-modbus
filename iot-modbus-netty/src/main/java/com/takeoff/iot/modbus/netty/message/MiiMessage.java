package com.takeoff.iot.modbus.netty.message;

import com.takeoff.iot.modbus.netty.data.base.MiiData;

/**
 * 类功能说明：设备通讯信息<br/>
 * 公司名称：takeoff开源 <br/>
 * 作者：luorongxi <br/>
 */
public interface MiiMessage {
	
	/**
	 * 起始符
	 */
	final byte[] BEGIN_BYTES = {(byte) 0x7E};
	
	/**
	 * 结束符
	 */
	final byte[] END_BYTES = {(byte) 0x7F};
	
	/**
	 * 数据长度.  <br>
	 * <table border="1" >
	 * <tr> <th>START_SIZE</th> <th>DATA_SIZE</th> <th>COMMAND_SIZE</th> <th>DEVICE_SIZE</th> </tr>
	 * <tr> <th>起始符</th> <th>数据长度</th> <th>指令位</th> <th>设备地址</th>  </tr>
	 * <tr> <th>SHELF_SIZE</th> <th>SLOT_SIZE</th> <th>CHECKCODE_SIZE</th> <th>END_SIZE</th> </tr>
	 * <tr> <th>层地址</th> <th>槽地址</th> <th>校验位</th> <th>结束符</th> </tr>
	 * </table><br>
	 */
	final int BEGIN_SIZE = 1, DATA_SIZE = 2, COMMAND_SIZE = 1, DEVICE_SIZE = 1, SHELF_SIZE = 1, SLOT_SIZE = 1, CHECKCODE_SIZE = 2, END_SIZE = 1;
	
	/**
	 * 数据下标.  <br>
	 * <table border="1" >
	 * <tr> <th>HEADER_LINDEX</th> <th>DATA_LINDEX</th> <th>DATA_LINDEX</th> <th>COMMAND_INDEX</th> </tr>
	 * <tr> <th>起始符</th> <th>数据长度开始位</th> <th>数据长度结束位</th> <th>指令位</th> </tr>
	 * </table><br>
	 */
	final int BEGIN_INDEX = 0, DATA_INDEX = 1, COMMAND_INDEX = 3;
	
	/**
	 * command() 返回消息的指令类型.  <br>
	 * <table border="1" >
	 * <tr> <th>ACK</th> <th>LED</th> <th>LOCK</th> <th>CARD</th> <th>POWER</th> </tr>
	 * <tr> <th>应答类型</th>  <th>LED灯</th> <th>门锁命令</th> <th>读卡器</th> <th>层板供电控制</th> </tr>
	 * <tr> <th>STATUS</th> <th>GETOLINE</th> <th>BACKLIGHT</th> <th>LCD</th> <th>LCDCONFIG</th> </tr>
	 * <tr> <th>上报状态</th>  <th>检查在线设备</th> <th>背光灯控制</th> <th>LCD内容显示数据传输命令</th> <th>LCD配置命令</th> </tr>
	 * <tr> <th>LCDPOWER</th> <th>LCDCLOCK</th> <th>HM</th> <th>ESCALE</th> <th>BARCODE</th> </tr>
	 * <tr> <th>LCD电源供电控制指令</th> <th>LCD显示同步时间</th> <th>温湿度数据</th> <th>电子称相关命令</th> <th>扫描头数据</th> </tr>
	 * <tr> <th>FINGER</th> <th>MCUCONFIG</th> <th>OTA</th> <th>APDS</th> <th>INFO</th> </tr>
	 * <tr> <th>手指识别数据</th> <th>配置信息</th> <th>在线升级</th> <th>光电传感器</th> <th>信息</th> </tr>
	 * <tr> <th>WLED</th> <th>WLEDACTION</th> <th>CABPH</th> <th>LAYPH</th> <th>CARDPH</th> </tr>
	 * <tr> <th>三色灯报警器</th> <th>按键输入</th> <th>设备接口透传</th> <th>层接口透传</th> <th>读卡器接口透传</th> </tr>
	 * <tr> <th>HEARTBEAT</th> <th>CHECKSLEVE</th>  </tr>
	 * <tr> <th>空闲时与上位机连接的心跳包(5秒)</th> <th>主机查询从机数据命令</th> </tr>
	 * </table><br>
	 */
	final int ACK = 0x01,LED = 0x02, LOCK = 0x03, CARD = 0x04, POWER = 0x05,
			STATUS = 0x06, GETOLINE = 0x07, BACKLIGHT = 0x08, LCD = 0x09, LCDCONFIG = 0x0A,
			LCDPOWER = 0x0B, LCDCLOCK = 0x0C, HM = 0x0D, ESCALE = 0x0E, BARCODE = 0x0F,
			FINGER = 0x10, MCUCONFIG = 0x11, OTA = 0x12, APDS = 0x13, INFO = 0x14,
			WLED = 0x15, WLEDACTION = 0x16, CABPH = 0x17, LAYPH = 0x18, CARDPH = 0x19,
			HEARTBEAT = 0x3E, CHECKSLEVE = 0x3F;
	
	/**
	 * 指静脉数据常量.  <br>
	 * <table border="1" >
	 * <tr> <th>USER_CODE</th> <th>STATUS_CODE</th> <th>DEVID</th> <th>GID</th> </tr>
	 * <tr> <th>用户码</th> <th>状态码</th> <th>Devid</th> <th>GID(分组)</th> </tr>
	 * </table><br>
	 */
	final int USER_CODE = 0x00, STATUS_CODE = 0x40, DEVID = 0xFF, GID = 0x00, END_CODE = 0x0D;
	
	/**
	 * type() 信息类型. <br>
	 * SEND 发送 <br>
	 * RECV 接收 <br>
	 */
	final int SEND = 0x00,RECV = 0x80;
	
	/**
	 * 返回设备组编码.
	 * @return 设备组编码
	 */
	String deviceGroup();
	
	/**
	 * 返回消息的指令类型.<br>
	 * <table border="1" >
	 * <tr> <th>UNLOCK</th> <th>LED</th> <th>BACKLIGHT</th> <th>READRF</th> <th>ENDRF</th> <th>PRINTING</th> <th>TEMPANDHUM</th> </tr>
	 * <tr> <td>开锁</td>  <td>LED灯</td> <td>背光灯</td> <td>开始读RFID</td> <td>RFID读取停止</td> <td>打印</td> <td>温度和湿度</td> </tr>
	 * <tr> <th>REGOFINGER</th> <th>CHECKFINGER</th> <th>DELFINGER</th> <th>CARD</th> <th>BARCODE</th> <th>CLOSEDOOR</th> <th> </th> </tr>
	 * <tr> <td>注册指纹</td>  <td>检查指纹</td> <td>删除指纹</td> <td>工卡信息</td> <td>扫码信息</td> <td>关门信息</td> <td> </td> </tr>
	 * </table><br>
	 * @return 指令类型 <br>
	 */
	int command();
	
	/**
	 * 返回消息的数据对象的字节长度.
	 * @return 数据对象的字节长度
	 */
	int length();
	
	/**
	 * 返回信息类型.
	 * SEND 发送 <br>
	 * RECV 接收 <br>
	 * @return 信息类型
	 */
	int type();
	
	/**
	 * 返回消息的数据对象.
	 * @return 数据对象
	 */
	MiiData data();
	
	/**
	 * 返回消息的字节数组.
	 * @return 字节数组
	 */
	byte[] toBytes();
}
