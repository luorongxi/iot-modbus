package com.takeoff.iot.modbus.netty.channel;

import java.net.InetSocketAddress;

import com.takeoff.iot.modbus.netty.message.MiiMessage;
import com.takeoff.iot.modbus.netty.service.NameValueImpl;
import io.netty.channel.ChannelHandlerContext;

public class MiiContextChannel extends NameValueImpl implements MiiChannel {
	
	private final ChannelHandlerContext ctx;
	private final InetSocketAddress address;
	
	public MiiContextChannel(ChannelHandlerContext ctx) {
		this.ctx = ctx;
		this.address = (InetSocketAddress) this.ctx.channel().remoteAddress();
		name(address.getHostString());
	}
	
	public void send(MiiMessage msg) {
		this.ctx.writeAndFlush(msg);
	}
	
	public InetSocketAddress remoteAddress() {
		return (InetSocketAddress) this.ctx.channel().remoteAddress();
	}
}
