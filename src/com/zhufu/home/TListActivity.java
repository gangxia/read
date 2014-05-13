package com.zhufu.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zhufu.adapter.ItemAdapter;
import com.zhufu.db.DBService;
import com.zhufu.mobile.safety.BookActivity;
import com.zhufu.mobile.safety.R;
import com.zhufuyisheng.util.Mhttppost;
import com.zhufuyisheng.util.Mstring;

public class TListActivity extends Activity {
	TextView label_1, label_2, label_3;
	ListView list1, list2, list3;

	static List<Map<String, Object>> data;
	static List<Map<String, Object>> data2;
	static List<Map<String, Object>> data3;
	ItemAdapter adapter;
	ItemAdapter adapter2;
	ItemAdapter adapter3;
	Context context;
	TextView more;
	LinearLayout loadProgressBar;
	TextView more2;
	LinearLayout loadProgressBar2;
	TextView more3;
	LinearLayout loadProgressBar3;

	static int pageNow = 0;
	static int pageNow2 = 0;
	static int pageNow3 = 0;

	static boolean isMore = true;
	static boolean isMore2 = true;
	static boolean isMore3 = true;
	Dialog dialog;
	static DBService dbService;
	String tid = "1";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.home_layout);

		context = this;
		dbService = new DBService(this);
		tid = getIntent().getExtras().getString("tid");

		data = new ArrayList<Map<String, Object>>();
		data2 = new ArrayList<Map<String, Object>>();
		data3 = new ArrayList<Map<String, Object>>();
		label_1 = (TextView) findViewById(R.id.label_1);
		label_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				label_click(1);
			}
		});
		label_2 = (TextView) findViewById(R.id.label_2);
		label_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				label_click(2);
			}
		});
		label_3 = (TextView) findViewById(R.id.label_3);
		label_3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				label_click(3);
			}
		});
		list1 = (ListView) findViewById(R.id.lv_1);
		list2 = (ListView) findViewById(R.id.lv_2);
		list3 = (ListView) findViewById(R.id.lv_3);
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

	}

	public void initdata() {
		dialog = ProgressDialog.show(context, null,
				"加载中....\n 下载后，图书长按导出txt，可以在任何小说阅读器打开！ ");
		dialog.setCancelable(true);
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				init();
				initValue();

				initValue2();

				initValue3();

				Message mes = handler.obtainMessage(4);
				handler.sendMessage(mes);
			}
		}.start();

	}

	public void init() {

		Map<String, Object> map;

		try {
			String sql = "select * from read order by id desc limit 1 ";
			String[] arg = new String[] {};
			// if ("1".equals(tag)) {
			//
			// arg = new String[] { "1" };
			// } else {
			// arg = new String[] { "2" };
			// }
			Cursor cursor = dbService.query(sql, null);
			cursor.moveToFirst();
			String u;
			if (cursor != null && cursor.getCount() > 0) {

				u = cursor.getString(cursor.getColumnIndex("uid"));

			} else {
				u = "0";
			}
			cursor.close();
			String callback = Mhttppost.post(Mstring.read, "love",
					Mstring.decrypetkey(), "id", u, "tid", tid);
			JSONArray arrayJson = new JSONArray(callback); // 得到json数据开始字符

			// json解析
			for (int i = 0; i < arrayJson.length(); i++) {
				JSONObject tempJson = arrayJson.optJSONObject(i);
				map = new HashMap<String, Object>();
				String uid = tempJson.getString("id");
				String name = tempJson.getString("name");
				String date = tempJson.getString("date");
				String tag1 = tempJson.getString("tag1");
				String tag2 = tempJson.getString("tag2");

				String sql2 = "insert into read(uid,name,date,tag1,tag2) values(?,?,?,?,?)";
				Object[] object = new Object[] { uid, name, date, tag1, tag2 };
				dbService.execSQL(sql2, object);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

				adapter.notifyDataSetChanged();
				if (isMore == true) {

					more.setVisibility(View.VISIBLE);
				}

				loadProgressBar.setVisibility(View.GONE);
				break;
			case 2:

				adapter2.notifyDataSetChanged();
				if (isMore2 == true) {

					more2.setVisibility(View.VISIBLE);
				}

				loadProgressBar2.setVisibility(View.GONE);
				break;
			case 3:

				adapter3.notifyDataSetChanged();
				if (isMore3 == true) {

					more3.setVisibility(View.VISIBLE);
				}

				loadProgressBar3.setVisibility(View.GONE);
				break;
			case 4:

				addPageMore();
				adapter = new ItemAdapter(data, context);
				list1.setAdapter(adapter);

				addPageMore2();

				adapter2 = new ItemAdapter(data2, context);
				list2.setAdapter(adapter2);

				addPageMore3();
				adapter3 = new ItemAdapter(data3, context);
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

		try {
			String sql = "select uid,name,tag1,tag2,date from read order by uid   desc limit ?, 20";
			String[] arg = new String[] { "" + pageNow };
			Cursor cursor = dbService.query(sql, arg);
			cursor.moveToFirst();
			if (cursor.getCount() < 10) {
				isMore = false;
			}
			if (cursor != null && cursor.getCount() > 0) {
				do {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name",
							cursor.getString(cursor.getColumnIndex("name")));
					map.put("date",
							cursor.getString(cursor.getColumnIndex("date")));
					map.put("tag1",
							cursor.getString(cursor.getColumnIndex("tag1")));
					map.put("tag2",
							cursor.getString(cursor.getColumnIndex("tag2")));

					data.add(map);
					pageNow++;
				} while (cursor.moveToNext());

			}
			cursor.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void initValue2() {

		try {
			String sql = "select uid,name,tag1,tag2,date from read order by uid   desc limit ?, 20";
			String[] arg = new String[] { "" + pageNow2 };
			Cursor cursor = dbService.query(sql, arg);
			cursor.moveToFirst();
			if (cursor.getCount() < 10) {
				isMore2 = false;
			}
			if (cursor != null && cursor.getCount() > 0) {
				do {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name",
							cursor.getString(cursor.getColumnIndex("name")));
					map.put("date",
							cursor.getString(cursor.getColumnIndex("date")));
					map.put("tag1",
							cursor.getString(cursor.getColumnIndex("tag1")));
					map.put("tag2",
							cursor.getString(cursor.getColumnIndex("tag2")));

					data2.add(map);
					pageNow2++;
				} while (cursor.moveToNext());

			}
			cursor.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void initValue3() {

		try {
			String sql = "select uid,name,tag1,tag2,date from read order by uid   desc limit ?, 20";
			String[] arg = new String[] { "" + pageNow3 };
			Cursor cursor = dbService.query(sql, arg);
			cursor.moveToFirst();
			if (cursor.getCount() < 10) {
				isMore3 = false;
			}
			if (cursor != null && cursor.getCount() > 0) {
				do {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name",
							cursor.getString(cursor.getColumnIndex("name")));
					map.put("date",
							cursor.getString(cursor.getColumnIndex("date")));
					map.put("tag1",
							cursor.getString(cursor.getColumnIndex("tag1")));
					map.put("tag2",
							cursor.getString(cursor.getColumnIndex("tag2")));

					data3.add(map);
					pageNow3++;
				} while (cursor.moveToNext());

			}
			cursor.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
