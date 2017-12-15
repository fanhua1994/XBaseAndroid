package com.hengyi.baseandroidcore.validation;

import com.hengyi.baseandroidcore.validation.annotation.IntCheck;
import com.hengyi.baseandroidcore.validation.annotation.IntSize;
import com.hengyi.baseandroidcore.validation.annotation.NoEmpty;
import com.hengyi.baseandroidcore.validation.annotation.Regex;
import com.hengyi.baseandroidcore.validation.annotation.StringCheck;
import com.hengyi.baseandroidcore.validation.annotation.StringSize;
import com.hengyi.baseandroidcore.validation.common.ValidMsg;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表单验证
 * 1.验证字符串是否为空
 * 2.验证数字范围
 * 3.验证码字符串长度
 * 4.验证手机号
 * 5.验证密码
 * 6.验证邮箱
 * 7.包含某几个值
 * @author Fanhua
 */
public class Validation{
	private static ValidMsg v = new ValidMsg();
	
	
	/**
	 * 自动验证
	 * @param obj
	 * @return
	 */
	public static ValidMsg AutoVerifiy(Object obj){
		try {
			Field[] field = obj.getClass().getDeclaredFields();
			v.setPass(true);
			
			StringCheck str_check = null;
			IntCheck int_check = null;
			NoEmpty no_empty = null;
			StringSize str_size = null;
			IntSize int_size = null;
			Regex regex = null;
			
			String name = null,val = null;
			int valInt = 0;
			Method m = null;
			
			for(int i = 0;i<field.length;i++){
				str_check = field[i].getAnnotation(StringCheck.class);
				int_check = field[i].getAnnotation(IntCheck.class);
				no_empty = field[i].getAnnotation(NoEmpty.class);
				str_size = field[i].getAnnotation(StringSize.class);
				int_size = field[i].getAnnotation(IntSize.class);
				regex = field[i].getAnnotation(Regex.class);
				
				name = field[i].getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                m = obj.getClass().getDeclaredMethod("get"+name);
				m.setAccessible(true);

				if(str_check != null) {
					val = (String) m.invoke(obj);
					v = StringCheck(val, str_check.message(),str_check.value());
					if(!v.isPass()){
						break;
					}
				}
				
				if(int_check != null) {
					valInt = (Integer) m.invoke(obj);
					v = IntCheck(valInt, int_check.message(), int_check.value());
					if(!v.isPass()){
						break;
					}
				}
				
				if(no_empty != null) {
					val = (String) m.invoke(obj);
					v = StringEmpty(val, no_empty.message());
					if(!v.isPass()){
						break;
					}
				}
				
				if(str_size != null) {
					val = (String) m.invoke(obj);
					v = StringSize(val, str_size.message(), str_size.minvalue(), str_size.maxvalue());
					if(!v.isPass()){
						break;
					}
				}
				
				if(int_size != null) {
					valInt = (Integer) m.invoke(obj);
					v = IntSize(valInt, int_size.message(), int_size.minvalue(), int_size.maxvalue());
					if(!v.isPass()){
						break;
					}
				}
				
				if(regex != null) {
					val = (String) m.invoke(obj);
					v = StringRegex(val, regex.message(), regex.value());
					if(!v.isPass()){
						break;
					}
				}
			}
			
			return v;
		} catch (Exception e) {
			v.setPass(false);
			v.setMsg("出现未知错误");
		}//初始化这个对象
		return v;
	}
	
	//验证字符串是否为空
	public static ValidMsg StringEmpty(String str,String message){
		if(str == null || str.isEmpty()){
			v.setMsg(message);
			v.setPass(false);
		}else{
			v.setMsg("");
			v.setPass(true);
		}
		return v;
	}
	
	//验证字符串是否为空并且验证长度
	public static ValidMsg StringSize(String str,String message,int minlength,int maxlength){
		if(str == null || str.isEmpty()){
			v.setMsg(message);
			v.setPass(false);
			return v;
		}
		
		if(str.length() < minlength || str.length() > maxlength){
			v.setMsg(message);
			v.setPass(false);
			return v;
		}
		
		v.setPass(true);
		return v;
	}
	
	public static ValidMsg StringCheck(String str,String message,Object... obj){
		if(obj.length == 0){
			v.setPass(true);
			return v;
		}else{
			String check_item = null;
			boolean isOk = false;
			for(int i = 0;i<obj.length;i++){
				check_item = obj[i].toString();
				if(str.equals(check_item)){
					isOk = true;
					break;
				}
			}
			
			if(isOk){
				v.setMsg("");
				v.setPass(true);
			}else{
				v.setPass(false);
				v.setMsg(message);
			}
			
			return v;
		}
	}
	
	public static ValidMsg IntCheck(int str,String message,int[] vals){
		boolean isok = false;
		for(int v : vals){
			if(v == str){
				isok = true;
				break;
			}
		}
		if(!isok)
			v.setMsg(message);
		
		v.setPass(isok);
		return v;
	}
	
	public static ValidMsg StringCheck(String str,String message,String[] obj){
		if(obj.length == 0){
			v.setPass(true);
			return v;
		}else{
			String check_item = null;
			boolean isOk = false;
			for(int i = 0;i<obj.length;i++){
				check_item = obj[i];
				if(str.equals(check_item)){
					isOk = true;
					break;
				}
			}
			
			if(isOk){
				v.setPass(true);
			}else{
				v.setPass(false);
				v.setMsg(message);
			}
			
			return v;
		}
	}
	
	//正则表达式验证
	public static ValidMsg StringRegex(String value,String message,String regex){
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(value);
		
		if(m.matches()){
			v.setPass(true);
			v.setMsg("");
		}else{
			v.setPass(false);
			v.setMsg(message);
		}
		
		return v;
	}
	
	//验证数字范围
	public static ValidMsg IntSize(int value,String message,int minvalue,int maxvalue){
		if(value < minvalue || value > maxvalue){
			v.setMsg(message);
			v.setPass(false);
		}else{
			v.setPass(true);
		}
		return v;
	}
}
