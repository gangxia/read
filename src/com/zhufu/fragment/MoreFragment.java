package com.zhufu.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dlnetwork.Dianle;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.zhufu.mobile.safety.R;
import com.zhufuyisheng.util.Mhelp;
import com.zhufuyisheng.util.Mstring;

public class MoreFragment extends Fragment {
	Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		View settingLayout = inflater.inflate(R.layout.setting_layout,
				container, false);
		Button about = (Button) settingLayout.findViewById(R.id.about);
		about.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						context);
				alertDialog
						.setTitle("软件说明")
						.setMessage(
								"    本软件适用于Android 2.2及以上固件的设备，本软件下载、安装完全免费。用户在使用的过程中所产生的移动业务的数据费用皆由移动通信运营商收取。建议用户选择WIFI网络使用本软件，以减少相关的上网数据费用！\n      本软体内所有内容来源皆取自网络资源。本软件仅为大家更加便利的娱乐观赏.版权与著作权皆为原网站、原作者所有，绝无有内容侵权之意。其中内容若有不妥，或是侵犯了您的合法权益，请麻烦通知我们，我们将会迅速协助将侵权的部分移除，谢谢!\n  有问题可以反馈！ \n   谢谢支持")
						.setNegativeButton("知道了", null).show();
			}
		});

		Button share = (Button) settingLayout.findViewById(R.id.share);
		share.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_SEND);

				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
				intent.putExtra(Intent.EXTRA_TEXT,
						"屌丝最喜欢的阅读器强势来袭，下载地址：http://www.zhufuyisheng.com/phone/read/read.apk");
				startActivity(Intent.createChooser(intent, "分享"));
			}
		});
		Button update = (Button) settingLayout.findViewById(R.id.ver);
		update.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UmengUpdateAgent.update(context);
				UmengUpdateAgent.setUpdateOnlyWifi(false);

				UmengUpdateAgent.setUpdateAutoPopup(false);
				UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
					@Override
					public void onUpdateReturned(int updateStatus,
							UpdateResponse updateInfo) {
						switch (updateStatus) {
						case 0: // has update
							UmengUpdateAgent.showUpdateDialog(context,
									updateInfo);
							break;
						case 1: // has no update
							Mhelp.shoarttoast(context, "恭喜，当前为最新版本，没有更新");
							break;
						case 2: // none wifi

							break;
						case 3: // time out
							Mhelp.shoarttoast(context, "超时");
							break;
						}
					}
				});
			}
		});
		Button feedback = (Button) settingLayout.findViewById(R.id.feedback);
		feedback.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FeedbackAgent agent = new FeedbackAgent(context);
				agent.startFeedbackActivity();
			}
		});
		Button app = (Button) settingLayout.findViewById(R.id.app);
		app.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dianle.showOffers();
			}
		});

		Button zhufu = (Button) settingLayout.findViewById(R.id.zhufu);
		zhufu.setVisibility(View.GONE);
		zhufu.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri web = Uri.parse(Mstring.zhufu);
				Intent i = new Intent(Intent.ACTION_VIEW, web);
				startActivity(i);
			}
		});
		return settingLayout;
	}
}
