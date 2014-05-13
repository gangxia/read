package com.zhufu.db;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import android.content.Context;

/**
 * 创建数据库
 * 
 * @author
 * 
 */
public class LocalBook extends SQLiteOpenHelper {
	private static String DATABASE_NAME = "book.db";
	private static int DATABASE_VERSION = 1;
	private String PATH = "path";
	private String TYPE = "type";
	private String s = null;

	public LocalBook(Context context, String s) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.s = s;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE "
				+ s
				+ " ( parent text not null, "
				+ PATH
				+ " text not null,bookid text not null,bookimg text not null,coding text not null, "
				+ TYPE + " text not null" + ", now  text not null, ready);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
