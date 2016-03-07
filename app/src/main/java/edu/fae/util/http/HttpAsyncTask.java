package edu.fae.util.http;

import java.util.List;

import org.apache.http.cookie.Cookie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import edu.fae.util.AppFactory;


/**
 * 
 * @author robsonluz
 *
 * @param <PARAM>
 */
public abstract class HttpAsyncTask<PARAM> extends AsyncTask<PARAM, Void, JsonResult> {
	protected JsonResultHandler resultHandler;
	protected Context context;
	
	
	public HttpAsyncTask(JsonResultHandler resultHandler, Context context) {
		this.resultHandler = resultHandler;
		this.context = context;
	}
	
	@Override
	protected final JsonResult doInBackground(PARAM... params) {
		//boolean error = false;
		JsonResult result = new JsonResult();
		try {
			String json = load(params);
			if(json!=null) {
				json = json.trim();
				if(json.startsWith("[")) {
					result.setJsonArray(new JSONArray(json));
				}else if(json.startsWith("{")) {
					result.setJsonObject(new JSONObject(json));
				}else{
					result.setErrorMessage("Invalid json response!");
				}

			}else{
				result.setErrorMessage("Sem resultado");
			}
			
		} catch (JSONException ex) {
			//error = true;
			result.setException(ex);
			result.setErrorMessage("Error parsing json!");
		} catch (Exception e) {
			//error = true;
			result.setErrorMessage(e.getMessage());
			result.setException(e);
		}
		
//		if(error) {
//			if(context!=null) {
//				final Activity act = (Activity) context;
//				act.runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						try{
//							Toast.makeText(act, "Não foi possível acessar este Recurso! Verifique sua conexão com a Internet e tente novamente!", Toast.LENGTH_LONG).show();
//						}catch(Exception ex) {
//							ex.printStackTrace();
//						}
//					}
//				});
//			}
//		}
		
		return result;
	}
	
	protected void syncCookies() {
		if(context!=null) {
			List<Cookie> cookies = AppFactory.getHttpClient(context).getCookieStore().getCookies();

			if (!cookies.isEmpty())
			{

			    CookieSyncManager.createInstance(context);
			    CookieManager cookieManager = CookieManager.getInstance();
			    
			    
			    
			    // sync all the cookies in the httpclient with the webview
			    // by generating cookie string
			    for (Cookie cookie : cookies)
			    {
			        Cookie sessionInfo = cookie;
			        String cookieString = sessionInfo.getName() + "=" + sessionInfo.getValue() + ";    domain=" + sessionInfo.getDomain();
			        
			        cookieManager.setCookie("www.fae.edu", cookieString);
			        
			        CookieSyncManager.getInstance().sync();
			    }
			}				
		}
		
	}
	
	protected abstract String load(PARAM ...params) throws Exception ;
	
	@Override
	protected void onPostExecute(JsonResult result) {
		super.onPostExecute(result);
		resultHandler.onJsonResult(result);
	}	

}
