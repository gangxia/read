package com.zhufu.home;

import net.sqlcipher.database.SQLiteDatabase;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import cn.waps.AppConnect;

import com.dlnetwork.Dianle;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.zhufu.fragment.ImgFragment;
import com.zhufu.fragment.MoreFragment;
import com.zhufu.fragment.ReadFragment;
import com.zhufu.fragment.VideoFragment;
import com.zhufu.mobile.safety.R;
import com.zhufufb.FeedbackSDK;
import com.zhufuyisheng.util.Mhelp;

/**
 * 项目的主Activity，所有的Fragment都嵌入在这里。
 * 
 * @author
 */
public class HomeActivity extends FragmentActivity implements OnClickListener {

	private VideoFragment videoFragment;
	private ImgFragment imgFragment;
	private ReadFragment readFragment;
	private MoreFragment moreFragment;

	/**
	 * 电影界面布局
	 */
	private View videoLayout;

	/**
	 * 图片界面布局
	 */
	private View imgLayout;

	/**
	 * 小说界面布局
	 */
	private View readLayout;

	/**
	 * 设置界面布局
	 */
	private View settingLayout;

	/**
	 * 在Tab布局上显示消息标题的控件
	 */
	private TextView videoText;

	private TextView imgText;

	private TextView readText;

	private TextView settingText;

	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.logo);
		init();

	}

	public void init() {
		new Thread() {
			public void run() {
				Looper.prepare();
				SQLiteDatabase.loadLibs(HomeActivity.this);
				// 初始化布局元素

				fragmentManager = getSupportFragmentManager();
				// 第一次启动时选中第0个tab

				FeedbackAgent agent = new FeedbackAgent(HomeActivity.this);
				agent.sync();
				UmengUpdateAgent.update(HomeActivity.this);
				UmengUpdateAgent.setUpdateOnlyWifi(false);

				UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
					@Override
					public void onUpdateReturned(int updateStatus,
							UpdateResponse updateInfo) {
						switch (updateStatus) {
						case 0: // has update
							UmengUpdateAgent.showUpdateDialog(
									HomeActivity.this, updateInfo);
							break;
						case 1: // has no update

							break;
						case 2: // none wifi

							break;
						case 3: // time out

							break;
						}
					}
				});
				Dianle.initDianleContext(HomeActivity.this,
						"1ccdc355822e988f8f262d09cd6d5028", "gfan");

				Dianle.setCustomActivity("com.dian.MyView");
				Dianle.setCustomService("com.dian.MyService");
				FeedbackSDK.init(HomeActivity.this, "010M0P00");

				AppConnect.getInstance("df0f22eb26e885ace71fb94d092c5326",
						"gp", HomeActivity.this);

				AppConnect.getInstance(HomeActivity.this).initPopAd(
						HomeActivity.this);
				Message mes = handler.obtainMessage(1);
				handler.sendMessage(mes);
				Looper.loop();
			}
		}.start();
	}

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				setContentView(R.layout.home);
				initViews();
				setTabSelection(0);
				String first = Mhelp.GetSharedPreferences(HomeActivity.this,
						"first", "first");
				if ("no".equals(first)) {

				} else {
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(
							HomeActivity.this);
					alertDialog
							.setTitle("软件说明")
							.setMessage(
									"    本软件适用于Android 2.2及以上固件的设备，本软件下载、安装完全免费。用户在使用的过程中所产生的移动业务的数据费用皆由移动通信运营商收取。建议用户选择WIFI网络使用本软件，以减少相关的上网数据费用！\n      本软体内所有内容来源皆取自网络资源。本软件仅为大家更加便利的娱乐观赏.版权与著作权皆为原网站、原作者所有，绝无有内容侵权之意。其中内容若有不妥，或是侵犯了您的合法权益，请麻烦通知我们，我们将会迅速协助将侵权的部分移除，谢谢!\n  有问题可以反馈！ \n   谢谢支持")
							.setNegativeButton("知道了", null).show();
					Mhelp.SetSharedPreferences(HomeActivity.this, "first",
							"first", "no");
				}
				break;

			default:
				break;
			}

			super.handleMessage(msg);
		}
	};

	/**
	 * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
	 */
	private void initViews() {
		videoLayout = findViewById(R.id.video_layout);
		imgLayout = findViewById(R.id.img_layout);
		readLayout = findViewById(R.id.read_layout);

		settingLayout = findViewById(R.id.set_layout);

		videoText = (TextView) findViewById(R.id.video_text);
		imgText = (TextView) findViewById(R.id.img_text);

		settingText = (TextView) findViewById(R.id.set_text);
		videoLayout.setOnClickListener(this);
		imgLayout.setOnClickListener(this);
		readLayout.setOnClickListener(this);

		settingLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_layout:
			// 当点击了消息tab时，选中第1个tab
			setTabSelection(0);
			break;
		case R.id.type_layout:
			// 当点击了联系人tab时，选中第2个tab
			setTabSelection(1);
			break;
		case R.id.search_layout:
			// 当点击了动态tab时，选中第3个tab
			setTabSelection(2);
			break;

		case R.id.books_layout:
			// 当点击了动态tab时，选中第3个tab
			setTabSelection(3);
			break;
		case R.id.setting_layout:
			// 当点击了设置tab时，选中第4个tab
			setTabSelection(4);
			break;
		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 * 
	 */
	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了消息tab时，改变控件的图片和文字颜色

			videoText.setTextColor(Color.WHITE);
			if (videoFragment == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				videoFragment = new VideoFragment();
				// transaction.add(0, messageFragment);
				transaction.add(R.id.content, videoFragment);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(videoFragment);
			}
			break;
		case 1:
			// 当点击了联系人tab时，改变控件的图片和文字颜色

			imgText.setTextColor(Color.WHITE);
			if (imgFragment == null) {
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				imgFragment = new ImgFragment();
				transaction.add(R.id.content, imgFragment);
			} else {
				// 如果ContactsFragment不为空，则直接将它显示出来
				transaction.show(imgFragment);
			}
			break;
		case 2:
			// 当点击了动态tab时，改变控件的图片和文字颜色

			readText.setTextColor(Color.WHITE);
			if (readFragment == null) {
				// 如果NewsFragment为空，则创建一个并添加到界面上
				readFragment = new ReadFragment();
				transaction.add(R.id.content, readFragment);
			} else {
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.show(readFragment);
			}
			break;

		case 3:
			// 当点击了设置tab时，改变控件的图片和文字颜色

			settingText.setTextColor(Color.WHITE);
			if (moreFragment == null) {
				// 如果SettingFragment为空，则创建一个并添加到界面上
				moreFragment = new MoreFragment();
				transaction.add(R.id.content, moreFragment);
			} else {
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.show(moreFragment);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {

		videoText.setTextColor(Color.parseColor("#82858b"));

		imgText.setTextColor(Color.parseColor("#82858b"));

		readText.setTextColor(Color.parseColor("#82858b"));

		settingText.setTextColor(Color.parseColor("#82858b"));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (videoFragment != null) {
			transaction.hide(videoFragment);
		}
		if (imgFragment != null) {
			transaction.hide(imgFragment);
		}
		if (readFragment != null) {
			transaction.hide(readFragment);
		}

		if (moreFragment != null) {
			transaction.hide(moreFragment);
		}
	}

	boolean exit = false;

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(this).setTitle("亲,你要退出嘛?")

			.setPositiveButton("退出", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					android.os.Process.killProcess(android.os.Process.myPid());

				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {

				}
			}).show();

			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	public void onResume() {
		super.onResume();

		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
