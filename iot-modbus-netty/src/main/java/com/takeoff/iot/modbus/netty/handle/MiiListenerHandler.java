package com.takeoff.iot.modbus.netty.handle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.takeoff.iot.modbus.common.entity.ChannelConnectData;
import com.takeoff.iot.modbus.common.enums.DeviceConnectEnum;
import com.takeoff.iot.modbus.common.utils.JudgeEmptyUtils;
import com.takeoff.iot.modbus.common.utils.SpringContextUtil;
import com.takeoff.iot.modbus.netty.device.MiiControlCentre;
import com.takeoff.iot.modbus.common.message.MiiMessage;
import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.channel.MiiContextChannel;
import com.takeoff.iot.modbus.netty.listener.MiiListener;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.context.ApplicationContext;

/**
 * 类功能说明：自定义数据接收监听器<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Sharable
public class MiiListenerHandler extends SimpleChannelInboundHandler<MiiMessage> {
	
	private Map<Integer, MiiListener> listeners = new ConcurrentHashMap<>();
	private MiiControlCentre centre;
	private ApplicationContext getApplicationContext = SpringContextUtil.applicationContext;
	
	public MiiListenerHandler(){
	}
	
	public MiiListenerHandler(MiiControlCentre centre){
		this.centre = centre;
	}
	
	public MiiListener addListener(int command, MiiListener listener){
		MiiListener pre = null;
		if(hasListener(command)){
			pre = listeners.get(command);
		}
		listeners.put(command, listener);
		return pre;
	}
	
	public MiiListener removeListener(int command){
		return listeners.remove(command);
	}
	
	public boolean hasListener(int command){
		return listeners.get(command) != null;
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MiiMessage msg) throws Exception {
		MiiListener listener = listeners.get(msg.command());
		if(listener != null){
			MiiChannel channel = centre == null ? new MiiContextChannel(ctx) : centre.get(msg.deviceGroup());
			listener.receive(channel == null ? new MiiContextChannel(ctx) : channel, msg);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		if(!JudgeEmptyUtils.isEmpty(channel.remoteAddress())){
			String address = channel.remoteAddress().toString().substring(1,channel.remoteAddress().toString().length());
			ChannelConnectData connectServerData = new ChannelConnectData(this, DeviceConnectEnum.ON_LINE.getKey(), address);
			if(!JudgeEmptyUtils.isEmpty(connectServerData) && !JudgeEmptyUtils.isEmpty(getApplicationContext)){
				getApplicationContext.publishEvent(connectServerData);
			}
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		if(!JudgeEmptyUtils.isEmpty(channel.remoteAddress())){
			String address = channel.remoteAddress().toString().substring(1,channel.remoteAddress().toString().length());
			ChannelConnectData connectServerData = new ChannelConnectData(this, DeviceConnectEnum.BREAK_OFF.getKey(), address);
			if(!JudgeEmptyUtils.isEmpty(connectServerData) && !JudgeEmptyUtils.isEmpty(getApplicationContext)){
				getApplicationContext.publishEvent(connectServerData);
			}
		}
		//连接断开后的最后处理
		ctx.pipeline().remove(this);
		ctx.deregister();
		ctx.close();
	}
}
