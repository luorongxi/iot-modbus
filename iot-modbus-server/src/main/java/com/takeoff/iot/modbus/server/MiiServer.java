package com.takeoff.iot.modbus.server;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.takeoff.iot.modbus.common.utils.CacheUtils;
import com.takeoff.iot.modbus.netty.device.MiiDeviceChannel;
import com.takeoff.iot.modbus.netty.device.MiiDeviceGroup;
import com.takeoff.iot.modbus.netty.device.MiiControlCentre;
import com.takeoff.iot.modbus.common.bytes.factory.MiiDataFactory;
import com.takeoff.iot.modbus.common.data.MiiHeartBeatData;
import com.takeoff.iot.modbus.common.message.MiiMessage;
import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.channel.MiiChannelGroup;
import com.takeoff.iot.modbus.netty.data.factory.MiiServerDataFactory;
import com.takeoff.iot.modbus.netty.handle.*;
import com.takeoff.iot.modbus.netty.listener.MiiListener;
import com.takeoff.iot.modbus.server.connect.MiiServerConnect;
import com.takeoff.iot.modbus.server.message.sender.MiiServerMessageSender;
import com.takeoff.iot.modbus.server.message.sender.ServerMessageSender;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 类功能说明：设备通讯服务端<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
public class MiiServer extends ChannelInitializer<SocketChannel> implements MiiControlCentre {

	private static int IDLE_TIMEOUT = 60000;
	
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private ChannelFuture future;
	private int port,nThread;
	@Getter
	private MiiChannelGroup groups;
	private MiiServerConnect connect;
	private ServerMessageSender sender;
	private MiiListenerHandler handler;
	private MiiDataFactory dataFactory;

	/**
	 * 创建指定服务端口，默认线程数的服务端
	 * @param port 服务端口
	 */
	public MiiServer(int port){
		this(port, 0, IDLE_TIMEOUT);
	}
	
	/**
	 * 创建指定服务端口，指定线程数的服务端
	 * @param port 服务端口
	 * @param nThread 执行线程池线程数
	 * @param heartBeatTime 心跳检测超时时间(单位：毫秒)
	 */
	public MiiServer(int port, int nThread, int heartBeatTime){
		this.port = port;
		this.nThread = nThread;
		this.IDLE_TIMEOUT = heartBeatTime;
		this.groups = new MiiChannelGroup();
		this.connect = new MiiServerConnect();
		this.sender = new MiiServerMessageSender();
		this.handler = new MiiListenerHandler(this.groups);
		this.handler.addListener(MiiMessage.HEARTBEAT, new MiiListener() {
			
			@Override
			public void receive(MiiChannel channel, MiiMessage message) {
				//通讯通道绑定设备IP
				groups.get(channel.name()).name(message.deviceGroup());
				log.info("Netty通讯已绑定设备IP："+ message.deviceGroup());
			}
		});
		this.dataFactory = new MiiServerDataFactory();
	}
	
	/**
	 * 启动服务
	 */
	public void start(){
	    bossGroup = new NioEventLoopGroup(1);
	    workerGroup = new NioEventLoopGroup(nThread);
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
         .channel(NioServerSocketChannel.class)
         .handler(new LoggingHandler(LogLevel.INFO))
         .childHandler(this);
        future = b.bind(port);
	}
	
	/**
	 * 停止服务
	 */
	public void stop(){
		future.channel().closeFuture();
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}
	
	/**
	 * 根据名称/地址找已连接设备组
	 * 名称/地址不存在或者未连接时返回null值
	 * @param name 名称/地址
	 * @return 设备组
	 */
	public MiiChannel group(String name) {
		return get(name);
	}
	
	/**
	 * 列出所有已连接设备组清单
	 * @return 所有已连接身边组清单
	 */
	public List<MiiChannel> groups() {
		return groups.list();
	}
		
	public ServerMessageSender sender(){
		return sender;
	}
	
	/**
	 * 添加接收指定指令的消息监听器
	 * @param command 指令类型 {@link MiiMessage}
	 * @param listener 消息监听器
	 * @return 上一个消息监听器，如果没有返回null
	 */
	public MiiListener addListener(int command, MiiListener listener){
		return handler.addListener(command, listener);
	}
	
	/**
	 * 移除接收指定指令的消息监听器
	 * @param command 指令类型 {@link MiiMessage}
	 * @return 移除消息监听器，如果没有返回null
	 */
	public MiiListener removeListener(int command){
		return handler.removeListener(command);
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		MiiDeviceGroup group = new MiiDeviceChannel(ch);
		add(group);
		//服务端心跳检测超时时间，超时则主动断开链接
		p.addLast(new IdleStateHandler(0, 0, IDLE_TIMEOUT, TimeUnit.MILLISECONDS));
		p.addLast(new ChannelInboundHandlerAdapter(){
			
			@Override
			public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
				if(evt instanceof IdleStateEvent){
					ctx.disconnect();
				} else {
					super.userEventTriggered(ctx, evt);
				}
			}
		});
		p.addLast(new MiiMessageEncoder());
		p.addLast(new MiiBasedFrameDecoder());
		p.addLast(new MiiMessageDecoder(dataFactory));
		p.addLast(connect);
		p.addLast(handler);
		p.addLast(new MiiExceptionHandler());
	}

	@Override
	public boolean add(MiiChannel channel) {
		return groups.add(channel);
	}

	@Override
	public MiiChannel remove(String name) {
		return groups.remove(name);
	}

	@Override
	public MiiChannel get(String name) {
		return groups.get(name);
	}

}
