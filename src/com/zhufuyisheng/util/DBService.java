package com.zhufuyisheng.util;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import android.content.Context;

public class DBService extends SQLiteOpenHelper {
	private final static int DATABASE_VERSION = 1;
	private final static String DATABASE_NAME = "user.db";

	private final static String TABLE_NAME = "tag";
	private final static String ID = "id";

	private final static String UID = "uid";
	private final static String TYPE = "type"; // 1,点击 2,赞

	public void onCreate(SQLiteDatabase db) {

		StringBuilder sql2 = new StringBuilder();
		sql2.append("CREATE TABLE ");
		sql2.append(TABLE_NAME);
		sql2.append(" (");
		sql2.append(ID + " INTEGER primary key autoincrement, ");

		sql2.append(UID + " INTEGER,");
		sql2.append(TYPE + " INTEGER");

		sql2.append(");");
		db.execSQL(sql2.toString());
	}

	public DBService(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
