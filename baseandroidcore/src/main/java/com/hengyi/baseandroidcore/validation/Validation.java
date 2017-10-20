package com.hengyi.baseandroidcore.validation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

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
	private static final String check_mail = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	private static final String check_phone = "^1\\d{10}$";
	private static final String check_pass = "^\\w{6,20}$";
	
	private static ValidMsg v = new ValidMsg();
	private static StringBuilder sb = new StringBuilder();
	
	public static final int MAIL_VAIL = 1;
	public static final int PASS_VAIL = 2;
	public static final int IPHONE_VAIL = 3;
	
	
	/**
	 * 自动验证
	 * @param obj
	 * @return
	 */
	public static ValidMsg AutoVerifiy(Object obj){
		try {
			Field[] field = obj.getClass().getDeclaredFields();
			v.setPass(true);
			Check check = null;
			String name = null,val = null;
			int valInt = 0;
			Method m = null;
			
			for(int i = 0;i<field.length;i++){
				check = field[i].getAnnotation(Check.class);
				name = field[i].getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                m = obj.getClass().getDeclaredMethod("get"+name);
				m.setAccessible(true);
                
				switch(check.method()){
					case Methods.StringEmpty:
						val = (String) m.invoke(obj);
						v = StringEmpty(val,check.name());
						break;
					
					case Methods.StringSize:
						val = (String) m.invoke(obj);
						v = StringSize(val, check.name(), check.minlength(), check.maxlength());
						break;
						
					case Methods.StringCheck:
						val = (String) m.invoke(obj);
						v = StringCheck(val,check.name(),check.param());
						break;
						
					case Methods.StringType:
						val = (String) m.invoke(obj);
						v = StringType(val, check.name(),check.type());
						break;
						
					case Methods.IntSize:
						valInt = (int) m.invoke(obj);
						v = IntSize(valInt, check.name(), check.minvalue(), check.maxvalue());
						break;
						
					case Methods.StringRegex:
						val = (String) m.invoke(obj);
						v = StringRegex(val, check.name(),check.regex());
						break;
				}
				
				if(!v.isPass()){
					break;
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
	public static ValidMsg StringEmpty(String str,String varname){
		sb.delete(0, sb.length());
		if(str == null || str.isEmpty()){
			sb.append(varname).append("不能为空");
			v.setMsg(sb.toString());
			v.setPass(false);
		}else{
			v.setPass(true);
		}
		return v;
	}
	
	//验证字符串是否为空并且验证长度
	public static ValidMsg StringSize(String str,String varname,int minlength,int maxlength){
		sb.delete(0, sb.length());
		if(str == null || str.isEmpty()){
			sb.append("请将").append(varname).append("填写完整");
			v.setMsg(sb.toString());
			v.setPass(false);
			return v;
		}
		
		sb.delete(0, sb.length());
		if(str.length() < minlength || str.length() > maxlength){
			sb.append(varname).append("长度应在").append(minlength).append("-").append(maxlength).append("之间");
			v.setMsg(sb.toString());
			v.setPass(false);
			return v;
		}
		
		v.setPass(true);
		return v;
	}
	
	public static ValidMsg StringCheck(String str,String varname,Object... obj){
		if(obj.length == 0){
			v.setPass(true);
			return v;
		}else{
			sb.delete(0, sb.length());
			String check_item = null;
			boolean isOk = false;
			for(int i = 0;i<obj.length;i++){
				check_item = obj[i].toString();
				sb.append(check_item).append(",");
				if(str.equals(check_item)){
					isOk = true;
					break;
				}
			}
			
			if(isOk){
				v.setPass(true);
			}else{
				v.setPass(false);
				v.setMsg(varname + "必须包含以下值：" + sb.toString());
			}
			
			return v;
		}
	}
	
	public static ValidMsg IntCheck(int str,String varname,int[] vals){
		boolean isok = false;
		for(int v : vals){
			if(v == str){
				isok = true;
				break;
			}
		}
		sb.delete(0, sb.length());
		if(!isok)
			sb.append("输入的").append(varname).append("请包含以下值：").append(Arrays.toString(vals));
			v.setMsg(sb.toString());
		
		v.setPass(isok);
		
		return v;
	}
	
	public static ValidMsg StringCheck(String str,String varname,String[] obj){
		if(obj.length == 0){
			v.setPass(true);
			return v;
		}else{
			StringBuilder sb = new StringBuilder();
			String check_item = null;
			boolean isOk = false;
			for(int i = 0;i<obj.length;i++){
				check_item = obj[i];
				sb.append(check_item).append(",");
				if(str.equals(check_item)){
					isOk = true;
					break;
				}
			}
			
			if(isOk){
				v.setPass(true);
			}else{
				v.setPass(false);
				v.setMsg(varname + "必须包含以下值：" + sb.toString().substring(0,sb.length() - 1));
			}
			
			return v;
		}
	}
	
	//验证字符串是否为空并且验证长度并验证数据类型
	public static ValidMsg StringType(String str,String varname,int type){
		sb.delete(0, sb.length());
		if(str == null || str.isEmpty()){
			sb.append("请将").append(varname).append("填写完整");
			v.setMsg(sb.toString());
			v.setPass(false);
			return v;
		}
		
		switch(type){
			case MAIL_VAIL:
				if(!str.matches(check_mail)){
					v.setPass(false);
					v.setMsg("邮箱格式错误");
					return v;
				}
				break;
				
			case PASS_VAIL:
				if(!str.matches(check_pass)){
					v.setPass(false);
					v.setMsg("密码格式错误，6-20位数字大小写字母下划线");
					return v;
				}
				break;
				
			case IPHONE_VAIL:
				if(!str.matches(check_phone)){
					v.setPass(false);
					v.setMsg("手机号码格式错误");
					return v;
				}
				break;	
		}
		v.setPass(true);
		
		return v;
	}
	
	//正则表达式验证
	public static ValidMsg StringRegex(String value,String varname,String regex){
		if(value.matches(regex)){
			v.setPass(true);
		}else{
			sb.delete(0,sb.length());
			sb.append(varname).append("验证未通过");
			v.setPass(false);
			v.setMsg(sb.toString());
		}
		
		return v;
	}
	
	//验证数字范围
	public static ValidMsg IntSize(int value,String varname,int minvalue,int maxvalue){
		sb.delete(0, sb.length());
		if(value < minvalue || value > maxvalue){
			sb.append("请将").append(varname).append("保持在").append(minvalue).append("-").append(maxvalue).append("之间");
			v.setMsg(sb.toString());
			v.setPass(false);
		}else{
			v.setPass(true);
		}
		return v;
	}
}
