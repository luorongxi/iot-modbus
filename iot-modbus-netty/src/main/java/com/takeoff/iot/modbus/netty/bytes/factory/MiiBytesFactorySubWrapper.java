package com.takeoff.iot.modbus.netty.bytes.factory;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 类功能说明：指令长度截取工厂<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public class MiiBytesFactorySubWrapper<V extends E,E> implements MiiBytesFactory<E> {
	private MiiBytesFactory<V> factory;
	private int startPos, endPos;
	private Type vType;
	
	public MiiBytesFactorySubWrapper(MiiBytesFactory<V> factory, int startPos, int endPos){
		this.factory = factory;
		this.startPos = startPos;
		this.endPos = endPos;
		
		if(this.factory.getClass().getGenericSuperclass() instanceof ParameterizedType){
			vType = ((ParameterizedType) this.factory.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			if(!(vType instanceof Class)){
				throw new RuntimeException("因为校验类型需要，请factory参数在new创建对象的构造函数后添加{}");
			}
		} else if(this.factory.getClass().getGenericInterfaces()[0] instanceof ParameterizedType){
				vType = ((ParameterizedType) this.factory.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
				if(vType instanceof ParameterizedType){
					vType = ((ParameterizedType) vType).getRawType();
				}
				if(!(vType instanceof Class)){
					throw new RuntimeException("因为校验类型需要，请factory参数在new创建对象的构造函数后添加{}");
				}
		} else {
			throw new RuntimeException("因为校验类型需要，请factory参数在new创建对象的构造函数后添加{}");
		}
	}
	
	@Override
	public byte[] toBytes(E... contents) {
		
		if(startPos >=  contents.length){
			return ArrayUtils.EMPTY_BYTE_ARRAY;
		}
		
		E[] subs = ArrayUtils.subarray(contents, startPos
				, Math.min(endPos > 0 ? endPos : contents.length, contents.length));
		List<V> list = new ArrayList<>();
		for(E content : subs){
			try {
				list.add((V) content);
			} catch (ClassCastException e) {
				throw new ClassCastException(
						String.format("%s和%s之间存在%s类型."
								, startPos, endPos, content.getClass().getSimpleName()));
			}
		}
		
		return factory.toBytes(list.toArray((V[]) Array.newInstance((Class) vType, list.size())));
	}

	public Type getVType(){
		return vType;
	}
}

