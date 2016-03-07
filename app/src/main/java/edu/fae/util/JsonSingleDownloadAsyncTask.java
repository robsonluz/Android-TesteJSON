package edu.fae.util;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import edu.fae.util.http.GetAsyncTask;
import edu.fae.util.http.JsonResult;
import edu.fae.util.http.JsonResultHandler;

/**
 * 
 * @author Robson
 * 
 */
public class JsonSingleDownloadAsyncTask<E> implements JsonResultHandler {
	private JsonSingleResult<E> jsonSingleResult;
	private JsonParse<E> jsonParse;
	private ProgressDialog dialog;
	private Activity detailActivity;

	public JsonSingleDownloadAsyncTask(Activity detailActivity, JsonSingleResult<E> jsonSingleResult, JsonParse<E> jsonParse) {
		super();
		this.detailActivity = detailActivity;
		this.jsonSingleResult = jsonSingleResult;
		this.jsonParse = jsonParse;
	}


    public void execute(String url) {
        //dialog = ProgressDialog.show(detailActivity, "Aguarde", "Baixando dados...");

        GetAsyncTask getService = new GetAsyncTask(this, detailActivity);
        getService.execute(url);
    }

    @Override
    public void onJsonResult(JsonResult result) {
        if(dialog!=null) {
            dialog.dismiss();
        }

        String errorMessage = null;

        if(result!=null && result.hasSuccess() && result.hasJsonObject()) {

            try {

                E model = jsonParse.parse(result.getJsonObject());
                jsonSingleResult.onJsonResult(model);

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
            Toast.makeText(this.detailActivity, errorMessage, Toast.LENGTH_LONG).show();
        }

    }

	

}
