package robs.testejson;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import edu.fae.util.JsonListDownloadAsyncTask;
import edu.fae.util.JsonParse;
import robs.testejson.model.Noticia;

/**
 * 
 * @author Robson
 *
 */
public class NoticiaListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_noticia_list);

		//Capturando o listView do layout
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(this);

		//Adapter para a noticia
		NoticiaListAdapter noticiasAdapter = new NoticiaListAdapter(this);
		listView.setAdapter(noticiasAdapter);
		
		//Transforma o json em objeto Noticia
		JsonParse<Noticia> noticiaParse = new JsonParse<Noticia>() {
			@Override
			public Noticia parse(JSONObject json) throws JSONException {
				Noticia noticia = new Noticia();
				noticia.setTitulo(json.getString("titulo"));
				noticia.setIdNoticia(json.getLong("idNoticia"));
				noticia.setImagem(json.getString("imagem"));
				return noticia;
			}
		};

		//Propriedade root do json
		String jsonRoot = "noticia";

		//Responsavel por fazer o download de forma assincrona do json
		JsonListDownloadAsyncTask<Noticia> noticiasDownload = 
					new JsonListDownloadAsyncTask<Noticia>(this, noticiasAdapter, noticiaParse, jsonRoot);
		
		//Passamos a URL que devolve o json
        noticiasDownload.execute("http://www.fae.edu/teste/mobile/noticias.vm");
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, NoticiaDetailActivity.class);
		Noticia noticia = (Noticia) parent.getAdapter().getItem(position);
		//Passando o id da noticia clicada para a tela de detalhe
		intent.putExtra("idNoticia", noticia.getIdNoticia());
		startActivity(intent);
	}

}
