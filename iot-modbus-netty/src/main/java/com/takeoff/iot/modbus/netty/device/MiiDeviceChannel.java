package com.takeoff.iot.modbus.netty.device;

import java.net.InetSocketAddress;

import com.takeoff.iot.modbus.netty.message.MiiMessage;
import com.takeoff.iot.modbus.netty.service.NameValueImpl;
import io.netty.channel.Channel;

public class MiiDeviceChannel extends NameValueImpl implements MiiDeviceGroup {
	
	private InetSocketAddress address;
	private Channel channel;
	
	public MiiDeviceChannel(InetSocketAddress address, Channel channel){
		this.channel = channel;
		this.address = address;
		name(address.getHostString());
	}
	
	public MiiDeviceChannel(Channel channel){
		this((InetSocketAddress) channel.remoteAddress(), channel);
	}
	
	public int port(){
		return address.getPort();
	}
	public String address() {
		return address.getAddress().getHostAddress();
	}

	public void send(MiiMessage msg) {
		channel.writeAndFlush(msg);
	}
}
