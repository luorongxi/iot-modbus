package com.takeoff.iot.modbus.client.connect;

import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

/**
 * 类功能说明：客户端链接管理器<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Sharable
public abstract class MiiConnectManager extends ChannelInboundHandlerAdapter implements TimerTask {
	private static final int STATE_START = 1,STATE_STOP = 0;
	
	private final Bootstrap boot;
	private final SocketAddress address;
	private final Timer timer;
		
	private volatile int state = STATE_START;
	/**
	 * 重连失败次数
	 */
	private int retries;
	
	public MiiConnectManager(Bootstrap boot,SocketAddress address){
		this.boot = boot;
		this.address = address;
		this.timer = new HashedWheelTimer();
	}
	
	public void start(){
		state = STATE_START;
	}
	
	public void stop(){
		state = STATE_STOP;
	}
	
	public ChannelFuture connect(){
		ChannelFuture future;
		
		synchronized (boot) {		
			future = boot.connect(address);
		}
		
		//连接失败触发Channel失效事件
		future.addListener((ChannelFutureListener) listener -> {
			if(!listener.isSuccess()){
				listener.channel().pipeline().fireChannelInactive();
			} else {
				afterSuccess();
			}
		});
		return future;
	}
	
	@Override
	public void run(Timeout timeout) throws Exception {
		connect();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		//成功后，重连失败次数清零
		retries = 0;

		ctx.fireChannelActive();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		
		retries++;
				
		if(state == STATE_START){
			//根据重连次数，翻倍重连间隔，最高约1分钟（65.536秒）
			long timeout = 4 << (Math.min(retries, 15));
			timer.newTimeout(this, timeout, TimeUnit.MILLISECONDS);
		}
		
		ctx.fireChannelInactive();
	}
	
	public abstract void afterSuccess();
}
