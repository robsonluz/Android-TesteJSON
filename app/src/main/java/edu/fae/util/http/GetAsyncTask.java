package edu.fae.util.http;

import android.content.Context;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import edu.fae.util.AppFactory;
import edu.fae.util.Utils;


/**
 * 
 * @author Robson
 * 
 */
public class GetAsyncTask extends HttpAsyncTask<String> {
	
	public GetAsyncTask(JsonResultHandler resultHandler, Context context) {
		super(resultHandler, context);
	}

	@Override
	protected String load(String... params) throws Exception {
		String urlString = params[0];
		StringBuilder builder = new StringBuilder();
		
		HttpGet httpGet = new HttpGet(urlString);
		httpGet.setHeader("User-Agent", Utils.getHttpUserAgent(super.context));
		
//		try {
			
			HttpResponse response = AppFactory.getHttpClient(context).execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				String html = EntityUtils.toString(entity);	
				builder.append(html);
				/*
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
				*/
				super.syncCookies();
				
				entity.consumeContent();
				
				/*
				entity.consumeContent();
				reader.close();
				*/
			} else {
				//Log.e(ParseJSON.class.toString(), "Failed to download file");
			}
			
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} 
		return builder.toString();

	}
    
/*
	@Override
	protected JSONArray doInBackground(String... params) {
		String urlString = params[0];

		try {

			return JsonLoad.loadArrayFromURL(urlString, context);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
*/


}
