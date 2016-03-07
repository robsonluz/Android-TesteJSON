package edu.fae.util.http;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * @author robsonluz
 *
 */
public class JsonResult {
	private JSONObject jsonObject;
	private JSONArray jsonArray;
	
	private Exception exception;
	private String errorMessage;
	
	public boolean hasError() {
		return exception != null || errorMessage!=null;
	}
	
	public void printError() {
		System.out.println("Error: " + errorMessage);
		if(exception!=null) {
			exception.printStackTrace();
		}
	}
	
	public boolean hasSuccess() {
		return !hasError();
	}
	
	public boolean hasJsonArray() {
		return jsonArray!=null;
	}
	public boolean hasJsonObject() {
		return jsonObject!=null;
	}
	
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	public JSONArray getJsonArray() {
		return jsonArray;
	}
	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
