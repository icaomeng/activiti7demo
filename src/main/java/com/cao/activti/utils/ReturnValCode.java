package com.cao.activti.utils;

public class ReturnValCode {

	/**
	 * 操作失败的标志码
	 */
	public static final int RETURN_VALUE_CODES_FAIL = -1;

	/**
	 * 操作成功的标志码
	 */
	public static final int RTN_VAL_CODE_SUCCESS = 0;

	/**
	 * 登录成功
	 */
	public static final int LOGIN_SUCCESS = 101;

	public static final int UN_LOGIN_OR_TIME_OUT = -101;

	/**
	 * 登录失败-账户名或者密码错误
	 */
	public static final int LOGIN_FAIL = 102;

	/**
	 * 非激活状态
	 */
	public static final int LOGIN_UN_ACTIV = 103;

	/**
	 * 应用程序与数据库操作发生错误
	 */
	public static final int LOGIN_EXCEPTION = 104;


}
