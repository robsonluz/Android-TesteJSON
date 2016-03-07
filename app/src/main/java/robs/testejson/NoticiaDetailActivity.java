package robs.testejson;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import edu.fae.util.JsonParse;
import edu.fae.util.JsonSingleDownloadAsyncTask;
import edu.fae.util.JsonSingleResult;
import robs.testejson.model.Noticia;

/**
 * 
 * @author Robson
 *
 */
public class NoticiaDetailActivity extends AppCompatActivity implements JsonSingleResult<Noticia>{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_noticia_detail);
		
		//Pega o id da noticia clicada
		Long idNoticia = (Long) getIntent().getExtras().get("idNoticia");
		
		//Transforma o json em objeto Noticia
		JsonParse<Noticia> noticiaParse = new JsonParse<Noticia>() {
			@Override
			public Noticia parse(JSONObject json) throws JSONException {
				Noticia noticia = new Noticia();
				noticia.setTitulo(json.getString("titulo"));
				noticia.setTexto(json.getString("texto"));
				return noticia;
			}
		};		
		
		//Responsavel por fazer o download de forma assincrona do json
		JsonSingleDownloadAsyncTask<Noticia> jsonDownload = 
				new JsonSingleDownloadAsyncTask<Noticia>(this, this, noticiaParse);
		
		//Chama o json que devolve os detalhes de uma noticia
		jsonDownload.execute("http://www.fae.edu/teste/mobile/noticia_detalhe.vm?idNoticia=" + idNoticia);
	}

	/**
	 * Chamado quando o servidor retornar o json
	 */
	@Override
	public void onJsonResult(Noticia noticia) {
		TextView textNoticiaTitulo = (TextView) findViewById(R.id.textNoticiaTitulo);
		TextView textNoticiaTexto = (TextView) findViewById(R.id.textNoticiaTexto);
		
		textNoticiaTitulo.setText(noticia.getTitulo());
		textNoticiaTexto.setText(noticia.getTexto());
	}

}
