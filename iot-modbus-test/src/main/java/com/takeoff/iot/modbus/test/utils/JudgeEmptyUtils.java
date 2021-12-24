package com.takeoff.iot.modbus.test.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类功能说明：判空工具类<br/>
 * 公司名称：TF（腾飞）开源 <br/>
 * 作者：luorongxi <br/>
 */
public final class 	JudgeEmptyUtils{

    public static final String EMPTY = "";

    private final static Logger logger = LoggerFactory.getLogger(JudgeEmptyUtils.class);

    /**
     * 函数功能说明 ： 私有构造方法,将该工具类设为单例模式<br/>
     * 参数：  <br/>
     */
    private JudgeEmptyUtils() {
    }

    /**
     * 函数功能说明 ：  判断字符串是否为空 <br/>
     * 参数：@param str
     * 参数：@return <br/>
     * return：boolean <br/>
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str);
    }

    /**
     * 函数功能说明 ：  判断对象数组是否为空<br/>
     * 参数：@param obj
     * 参数：@return <br/>
     * return：boolean <br/>
     */
    public static boolean isEmpty(Object[] obj) {
        return null == obj || 0 == obj.length;
    }

    /**
     * 函数功能说明 ：判断对象是否为空 <br/>
     * 参数：@param obj
     * 参数：@return <br/>
     * return：boolean <br/>
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
     * 函数功能说明 ： 判断集合是否为空<br/>
     * 参数：@param obj
     * 参数：@return <br/>
     * return：boolean <br/>
     */
    public static boolean isEmpty(List<?> obj) {
        return null == obj || obj.isEmpty();
    }
    

    /**
     * 函数功能说明 ： 判断Map集合是否为空 <br/>
     * 参数：@param obj
     * 参数：@return <br/>
     * return：boolean <br/>
     */
    public static boolean isEmpty(Map<?, ?> obj) {
        return null == obj || obj.isEmpty();
    }

    /**
     * 函数功能说明 ： 判断Set集合是否为空 <br/>
     * 参数：@param obj
     * 参数：@return <br/>
     * return：boolean <br/>
     */
    public static boolean isEmpty(Set<?> obj) {
        return null == obj || obj.isEmpty();
    }

    /**
     * 函数功能说明 ：获得文件名的后缀名 <br/>
     * 参数：@param fileName
     * 参数：@return <br/>
     * return：String <br/>
     */
    public static String getExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 函数功能说明 ： 获取去掉横线的长度为32的UUID串<br/>
     * 参数：@return <br/>
     * return：String <br/>
     */
    public static String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 函数功能说明 ： 获取带横线的长度为36的UUID串<br/>
     * 参数：@return <br/>
     * return：String <br/>
     */
    public static String get36UUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 函数功能说明 ： 计算采用utf-8编码方式时字符串所占字节数<br/>
     * 参数：@param content
     * 参数：@return <br/>
     * return：int <br/>
     */
    public static int getByteSize(String content) {
        int size = 0;
        if (null != content) {
            try {
                // 汉字采用utf-8编码时占3个字节
                size = content.getBytes("utf-8").length;
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage());
            }
        }
        return size;
    }
    
    /**
     * 函数功能说明 ：Set集合拼接in查询参数 <br/>
     * 参数：@param param
     * 参数：@return <br/>
     * return：String <br/>
     */
    public static String getInParam(Set<String> param) {
        if(isEmpty(param)){
            return "''";
        }

    	String inParem = "";
    	for (String str : param) {
            inParem = inParem + "'" + str + "',";           
		}
    	if(!isEmpty(inParem)){
            inParem = inParem.substring(0, inParem.length() - 1);
        }
    	return inParem;
    }
    
    /**
     * 函数功能说明 ：集合拼接in查询参数 <br/>
     * 参数：@param param
     * 参数：@return <br/>
     * return：String <br/>
     */
    public static String getInParam(Collection<String> param) {
        String inParem = "";
        for (String str : param) {
            inParem = inParem + "'" + str + "',";
        }
        inParem = inParem.substring(0, inParem.length() - 1);
        return inParem;
    }
    
    /**
     * 函数功能说明 ： 拼接下划线字符串<br/>
     * 参数：@param param
     * 参数：@return <br/>
     * return：String <br/>
     */
    public static String splicingUnderline(Collection<String> param){
    	String inParem = "";
        for (String str : param) {
            inParem = inParem + str + "_";
        }
        inParem = inParem.substring(0, inParem.length() - 1);
        return inParem;
    }

    /**
     * 函数功能说明 ： 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址<br/>
     * 参数：@param request
     * 参数：@return <br/>
     * return：String <br/>
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }
    public static String join(Collection collection, String separator) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), separator);
    }  public static String join(Iterator iterator, String separator) {

        // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return ObjectUtils.toString(first);
        }

        // two or more elements
        StrBuilder buf = new StrBuilder(256); // Java default is 16, probably too small
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }
    /**
     * 去掉字符串首尾空格
     *
     * @return
     */
    public static String trim(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.trim();
    }

    /**
     * 获取对象的使用属性包括父类
     *
     * @param object
     * @return
     */
    public static List<Field> getAllField(Object object) {
        List<Field> fieldList = new LinkedList<>();
        if (object == null) {
            return new LinkedList<>();
        }
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Collections.addAll(fieldList, fields);
        // 处理父类字段
        Class<?> superClass = clazz.getSuperclass();
        if (superClass.equals(Object.class)) {
            return fieldList;
        }
        fields = superClass.getDeclaredFields();
        Collections.addAll(fieldList, fields);
        return fieldList;

    }
    /**
    * @Description: 判断是否为零
    * @Author: ZhengHaiWen
    * @Date: 2020/3/17
    **/
    public static boolean isZero(int num){
        return num != 0;
    }
    /**
     * @Description: 判断是否为零
     * @Author: ZhengHaiWen
     * @Date: 2020/3/17
     **/
    public static boolean isZero(long num){
        return  num != 0L;
    }

    /**
    * 函数功能说明 ：添加redis 过期时间 <br/>
    * 参数：@param redisLockExpireTime
    * 参数：@return <br/>
    * return：String <br/>
     */
	public static int addTimeMillis(int redisLockExpireTime) {
		return (int) (System.currentTimeMillis() +redisLockExpireTime);
	}

	/**
	 * 获取redis key值
	* 函数功能说明 ：获取redis key值 <br/>
	* 参数：@param stockSingleLockPrefix
	* 参数：@param valueOf
	* 参数：@return <br/>
	* return：String <br/>
	 */
	public static String getPrefixKey(String stockSingleLockPrefix, String valueId) {
		return stockSingleLockPrefix + String.valueOf(valueId);
	}
}
