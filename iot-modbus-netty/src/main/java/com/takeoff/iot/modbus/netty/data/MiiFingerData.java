package com.takeoff.iot.modbus.netty.data;

import java.util.ArrayList;
import java.util.List;

import com.takeoff.iot.modbus.netty.data.base.MiiSlotData;
import com.takeoff.iot.modbus.netty.utils.IntegerByteTransform;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.util.encoders.Hex;

import lombok.extern.slf4j.Slf4j;

/**
 * 类功能说明：指静脉指令处理<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
public class MiiFingerData extends MiiSlotData implements Finger {

	private int fingerType;
	private int fingerCmd;
	private int fingerId;
	private List fingerIdList;
	private byte[] fingerTemplate;
	
	public MiiFingerData(byte[] datas) {
		super(datas);
		fingerDataHandle();
	}
	
	private void fingerDataHandle() {
		fingerType = datas[FINGERTYPE_INDEX] & 0xFF;
		boolean userTran = false;
		switch (fingerType) {
        case WDH320S_USER_Veri_GetID:
        	log.info("WDH320S_USER_Veri_GetID：" +Hex.toHexString(datas));
            byte[] fingerByte = ArrayUtils.subarray(datas, 8, datas.length - 3);
            fingerId = IntegerByteTransform.bytesToInt(fingerByte);
            break;
        case WDH320S_USER_Tran:
        	log.info("WDH320S_USER_Tran：" +Hex.toHexString(datas));
            fingerDeviceData();
            userTran = true;
            break;
		}
		//如果是透传则不用再次解析
		if(!userTran){
			fingerDeviceData();
		}
    }

	private void fingerDeviceData() {
		fingerCmd = datas[FINGERCMD_INDEX] & 0xFF;
        switch (fingerCmd) {
            case CMD_UPLOAD_ALL_ID://上传所有手指 ID
            	log.info("CMD_UPLOAD_ALL_ID：" +Hex.toHexString(datas));
                byte[] fingerCountByte = ArrayUtils.subarray(datas, 16, 18);
                int fingerCount = IntegerByteTransform.bytesToInt(fingerCountByte);
                byte[] fingerIds = ArrayUtils.subarray(datas, 18, 18 + fingerCount * 3);
                fingerIdList = new ArrayList();
                for (int i = 0; i < fingerIds.length; i += 3) {
                    byte[] fingerIdByte = new byte[2];
                    fingerIdByte[0] = fingerIds[i];
                    fingerIdByte[1] = fingerIds[i + 1];
                    fingerId = IntegerByteTransform.bytesToInt(fingerIdByte);
                    fingerIdList.add(fingerId);
                }
                break;
            case CMD_UPLOAD_INFOR_TEMPLATES://上传指定手指以及对应模板
            	log.info("CMD_UPLOAD_INFOR_TEMPLATES：" +Hex.toHexString(datas));
                //大于9说明有附加指令
                if (datas.length > 9) {
                    byte[] fingerIdByte = ArrayUtils.subarray(datas, 16, 18);
                    fingerId = IntegerByteTransform.bytesToInt(fingerIdByte);
                    fingerTemplate = ArrayUtils.subarray(datas, 16, datas.length - 2);
                }
                break;
            case CMD_REG_END://注册手指
            	log.info("CMD_REG_END：" +Hex.toHexString(datas));
            	byte gid = datas[GID_INDEX];
                if (gid == 0x00) {
                	fingerType = WDH320S_USER_Reg_End;
                } else {
                	fingerType = WDH320S_USER_FALT;
                }
                break;
            case CMD_UPLOAD_VERSION://获取固件版本号
            	log.info("CMD_UPLOAD_VERSION：" +Hex.toHexString(datas));
                break;
        }
	}

	/**
	 * 返回枚举标识
	 * @return 枚举标识
	 */
	@Override
	public int fingerType() {
		return fingerType;
	}

	/**
	 * 返回状态码
	 * @return 状态码
	 */
	@Override
	public int fingerStatus() {
		return datas[FINGERSTATUS_INDEX] & 0xFF;
	}

	/**
	 * 返回指令码
	 * @return 指令码
	 */
	@Override
	public int fingerCmd() {
		return fingerCmd;
	}

	/**
	 * 返回手指ID
	 * @return 手指ID
	 */
	@Override
	public int fingerId() {
		return fingerId;
	}

	/**
	 * 返回手指ID集合
	 * @return 手指ID集合
	 */
	public List fingerIdList() {
		return fingerIdList;
	}
	
	/**
	 * 返回指静脉模板
	 * @return 指静脉模板
	 */
	public byte[] fingerTemplate() {
		return fingerTemplate;
	}

}
