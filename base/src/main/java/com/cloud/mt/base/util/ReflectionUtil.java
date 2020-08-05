package com.cloud.mt.base.util;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射相关辅助方法
 */
public class ReflectionUtil {


	/**
	 * 判断对象是否有指定属性
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static boolean isExistField(Object object,String fieldName) {
		return isExistField(object.getClass(),fieldName);
	}


	/**
	 * 判断类是否有指定属性
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static boolean isExistField(Class clazz,String fieldName) {
		try {
			clazz.getDeclaredField(fieldName);
			return Boolean.TRUE;
		} catch (NoSuchFieldException e) {
			return Boolean.FALSE;
		}
	}

	/**
	 * 动态获取属性值
	 * @param fieldName
	 * @param object
	 * @return
	 */
	public static Object dynamicGetValue(String fieldName, Object object) {
		return dynamicGetValue(fieldName, object.getClass(),object);
	}
		/**
		 * 动态获取属性值
		 * @param fieldName
		 * @param object
		 * @param clazz
		 * @return
		 */
	public static Object dynamicGetValue(String fieldName,Class clazz,Object object) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return  field.get(object);
		} catch (Exception e) {
			throw new IllegalArgumentException("动态获取属性值出错",e);
		}
	}

	/**
	 * 动态设置属性值
	 * @param fieldName 属性名
	 * @param value	属性值
	 * @param object	对象
	 */
	public static void dynamicSetValue(String fieldName,Object value, Object object) {
		dynamicSetValue(fieldName,object.getClass(),value, object);
	}
	/**
	 * 动态设置属性值
	 * @param fieldName 属性名
	 * @param clazz	对象类型
	 * @param value	属性值
	 * @param object	对象
	 */
	public static void dynamicSetValue(String fieldName,Class clazz,Object value, Object object) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(object,value);
		} catch (Exception e) {
			throw new IllegalArgumentException("动态设置属性值出错",e);
		}
	}

	/**
	 * 根据方法名调用指定对象的方法
	 * @param object 要调用方法的对象
	 * @param methodName 要调用的方法名
	 * @param args 参数对象数组
	 * @return
	 */
	public static Object invoke(Object object, String methodName, Object... args){
		Object result = null;
		Class<? extends Object> clazz = object.getClass();
		Method method = getMethod(clazz, methodName, args);
		if(method != null) {
			try {
				method.setAccessible(true);
				result = method.invoke(object, args);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new IllegalArgumentException(e);
			}
			return result;
		} else {
			throw new IllegalArgumentException(clazz.getName() + " 类中没有找到 " + method + " 方法。");
		}
	}
	
	/**
	 * 根据方法名和参数对象查找方法
	 * @param clazz
	 * @param name
	 * @param args 参数实例数据
	 * @return
	 */
	public static Method getMethod(Class<? extends Object> clazz, String name, Object[] args) {
		Method queryMethod = null;
		Method[] methods = clazz.getDeclaredMethods();
		for(Method method:methods) {
			if(method.getName().equals(name)) {
				Class<?>[] parameterTypes = method.getParameterTypes();
				if(parameterTypes.length == args.length) {
					boolean isSameMethod = true;
					for(int i=0; i<parameterTypes.length; i++) {
						Object arg = args[i];
						if(arg == null) {
							arg = "";
						}
						if(!parameterTypes[i].equals(args[i].getClass()) && ! parameterTypes[i].isAssignableFrom(args[i].getClass()) ) {
							isSameMethod = false;
						}
					}
					if(isSameMethod) {
						queryMethod = method;
						break ;
					}
				}
			}
		}
		return queryMethod;
	}


	/**
	 * 查找当前类的字段，当前类查找不到，往父类查找
	 * @param _class
	 * @param bean
	 * @param fieldName
	 * @return
	 */
	public static Object getProperty(Class _class, Object bean, String fieldName) {
		Object obj = null;
		Field[] fields = _class.getDeclaredFields();
		Field.setAccessible(fields, true);
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (fieldName.equals(field.getName())) {
				try {
					obj = field.get(bean);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException("动态获取属性值出错",e);
				} catch (IllegalAccessException e) {
					throw new IllegalArgumentException("动态获取属性值出错",e);
				}
				break;
			}
		}
		if (obj == null && _class.getGenericSuperclass() != null) {
			obj = getProperty(_class.getSuperclass(), bean, fieldName);
		}
		return obj;
	}


}
