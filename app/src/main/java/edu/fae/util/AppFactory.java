package edu.fae.util;

import android.content.Context;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import edu.fae.util.http.PersistentCookieStore;


/**
 * 
 * @author robsonluz
 *
 */
public class AppFactory {
	public static final String appVersion = "1";
	
	public static String mobileToken;
	public static boolean notification = false;
	//private static DefaultHttpClient httpClient;
	
//	public static void initHttpClient(Context context) {
//		httpClient = new DefaultHttpClient();
//		
//		//Configura o timeout de 60 segundos
//		HttpParams params = httpClient.getParams();
//		HttpConnectionParams.setConnectionTimeout(params, 40000);
//		HttpConnectionParams.setSoTimeout(params, 40000);
//		
//		
//		httpClient.setCookieStore(new PersistentCookieStore(context));
//	}
	
	public static DefaultHttpClient getHttpClient(Context context) {
//		if(httpClient==null) {
//			initHttpClient(context);
//		}
			
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		//Configura o timeout de 60 segundos
		HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 40000);
		HttpConnectionParams.setSoTimeout(params, 40000);
		
		
		httpClient.setCookieStore(new PersistentCookieStore(context));
			
		return httpClient;
	}
	
	

}
