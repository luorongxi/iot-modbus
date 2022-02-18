package com.takeoff.iot.modbus.common.entity;

import lombok.Data;

/**
 * 类功能说明：门锁状态实体类<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
@Data
public class LcdData {

	/**
	 * 任务id（识别码）
	 */
	private String taskId;
	
	/**
	 * 显示模式（0：无；1：清除匹配内容，需要与识别码一起下发；2：清除全部显示数据；3：刷新时间同步；4：层板断电；5：层板上电；6：显示上架模式；7：显示库存模式；8：显示拣选模式）
	 */
	private int showType;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 设备组编码
	 */
	private String deviceGroup;
	
	/**
	 * 设备号
	 */
	private int device;
	
	/**
	 * 层号
	 */
	private int shelf;
	
	/**
	 * 槽位号
	 */
	private int slot;
	
	/**
	 * 商品编码
	 */
	private String code;
	
	/**
	 * 商品名称
	 */
	private String name;
	
	/**
	 * 商品规格
	 */
	private String spec;
	
	/**
	 * 生产厂家
	 */
	private String factory;
	
	/**
	 * 商品单位
	 */
	private String unit;
	
	/**
	 * 库存数量
	 */
	private int stock;
	
	/**
	 * 上架/拣选数量
	 */
	private int quantity;
	
}
