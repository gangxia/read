package com.zhufu.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zhufu.mobile.safety.BookActivity;
import com.zhufu.mobile.safety.R;
import com.zhufuyisheng.util.Mhttppost;
import com.zhufuyisheng.util.Mstring;
import com.zhufuyisheng.view.MyAdapter;

public class ImgFragment extends Fragment {
	TextView label_1, label_2, label_3;
	ListView list1, list2, list3;

	static List<Map<String, Object>> data;
	static List<Map<String, Object>> data2;
	static List<Map<String, Object>> data3;
	MyAdapter adapter;
	MyAdapter adapter2;
	MyAdapter adapter3;
	Context context;
	TextView more;
	LinearLayout loadProgressBar;
	TextView more2;
	LinearLayout loadProgressBar2;
	TextView more3;
	LinearLayout loadProgressBar3;

	static int pageNow = 1;
	static int pageNow2 = 1;
	static int pageNow3 = 1;

	static boolean isMore = true;
	static boolean isMore2 = true;
	static boolean isMore3 = true;
	Dialog dialog;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.home_layout, container, false);
		context = getActivity();
		pageNow = 1;
		pageNow2 = 1;
		pageNow3 = 1;

		isMore = true;
		isMore2 = true;
		isMore3 = true;
		data = new ArrayList<Map<String, Object>>();
		data2 = new ArrayList<Map<String, Object>>();
		data3 = new ArrayList<Map<String, Object>>();
		label_1 = (TextView) layout.findViewById(R.id.label_1);
		label_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				label_click(1);
			}
		});
		label_2 = (TextView) layout.findViewById(R.id.label_2);
		label_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				label_click(2);
			}
		});
		label_3 = (TextView) layout.findViewById(R.id.label_3);
		label_3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				label_click(3);
			}
		});
		list1 = (ListView) layout.findViewById(R.id.lv_1);
		list2 = (ListView) layout.findViewById(R.id.lv_2);
		list3 = (ListView) layout.findViewById(R.id.lv_3);
		initdata();

		list1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, BookActivity.class);

				intent.putExtra("id", data.get(arg2).get("id").toString());
				intent.putExtra("author", data.get(arg2).get("author")
						.toString());
				intent.putExtra("text", data.get(arg2).get("text").toString());
				intent.putExtra("name", data.get(arg2).get("name").toString());
				intent.putExtra("img", data.get(arg2).get("img").toString());
				intent.putExtra("type", data.get(arg2).get("type").toString());
				intent.putExtra("url", data.get(arg2).get("url").toString());
				intent.putExtra("role", data.get(arg2).get("role").toString());
				intent.putExtra("size", data.get(arg2).get("size").toString());
				intent.putExtra("tag1", data.get(arg2).get("tag1").toString());
				intent.putExtra("tag2", data.get(arg2).get("tag2").toString());
				startActivity(intent);

			}
		});
		list2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, BookActivity.class);

				intent.putExtra("id", data2.get(arg2).get("id").toString());
				intent.putExtra("author", data2.get(arg2).get("author")
						.toString());
				intent.putExtra("text", data2.get(arg2).get("text").toString());
				intent.putExtra("name", data2.get(arg2).get("name").toString());
				intent.putExtra("img", data2.get(arg2).get("img").toString());
				intent.putExtra("type", data2.get(arg2).get("type").toString());
				intent.putExtra("url", data2.get(arg2).get("url").toString());
				intent.putExtra("role", data2.get(arg2).get("role").toString());
				intent.putExtra("size", data2.get(arg2).get("size").toString());
				intent.putExtra("tag1", data2.get(arg2).get("tag1").toString());
				intent.putExtra("tag2", data2.get(arg2).get("tag2").toString());
				startActivity(intent);

			}
		});
		list3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, BookActivity.class);

				intent.putExtra("id", data3.get(arg2).get("id").toString());
				intent.putExtra("author", data3.get(arg2).get("author")
						.toString());
				intent.putExtra("text", data3.get(arg2).get("text").toString());
				intent.putExtra("name", data3.get(arg2).get("name").toString());
				intent.putExtra("img", data3.get(arg2).get("img").toString());
				intent.putExtra("type", data3.get(arg2).get("type").toString());
				intent.putExtra("url", data3.get(arg2).get("url").toString());
				intent.putExtra("role", data3.get(arg2).get("role").toString());
				intent.putExtra("size", data3.get(arg2).get("size").toString());
				intent.putExtra("tag1", data3.get(arg2).get("tag1").toString());
				intent.putExtra("tag2", data3.get(arg2).get("tag2").toString());
				startActivity(intent);

			}
		});
		return layout;
	}

	public void initdata() {
		dialog = ProgressDialog.show(context, null,
				"加载中....\n 下载后，图书长按导出txt，可以在任何小说阅读器打开！ ");
		dialog.setCancelable(true);
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				initValue();

				initValue2();

				initValue3();

				Message mes = handler.obtainMessage(4);
				handler.sendMessage(mes);
			}
		}.start();

	}

	public void label_click(int i) {
		if (i == 1) {
			label_1.setTextColor(Color.parseColor("#000000"));
			label_2.setTextColor(Color.parseColor("#008FEE"));
			label_3.setTextColor(Color.parseColor("#008FEE"));
			list1.setVisibility(View.VISIBLE);
			list2.setVisibility(View.GONE);
			list3.setVisibility(View.GONE);
		} else if (i == 2) {
			label_2.setTextColor(Color.parseColor("#000000"));
			label_1.setTextColor(Color.parseColor("#008FEE"));
			label_3.setTextColor(Color.parseColor("#008FEE"));
			list2.setVisibility(View.VISIBLE);
			list1.setVisibility(View.GONE);
			list3.setVisibility(View.GONE);
		} else if (i == 3) {
			label_3.setTextColor(Color.parseColor("#000000"));
			label_1.setTextColor(Color.parseColor("#008FEE"));
			label_2.setTextColor(Color.parseColor("#008FEE"));
			list3.setVisibility(View.VISIBLE);
			list1.setVisibility(View.GONE);
			list2.setVisibility(View.GONE);
		}
	}

	private void addPageMore() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.list_page_load, null);
		more = (TextView) view.findViewById(R.id.more_id);
		if (isMore == false) {
			more.setVisibility(View.GONE);
		} else {
			more.setVisibility(View.VISIBLE);
		}
		loadProgressBar = (LinearLayout) view.findViewById(R.id.load_id);
		more.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				more.setVisibility(View.GONE);

				loadProgressBar.setVisibility(View.VISIBLE);
				new Handler().post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						initValue();

						Message mes = handler.obtainMessage(1);
						handler.sendMessage(mes);
					}
				});

			}
		});
		list1.addFooterView(view);
	}

	private void addPageMore2() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.list_page_load, null);
		more2 = (TextView) view.findViewById(R.id.more_id);
		if (isMore2 == false) {
			more2.setVisibility(View.GONE);
		} else {
			more2.setVisibility(View.VISIBLE);
		}
		loadProgressBar2 = (LinearLayout) view.findViewById(R.id.load_id);
		more2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				more2.setVisibility(View.GONE);

				loadProgressBar2.setVisibility(View.VISIBLE);
				new Handler().post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						initValue2();

						Message mes = handler.obtainMessage(2);
						handler.sendMessage(mes);
					}
				});

			}
		});
		list2.addFooterView(view);
	}

	private void addPageMore3() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.list_page_load, null);
		more3 = (TextView) view.findViewById(R.id.more_id);
		if (isMore3 == false) {
			more3.setVisibility(View.GONE);
		} else {
			more3.setVisibility(View.VISIBLE);
		}
		loadProgressBar3 = (LinearLayout) view.findViewById(R.id.load_id);
		more3.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				more3.setVisibility(View.GONE);

				loadProgressBar3.setVisibility(View.VISIBLE);
				new Handler().post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						initValue3();

						Message mes = handler.obtainMessage(3);
						handler.sendMessage(mes);
					}
				});

			}
		});
		list3.addFooterView(view);
	}

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:

				adapter.count = data.size();

				adapter.notifyDataSetChanged();
				if (isMore == true) {

					more.setVisibility(View.VISIBLE);
				}

				loadProgressBar.setVisibility(View.GONE);
				break;
			case 2:

				adapter2.count = data2.size();

				adapter2.notifyDataSetChanged();
				if (isMore2 == true) {

					more2.setVisibility(View.VISIBLE);
				}

				loadProgressBar2.setVisibility(View.GONE);
				break;
			case 3:

				adapter3.count = data3.size();

				adapter3.notifyDataSetChanged();
				if (isMore3 == true) {

					more3.setVisibility(View.VISIBLE);
				}

				loadProgressBar3.setVisibility(View.GONE);
				break;
			case 4:

				addPageMore();
				adapter = new MyAdapter(data, context);
				list1.setAdapter(adapter);

				addPageMore2();

				adapter2 = new MyAdapter(data2, context);
				list2.setAdapter(adapter2);

				addPageMore3();
				adapter3 = new MyAdapter(data3, context);
				list3.setAdapter(adapter3);
				dialog.cancel();
				break;

			default:
				break;
			}

			super.handleMessage(msg);
		}
	};

	public static void initValue() {
		Map<String, Object> map;

		try {
			String callback = Mhttppost.post(Mstring.lab1, "love",
					Mstring.decrypetkey(), "type", "1", "page", "" + pageNow);
			JSONArray arrayJson = new JSONArray(callback); // 得到json数据开始字符
			if (arrayJson.length() < 10) {
				isMore = false;
			}

			// json解析
			for (int i = 0; i < arrayJson.length(); i++) {
				JSONObject tempJson = arrayJson.optJSONObject(i);
				map = new HashMap<String, Object>();
				map.put("id", tempJson.getString("id"));
				map.put("author", tempJson.getString("author"));
				map.put("text", tempJson.getString("text"));
				map.put("name", tempJson.getString("name"));
				map.put("img", tempJson.getString("img"));
				map.put("type", tempJson.getString("type"));
				map.put("url", tempJson.getString("url"));
				map.put("role", tempJson.getString("role"));
				map.put("size", tempJson.getString("size"));
				map.put("tag1", tempJson.getString("tag1"));
				map.put("tag2", tempJson.getString("tag2"));
				// ++page;
				data.add(map);
			}
			pageNow++;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void initValue2() {
		Map<String, Object> map;

		try {
			String callback = Mhttppost.post(Mstring.lab1, "love",
					Mstring.decrypetkey(), "type", "2", "page", "" + pageNow2);
			JSONArray arrayJson = new JSONArray(callback); // 得到json数据开始字符
			if (arrayJson.length() < 10) {
				isMore2 = false;
			}
			// json解析
			for (int i = 0; i < arrayJson.length(); i++) {
				JSONObject tempJson = arrayJson.optJSONObject(i);
				map = new HashMap<String, Object>();
				map.put("id", tempJson.getString("id"));
				map.put("author", tempJson.getString("author"));
				map.put("text", tempJson.getString("text"));
				map.put("name", tempJson.getString("name"));
				map.put("img", tempJson.getString("img"));
				map.put("type", tempJson.getString("type"));
				map.put("url", tempJson.getString("url"));
				map.put("role", tempJson.getString("role"));
				map.put("size", tempJson.getString("size"));
				map.put("tag1", tempJson.getString("tag1"));
				map.put("tag2", tempJson.getString("tag2"));
				// ++page;
				data2.add(map);
			}
			pageNow2++;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void initValue3() {
		Map<String, Object> map;

		try {
			String callback = Mhttppost.post(Mstring.lab1, "love",
					Mstring.decrypetkey(), "type", "3", "page", "" + pageNow3);
			JSONArray arrayJson = new JSONArray(callback); // 得到json数据开始字符
			if (arrayJson.length() < 10) {
				isMore3 = false;
			}
			// json解析
			for (int i = 0; i < arrayJson.length(); i++) {
				JSONObject tempJson = arrayJson.optJSONObject(i);
				map = new HashMap<String, Object>();
				map.put("id", tempJson.getString("id"));
				map.put("author", tempJson.getString("author"));
				map.put("text", tempJson.getString("text"));
				map.put("name", tempJson.getString("name"));
				map.put("img", tempJson.getString("img"));
				map.put("type", tempJson.getString("type"));
				map.put("url", tempJson.getString("url"));
				map.put("role", tempJson.getString("role"));
				map.put("size", tempJson.getString("size"));
				map.put("tag1", tempJson.getString("tag1"));
				map.put("tag2", tempJson.getString("tag2"));
				// ++page;
				data3.add(map);
			}
			pageNow3++;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
