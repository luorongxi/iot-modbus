package com.takeoff.iot.modbus.common.bytes.factory;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.takeoff.iot.modbus.common.data.MiiData;
import com.takeoff.iot.modbus.common.entity.LcdData;
import com.takeoff.iot.modbus.common.message.MiiMessage;
import com.takeoff.iot.modbus.common.utils.IntegerToByteUtil;
import com.takeoff.iot.modbus.common.utils.JudgeEmptyUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 类功能说明：LCD数据拼装工厂<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiLcdData2BytesFactory implements MiiEntityBytesFactory<LcdData> {

private static final Charset DEFAULT_CHARSET = Charset.forName("GBK");
	
	private Charset charset;
	
	public MiiLcdData2BytesFactory(Charset charset){
		this.charset = charset;
	}
	
	public MiiLcdData2BytesFactory(){
		this(DEFAULT_CHARSET);
	}
	
	@Override
	public byte[] toBytes(LcdData lcdData) {
		//商品名
		byte[] nameByte = new byte[0];
		if(!JudgeEmptyUtils.isEmpty(lcdData.getName())){
			byte[] bytes = toBytes(lcdData.getName());
			nameByte = getDataBytes(bytes, MiiData.AMP_LCD_PARA_NAME);
		}
		//商品规格
		byte[] specByte = new byte[0];
		if(!JudgeEmptyUtils.isEmpty(lcdData.getSpec())){
			byte[] bytes = toBytes(lcdData.getSpec());
			specByte = getDataBytes(bytes, MiiData.AMP_LCD_PARA_SPEC);
		}
		//数量参数（库存）
		byte[] stockByte = new byte[0];
		if(lcdData.getStock() >= 0){
			byte[] bytes = toBytes(String.valueOf(lcdData.getStock()));
			stockByte = getDataBytes(bytes, MiiData.AMP_LCD_PARA_NUM);
		}
		//生产厂家
		byte[] factoryByte = new byte[0];
		if(!JudgeEmptyUtils.isEmpty(lcdData.getFactory())){
			byte[] bytes = toBytes(lcdData.getFactory());
			factoryByte = getDataBytes(bytes, MiiData.AMP_LCD_PARA_VENDER);
		}
		//商品编码
		byte[] codeByte = new byte[0];
		if(!JudgeEmptyUtils.isEmpty(lcdData.getCode())){
			byte[] bytes = toBytes(lcdData.getCode());
			codeByte = getDataBytes(bytes, MiiData.AMP_LCD_PARA_CODE);
		}
		//商品单位
		byte[] unitByte = new byte[0];
		if(!JudgeEmptyUtils.isEmpty(lcdData.getUnit())){
			byte[] bytes = toBytes(lcdData.getUnit());
			unitByte = getDataBytes(bytes, MiiData.AMP_LCD_PARA_UNIT);
		}
		//上架/拣选数量
		byte[] quantityByte = new byte[0];
		if(lcdData.getQuantity() >= 0){
			byte[] bytes = toBytes(String.valueOf(lcdData.getQuantity()));
			quantityByte = getDataBytes(bytes, MiiData.AMP_LCD_PARA_EX1);
		}
		//任务id（识别码）
		byte[] taskIdByte = new byte[0];
		if(!JudgeEmptyUtils.isEmpty(lcdData.getTaskId())){
			byte[] bytes = toBytes(lcdData.getTaskId());
			taskIdByte = getDataBytes(bytes, MiiData.AMP_LCD_PARA_SN);
		}
		//显示模式
		byte[] setByte = new byte[0];
		if(lcdData.getShowType() >= 0){
			byte[] bytes = toBytes(String.valueOf(lcdData.getShowType()));
			setByte = getDataBytes(bytes, MiiData.AMP_LCD_PARA_SET);
		}
		//所有LCD显示数据
		byte[] lcdDataBytes = new byte[nameByte.length + specByte.length + stockByte.length + factoryByte.length + codeByte.length + unitByte.length + quantityByte.length + taskIdByte.length + setByte.length];
		System.arraycopy(nameByte, 0, lcdDataBytes, 0, nameByte.length);
		System.arraycopy(specByte, 0, lcdDataBytes, nameByte.length, specByte.length);
		System.arraycopy(stockByte, 0, lcdDataBytes, nameByte.length + specByte.length, stockByte.length);
		System.arraycopy(factoryByte, 0, lcdDataBytes, nameByte.length + specByte.length + stockByte.length, factoryByte.length);
		System.arraycopy(codeByte, 0, lcdDataBytes, nameByte.length + specByte.length + stockByte.length + factoryByte.length, codeByte.length);
		System.arraycopy(unitByte, 0, lcdDataBytes, nameByte.length + specByte.length + stockByte.length + factoryByte.length + codeByte.length, unitByte.length);
		System.arraycopy(quantityByte, 0, lcdDataBytes, nameByte.length + specByte.length + stockByte.length + factoryByte.length + codeByte.length + unitByte.length, quantityByte.length);
		System.arraycopy(taskIdByte, 0, lcdDataBytes, nameByte.length + specByte.length + stockByte.length + factoryByte.length + codeByte.length + unitByte.length + quantityByte.length, taskIdByte.length);
		System.arraycopy(setByte, 0, lcdDataBytes, nameByte.length + specByte.length + stockByte.length + factoryByte.length + codeByte.length + unitByte.length + quantityByte.length + taskIdByte.length, setByte.length);
		return lcdDataBytes;
	}

	/**
	 * 拼装LCD显示数据（格式：命令符1位 + 数据长度1位 + 数据字符串）
	 * 参数：@param bytes
	 * 参数：@param flag
	 * 参数：@return <br/>
	 */
	private byte[] getDataBytes(byte[] bytes, int flag) {
		byte[] dateBytes = new byte[bytes.length + 2];
		dateBytes[MiiMessage.BEGIN_INDEX] = (byte) flag;
		byte[] lengthBytes = IntegerToByteUtil.intToByteArray(bytes.length, 1);
		System.arraycopy(lengthBytes, 0, dateBytes, 1, lengthBytes.length);
		System.arraycopy(bytes, 0, dateBytes , 2, bytes.length);
		return dateBytes;
	}

	/**
	 * 字符串转16进制byte[]
	 * @param contents
	 * @return
	 */
	@Override
	public byte[] toBytes(String... contents) {
		List<Byte> resList = new ArrayList<>();
		for(int i = 0; i < contents.length;i++){
			String content = contents[i];
			Collections.addAll(resList, ArrayUtils.toObject(content.getBytes(charset)));
		}
		return ArrayUtils.toPrimitive(
				resList.toArray(new Byte[resList.size()]));
	}
}
