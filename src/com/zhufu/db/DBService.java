package com.zhufu.db;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;

import com.zhufuyisheng.util.Mstring;

public class DBService extends SQLiteOpenHelper {
	private final static int DATABASE_VERSION = 2;
	private final static String DATABASE_NAME = "user.db";

	private final static String TABLE_NAME = "tag";
	private final static String ID = "id";

	private final static String UID = "uid";
	private final static String TYPE = "type"; // 1,点击 2,赞

	public void onCreate(SQLiteDatabase db) {

		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE ");
		sql.append("type");
		sql.append(" (");
		sql.append(ID + " INTEGER primary key autoincrement, ");

		sql.append("uid" + " TEXT,");
		sql.append("stat" + " TEXT,");
		sql.append("type" + " TEXT,");
		sql.append("img" + " TEXT,");
		sql.append("head" + " TEXT,");
		sql.append("tid" + " TEXT");

		sql.append(");");
		db.execSQL(sql.toString());

		StringBuilder sql2 = new StringBuilder();
		sql2.append("CREATE TABLE ");
		sql2.append(TABLE_NAME);
		sql2.append(" (");
		sql2.append(ID + " INTEGER primary key autoincrement, ");

		sql2.append(UID + " INTEGER,");
		sql2.append(TYPE + " INTEGER");

		sql2.append(");");
		db.execSQL(sql2.toString());

		StringBuilder sql3 = new StringBuilder();
		sql3.append("CREATE TABLE ");
		sql3.append("read");
		sql3.append(" (");
		sql3.append(ID + " INTEGER primary key autoincrement, ");
		sql3.append("uid" + " TEXT,");
		sql3.append("name" + " TEXT,");
		sql3.append("date" + " TEXT,");
		sql3.append("tag1" + " TEXT,");
		sql3.append("tag2" + " TEXT,");

		sql3.append("boby" + " TEXT");

		sql3.append(");");
		db.execSQL(sql3.toString());

		StringBuilder sql4 = new StringBuilder();
		sql4.append("CREATE TABLE ");
		sql4.append("img");
		sql4.append(" (");
		sql4.append(ID + " INTEGER primary key autoincrement, ");
		sql4.append("uid" + " TEXT,");
		sql4.append("name" + " TEXT,");
		sql4.append("date" + " TEXT,");
		sql4.append("tag1" + " TEXT,");
		sql4.append("tag2" + " TEXT,");

		sql4.append("boby" + " TEXT");

		sql4.append(");");
		db.execSQL(sql4.toString());

		StringBuilder sql5 = new StringBuilder();
		sql5.append("CREATE TABLE ");
		sql5.append("video");
		sql5.append(" (");
		sql5.append(ID + " INTEGER primary key autoincrement, ");
		sql5.append("uid" + " TEXT,");
		sql5.append("name" + " TEXT,");
		sql5.append("date" + " TEXT,");
		sql5.append("tag1" + " TEXT,");
		sql5.append("tag2" + " TEXT,");

		sql5.append("boby" + " TEXT");

		sql5.append(");");
		db.execSQL(sql5.toString());
	}

	public DBService(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public void execSQL(String sql, Object[] args) {
		SQLiteDatabase db = this.getWritableDatabase(Mstring.decrypetkey());
		db.execSQL(sql, args);
	}

	public Cursor query(String sql, String[] args) {
		SQLiteDatabase db = this.getReadableDatabase(Mstring.decrypetkey());
		Cursor cursor = db.rawQuery(sql, args);
		return cursor;
	}

}
