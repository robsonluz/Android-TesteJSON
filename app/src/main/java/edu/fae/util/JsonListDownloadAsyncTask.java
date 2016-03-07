package edu.fae.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import edu.fae.util.http.GetAsyncTask;
import edu.fae.util.http.JsonResult;
import edu.fae.util.http.JsonResultHandler;

/**
 * 
 * @author Robson
 * 
 */
public class JsonListDownloadAsyncTask<E> implements JsonResultHandler {
	private Activity listActivity;
	private ArrayAdapter<E> adapter;
	private JsonParse<E> jsonParse;
	private ProgressDialog dialog;
	private String jsonListName;

	public JsonListDownloadAsyncTask(Activity listActivity, ArrayAdapter<E> adapter, JsonParse<E> jsonParse, String jsonListName) {
		super();
		this.listActivity = listActivity;
		this.adapter = adapter;
		this.jsonParse = jsonParse;
		this.jsonListName = jsonListName;
	}


    public void execute(String url) {
        //dialog = ProgressDialog.show(listActivity, "Aguarde", "Baixando dados...");

        GetAsyncTask getService = new GetAsyncTask(this, listActivity);
        getService.execute(url);
    }

    @Override
    public void onJsonResult(JsonResult result) {
        if(dialog!=null) {
            dialog.dismiss();
        }

        String errorMessage = null;

        if(result!=null && result.hasSuccess()) {

            try {
                adapter.clear();

                JSONArray jsonArray = null;

                if(result.hasJsonObject()) {
                    jsonArray = result.getJsonObject().getJSONArray(jsonListName);
                }else if(result.hasJsonArray()) {
                    jsonArray = result.getJsonArray();
                }

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonItem = new JSONObject(jsonArray.getString(i));
                    //models.add(jsonParse.parse(jsonItem));
                    adapter.add(jsonParse.parse(jsonItem));
                }



            } catch (JSONException e) {
                e.printStackTrace();
                errorMessage = e.getMessage();
            }
        }else if(result!=null && result.hasError()){
            errorMessage = result.getErrorMessage();
        }else{
            errorMessage = "Erro ao carregar JSON";
        }

        if(errorMessage!=null) {
            Toast.makeText(this.listActivity, errorMessage, Toast.LENGTH_LONG).show();
        }

    }

}
