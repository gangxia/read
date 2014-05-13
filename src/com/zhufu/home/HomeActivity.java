package com.zhufu.home;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhufu.db.DBService;
import com.zhufu.fragment.ImgFragment;
import com.zhufu.fragment.MoreFragment;
import com.zhufu.fragment.ReadFragment;
import com.zhufu.fragment.VideoFragment;
import com.zhufu.mobile.safety.R;
import com.zhufuyisheng.util.Mhttppost;
import com.zhufuyisheng.util.Mstring;

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
	static DBService dbService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.home);
		dbService = new DBService(this);
		init();

	}

	public void init() {
		initValue();
		initViews();
		setTabSelection(0);

	}

	/**
	 * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
	 */
	private void initViews() {
		fragmentManager = getSupportFragmentManager();
		videoLayout = findViewById(R.id.video_layout);
		imgLayout = findViewById(R.id.img_layout);
		readLayout = findViewById(R.id.read_layout);

		settingLayout = findViewById(R.id.set_layout);

		videoText = (TextView) findViewById(R.id.video_text);
		imgText = (TextView) findViewById(R.id.img_text);
		readText = (TextView) findViewById(R.id.read_text);
		settingText = (TextView) findViewById(R.id.set_text);
		videoLayout.setOnClickListener(this);
		imgLayout.setOnClickListener(this);
		readLayout.setOnClickListener(this);

		settingLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.video_layout:
			// 当点击了消息tab时，选中第1个tab
			setTabSelection(0);
			break;
		case R.id.img_layout:
			// 当点击了联系人tab时，选中第2个tab
			setTabSelection(1);
			break;
		case R.id.read_layout:
			// 当点击了动态tab时，选中第3个tab
			setTabSelection(2);
			break;

		case R.id.set_layout:
			// 当点击了设置tab时，选中第4个tab
			setTabSelection(3);
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

	public static void initValue() {
		Map<String, Object> map;

		try {
			String sql = "select uid from type order by id desc limit 1 ";
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
			String callback = Mhttppost.post(Mstring.type, "love",
					Mstring.decrypetkey(), "id", u);
			JSONArray arrayJson = new JSONArray(callback); // 得到json数据开始字符

			// json解析
			for (int i = 0; i < arrayJson.length(); i++) {
				JSONObject tempJson = arrayJson.optJSONObject(i);
				map = new HashMap<String, Object>();
				String uid = tempJson.getString("id");
				String stat = tempJson.getString("stat");
				String type = tempJson.getString("type");
				String tid = tempJson.getString("tid");
				String img = tempJson.getString("img");
				String head = tempJson.getString("head");

				String sql2 = "insert into type(uid,stat,type,tid,img,head) values(?,?,?,?,?,?)";
				Object[] object = new Object[] { uid, stat, type, tid, img,
						head };
				dbService.execSQL(sql2, object);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
