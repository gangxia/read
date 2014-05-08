package com.zhufuyisheng.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.zhufu.mobile.safety.R;

public class LocAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<HashMap<String, Object>> listItem = null;

	DisplayImageOptions options;

	ImageLoader imageLoader;

	public LocAdapter(Context context,
			ArrayList<HashMap<String, Object>> listItem) {
		this.mContext = context;
		this.listItem = listItem;

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));

		options = new DisplayImageOptions.Builder()

		.showImageForEmptyUri(R.drawable.book).showImageOnFail(R.drawable.book)
				.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unused")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.list_item, null);

		ImageView img = (ImageView) view.findViewById(R.id.img);
		TextView bookName = (TextView) view.findViewById(R.id.name);

		bookName.setText((String) (listItem.get(position).get("name")));
		imageLoader.displayImage(listItem.get(position).get("img").toString(),
				img, options, new SimpleImageLoadingListener() {
				});

		return view;
	}

}
