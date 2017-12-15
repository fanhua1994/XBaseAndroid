package com.hengyi.baseandroidcore.validation.common;
/**
 * 公用的正则表达式
 * @author Administrator
 *
 */
public class Regexs {
	public static final String MAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	public static final String PHONE = "^1\\d{10}$";
	public static final String PASSWORD = "^\\w{6,20}$";
	public static final String CHINESE = "^[\\u4e00-\\u9fa5]$";
	public static final String IDCARD = "^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$";
}
