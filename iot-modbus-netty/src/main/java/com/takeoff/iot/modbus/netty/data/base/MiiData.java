package com.takeoff.iot.modbus.netty.data.base;

/**
 * 类功能说明：设备信息常量<br/>
 * 公司名称：takeoff开源 <br/>
 * 作者：luorongxi <br/>
 */
public interface MiiData {
	
	/**
	 * 数据内容下标.<br>
	 * <table border="1" >
	 * <tr> <th>CONTENT_INDEX</th> </tr>
	 * <tr> <td>数据内容</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int CONTENT_INDEX = 4;
	
	/**
	 * 设备数据下标.<br>
	 * <table border="1" > device
	 * <tr> <th>DEVICE_INDEX</th> <th>SHELF_INDEX</th> <th>SLOT_INDEX</th> </tr>
	 * <tr> <td>设备号</td>  <td>层号</td> <td>槽位号</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int DEVICE_INDEX = 1, SHELF_INDEX = 2, SLOT_INDEX = 3;
	
	/**
	 * 门锁数据下标.<br>
	 * <table border="1" >
	 * <tr> <th>LOCKNO_INDEX</th> <th>LOCKSTATUS_INDEX</th> <th>SENSORSTATUS_INDEX</th> </tr>
	 * <tr> <td>门锁号</td>  <td>门锁状态码</td> <td>传感器状态码</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int LOCKNO_INDEX = 0, LOCKSTATUS_INDEX = 1, SENSORSTATUS_INDEX = 2;
	
	/**
	 * 指静脉数据下标.<br>
	 * <table border="1" >
	 * <tr> <th>FINGERTYPE_INDEX</th> <th>FINGERSTATUS_INDEX</th> <th>FINGERCMD_INDEX</th> </tr>
	 * <tr> <td>枚举标识</td>  <td>状态码</td> <td>指令码</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int FINGERTYPE_INDEX = 4, FINGERSTATUS_INDEX = 5, FINGERCMD_INDEX = 6, GID_INDEX = 10;
	
	/**
	 * 指静脉操作状态.<br>
	 * <table border="1" >
	 * <tr> <th>WDH320S_USER_SUCCESS</th> <th>WDH320S_USER_FALT</th> </tr>
	 * <tr> <td>操作成功</td>  <td>操作失败</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int WDH320S_USER_SUCCESS = 0x00, WDH320S_USER_FALT = 0x01;
	
	/**
	 * 检查手指状态.<br>
	 * <table border="1" >
	 * <tr> <th>WDH320S_USER_NoTip</th> <th>WDH320S_USER_NoPulp</th> <th>WDH320S_USER_NoFinger</th> </tr>
	 * <tr> <td>只检测到指腹未检测到指尖</td>  <td>只检测到指尖未检测到指腹</td> <td>未检测到手指</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int WDH320S_USER_NoTip = 0x02, WDH320S_USER_NoPulp = 0x03, WDH320S_USER_NoFinger = 0x04;
	
	/**
	 * 验证手指状态.<br>
	 * <table border="1" >
	 * <tr> <th>WDH320S_USER_Veri_GetID</th> <th>WDH320S_USER_Veri_NoID</th> <th>WDH320S_USER_Veri_Fail</th> </tr>
	 * <tr> <td>获取到ID</td>  <td>不存在该手指FID，未注册或者未下载/无注册数据</td> <td>验证失败</td> </tr>
	 * <tr> <th>WDH320S_USER_Veri_Template</th> <th>WDH320S_USER_Veri_CapTimeOut</th> <th>WDH320S_USER_Veri_FigTimeOut</th> </tr>
	 * <tr> <td>生成的模板不合格,请重放手指</td>  <td>拍照超时,请重放手指</td> <td>放置手指超时</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int WDH320S_USER_Veri_GetID = 0x05, WDH320S_USER_Veri_NoID = 0x06, WDH320S_USER_Veri_Fail = 0x07, 
			WDH320S_USER_Veri_Template = 0x08, WDH320S_USER_Veri_CapTimeOut = 0x09, WDH320S_USER_Veri_FigTimeOut = 0x0A;
	
	/**
	 * 指静脉注册状态.<br>
	 * <table border="1" >
	 * <tr> <th>WDH320S_USER_Reg_BufFull</th> <th>WDH320S_USER_Reg_FidDef</th> <th>WDH320S_USER_Reg_FingerDef</th> <th>WDH320S_USER_Reg_WaitFon1</th> </tr>
	 * <tr> <td>注册：模板缓存满</td>  <td>注册：ID号已被占用</td> <td>注册：手指已注册过</td> <td>注册：开始注册，请放入手指</td> </tr>
	 * <tr> <th>WDH320S_USER_Reg_Sucess1</th> <th>WDH320S_USER_Reg_WaitFon2</th> <th>WDH320S_USER_Reg_Sucess2</th> <th>WDH320S_USER_Reg_WaitFon3</th> </tr>
	 * <tr> <td>注册：第一次注册成功，请移开手指</td>  <td>注册：第二次注册，请放入手指</td> <td>注册：第二次注册成功，请移开手指</td> <td>注册：第三次注册，请放入手指</td> </tr>
	 * <tr> <th>WDH320S_USER_Reg_Sucess3</th> <th>WDH320S_USER_Reg_WaitFon4</th> <th>WDH320S_USER_Reg_Sucess4</th> <th>WDH320S_USER_Reg_WaitFon5</th> </tr>
	 * <tr> <td>注册：第三次注册成功，请移开手指</td>  <td>注册：第四次注册，请放入手指</td> <td>注册：第四次注册成功，请移开手指</td> <td>注册：第五次注册，请放入手指</td> </tr>
	 * <tr> <th>WDH320S_USER_Reg_Sucess5</th> <th>WDH320S_USER_Reg_WaitFon6</th> <th>WDH320S_USER_Reg_End</th> <th>WDH320S_USER_Reg_TimeOut</th> </tr>
	 * <tr> <td>注册：第五次注册成功，请移开手指</td>  <td>注册：注册完成，请放入手指6--验证</td> <td>注册：注册完成</td> <td>注册：注册超时</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int WDH320S_USER_Reg_BufFull = 0x20, WDH320S_USER_Reg_FidDef = 0x21, WDH320S_USER_Reg_FingerDef = 0x22, WDH320S_USER_Reg_WaitFon1 = 0x23,
			WDH320S_USER_Reg_Sucess1 = 0x24, WDH320S_USER_Reg_WaitFon2 = 0x25, WDH320S_USER_Reg_Sucess2 = 0x26, WDH320S_USER_Reg_WaitFon3 = 0x27, 
			WDH320S_USER_Reg_Sucess3 = 0x28, WDH320S_USER_Reg_WaitFon4 = 0x29, WDH320S_USER_Reg_Sucess4 = 0x2A, WDH320S_USER_Reg_WaitFon5 = 0x2B, 
			WDH320S_USER_Reg_Sucess5 = 0x2C, WDH320S_USER_Reg_WaitFon6 = 0x2D, WDH320S_USER_Reg_End = 0x2E, WDH320S_USER_Reg_TimeOut = 0x2F;
	
	/**
	 * 指静脉注册异常.<br>
	 * <table border="1" >
	 * <tr> <th>WDH320S_USER_RegEx_TemplateOcc</th> <th>WDH320S_USER_RegEx_TemplateFail</th> <th>WDH320S_USER_RegEx_FingerOcc</th> </tr>
	 * <tr> <td>注册：注册时再次采集静脉特征差异过大,请重新注册</td>  <td>注册：注册时生成的模板不合格,请重新注册</td> <td>注册：与前一次采集手指下发信息不同,请重新注册</td> </tr>
	 * <tr> <th>WDH320S_USER_RegEx_End</th> <th>WDH320S_USER_RegEx_CapTimeOut</th> </tr>
	 * <tr> <td>注册：发送结束注册命令返回结束注册成功 </td>  <td>注册：注册时拍照超时</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int WDH320S_USER_RegEx_TemplateOcc = 0x30, WDH320S_USER_RegEx_TemplateFail = 0x31, WDH320S_USER_RegEx_FingerOcc = 0x32,
			WDH320S_USER_RegEx_End = 0x33, WDH320S_USER_RegEx_CapTimeOut = 0x34;
	
	/**
	 * 指静脉结束注册.<br>
	 * <table border="1" >
	 * <tr> <th>WDH320S_USER_End_SUCCESS</th> <th>WDH320S_USER_End_TemplateFail</th> <th>WDH320S_USER_End_TemplateFull</th> </tr>
	 * <tr> <td>结束注册：结束成功</td>  <td>结束失败：保存失败-模板数量小于3</td> <td>结束失败：保存失败-静脉模板空间满 </td> </tr>
	 * <tr> <th>WDH320S_USER_End_InfoFull</th> <th>WDH320S_USER_End_ErrFlash</th> </tr>
	 * <tr> <td>结束失败：保存失败-信息头空间满 </td>  <td>结束失败：保存失败-Flash操作失败</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int WDH320S_USER_End_SUCCESS = 0x35, WDH320S_USER_End_TemplateFail = 0x36, WDH320S_USER_End_TemplateFull = 0x37,
			WDH320S_USER_End_InfoFull = 0x38, WDH320S_USER_End_ErrFlash = 0x39;
	
	/**
	 * 指静脉删除.<br>
	 * <table border="1" >
	 * <tr> <th>WDH320S_USER_Del_OneSucess</th> <th>WDH320S_USER_Del_AllSucess</th> </tr>
	 * <tr> <td>删除：单个删除成功</td>  <td>删除：全部删除成功</td> </tr>
	 * <tr> <th>WDH320S_USER_Del_OneFalt</th> <th>WDH320S_USER_Del_OneFnull</th> </tr>
	 * <tr> <td>删除：单个删除失败或者无数据 </td>  <td>删除：无此ID</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int WDH320S_USER_Del_OneSucess = 0x3A, WDH320S_USER_Del_AllSucess = 0x3B, 
			WDH320S_USER_Del_OneFalt = 0x3C, WDH320S_USER_Del_OneFnull = 0x3D;
	
	/**
	 * 指静脉模板状态.<br>
	 * <table border="1" >
	 * <tr> <th>WDH320S_USER_Get_TemplateSucess</th> <th>WDH320S_USER_Get_TemplateFail</th> <th>WDH320S_USER_Download_Sucess</th> <th>WDH320S_USER_Download_Fail</th> </tr>
	 * <tr> <td>从指静脉获取模板成功</td>  <td>从指静脉获取模板失败:无此模板</td> <td>下载模板成功</td> <td>下载模板失败</td> </tr>
	 * <tr> <th>WDH320S_USER_Download_InfoFull</th> <th>WDH320S_USER_Download_TemplateFull</th> <th>WDH320S_USER_Download_FailFlash</th> </tr>
	 * <tr> <td>下载模板失败:信息头空间满</td>  <td>下载模板失败:模板空间满</td> <td>下载模板失败:Flash操作失败</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int WDH320S_USER_Get_TemplateSucess = 0x40, WDH320S_USER_Get_TemplateFail = 0x41, WDH320S_USER_Download_Sucess = 0x42, WDH320S_USER_Download_Fail = 0x43,
			WDH320S_USER_Download_InfoFull = 0x44, WDH320S_USER_Download_TemplateFull = 0x45, WDH320S_USER_Download_FailFlash = 0x46; 
	
	/**
	 * 指静脉设备状态.<br>
	 * <table border="1" >
	 * <tr> <th>WDH320S_USER_No_Device</th> </tr>
	 * <tr> <td>无设备</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int WDH320S_USER_No_Device = 0xFD;
	
	/**
	 * 指静脉设备通讯状态.<br>
	 * <table border="1" >
	 * <tr> <th>WDH320S_USER_Tran</th> <th>WDH320S_USER_UNKONW</th> </tr>
	 * <tr> <td>透传数据/原始数据内容传输</td>  <td>未知错误 </td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int WDH320S_USER_Tran = 0xFE, WDH320S_USER_UNKONW = 0xFF;
	
	/**
	 * 指静脉设备指令状态.<br>
	 * <table border="1" >
	 * <tr> <th>CMD_ONE_VS_N</th> <th>CMD_ONE_VS_G</th> <th>CMD_ONE_VS_ONE</th> <th>CMD_REGISTER</th> </tr>
	 * <tr> <td>采集特征并 1:N 比对</td>  <td>采集特征并 1:G 比对</td> <td>采集特征并 1:1 比对</td> <td>注册手指</td> </tr>
	 * <tr> <th>CMD_REG_END</th> <th>CMD_DELETE_ONE</th> <th>CMD_DELETE_ALL</th> <th>CMD_UPLOAD_ALL_ID</th> </tr>
	 * <tr> <td>注册结束</td>  <td>删除单个手指</td> <td>删除所有手指</td> <td>上传所有手指 ID 信息</td> </tr>
	 * <tr> <th>CMD_UPLOAD_INFOR</th> <th>CMD_UPLOAD_TEMPLATE</th> <th>CMD_UPLOAD_INFOR_TEMPLATES</th> <th>CMD_CREAT_TEMPLATE</th> </tr>
	 * <tr> <td>上传指定手指信息</td>  <td>上传指定手指模板</td> <td>上传指定手指以及对应模板</td> <td>采集并上传模板</td> </tr>
	 * <tr> <th>CMD_DOWNLOAD_INFOR_TEMPLATES</th> <th>CMD_UPLOAD_VERSION</th> <th>CMD_UPLOAD_COUNT</th> <th>CMD_CREAT_TEMPLATE</th> </tr>
	 * <tr> <td>下载手指信息头和所有模板</td>  <td>获取固件版本号</td> <td>获取注册手指总数</td> <td>获取手指状态</td> </tr>
	 * <tr> <th>CMD_UPLOAD_SEQUENCE</th> <th>CMD_SET_BAUD</th> <th>CMD_SET_DEVID</th> </tr>
	 * <tr> <td>获取设备序列号</td>  <td>设置波特率</td> <td>设置设备编号</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int CMD_ONE_VS_N = 0x00, CMD_ONE_VS_G = 0x01, CMD_ONE_VS_ONE = 0x02, CMD_REGISTER = 0x03,
			CMD_REG_END = 0x04, CMD_DELETE_ONE = 0x05, CMD_DELETE_ALL = 0x06, CMD_UPLOAD_ALL_ID = 0x07, 
			CMD_UPLOAD_INFOR = 0x08, CMD_UPLOAD_TEMPLATE = 0x09, CMD_UPLOAD_INFOR_TEMPLATES = 0x0A, CMD_CREAT_TEMPLATE = 0x0B,
			CMD_DOWNLOAD_INFOR_TEMPLATES = 0x0C, CMD_UPLOAD_VERSION = 0x0D, CMD_UPLOAD_COUNT = 0x0E, CMD_CHK_FINGER = 0x0F, 
			CMD_UPLOAD_SEQUENCE = 0x10, CMD_SET_BAUD = 0x11, CMD_SET_DEVID = 0x12;
	
	/**
	 * doorCode() 柜门锁.<br>
	 * <table border="1" >
	 * <tr> <th>ONELOCK</th> <th>TWOLOCK</th> <th>THREELOCK</th> </tr>
	 * <tr> <td>柜门锁1</td>  <td>柜门锁2</td> <td>柜门锁3</td> </tr>
	 * <tr> <th>FOURLOCK</th> <th>FIVELOCK</th> <th>SIXLOCK</th> </tr>
	 * <tr> <td>柜门锁4</td>  <td>柜门锁5</td> <td>柜门锁6</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int ONELOCK = 0x01, TWOLOCK = 0x02, THREELOCK = 0x03, FOURLOCK = 0x04, FIVELOCK = 0x05, SIXLOCK = 0x06;
	
	/**
	 *  statusCode() 锁状态.<br>
	 * <table border="1" >
	 * <tr> <th>LOCK</th> <th>OPEN</th> </tr>
	 * <tr> <td>上锁</td>  <td>开锁</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int LOCK = 0x00, OPEN = 0x01;
	
	/**
	 *  statusCode() 卡号返回门标识.<br>
	 * <table border="1" >
	 * <tr> <th>FRONT</th> <th>BACK</th> </tr>
	 * <tr> <td>前门</td>  <td>后门</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int FRONT = 0x00, BACK = 0x01;
	
	/**
	 *  statusCode() 前后门标识.<br>
	 * <table border="1" >
	 * <tr> <th>DOOR_FRONT</th> <th>DOOR_BACK</th> </tr>
	 * <tr> <td>前门</td>  <td>后门</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int DOOR_FRONT = 0x01, DOOR_BACK = 0x02;
	
	/**
	 *  statusCode() 指静脉状态.<br>
	 * <table border="1" >
	 * <tr> <th>UPLOAD</th> <th>DOWNLOAD</th> </tr>
	 * <tr> <td>上传模板</td>  <td>下载模板</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int UPLOAD = 0x14, DOWNLOAD = 0x15;
	
	/**
	 * 返回门状态.<br>
	 * <table border="1" >
	 * <tr> <th>CLOSES</th> <th>OPENS</th> </tr>
	 * <tr> <td>关</td>  <td>开</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int CLOSES = 0x11, OPENS = 0x12;
	
	/**
	 *  statusCode() 报警灯状态.<br>
	 * <table border="1" >
	 * <tr> <th>CLOSE</th></tr>
	 * <tr> <td>关</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int CLOSE = 0x01;
	
	/**
	 * 层号或者槽号.<br>
	 * <table border="1" >
	 * <tr> <th>NULL</th> <th>ONE</th> <th>ALL</th> </tr>
	 * <tr> <td>所有设备都不执行</td> <td>一层或一槽</td> <td>所有设备一起执行</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int NULL = 0x00, ONE = 0x01, ALL = 0xFF;
	
	/**
	 * statusCode() 控制LED灯和背光灯的状态.<br>
	 * <table border="1" >
	 * <tr> <th>OFF</th> <th>RED</th> <th>BULE</th> <th>STR</th> </tr>
	 * <tr> <td>关闭</td>  <td>红色</td> <td>蓝色</td> <td>字符</td> </tr>
	 * <tr> <th>ON</th> <th>GREEN</th> <th>RGB</th> <th>BATCH</th> </tr>
	 * <tr> <td>开启</td>  <td>绿色</td> <td>三色</td> <td>批量发送</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int OFF = 0x00,ON = 0x01,STR = 0x05,BATCH = 0x06,
			RED = 0x01, GREEN = 0x02, BULE = 0x03, RGB = 0x04;
	
	/**
	 * statusCode() 扫描器模式.<br>
	 * <table border="1" >
	 * <tr> <th>ONCE</th> <th>ALWAYS</th> <th>NEVER</th> </tr>
	 * <tr> <td>触发</td>  <td>长亮</td> <td>关闭</td> </tr>
	 * </table><br>
	 * <br>
	 */
	final int ONCE = 0x00,ALWAYS = 0x01,NEVER = 0x02;
	
	/**
	 * 返回数据的字节数组
	 * @return 字节数组
	 */
	byte[] toBytes();

}
