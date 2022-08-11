package com.takeoff.iot.modbus.client;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.takeoff.iot.modbus.client.connect.MiiConnectManager;
import com.takeoff.iot.modbus.client.message.sender.ClientMessageSender;
import com.takeoff.iot.modbus.client.message.sender.MiiClientMessageSender;
import com.takeoff.iot.modbus.common.bytes.factory.MiiDataFactory;
import com.takeoff.iot.modbus.common.message.MiiMessage;
import com.takeoff.iot.modbus.common.utils.JudgeEmptyUtils;
import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.data.factory.MiiClientDataFactory;
import com.takeoff.iot.modbus.netty.device.MiiDeviceChannel;
import com.takeoff.iot.modbus.netty.handle.MiiBasedFrameDecoder;
import com.takeoff.iot.modbus.netty.handle.MiiListenerHandler;
import com.takeoff.iot.modbus.netty.handle.MiiMessageDecoder;
import com.takeoff.iot.modbus.netty.handle.MiiMessageEncoder;
import com.takeoff.iot.modbus.netty.listener.MiiListener;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 类功能说明：设备通讯客户端<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
public class MiiClient extends ChannelInitializer<SocketChannel> {

	private static final int IDLE_TIMEOUT = 60;

	private Map<String, Object> workerGroupMap = new HashMap<String, Object>();

	private String deviceGroup;
	private int nThread;
	private MiiListenerHandler handler;
	private MiiDataFactory dataFactory;
	private ClientMessageSender sender;

	private Set<InetSocketAddress> addressSet = new HashSet<>();

	private Map<String, Object> cmMap = new HashMap<String, Object>();

	private Map<String, Object> futureMap = new HashMap<String, Object>();

	private static Map<String, Object> channelMap = new HashMap<>();
	
	public MiiClient(String deviceGroup){
		this(deviceGroup, 0);
	}
	
	public MiiClient(String deviceGroup,int nThread){
		this.deviceGroup = deviceGroup;
		this.nThread = nThread;
		this.handler = new MiiListenerHandler();
		this.dataFactory = new MiiClientDataFactory();
	}

	/**
	 * 连接服务端
	 */
	public ChannelFuture connect(String serverHost, int serverPort) throws InterruptedException {
		EventLoopGroup workerGroup = new NioEventLoopGroup(nThread);
		workerGroupMap.put(serverHost, workerGroup);
		Bootstrap boot = new Bootstrap()
				.group(workerGroup)
				.channel(NioSocketChannel.class)
				.handler(this);
		InetSocketAddress address = InetSocketAddress.createUnresolved(serverHost, serverPort);
		addressSet.add(address);
		MiiConnectManager cm = new MiiConnectManager(boot, address){
			@Override
			public void afterSuccess() {
				sender().registerGroup(serverHost, deviceGroup);
			}
		};
		cmMap.put(serverHost, cm);
		ChannelFuture future = cm.connect().sync();
		futureMap.put(serverHost, future);
		return future;
    }
	
	/**
	 * 停止服务
	 */
	public ChannelFuture disconnect(String serverHost){
		MiiConnectManager cm = (MiiConnectManager) cmMap.get(serverHost);
		cm.stop();
		ChannelFuture future = (ChannelFuture) futureMap.get(serverHost);
		ChannelFuture f = future.channel().closeFuture();
		EventLoopGroup workerGroup = (EventLoopGroup) workerGroupMap.get(serverHost);
		workerGroup.shutdownGracefully();
		future = null;
		return f;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		for(InetSocketAddress address : addressSet){
			MiiChannel channel = new MiiDeviceChannel(address, ch);
			MiiChannel isExist = (MiiChannel) channelMap.get(channel.name());
			if(JudgeEmptyUtils.isEmpty(isExist)){
				this.channelMap.put(channel.name(), channel);
				this.sender = new MiiClientMessageSender(channel);
				MiiConnectManager cm = (MiiConnectManager) cmMap.get(channel.name());
				p.addLast(cm);
				p.addLast(new IdleStateHandler(0, 0, IDLE_TIMEOUT, TimeUnit.SECONDS));
				p.addLast(new ChannelInboundHandlerAdapter(){

					@Override
					public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
						if(evt instanceof IdleStateEvent){
							sender().registerGroup(channel.name(), deviceGroup);
						} else {
							super.userEventTriggered(ctx, evt);
						}
					}

				});
				p.addLast(new MiiMessageEncoder());
				p.addLast(new MiiBasedFrameDecoder());
				p.addLast(new MiiMessageDecoder(channel, dataFactory));
				p.addLast(handler);
			}
		}
	}
		
	public ClientMessageSender sender(){
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

}
