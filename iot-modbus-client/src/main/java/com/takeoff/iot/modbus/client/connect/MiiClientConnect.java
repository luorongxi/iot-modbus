package com.takeoff.iot.modbus.client.connect;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.takeoff.iot.modbus.client.message.sender.MiiClientMessageSender;
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
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;

/**
 * 类功能说明：客户端链接管理器<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
@Sharable
public abstract class MiiClientConnect extends ChannelInboundHandlerAdapter implements TimerTask {

	private ApplicationContext getApplicationContext = SpringContextUtil.applicationContext;

	private static int TIMEOUT = 5000;

	private final Bootstrap boot;
	private SocketAddress address;
	private Timer timer;

	/**
	 * 连接成功次数
	 */
	private Map<String, Integer> onLineMap = new HashMap<>();

	/**
	 * 连接断开次数
	 */
	private Map<String, Integer> breakOffMap = new HashMap<>();

	/**
	 * 重连失败次数
	 */
	private Map<String, Integer> retriesMap = new HashMap<>();
	
	public MiiClientConnect(Bootstrap boot, SocketAddress address, int reconnectTime){
		this.boot = boot;
		this.address = address;
		this.TIMEOUT = reconnectTime;
		this.timer = new HashedWheelTimer();
	}
	
	public ChannelFuture connect() throws Exception {
		ChannelFuture future = boot.connect(address);
		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (!future.isSuccess()) {
					//连接失败，重连服务端，重连交给后端线程执行
					future.channel().eventLoop().schedule(() -> {
						Integer retries = (ObjectUtils.isEmpty(retriesMap.get(address.toString())) ? 0 : retriesMap.get(address.toString())) + 1;
						retriesMap.put(address.toString(), retries);
						if(!JudgeEmptyUtils.isEmpty(address)){
							ChannelConnectData connectServerData = new ChannelConnectData(this, DeviceConnectEnum.BREAK_RECONNECT.getKey(), address.toString(), retries);
							if(!JudgeEmptyUtils.isEmpty(connectServerData) && !JudgeEmptyUtils.isEmpty(getApplicationContext)){
								getApplicationContext.publishEvent(connectServerData);
							}
						}
						try {
							connect();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}, TIMEOUT, TimeUnit.MILLISECONDS);
				} else {
					//服务端连接成功
					afterSuccess();
					Integer onLine = (ObjectUtils.isEmpty(onLineMap.get(address.toString())) ? 0 : onLineMap.get(address.toString())) + 1;
					onLineMap.put(address.toString(), onLine);
					if(!JudgeEmptyUtils.isEmpty(address)){
						ChannelConnectData connectServerData = new ChannelConnectData(this, DeviceConnectEnum.ON_LINE.getKey(), address.toString(), onLine);
						if(!JudgeEmptyUtils.isEmpty(connectServerData) && !JudgeEmptyUtils.isEmpty(getApplicationContext)){
							getApplicationContext.publishEvent(connectServerData);
						}
					}
				}
			}
		});
		//对通道关闭进行监听
		future.channel().closeFuture().sync();
		return future;
	}

	@Override
	public void run(Timeout timeout) throws Exception {
		connect();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//成功后，重连失败次数清零
		Channel channel = ctx.channel();
		ctx.fireChannelActive();
		if(!JudgeEmptyUtils.isEmpty(channel.remoteAddress())){
			String address = channel.remoteAddress().toString().substring(1,channel.remoteAddress().toString().length());
			retriesMap.put(address.toString(), 0);
			//将柜地址与通讯管道的绑定关系写入缓存
			MiiChannel miiChannel = new MiiDeviceChannel(channel);
			CacheUtils.put(miiChannel.name(), miiChannel);
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ctx.fireChannelInactive();
		Channel channel = ctx.channel();
		if(!JudgeEmptyUtils.isEmpty(channel) && !JudgeEmptyUtils.isEmpty(channel.remoteAddress())){
			String address = channel.remoteAddress().toString().substring(1,channel.remoteAddress().toString().length());
			Integer breakOff = (ObjectUtils.isEmpty(breakOffMap.get(address)) ? 0 : breakOffMap.get(address)) + 1;
			breakOffMap.put(address, breakOff);
			ChannelConnectData connectServerData = new ChannelConnectData(this, DeviceConnectEnum.BREAK_OFF.getKey(), address, breakOff);
			if(!JudgeEmptyUtils.isEmpty(connectServerData) && !JudgeEmptyUtils.isEmpty(getApplicationContext)){
				getApplicationContext.publishEvent(connectServerData);
			}
			//将通讯管道的绑定关系从缓存中删除
			MiiChannel miiChannel = new MiiDeviceChannel(channel);
			MiiChannel isExist = (MiiChannel) CacheUtils.get(miiChannel.name());
			if(!JudgeEmptyUtils.isEmpty(isExist)){
				CacheUtils.remove(miiChannel.name());
				timer.newTimeout(this, TIMEOUT, TimeUnit.MILLISECONDS);
			}
			//连接断开后的最后处理
			ctx.pipeline().remove(ctx.handler());
			ctx.deregister();
			ctx.close();
		}
	}

	public abstract void afterSuccess();
}
