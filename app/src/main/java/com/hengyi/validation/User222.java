package com.hengyi.validation;

import com.hengyi.baseandroidcore.validation.annotation.IntSize;
import com.hengyi.baseandroidcore.validation.annotation.NoEmpty;
import com.hengyi.baseandroidcore.validation.annotation.Regex;
import com.hengyi.baseandroidcore.validation.annotation.StringCheck;
import com.hengyi.baseandroidcore.validation.annotation.StringSize;
import com.hengyi.baseandroidcore.validation.common.Regexs;

public class User222 {
	
	@IntSize(message="年龄不能小于10不能大于100",minvalue=10,maxvalue=100)
	private Integer age;
	
	@StringCheck(message="请选择正确的性别",value= {"男","女"})
	private String sex;
	
	//匹配中文出现小问题
	//@Regex(value=Regexs.CHINESE,message="请输入中文名称")
	@StringSize(message="请输入4个字以内的中文",minvalue=2,maxvalue=4)
	private String name;
	
	@NoEmpty(message="地址不能为空哦")
	private String address;
	
	@Regex(value= Regexs.MAIL,message="请输入正确的邮箱")
	private String mail;
	
	@Regex(value=Regexs.IDCARD,message="请输入正确的身份证")
	private String idcard;
	
	

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
