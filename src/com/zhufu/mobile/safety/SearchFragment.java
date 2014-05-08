package com.zhufu.mobile.safety;

import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.zhufuyisheng.util.KeywordsFlow;
import com.zhufuyisheng.util.Mstring;

public class SearchFragment extends Fragment {
	/**
	 * 关键字
	 */
	// KeywordsFlow keywordsFlow;
	/**
	 * 搜索按钮
	 */
	TextView search;
	/**
	 * 随机换数据
	 */
	TextView up;
	/**
	 * 搜索输入
	 */
	EditText edit;
	Context context;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		System.out.println(Mstring.decrypetkey());
		View layout = inflater
				.inflate(R.layout.search_layout, container, false);

		// keywordsFlow = (KeywordsFlow) layout.findViewById(R.id.keyword);
		// keywordsFlow.setOnItemClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// String keyword = ((TextView) v).getText().toString().trim();
		// getjson(keyword);
		//
		// }
		// });
		search = (TextView) layout.findViewById(R.id.search);
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String text = edit.getText().toString().trim();
				getjson(text);

			}
		});
		up = (TextView) layout.findViewById(R.id.up);
		up.setVisibility(View.GONE);
		// up.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// keywordsFlow.rubKeywords();
		// // keywordsFlow.rubAllViews();
		// feedKeywordsFlow(keywordsFlow, Mstring.keyword);
		// keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
		// }
		// });
		edit = (EditText) layout.findViewById(R.id.edit);
		// keywordsFlow.setDuration(800l);
		//
		// feedKeywordsFlow(keywordsFlow, Mstring.keyword);
		// keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
		return layout;
	}

	private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
		Random random = new Random();
		for (int i = 0; i < KeywordsFlow.MAX; i++) {
			int ran = random.nextInt(arr.length);
			String tmp = arr[ran];
			keywordsFlow.feedKeyword(tmp);
		}
	}

	public void getjson(final String text) {

		Intent intent = new Intent(context, SearchListActivity.class);

		intent.putExtra("value", text);
		startActivity(intent);

	}

}
