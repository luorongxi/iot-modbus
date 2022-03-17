package com.takeoff.iot.modbus.serialport.utils;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import com.takeoff.iot.modbus.serialport.data.factory.SerialportDataReceiveFactory;
import com.takeoff.iot.modbus.serialport.handler.NettyRxtxDecoderHandler;
import com.takeoff.iot.modbus.serialport.handler.NettyRxtxFrameDecoder;
import org.bouncycastle.util.encoders.Hex;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.rxtx.RxtxChannel;
import io.netty.channel.rxtx.RxtxChannelConfig;
import io.netty.channel.rxtx.RxtxDeviceAddress;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 类功能说明：netty链接串口工具类 <br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Slf4j
public class NettyRxtxClientUtil {
	
	public static RxtxChannel channel;

	public static void createRxtx(String portName, Integer baudrate, Integer thread) throws Exception {
		RxtxChannel rxtxChannel = new RxtxChannel();
        //串口使用阻塞io
        EventLoopGroup group = new OioEventLoopGroup(thread);
        try {
            Bootstrap bootstrap  = new Bootstrap();
            bootstrap.group(group)
                    .channelFactory(() -> {
                        rxtxChannel.config()
                                .setBaudrate(baudrate)
                                .setDatabits(RxtxChannelConfig.Databits.DATABITS_8)
                                .setParitybit(RxtxChannelConfig.Paritybit.NONE)
                                .setStopbits(RxtxChannelConfig.Stopbits.STOPBITS_1);
                        return rxtxChannel ;
                    })
                    .handler(new ChannelInitializer<RxtxChannel>() {
                        @Override
                        protected void initChannel(RxtxChannel rxtxChannel) {
                            rxtxChannel.pipeline().addLast(
                            		new NettyRxtxFrameDecoder(),
                                    new StringEncoder(StandardCharsets.UTF_8),
                                    new NettyRxtxDecoderHandler(new SerialportDataReceiveFactory())
                            );
                        }
                    });
            channel = rxtxChannel;
            ChannelFuture f = bootstrap.connect(new RxtxDeviceAddress(portName)).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void start(String portName, Integer baudrate, Integer thread){
        CompletableFuture.runAsync(()->{
            try {
                //阻塞的函数
            	createRxtx(portName, baudrate, thread);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, Executors.newSingleThreadExecutor());//不传默认使用ForkJoinPool，都是守护线程
    }

    /**
     * 发送数据
     * @param bytes
     */
    public static void writeAndFlush(byte[] bytes) {
        if(!channel.isActive()
                || !channel.isOpen()
                || !channel.isWritable()){
            return;
        }
        ByteBuf buffer = channel.alloc().buffer();
        ByteBuf byteBuf = buffer.writeBytes(bytes);
        channel.writeAndFlush(byteBuf).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                log.info("指令下发成功：" + Hex.toHexString(bytes));
            } else {
                log.error("指令下发失败：" + Hex.toHexString(bytes));
            }
        });
    }
}
