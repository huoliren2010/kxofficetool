package util;

import org.json.JSONObject;

public class Response {
	public static final int SUCCESS_CODE = 200;//means success result
	public static final int ERROR_CODE = 400;
	
	public static final String CODE = "code";//int
	public static final String DATA = "data";//jsonobj
	public static final String STATUS = "status";//string
	public static final String MESSAGE = "message";//string
	public static final String SUCCESS = "SUCCESS";//string
	
	int status;
	String data;
	String message;
	public Response addStatus(int status){
		this.status = status;
		return this;
	}
	
	public Response addData(String data){
		this.data = data;
		return this;
	}
	
	public Response addMessage(String message){
		this.message = message;
		return this;
	}

	public Response(int status, String data, String message) {
		super();
		this.status = status;
		this.data = data;
		this.message = message;
	}

	@Override
	public String toString() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(STATUS, status);
		jsonObj.put(DATA, data);
		jsonObj.put(MESSAGE, message);
		return jsonObj.toString();
	}
	
	
}
