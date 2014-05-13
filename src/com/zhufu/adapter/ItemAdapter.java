package com.zhufu.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhufu.mobile.safety.R;

public class ItemAdapter extends BaseAdapter {
	List<Map<String, Object>> data;
	Context context;

	public ItemAdapter(List<Map<String, Object>> data, Context context) {
		super();
		this.data = data;
		this.context = context;

	}

	private class ViewHolder {
		public TextView name;
		public TextView date;
		public TextView tag1;
		public TextView tag2;

	}

	@Override
	public int getCount() {
		return data.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		if (convertView == null) {
			view = LayoutInflater.from(context).inflate(R.layout.list_all,
					parent, false);
			holder = new ViewHolder();
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.date = (TextView) view.findViewById(R.id.date);
			holder.tag1 = (TextView) view.findViewById(R.id.tag1);
			holder.tag2 = (TextView) view.findViewById(R.id.tag2);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		if (position % 2 == 0) {
			view.setBackgroundColor(Color.parseColor("#F7F7F7"));
		} else {
			view.setBackgroundColor(Color.parseColor("#ffffff"));
		}

		holder.name.setText(data.get(position).get("name").toString());
		holder.date.setText(data.get(position).get("date").toString());
		holder.tag1.setText(String.format("点击:%s",
				data.get(position).get("tag1").toString()));
		holder.tag2.setText(String.format("赞:%s", data.get(position)
				.get("tag2").toString()));

		return view;
	}
}
