package com.takeoff.iot.modbus.server.connect;

import com.takeoff.iot.modbus.common.entity.ChannelConnectData;
import com.takeoff.iot.modbus.common.enums.DeviceConnectEnum;
import com.takeoff.iot.modbus.common.utils.CacheUtils;
import com.takeoff.iot.modbus.common.utils.JudgeEmptyUtils;
import com.takeoff.iot.modbus.common.utils.SpringContextUtil;
import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.device.MiiDeviceChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.ChannelHandler.Sharable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 类功能说明：服务端链接管理器<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
@Sharable
public class MiiServerConnect extends ChannelInboundHandlerAdapter {

	private ApplicationContext getApplicationContext = SpringContextUtil.applicationContext;

	/**
	 * 连接成功次数
	 */
	private Map<String, Integer> onLineMap = new HashMap<>();

	/**
	 * 连接断开次数
	 */
	private Map<String, Integer> breakOffMap = new HashMap<>();

	public MiiServerConnect(){

	}


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//成功后，重连失败次数清零
		Channel channel = ctx.channel();
		ctx.fireChannelActive();
		if(!JudgeEmptyUtils.isEmpty(channel.remoteAddress())){
			String address = channel.remoteAddress().toString().substring(1,channel.remoteAddress().toString().length());
			MiiChannel miiChannel = new MiiDeviceChannel(channel);
			Integer onLine = (ObjectUtils.isEmpty(onLineMap.get(miiChannel.name())) ? 0 : onLineMap.get(miiChannel.name())) + 1;
			onLineMap.put(miiChannel.name(), onLine);
			ChannelConnectData connectServerData = new ChannelConnectData(this, DeviceConnectEnum.ON_LINE.getKey(), address, onLine);
			if(!JudgeEmptyUtils.isEmpty(connectServerData) && !JudgeEmptyUtils.isEmpty(getApplicationContext)){
				getApplicationContext.publishEvent(connectServerData);
				//将柜地址与通讯管道的绑定关系写入缓存
				CacheUtils.put(miiChannel.name(), miiChannel);
			}
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ctx.fireChannelInactive();
		Channel channel = ctx.channel();
		if(!JudgeEmptyUtils.isEmpty(channel) && !JudgeEmptyUtils.isEmpty(channel.remoteAddress())){
			String address = channel.remoteAddress().toString().substring(1,channel.remoteAddress().toString().length());
			MiiChannel miiChannel = new MiiDeviceChannel(channel);
			Integer breakOff = (ObjectUtils.isEmpty(breakOffMap.get(miiChannel.name())) ? 0 : breakOffMap.get(miiChannel.name())) + 1;
			breakOffMap.put(miiChannel.name(), breakOff);
			ChannelConnectData connectServerData = new ChannelConnectData(this, DeviceConnectEnum.BREAK_OFF.getKey(), address, breakOff);
			if(!JudgeEmptyUtils.isEmpty(connectServerData) && !JudgeEmptyUtils.isEmpty(getApplicationContext)){
				getApplicationContext.publishEvent(connectServerData);
			}
			//将通讯管道的绑定关系从缓存中删除
			CacheUtils.remove(miiChannel.name());
			//连接断开后的最后处理
			ctx.pipeline().remove(ctx.handler());
			ctx.deregister();
			ctx.close();
		}
	}

}
