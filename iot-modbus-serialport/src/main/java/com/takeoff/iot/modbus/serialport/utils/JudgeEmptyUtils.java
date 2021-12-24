package com.takeoff.iot.modbus.serialport.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类功能说明：判空工具类<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public final class 	JudgeEmptyUtils{

    /**
     * 私有构造方法,将该工具类设为单例模式
     */
    private JudgeEmptyUtils() {
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str);
    }

    /**
     * 判断对象数组是否为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object[] obj) {
        return null == obj || 0 == obj.length;
    }

    /**
     * 判断对象是否为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        }
        if (obj instanceof String) {
            return ((String) obj).trim().isEmpty();
        }
        return !(obj instanceof Number) ? false : true;
    }

    /**
     * 判断集合是否为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(List<?> obj) {
        return null == obj || obj.isEmpty();
    }


    /**
     * 判断Map集合是否为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(Map<?, ?> obj) {
        return null == obj || obj.isEmpty();
    }

    /**
     * 判断Set集合是否为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(Set<?> obj) {
        return null == obj || obj.isEmpty();
    }

    



}
