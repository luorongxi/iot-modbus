package com.takeoff.iot.modbus.netty.handle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.takeoff.iot.modbus.netty.device.MiiControlCentre;
import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.channel.MiiContextChannel;
import com.takeoff.iot.modbus.netty.listener.MiiListener;
import com.takeoff.iot.modbus.netty.message.MiiMessage;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 类功能说明：自定义数据接收监听器<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Sharable
public class MiiListenerHandler extends SimpleChannelInboundHandler<MiiMessage> {
	
	private Map<Integer, MiiListener> listeners = new ConcurrentHashMap<>();
	private MiiControlCentre centre;
	
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
}
