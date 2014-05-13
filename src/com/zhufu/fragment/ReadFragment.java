package com.zhufu.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zhufu.db.DBService;
import com.zhufu.home.TListActivity;
import com.zhufu.mobile.safety.R;

public class ReadFragment extends Fragment {

	ListView list1;

	static List<Map<String, Object>> data;

	Context context;

	static DBService dbService;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.video, container, false);
		context = getActivity();
		dbService = new DBService(context);

		data = new ArrayList<Map<String, Object>>();
		list1 = (ListView) layout.findViewById(R.id.listview);
		initValue();
		SimpleAdapter adapter = new SimpleAdapter(context, data,
				R.layout.list_type, new String[] { "type" },
				new int[] { R.id.t2 });
		list1.setAdapter(adapter);
		list1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, TListActivity.class);

				intent.putExtra("tid", data.get(arg2).get("tid").toString());

				startActivity(intent);

			}
		});

		return layout;
	}

	public static void initValue() {

		try {
			String sql = "select type,tid,img,head from type where stat=? ";
			String[] arg = new String[] { "0" };

			Cursor cursor = dbService.query(sql, arg);
			cursor.moveToFirst();

			if (cursor != null && cursor.getCount() > 0) {
				do {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("type",
							cursor.getString(cursor.getColumnIndex("type")));
					map.put("tid",
							cursor.getString(cursor.getColumnIndex("tid")));
					map.put("img",
							cursor.getString(cursor.getColumnIndex("img")));
					map.put("head",
							cursor.getString(cursor.getColumnIndex("head")));

					data.add(map);
				} while (cursor.moveToNext());

			}
			cursor.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
