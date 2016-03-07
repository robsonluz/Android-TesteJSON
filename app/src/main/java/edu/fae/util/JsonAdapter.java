package edu.fae.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

/**
 * 
 * @author robsonluz
 * 
 */
public abstract class JsonAdapter extends BaseAdapter implements ListAdapter {

	protected final Activity activity;
	private JSONArray jsonArray;
	protected LayoutInflater mInflater;
    //protected ImageFetcher mImageFetcher;    
    protected static final String IMAGE_CACHE_DIR = "thumbs";
    
    //private int mImageThumbSize;


	public JsonAdapter(Activity activity, JSONArray jsonArray) {
		assert activity != null;
		assert jsonArray != null;

		this.mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.jsonArray = jsonArray;
		this.activity = activity;
		
		
		//mImageThumbSize = activity.getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
		
        //ImageCacheParams cacheParams = new ImageCacheParams(activity, IMAGE_CACHE_DIR);

        // Set memory cache to 25% of mem class
        //cacheParams.setMemCacheSizePercent(activity, 0.25f);

        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
//        mImageFetcher = new ImageFetcher(activity, mImageThumbSize);
//        mImageFetcher.setLoadingImage(R.drawable.stream_empty_photo);
//        
//        mImageFetcher.addImageCache(((FragmentActivity) activity).getSupportFragmentManager(), cacheParams);
		
	}

	@Override
	public int getCount() {
		return jsonArray!=null ? jsonArray.length() : 0;
	}

	@Override
	public JSONObject getItem(int position) {
		return jsonArray!=null ? jsonArray.optJSONObject(position) :  null;
	}

	@Override
	public long getItemId(int position) {
		JSONObject jsonObject = getItem(position);
		return jsonObject.optLong("id");
	}

	
	protected String getValueFromJson(JSONObject item, String prop) {
		try {
			return item.getString(prop);
		} catch (JSONException e) {
			return null;
		}
	}
	
	/**
	 * Adiciona os elementos do jsonArray informado no adapter
	 * @param array
	 */
	public void add(JSONArray array) {
		try{
			if(jsonArray==null) {
				jsonArray = array;
			}else{
				for(int i=0;i<array.length();i++) {
					jsonArray.put(array.get(i));
				}
			}
			super.notifyDataSetChanged();
		}catch(JSONException ex) {
			ex.printStackTrace();
		}
	}
	
	public void clean() {
		jsonArray = new JSONArray();
		super.notifyDataSetChanged();
	}
	
	
	public abstract View getView(int position, View convertView, ViewGroup parent);
	

}