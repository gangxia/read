package com.zhufu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhufu.mobile.safety.R;
import com.zhufu.mobile.safety.TypeList;

public class TypeFragment extends Fragment {
	TextView lab1, lab2, lab3, lab4, lab5, lab6, lab7, lab8, lab9, lab10;
	Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		View layout = inflater.inflate(R.layout.type_layout, container, false);
		lab1 = (TextView) layout.findViewById(R.id.lab1);
		lab1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lab_click("玄幻魔法");
			}
		});
		lab2 = (TextView) layout.findViewById(R.id.lab2);
		lab2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lab_click("都市言情");
			}
		});
		lab3 = (TextView) layout.findViewById(R.id.lab3);
		lab3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lab_click("恐怖推理");
			}
		});
		lab4 = (TextView) layout.findViewById(R.id.lab4);
		lab4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lab_click("现代网络");
			}
		});
		lab5 = (TextView) layout.findViewById(R.id.lab5);
		lab5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lab_click("武侠仙侠");
			}
		});
		lab6 = (TextView) layout.findViewById(R.id.lab6);
		lab6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lab_click("古著史事");
			}
		});
		lab7 = (TextView) layout.findViewById(R.id.lab7);
		lab7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lab_click("科幻童话");
			}
		});
		lab8 = (TextView) layout.findViewById(R.id.lab8);
		lab8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lab_click("官场商场");
			}
		});
		lab9 = (TextView) layout.findViewById(R.id.lab9);
		lab9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lab_click("名人传记");
			}
		});
		lab10 = (TextView) layout.findViewById(R.id.lab10);
		lab10.setVisibility(View.GONE);
		lab10.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lab_click("成人激情");
			}
		});

		return layout;
	}

	public void lab_click(String value) {
		Intent intent = new Intent(context, TypeList.class);

		intent.putExtra("value", value);
		startActivity(intent);
	}

}
