package cn.tedu.note.web;

import java.io.Serializable;

/**
 * 用于封装JSON返回数据的值对象
 * 
 *
 */
public class  JsonResult <T> 
	implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public static final int SUCCESS=0;
	
	public static final int ERROR=1;
	
	private int state;
	private String message;
	private T data;
	public JsonResult() {
		super();
	}
	
	public JsonResult(int state, String message, T data) {
		super();
		this.state = state;
		this.message = message;
		this.data = data;
	}

	public JsonResult(String errorMessage){
		this(ERROR,errorMessage,null);
	}
	public JsonResult(int state,String message){
		this(state,message,null);
		
	}
	public JsonResult(T data){
		this(SUCCESS,"",data);
	}
	
	public JsonResult(Exception e) {
		this(ERROR,e.getMessage(),null);
	}

	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static int getSuccess() {
		return SUCCESS;
	}
	public static int getError() {
		return ERROR;
	}
	@Override
	public String toString() {
		return "JsonResult [state=" + state + ", message=" + message + ", data=" + data + "]";
	}
	
	
	

}
