package edu.fae.util.http;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import edu.fae.util.AppFactory;
import edu.fae.util.Utils;

/**
 * 
 * @author Robson
 * 
 */
public class PostAsyncTask extends HttpAsyncTask<PostRequest> {

	public PostAsyncTask(JsonResultHandler resultHandler, Context context) {
		super(resultHandler, context);
	}
    
	
	@Override
	protected String load(PostRequest... params) throws Exception {
		String urlString = params[0].getUrl();

		HttpPost httpPost = new HttpPost(urlString);
		httpPost.setHeader("User-Agent", Utils.getHttpUserAgent(super.context));

			
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        for(String param: params[0].getParams().keySet()) {
        	Object value = params[0].getParams().get(param);
        	if(value!=null) {
        		nameValuePairs.add(new BasicNameValuePair(param, value.toString()));
        	}
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));			
		
		HttpResponse response = AppFactory.getHttpClient(context).execute(httpPost);

		HttpEntity entity = response.getEntity();

		if (entity != null) {
			String json = EntityUtils.toString(entity);
						
			/*
			InputStream instream = entity.getContent();
			String json = Utils.streamToString(instream);
			*/
			super.syncCookies();
			
			entity.consumeContent();
			
			/*
			entity.consumeContent();
			instream.close();
			 */
			
			return json;
		}

		return null;
	}	



}
