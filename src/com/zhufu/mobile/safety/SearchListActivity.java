package com.zhufu.mobile.safety;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zhufu.adapter.MyAdapter;
import com.zhufuyisheng.util.Mhttppost;
import com.zhufuyisheng.util.Mstring;

public class SearchListActivity extends Activity {
	private ListView listView;
	private List<Map<String, Object>> data;
	private MyAdapter adapter;

	private int pageSize = 10;
	private final int pageType = 1;

	TextView moreTextView;

	private LinearLayout loadProgressBar;
	static String text;

	static int pageNow = 1;
	/**
	 * 滚动进度弹窗
	 */
	ProgressDialog dialog;
	Context context;
	static boolean isMore = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_page);
		context = this;
		text = getIntent().getExtras().getString("value");
		pageNow = 1;
		isMore = true;
		// page = page.replace("\uFEFF", "");
		// page = page.replace("\\s+", "");

		listView = (ListView) findViewById(R.id.lv_id);
		Button back = (Button) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		// new Handler().post(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// data = initValue();
		//
		// addPageMore();
		//
		// adapter = new MyAdapter(data, context);
		// listView.setAdapter(adapter);
		// }
		// });
		initdata();

		listView.setOnItemClickListener(new OnItemClickListener() {

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
	}

	public void initdata() {
		dialog = ProgressDialog.show(context, null,
				"加载中...." + Mstring.Get_Msg());
		dialog.setCancelable(true);
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				data = initValue();

				Message mes = handler.obtainMessage(2);
				handler.sendMessage(mes);
			}
		}.start();

	}

	private void chageListView() {
		List<Map<String, Object>> data = initValue();
		for (Map<String, Object> map : data) {
			this.data.add(map);
		}
		data = null;
	}

	private void addPageMore() {
		View view = LayoutInflater.from(this).inflate(R.layout.list_page_load,
				null);
		moreTextView = (TextView) view.findViewById(R.id.more_id);
		if (isMore == false) {
			moreTextView.setVisibility(View.GONE);
		} else {
			moreTextView.setVisibility(View.VISIBLE);
		}
		loadProgressBar = (LinearLayout) view.findViewById(R.id.load_id);
		moreTextView.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				moreTextView.setVisibility(View.GONE);

				loadProgressBar.setVisibility(View.VISIBLE);
				new Handler().post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						chageListView();

						Message mes = handler.obtainMessage(pageType);
						handler.sendMessage(mes);
					}
				});

			}
		});
		listView.addFooterView(view);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case pageType:

				adapter.count = data.size();

				adapter.notifyDataSetChanged();
				if (isMore == true) {

					moreTextView.setVisibility(View.VISIBLE);
				}

				loadProgressBar.setVisibility(View.GONE);
				break;
			case 2:

				addPageMore();

				adapter = new MyAdapter(data, context);
				listView.setAdapter(adapter);
				dialog.cancel();
				break;

			default:
				break;
			}

			super.handleMessage(msg);
		}
	};

	public static List<Map<String, Object>> initValue() {
		Map<String, Object> map;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			String callback = Mhttppost.post(Mstring.search, "love",
					Mstring.decrypetkey(), "text", text, "page", "" + pageNow);
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
				list.add(map);
			}
			pageNow++;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}