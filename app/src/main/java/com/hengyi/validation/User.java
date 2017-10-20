package com.hengyi.validation;


import com.hengyi.baseandroidcore.validation.Check;
import com.hengyi.baseandroidcore.validation.Methods;
import com.hengyi.baseandroidcore.validation.Validation;

public class User {
	
	@Check(name="用户年龄",method= Methods.IntSize,minvalue=10,maxvalue=100)
	private Integer age;
	
	@Check(name="用户性别",method=Methods.StringCheck,param = {"男","女"})
	private String sex;
	
	@Check(name="用户姓名",method=Methods.StringSize,maxlength = 4,minlength = 2)
	private String name;
	
	@Check(name="用户地址",method=Methods.StringSize,maxlength = 10,minlength = 2)
	private String address;
	
	@Check(name="用户邮箱",method=Methods.StringType,type= Validation.MAIL_VAIL)
	private String mail;
	
	@Check(name="用户身份证",method=Methods.StringRegex,regex="^\\d{17}(\\d|[A-Z])$")
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
