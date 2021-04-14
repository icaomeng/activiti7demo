package com.cao.activti.utils;

import java.io.Serializable;

/**
 * 消息对象
 *
 * @author fiver
 *
 */
public class AjaxObj implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4806448810229890854L;

	private int code;
	private String msg;
	private String token;
	private Object data;
	private Boolean flag;

	public AjaxObj() {

	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public AjaxObj(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public AjaxObj(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public AjaxObj(int code, String msg, Object data, Boolean flag) {
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.flag = flag;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
