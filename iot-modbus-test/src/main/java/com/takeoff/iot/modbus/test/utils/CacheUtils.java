package com.takeoff.iot.modbus.test.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 类功能说明：简单内存缓存工具类<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class CacheUtils {
	//键值对集合
    private final static Map<String, Entity> map = new HashMap<>();
    //定时器线程池，用于清除过期缓存
    private final static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
 
    /**
     * 添加缓存
     * @param key  键
     * @param data 值
     */
    public synchronized static void put(String key, Object data) {
    	CacheUtils.put(key, data, 0);
    }
 
    /**
     * 添加缓存
     * @param key    键
     * @param data   值
     * @param expire 过期时间，单位：毫秒， 0表示无限长
     */
    public synchronized static void put(String key, Object data, long expire) {
        //清除原键值对
    	CacheUtils.remove(key);
        //设置过期时间
        if (expire > 0) {
            Future future = executor.schedule(new Runnable() {
                @Override
                public void run() {
                    //过期后清除该键值对
                    synchronized (CacheUtils.class) {
                        map.remove(key);
                    }
                }
            }, expire, TimeUnit.MILLISECONDS);
            map.put(key, new Entity(data, future));
        } else {
            //不设置过期时间
            map.put(key, new Entity(data, null));
        }
    }
 
    /**
     * 读取缓存
     * @param key 键
     * @return
     */
    public synchronized static Object get(String key) {
        Entity entity = map.get(key);
        return entity == null ? null : entity.getValue();
    }
 
    /**
     * 读取缓存
     * @param key 键
     *            * @param clazz 值类型
     * @return
     */
    public synchronized static <T> T get(String key, Class<T> clazz) {
        return clazz.cast(CacheUtils.get(key));
    }
 
    /**
     * 清除缓存
     * @param key
     * @return
     */
    public synchronized static Object remove(String key) {
        //清除原缓存数据
        Entity entity = map.remove(key);
        if (entity == null) {
            return null;
        }
        //清除原键值对定时器
        Future future = entity.getFuture();
        if (future != null) {
            future.cancel(true);
        }
        return entity.getValue();
    }
 
    /**
     * 查询当前缓存的键值对数量
     * @return
     */
    public synchronized static int size() {
        return map.size();
    }
 
    /**
     * 缓存实体类
     */
    private static class Entity {
        //键值对的value
        private Object value;
        //定时器Future
        private Future future;
 
        public Entity(Object value, Future future) {
            this.value = value;
            this.future = future;
        }
 
        /**
         * 获取值
         * @return
         */
        public Object getValue() {
            return value;
        }
 
        /**
         * 获取Future对象
         * @return
         */
        public Future getFuture() {
            return future;
        }
    }
}
