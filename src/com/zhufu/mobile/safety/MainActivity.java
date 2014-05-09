package com.zhufu.mobile.safety;

import net.sqlcipher.database.SQLiteDatabase;
import android.app.AlertDialog;
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
import android.widget.Toast;
import cn.waps.AppConnect;

import com.dlnetwork.Dianle;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.zhufufb.FeedbackSDK;
import com.zhufuyisheng.util.Mhelp;

/**
 * 项目的主Activity，所有的Fragment都嵌入在这里。
 * 
 * @author guolin
 */
public class MainActivity extends FragmentActivity implements OnClickListener {

	private HomeFragment homeFragment;
	private TypeFragment typeFragment;
	private BooksFragment booksFragment;
	private SearchFragment searchFragment;
	private SettingFragment settingFragment;

	/**
	 * 主页界面布局
	 */
	private View homeLayout;

	/**
	 * 类型界面布局
	 */
	private View typeLayout;

	/**
	 * 搜索界面布局
	 */
	private View searchLayout;

	/**
	 * 书籍界面布局
	 */
	private View booksLayout;

	/**
	 * 设置界面布局
	 */
	private View settingLayout;

	/**
	 * 在Tab布局上显示消息标题的控件
	 */
	private TextView homeText;

	private TextView typeText;

	private TextView searchText;

	private TextView booksText;

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
		// setContentView(R.layout.activity_main);
		//
		// SQLiteDatabase.loadLibs(this);
		// // 初始化布局元素
		// initViews();
		// fragmentManager = getSupportFragmentManager();
		// // 第一次启动时选中第0个tab
		// setTabSelection(0);
		// FeedbackAgent agent = new FeedbackAgent(this);
		// agent.sync();
		// UmengUpdateAgent.update(MainActivity.this);
		// UmengUpdateAgent.setUpdateOnlyWifi(false);
		//
		// UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
		// @Override
		// public void onUpdateReturned(int updateStatus,
		// UpdateResponse updateInfo) {
		// switch (updateStatus) {
		// case 0: // has update
		// UmengUpdateAgent.showUpdateDialog(MainActivity.this,
		// updateInfo);
		// break;
		// case 1: // has no update
		//
		// break;
		// case 2: // none wifi
		//
		// break;
		// case 3: // time out
		//
		// break;
		// }
		// }
		// });
		// Dianle.initDianleContext(this, "1ccdc355822e988f8f262d09cd6d5028",
		// "gfan");
		//
		// Dianle.setCustomActivity("com.dian.MyView");
		// Dianle.setCustomService("com.dian.MyService");
		// FeedbackSDK.init(this, "010M0P00");
		//
		// TelephonyManager telephony = (TelephonyManager)
		// getSystemService(Context.TELEPHONY_SERVICE);
		// int type = telephony.getPhoneType();
		// if (type != TelephonyManager.PHONE_TYPE_NONE) {
		// QBLMA.initSDK(MainActivity.this, "10879");
		// QBLMA.initAD2(this);
		// }
		// String first = Mhelp.GetSharedPreferences(this, "first", "first");
		// if ("no".equals(first)) {
		//
		// } else {
		// AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		// alertDialog
		// .setTitle("软件说明")
		// .setMessage(
		// "    本软件适用于Android 2.2及以上固件的设备，本软件下载、安装完全免费。用户在使用的过程中所产生的移动业务的数据费用皆由移动通信运营商收取。建议用户选择WIFI网络使用本软件，以减少相关的上网数据费用！\n     本软件所有作品都来源网络，仅供书友免费预览！版权为原作者所有!版权人如果认为本站转载传播会侵犯您的权益，敬请来信告知，我们将立即删除！\n  有问题可以反馈！ \n   谢谢支持")
		// .setNegativeButton("知道了", null).show();
		// Mhelp.SetSharedPreferences(this, "first", "first", "no");
		// }

	}

	public void init() {
		new Thread() {
			public void run() {
				Looper.prepare();
				SQLiteDatabase.loadLibs(MainActivity.this);
				// 初始化布局元素

				fragmentManager = getSupportFragmentManager();
				// 第一次启动时选中第0个tab

				FeedbackAgent agent = new FeedbackAgent(MainActivity.this);
				agent.sync();
				UmengUpdateAgent.update(MainActivity.this);
				UmengUpdateAgent.setUpdateOnlyWifi(false);

				UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
					@Override
					public void onUpdateReturned(int updateStatus,
							UpdateResponse updateInfo) {
						switch (updateStatus) {
						case 0: // has update
							UmengUpdateAgent.showUpdateDialog(
									MainActivity.this, updateInfo);
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
				Dianle.initDianleContext(MainActivity.this,
						"1ccdc355822e988f8f262d09cd6d5028", "gfan");

				Dianle.setCustomActivity("com.dian.MyView");
				Dianle.setCustomService("com.dian.MyService");
				FeedbackSDK.init(MainActivity.this, "010M0P00");

				AppConnect.getInstance("df0f22eb26e885ace71fb94d092c5326",
						"gp", MainActivity.this);

				AppConnect.getInstance(MainActivity.this).initPopAd(
						MainActivity.this);
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
				setContentView(R.layout.activity_main);
				initViews();
				setTabSelection(0);
				String first = Mhelp.GetSharedPreferences(MainActivity.this,
						"first", "first");
				if ("no".equals(first)) {

				} else {
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(
							MainActivity.this);
					alertDialog
							.setTitle("软件说明")
							.setMessage(
									"    本软件适用于Android 2.2及以上固件的设备，本软件下载、安装完全免费。用户在使用的过程中所产生的移动业务的数据费用皆由移动通信运营商收取。建议用户选择WIFI网络使用本软件，以减少相关的上网数据费用！\n      本软体内所有内容来源皆取自网络资源。本软件仅为大家更加便利的娱乐观赏.版权与著作权皆为原网站、原作者所有，绝无有内容侵权之意。其中内容若有不妥，或是侵犯了您的合法权益，请麻烦通知我们，我们将会迅速协助将侵权的部分移除，谢谢!\n  有问题可以反馈！ \n   谢谢支持")
							.setNegativeButton("知道了", null).show();
					Mhelp.SetSharedPreferences(MainActivity.this, "first",
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
		homeLayout = findViewById(R.id.home_layout);
		typeLayout = findViewById(R.id.type_layout);
		searchLayout = findViewById(R.id.search_layout);
		booksLayout = findViewById(R.id.books_layout);
		settingLayout = findViewById(R.id.setting_layout);

		homeText = (TextView) findViewById(R.id.home_text);
		typeText = (TextView) findViewById(R.id.type_text);
		searchText = (TextView) findViewById(R.id.search_text);
		booksText = (TextView) findViewById(R.id.books_text);
		settingText = (TextView) findViewById(R.id.setting_text);
		homeLayout.setOnClickListener(this);
		typeLayout.setOnClickListener(this);
		searchLayout.setOnClickListener(this);
		booksLayout.setOnClickListener(this);
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

			homeText.setTextColor(Color.WHITE);
			if (homeFragment == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				homeFragment = new HomeFragment();
				// transaction.add(0, messageFragment);
				transaction.add(R.id.content, homeFragment);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(homeFragment);
			}
			break;
		case 1:
			// 当点击了联系人tab时，改变控件的图片和文字颜色

			typeText.setTextColor(Color.WHITE);
			if (typeFragment == null) {
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				typeFragment = new TypeFragment();
				transaction.add(R.id.content, typeFragment);
			} else {
				// 如果ContactsFragment不为空，则直接将它显示出来
				transaction.show(typeFragment);
			}
			break;
		case 2:
			// 当点击了动态tab时，改变控件的图片和文字颜色

			searchText.setTextColor(Color.WHITE);
			if (searchFragment == null) {
				// 如果NewsFragment为空，则创建一个并添加到界面上
				searchFragment = new SearchFragment();
				transaction.add(R.id.content, searchFragment);
			} else {
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.show(searchFragment);
			}
			break;
		case 3:
			// 当点击了动态tab时，改变控件的图片和文字颜色

			booksText.setTextColor(Color.WHITE);
			if (booksFragment == null) {
				// 如果NewsFragment为空，则创建一个并添加到界面上
				booksFragment = new BooksFragment();
				transaction.add(R.id.content, booksFragment);
			} else {
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.show(booksFragment);
			}
			break;
		case 4:
		default:
			// 当点击了设置tab时，改变控件的图片和文字颜色

			settingText.setTextColor(Color.WHITE);
			if (settingFragment == null) {
				// 如果SettingFragment为空，则创建一个并添加到界面上
				settingFragment = new SettingFragment();
				transaction.add(R.id.content, settingFragment);
			} else {
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.show(settingFragment);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {

		homeText.setTextColor(Color.parseColor("#82858b"));

		typeText.setTextColor(Color.parseColor("#82858b"));

		searchText.setTextColor(Color.parseColor("#82858b"));
		booksText.setTextColor(Color.parseColor("#82858b"));

		settingText.setTextColor(Color.parseColor("#82858b"));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (homeFragment != null) {
			transaction.hide(homeFragment);
		}
		if (typeFragment != null) {
			transaction.hide(typeFragment);
		}
		if (searchFragment != null) {
			transaction.hide(searchFragment);
		}
		if (booksFragment != null) {
			transaction.hide(booksFragment);
		}
		if (settingFragment != null) {
			transaction.hide(settingFragment);
		}
	}

	boolean exit = false;

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if ((keyCode == KeyEvent.KEYCODE_BACK)
				&& (event.getAction() == KeyEvent.ACTION_DOWN)
				&& (event.getRepeatCount() == 0)) {

			if (exit) {

				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				android.os.Process.killProcess(android.os.Process.myPid());
				return true;
			} else {
				exit = true;
				Toast.makeText(MainActivity.this, "再按一次返回键退出", 2000).show();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						exit = false;
					}
				}, 2000);
			}

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
