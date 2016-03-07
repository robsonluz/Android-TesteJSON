package edu.fae.util.http;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author robsonluz
 *
 */
public class PostRequest implements Serializable {
	private static final long serialVersionUID = -4766975947824100531L;
	
	private String url;
	private Map<String, Object> params;
	
	public PostRequest(String url) {
		this.url = url;
		this.params = new HashMap<String, Object>();
	}
	
	public void addParam(String name, Object value) {
		params.put(name, value);
	}
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	
}
