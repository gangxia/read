package com.zhufuyisheng.view;

import java.util.List;
import java.util.Map;

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

public class MyAdapter extends BaseAdapter {
	List<Map<String, Object>> data;
	Context context;
	public int count;
	DisplayImageOptions options;

	ImageLoader imageLoader;

	public MyAdapter(List<Map<String, Object>> data, Context context) {
		super();
		this.data = data;
		this.context = context;
		count = data.size();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));

		options = new DisplayImageOptions.Builder()

		.showImageForEmptyUri(R.drawable.book).showImageOnFail(R.drawable.book)
				.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.list_page_item, null);
		TextView name = (TextView) view.findViewById(R.id.name);
		TextView size = (TextView) view.findViewById(R.id.size);
		TextView type = (TextView) view.findViewById(R.id.type);
		TextView tag1 = (TextView) view.findViewById(R.id.tag1);
		TextView tag2 = (TextView) view.findViewById(R.id.tag2);
		ImageView img = (ImageView) view.findViewById(R.id.img);
		name.setText(data.get(position).get("name").toString());
		size.setText("大小:" + data.get(position).get("size").toString());
		type.setText(data.get(position).get("type").toString());
		tag1.setText("下载:" + data.get(position).get("tag1").toString());
		tag2.setText("好评:" + data.get(position).get("tag2").toString());
		imageLoader.displayImage(data.get(position).get("img").toString(), img,
				options, new SimpleImageLoadingListener() {
				});
		return view;
	}

}
