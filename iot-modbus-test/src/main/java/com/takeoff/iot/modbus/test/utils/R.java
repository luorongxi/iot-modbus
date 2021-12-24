package com.takeoff.iot.modbus.test.utils;

import java.util.HashMap;
import java.util.Map;

import cn.hutool.http.HttpStatus;

/**
 * 类功能说明：返回数据<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	private static final String CODE_TAG = "code"; //返回码
	
	private static final String Type_TAG = "type"; //操作类型
	
	private static final String MSG_TAG = "msg"; //返回提示
	
	private static final String DATA_TAG = "data"; //返回数据

	public R() {
		put(CODE_TAG, HttpStatus.HTTP_OK);
		put(MSG_TAG, "success");
	}
	
	public static R error() {
		return error(HttpStatus.HTTP_INTERNAL_ERROR, "未知异常，请联系管理员");
	}
	
	public static R error(String msg) {
		return error(HttpStatus.HTTP_INTERNAL_ERROR, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put(CODE_TAG, code);
		r.put(MSG_TAG, msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put(MSG_TAG, msg);
		return r;
	}
	
	public static R ok(Object data) {
		R r = new R();
		r.put(DATA_TAG, data);
		return r;
	}

	public static R ok(String type, Object data, String msg) {
		R r = new R();
		r.put(Type_TAG, type);
		r.put(DATA_TAG, data);
		r.put(MSG_TAG, msg);
		return r;
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}
	
	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
