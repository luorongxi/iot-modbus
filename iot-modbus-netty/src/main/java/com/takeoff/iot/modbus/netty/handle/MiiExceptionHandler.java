package com.takeoff.iot.modbus.netty.handle;

import com.takeoff.iot.modbus.common.entity.ChannelConnectData;
import com.takeoff.iot.modbus.common.enums.DeviceConnectEnum;
import com.takeoff.iot.modbus.common.utils.JudgeEmptyUtils;
import com.takeoff.iot.modbus.common.utils.SpringContextUtil;
import org.springframework.context.ApplicationContext;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MiiExceptionHandler extends ChannelDuplexHandler {
	
	private ApplicationContext getApplicationContext = SpringContextUtil.applicationContext;
	
	public MiiExceptionHandler(){
		
	}

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception { 
    	Channel channel = ctx.channel();
    	if(!JudgeEmptyUtils.isEmpty(channel.remoteAddress())){
    		String address = channel.remoteAddress().toString().substring(1,channel.remoteAddress().toString().length());
        	ChannelConnectData connectServerData = new ChannelConnectData(this, DeviceConnectEnum.ABNORMAL.getKey(), address);
            if(!JudgeEmptyUtils.isEmpty(connectServerData) && !JudgeEmptyUtils.isEmpty(getApplicationContext)){
            	getApplicationContext.publishEvent(connectServerData);
            }
            //由Tail节点对异常进行统一处理
            if(cause instanceof RuntimeException){
                log.info("处理业务异常："+channel.remoteAddress());
            }
            super.exceptionCaught(ctx, cause);
    	}
    }
}
