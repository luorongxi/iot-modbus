package com.takeoff.iot.modbus.netty.listener;

import com.takeoff.iot.modbus.netty.device.MiiDevice;
import com.takeoff.iot.modbus.netty.channel.MiiChannel;
import com.takeoff.iot.modbus.netty.data.base.MiiSlotData;
import com.takeoff.iot.modbus.netty.message.MiiMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 类功能说明：为信息监听器 {@link MiiListener} 加读写锁.统一分组下写锁与写锁或者读锁之间互斥，读锁与读锁之间可以同时获得锁<br/>
 * 公司名称：takeoff开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiReadWriteLock {

	private boolean fair;
	private Map<Object, ReadWriteLock> locks;

	/**
	 * 使用默认（非公平）的排序属性创建一个读写锁.
	 */
	public MiiReadWriteLock() {
		this(false);
	}

	/**
	 * 使用给定的公平策略创建一个读写锁.
	 * @param fair 是否使用公平排序策略
	 */
	public MiiReadWriteLock(boolean fair) {
		this.fair = fair;
		locks = new HashMap<>(); 
	}
	
	/**
	 * 为信息监听器添加读锁.
	 * @param listener 原信息监听器
	 * @return 添加读锁信息监听器
	 */
	public MiiListener readLockListener(MiiListener listener){
		return new ReadLockListener(this, listener);
	}
	
	/**
	 * 为信息监听器添加写锁.
	 * @param listener 原信息监听器
	 * @return 添加写锁信息监听器
	 */
	public MiiListener writeLockListener(MiiListener listener){
		return new WriteLockListener(this, listener);
	}
	
	/**
	 * 生成分组对象.
	 * 默认以设备组通讯deviceGroup加上设备号作为分组条件，可重写该方法改变分组条件.
	 * @param deviceGroup
	 * @param message
	 * @return
	 */
	protected Object group(String deviceGroup, MiiMessage message) {
		if(message instanceof MiiSlotData){
			return new MiiDevice(deviceGroup,((MiiSlotData) message).device());
		} else {
			return new MiiDevice(deviceGroup, 0);
		}
	}
	
	ReadWriteLock lock(Object deviceGroup){
		ReadWriteLock lock;
		if((lock = locks.get(deviceGroup)) == null){
			synchronized (this) {
				lock = locks.computeIfAbsent(deviceGroup
						, v -> new ReentrantReadWriteLock(fair));
			}
		}
		return lock;
	}
}

abstract class LockListener implements MiiListener {
	protected MiiReadWriteLock rwlock;
	protected MiiListener listener;
	public LockListener(MiiReadWriteLock rwlock, MiiListener listener) {
		this.rwlock = rwlock;
		this.listener = listener;
	}
	
	public void receive(MiiChannel channel, MiiMessage message) {
		Lock lock = lock(message.deviceGroup(), message);
		lock.lock();
		try {
			listener.receive(channel, message);
		} finally {
			lock.unlock();
		}
	}

	protected abstract Lock lock(String deviceGroup, MiiMessage message);
}

class WriteLockListener extends LockListener {

	public WriteLockListener(MiiReadWriteLock rwlock, MiiListener listener) {
		super(rwlock, listener);
	}

	@Override
	protected Lock lock(String deviceGroup, MiiMessage message) {
		return rwlock.lock(rwlock.group(deviceGroup, message)).writeLock();
	}

}

class ReadLockListener extends LockListener {

	public ReadLockListener(MiiReadWriteLock rwlock, MiiListener listener) {
		super(rwlock, listener);
	}

	@Override
	protected Lock lock(String deviceGroup, MiiMessage message) {
		return rwlock.lock(rwlock.group(deviceGroup, message)).readLock();
	}
}
