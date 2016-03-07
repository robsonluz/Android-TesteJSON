package robs.testejson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import edu.fae.util.BitmapDownloaderTask;
import robs.testejson.model.Noticia;

/**
 * 
 * @author Robson
 *
 */
public class NoticiaListAdapter extends ArrayAdapter<Noticia> {
	private Context context;
	
	
	public NoticiaListAdapter(Context context) {
		super(context, R.layout.row_layout);
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		//View de renderizacao de cada item
		View rowView = inflater.inflate(R.layout.row_layout, parent, false);
		
		//TextView para o titulo
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		
		
		//Noticia clicada
	    Noticia noticia = this.getItem(position);
	    //Seta o texto da noticia no TextView
	    textView.setText(noticia.getTitulo());
	    

		//Baixando imagem remota
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		imageView.setImageResource(R.drawable.empty_photo);
		
		//Faz o download da foto em outra thread
		BitmapDownloaderTask imageDownload = new BitmapDownloaderTask(imageView);
		imageDownload.execute(noticia.getImagem());
		
		return rowView;
	}
	
}
