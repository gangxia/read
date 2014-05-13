package com.zhufu.mobile.safety;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import net.sqlcipher.database.SQLiteDatabase;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.zhufu.db.DBService;
import com.zhufu.db.LocalBook;
import com.zhufuyisheng.util.CipherUtil;
import com.zhufuyisheng.util.FileDownloader;
import com.zhufuyisheng.util.Mhelp;
import com.zhufuyisheng.util.Mhttppost;
import com.zhufuyisheng.util.Mstring;
import com.zhufuyisheng.util.UnRarUtil;

public class BookActivity extends Activity {

	Context context;
	private ProgressDialog pd = null; // 下载进度条
	TextView name, size, author, role, type, tag1, tag2, text, go;
	Button back;
	ImageView img;
	LocalBook localbook;

	DisplayImageOptions options;

	ImageLoader imageLoader;
	static String book_url, book_id, book_name, book_type, book_img;

	FileDownloader fileDown;
	SQLiteDatabase db, db2;
	DBService dbService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.books_layout);
		context = this;
		book_url = getIntent().getExtras().getString("url").trim();
		book_name = getIntent().getExtras().getString("name").trim();
		book_id = getIntent().getExtras().getString("id").trim();
		book_img = getIntent().getExtras().getString("img").trim();
		localbook = new LocalBook(context, "localbook");
		dbService = new DBService(context);
		db = localbook.getWritableDatabase(Mstring.decrypetkey());
		db2 = dbService.getWritableDatabase(Mstring.decrypetkey());
		final Cursor cur = db.query("localbook", null, "bookid= '" + book_id
				+ "'", null, null, null, null);

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));

		options = new DisplayImageOptions.Builder()

		.showImageForEmptyUri(R.drawable.book).showImageOnFail(R.drawable.book)
				.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		name = (TextView) findViewById(R.id.name);
		author = (TextView) findViewById(R.id.author);
		role = (TextView) findViewById(R.id.role);
		type = (TextView) findViewById(R.id.type);
		size = (TextView) findViewById(R.id.size);
		tag1 = (TextView) findViewById(R.id.tag1);
		tag2 = (TextView) findViewById(R.id.tag2);
		text = (TextView) findViewById(R.id.text);
		go = (TextView) findViewById(R.id.go);
		back = (Button) findViewById(R.id.back);
		img = (ImageView) findViewById(R.id.img);

		if (cur.getCount() > 0) {
			go.setText("打开");
		} else {
			go.setText("下载");
		}

		name.setText("" + book_name);
		author.setText(" 作者：  "
				+ getIntent().getExtras().getString("author").trim());
		text.setText("  内容简介\n   "
				+ getIntent().getExtras().getString("text").trim());
		role.setText(" 主角：  "
				+ getIntent().getExtras().getString("role").trim());
		type.setText(" 类型：  "
				+ getIntent().getExtras().getString("type").trim());
		size.setText(" 大小：  "
				+ getIntent().getExtras().getString("size").trim());
		tag1.setText(" 下载：  "
				+ getIntent().getExtras().getString("tag1").trim());
		tag2.setText(" 好评：  "
				+ getIntent().getExtras().getString("tag2").trim());

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		go.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (cur.getCount() == 0) {
					if (fileDown != null && fileDown.isAlive()) {
						Mhelp.shoarttoast(context, "正在下载中");
						return;
					}

					String[] hou = book_url.split("\\.");
					if (hou != null && "txt".equals(hou[hou.length - 1])) {
						book_type = "txt";
						fileDown = new FileDownloader(book_url, Mstring.fileDir
								+ book_id + "/", book_id + ".txt",
								downloadHandler);
						fileDown.Start();

					} else {
						book_type = "rar";
						fileDown = new FileDownloader(book_url, Mstring.fileDir
								+ book_id + "/", book_id + ".rar",
								downloadHandler);
						fileDown.Start();
					}

					String sql = "select id from tag where  uid=" + book_id
							+ " and type=1 ";

					Cursor cursor = db2.query("tag", null, "uid=" + book_id
							+ " and type=1 ", null, null, null, null);
					cursor.moveToFirst();
					if (cursor.getCount() == 0) {
						String sqlinsert = "insert into tag(uid,type) values("
								+ book_id + ",1)";

						db2.execSQL(sqlinsert);
						Mhttppost.post(Mstring.tag1, "love",
								Mstring.decrypetkey(), "book_id", book_id);

					}

				} else {
					while (cur.moveToNext()) {
						String path = cur.getString(cur.getColumnIndex("path"));
						String name = cur.getString(cur
								.getColumnIndex("parent"));
						String img = cur.getString(cur
								.getColumnIndex("bookimg"));
						String coding = cur.getString(cur
								.getColumnIndex("coding"));
						try {
							new CipherUtil().decrypt(path + ".dat", path,
									Mstring.decrypetkey());
						} catch (GeneralSecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Intent it = new Intent(context, Read.class);

						it.putExtra("path", path);
						it.putExtra("coding", coding);
						startActivity(it);
					}

				}

			}
		});

		imageLoader.displayImage(book_img, img, options,
				new SimpleImageLoadingListener() {
				});

	}

	private Handler downloadHandler = new Handler() { // 用于接收消息，处理进度条
		@Override
		public void handleMessage(Message msg) { // 接收到的消息，并且对接收到的消息进行处理
			Log.d("DOWN", msg.what + "");
			if (!Thread.currentThread().isInterrupted()) {
				switch (msg.what) {
				case FileDownloader.START:
					if (pd == null || !pd.isShowing()) {

						pd = new ProgressDialog(context); // 实例化
						pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // 设置进度条风格
						pd.setTitle("下载中...." + Mstring.Get_Msg()); // 设置ProgressDialog
						// 标题

						pd.setIndeterminate(false); // 设置ProgressDialog
													// 的进度条是否不明确
						pd.setCancelable(true); // 设置ProgressDialog 是否可以按退回按键取消

					}
					pd.show();
					pd.setMax(msg.arg1);
					pd.setProgress(msg.arg2);
					break;
				case FileDownloader.PROCESS:
					pd.setMax(msg.arg1);
					pd.setProgress(msg.arg2);
					break;
				case FileDownloader.COMPLETE:
					String coding = "UTF-8";
					if ("rar".equals(book_type)) {
						try {
							coding = "GBK";
							UnRarUtil.deCompress(Mstring.fileDir + book_id
									+ "/" + book_id + ".rar", Mstring.fileDir
									+ book_id + "/");
							Mhelp.delete(new File(Mstring.fileDir + book_id
									+ "/" + book_id + ".rar"));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					File file = Mhelp.Scanning(new File(Mstring.fileDir
							+ book_id + "/"));
					if (file == null) {
						Mhelp.shoarttoast(context, "下载错误，重新下载吧！");

						Cursor cursor2 = db2.query("tag", null, "uid="
								+ book_id + " and type=3 ", null, null, null,
								null);
						cursor2.moveToFirst();
						if (cursor2.getCount() == 0) {
							String sqlinsert = "insert into tag(uid,type) values("
									+ book_id + ",3)";

							db2.execSQL(sqlinsert);
							Mhttppost.post(Mstring.tag3, "love",
									Mstring.decrypetkey(), "book_id", book_id);
							Mhelp.shoarttoast(context, "我们会尽快处理你的问题！！");
						}
						return;
					}
					try {
						new CipherUtil().encrypt(file.getAbsolutePath(),
								file.getAbsolutePath() + ".dat",
								Mstring.decrypetkey());
					} catch (GeneralSecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String sql1 = "insert into localbook (parent,path,bookid,bookimg,coding, type,now,ready) values('"
							+ book_name
							+ "','"
							+ file.getAbsolutePath()
							+ "','"
							+ book_id
							+ "','"
							+ book_img
							+ "','"
							+ coding + "',1,0,null" + ");";

					db.execSQL(sql1);
					pd.dismiss();
					Intent it = new Intent(context, Read.class);

					it.putExtra("path", file.getAbsolutePath());
					it.putExtra("coding", coding);
					startActivity(it);

					Mhelp.shoarttoast(context, "下载完成");
					pd = null;
					fileDown = null;

					break;
				case FileDownloader.ERROR:
					String error = (String) msg.obj;
					Cursor cursor2 = db2.query("tag", null, "uid=" + book_id
							+ " and type=3 ", null, null, null, null);
					cursor2.moveToFirst();
					if (cursor2.getCount() == 0) {
						String sqlinsert = "insert into tag(uid,type) values("
								+ book_id + ",3)";

						db2.execSQL(sqlinsert);
						Mhttppost.post(Mstring.tag3, "love",
								Mstring.decrypetkey(), "book_id", book_id);
						Mhelp.shoarttoast(context, "我们会尽快处理你的问题！！");

					}
					break;
				}
			}
			super.handleMessage(msg);
		}
	};

}