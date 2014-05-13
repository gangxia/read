package com.zhufu.fragment;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dlnetwork.Dianle;
import com.dlnetwork.GetTotalMoneyListener;
import com.dlnetwork.SpendMoneyListener;
import com.zhufu.db.DBService;
import com.zhufu.db.LocalBook;
import com.zhufu.db.MarkHelper;
import com.zhufu.mobile.safety.R;
import com.zhufu.mobile.safety.Read;
import com.zhufuyisheng.util.CipherUtil;
import com.zhufuyisheng.util.Mhelp;
import com.zhufuyisheng.util.Mhttppost;
import com.zhufuyisheng.util.Mstring;
import com.zhufuyisheng.view.LocAdapter;

public class BooksFragment extends Fragment implements GetTotalMoneyListener,
		SpendMoneyListener {
	Context context;
	long jifen;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		dbService = new DBService(context);

		db2 = dbService.getWritableDatabase(Mstring.decrypetkey());
		View layout = inflater.inflate(R.layout.bookshelf, container, false);

		map2 = new HashMap<String, Integer[]>();

		// 读取名为"mark"的sharedpreferences
		sp = context.getSharedPreferences("mark", context.MODE_PRIVATE);

		list = (ListView) layout.findViewById(R.id.ListView01);
		titletext = (TextView) layout.findViewById(R.id.titletext);
		titletext1 = (TextView) layout.findViewById(R.id.titletext1);

		titletext1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 进入导入图书界面
				// Intent intent = new Intent();
				// intent.setClass(context, InActivity.class);
				// startActivity(intent);

			}
		});
		localbook = new LocalBook(context, "localbook");
		// 添加长按点击
		list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {

				menu.add(Menu.NONE, 0, 0, "从阅读列表中删除");
				menu.add(Menu.NONE, 1, 0, "给个好评");
				menu.add(Menu.NONE, 2, 0, "提交错误乱码等问题");
				menu.add(Menu.NONE, 3, 0, "导出txt到SD卡(需20积分)");

			}

		});

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				try {
					showProgressDialog("正在加载电子书..." + Mstring.Get_Msg());
					// 修改数据库中图书的最近阅读状态为1
					String s = (String) listItem.get(arg2).get("path");
					try {
						new CipherUtil().decrypt(s + ".dat", s,
								Mstring.decrypetkey());
					} catch (GeneralSecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SQLiteDatabase db = localbook.getWritableDatabase(Mstring
							.decrypetkey());

					File f = new File(s);
					if (f.length() == 0) {
						Toast.makeText(context, "该文件为空文件", Toast.LENGTH_SHORT)
								.show();
						if (mpDialog != null) {
							mpDialog.dismiss();
						}
					} else {
						ContentValues values = new ContentValues();
						values.put("now", 1);// key为字段名，value为值
						db.update("localbook", values, "path=?",
								new String[] { s });// 修改状态为图书被已被导入
						db.close();
						String path = (String) listItem.get(arg2).get("path");
						it = new Intent();
						it.setClass(context, Read.class);
						it.putExtra("path", path);
						it.putExtra("coding",
								(String) listItem.get(arg2).get("coding"));
						startActivity(it);
					}
				} catch (SQLException e) {
					Log.e(TAG,
							"list.setOnItemClickListener-> SQLException error",
							e);
				} catch (Exception e) {
					Log.e(TAG, "list.setOnItemClickListener Exception", e);
				}
			}
		});

		return layout;
	}

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		System.out.println("ExampleFragment--onCreate");
	}

	private static final String TAG = "MainActivity";
	// private Boolean a = true, b = false, c = false;
	private SharedPreferences.Editor editor;

	private Intent it;
	private ListView list;
	private ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();;
	private SimpleAdapter listItemAdapter = null;
	ListView listView;

	private LocalBook localbook;
	private HashMap<String, Object> map = null;
	private Map<String, Integer[]> map2;// 存放本地推荐目录的小封面图片引用
	private MarkHelper markhelper;
	protected ProgressDialog mDialog = null;

	private AdapterView.AdapterContextMenuInfo menuInfo;

	private ProgressDialog mpDialog;

	private SharedPreferences sp;

	private TextView titletext, titletext1;

	private TextView imgButton;
	LocAdapter locAdapter;

	public void closeProgressDialog() {
		if (mpDialog != null) {
			mpDialog.dismiss();
		}
	}

	/**
	 * 获取SD卡根目录
	 * 
	 * @return
	 */
	public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdDir.toString();
	}

	/**
	 * 本地书库载入
	 */
	public void local() {
		SQLiteDatabase db = localbook
				.getReadableDatabase(Mstring.decrypetkey());
		if (listItem == null) {
			listItem = new ArrayList<HashMap<String, Object>>();

		}
		listItem.clear();
		Cursor cur = db.query("localbook", null, null, null, null, null, null);

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String path = cur.getString(cur.getColumnIndex("path"));
				String name = cur.getString(cur.getColumnIndex("parent"));
				String img = cur.getString(cur.getColumnIndex("bookimg"));
				String coding = cur.getString(cur.getColumnIndex("coding"));
				String bookid = cur.getString(cur.getColumnIndex("bookid"));
				map = new HashMap<String, Object>();

				map.put("name", name);
				map.put("img", img);

				map.put("path", path);
				map.put("coding", coding);
				map.put("bookid", bookid);

				listItem.add(map);
			}
		}
		db.close();
		cur.close();

		if (locAdapter == null) {
			locAdapter = new LocAdapter(context, listItem);
			list.setAdapter(locAdapter);
		}

	}

	SQLiteDatabase db2;
	DBService dbService;

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:// 删除文件
				// 获取点击的是哪一个文件
			menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			HashMap<String, Object> imap = listItem.get(menuInfo.position);
			String path0 = (String) imap.get("path");
			SQLiteDatabase db = localbook.getWritableDatabase(Mstring
					.decrypetkey());
			try {
				ContentValues values = new ContentValues();
				values.put("now", 0);// key为字段名，value为值
				values.put("type", 0);
				values.putNull("ready");
				// db.update("localbook", values, "path=?", new String[] { s
				// });// 修改状态为图书被已被导入
				db.update("localbook", values, "path=? and type=1",
						new String[] { path0 });// 修改状态为图书被已被导入
				// 清空对本书的记录
				editor = sp.edit();
				editor.remove(path0 + "jumpPage");
				editor.remove(path0 + "count");
				editor.remove(path0 + "begin");
				editor.commit();
				markhelper = new MarkHelper(context);
				// 删除数据库书签记录
				SQLiteDatabase db2 = markhelper.getWritableDatabase(Mstring
						.decrypetkey());
				db2.delete("markhelper", "path='" + path0 + "'", null);
				db2.close();
			} catch (SQLException e) {
				Log.e(TAG, "onContextItemSelected-> SQLException error", e);
			} catch (Exception e) {
				Log.e(TAG, "onContextItemSelected-> Exception error", e);
			}
			db.close();
			// 重新载入页面
			local();
			break;
		case 1:
			menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			HashMap<String, Object> imap2 = listItem.get(menuInfo.position);
			String book_id = (String) imap2.get("bookid");

			Cursor cursor = db2.query("tag", null, "uid=" + book_id
					+ " and type=2 ", null, null, null, null);
			cursor.moveToFirst();
			if (cursor.getCount() == 0) {
				String sqlinsert = "insert into tag(uid,type) values("
						+ book_id + ",2)";

				db2.execSQL(sqlinsert);
				Mhttppost.post(Mstring.tag2, "love", Mstring.decrypetkey(),
						"book_id", book_id);
				Mhelp.shoarttoast(context, "成功给了好评！！");
			} else {
				Mhelp.shoarttoast(context, "已经给了好评！");
			}
			break;
		case 2:
			menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			HashMap<String, Object> imap3 = listItem.get(menuInfo.position);
			String book_id2 = (String) imap3.get("bookid");

			Cursor cursor2 = db2.query("tag", null, "uid=" + book_id2
					+ " and type=3 ", null, null, null, null);
			cursor2.moveToFirst();
			if (cursor2.getCount() == 0) {
				String sqlinsert = "insert into tag(uid,type) values("
						+ book_id2 + ",3)";

				db2.execSQL(sqlinsert);
				Mhttppost.post(Mstring.tag3, "love", Mstring.decrypetkey(),
						"book_id", book_id2);
				Mhelp.shoarttoast(context, "我们会尽快处理你的问题！！");
			} else {
				Mhelp.shoarttoast(context, "已经提交过改错误！");
			}
			break;
		case 3:
			menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			HashMap<String, Object> imap4 = listItem.get(menuInfo.position);
			final String path = (String) imap4.get("path");
			if (jifen >= 20) {

				Dialog dialog = new AlertDialog.Builder(context)
						.setTitle("提示")
						.setMessage("导出图书，需要消耗20积分，是否继续?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {

										String path2 = path.replace(
												".zhufu_read", "txtfile");

										try {
											new CipherUtil().decrypt(path
													+ ".dat", path2,
													Mstring.decrypetkey());
											Dianle.spendMoney(20,
													BooksFragment.this);
											Mhelp.longtoast(context,
													"已经导出到文件目录：" + path2);
										} catch (GeneralSecurityException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();

									}
								}).create();
				dialog.show();

			} else {

				Dialog dialog = new AlertDialog.Builder(context)
						.setTitle("提示")
						.setMessage(
								"下载该资源需要20积分，您当前积分：" + jifen
										+ ", 积分不足，是否免费获取积分?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										Dianle.showOffers();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();

									}
								}).create();
				dialog.show();

			}
			break;

		}
		return super.onContextItemSelected(item);
	}

	public void onDestroy() {
		closeProgressDialog();
		super.onDestroy();

	}

	@Override
	public void onPause() {
		super.onPause();
		closeProgressDialog();

	}

	@Override
	public void onResume() {
		super.onResume();

		local();
		Dianle.getTotalMoney(this);
	}

	public void showProgressDialog(String msg) {
		mpDialog = new ProgressDialog(context);
		mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
		mpDialog.setMessage(msg);
		mpDialog.setIndeterminate(false);// 设置进度条是否为不明确
		mpDialog.setCancelable(true);// 设置进度条是否可以按退回键取消
		mpDialog.show();
	}

	@Override
	public void spendMoneyFailed(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void spendMoneySuccess(long arg0) {
		// TODO Auto-generated method stub
		jifen = arg0;
	}

	@Override
	public void getTotalMoneyFailed(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getTotalMoneySuccessed(String arg0, long arg1) {
		// TODO Auto-generated method stub
		jifen = arg1;
	}

}
