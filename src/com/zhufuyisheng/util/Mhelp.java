package com.zhufuyisheng.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Toast;

public class Mhelp {
	/**
	 * toast短消息框
	 * 
	 * @param context
	 * @param text
	 */
	public static void shoarttoast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * toast长消息框
	 * 
	 * @param context
	 * @param text
	 */
	public static void longtoast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

	/**
	 * 查询某目录下是否有txt文件
	 * 
	 * @param folder
	 * @return true 有 false 没有
	 */
	public static File Scanning(File folder) {
		// 指定正则表达式
		Pattern mPattern = Pattern.compile("([^\\.]*)\\.([^\\.]*)");
		// 当前目录下的所有文件
		final String[] filenames = folder.list();

		if (filenames != null) {
			// 遍历当前目录下的所有文件
			for (String name : filenames) {
				File file = new File(folder, name);
				// 如果是文件夹则继续递归当前方法
				if (file.isDirectory()) {
					Scanning(file);
				}
				// 如果是文件则对文件进行相关操作
				else {
					Matcher matcher = mPattern.matcher(name);
					if (matcher.matches()) {
						// 文件名称
						String fileName = matcher.group(1);
						// 文件后缀
						String fileExtension = matcher.group(2);
						// 文件路径
						String filePath = file.getAbsolutePath();

						if (isTxt(fileExtension)) {
							// 初始化音乐文件......................
							System.out
									.println("This file is Music File,fileName="
											+ fileName
											+ "."
											+ fileExtension
											+ ",filePath=" + filePath);
							return file;
						}

					}
				}
			}
		}
		return null;
	}

	/**
	 * 判断是否是txt文件
	 * 
	 * @param extension
	 *            后缀名
	 * @return
	 */
	public static boolean isTxt(String extension) {
		if (extension == null)
			return false;

		final String ext = extension.toLowerCase();
		if (ext.equals("txt")) {
			return true;
		}
		return false;
	}

	/**
	 * 删除文件和文件夹
	 * 
	 * @param file
	 */

	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}

		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}

			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
	}

	/**
	 * 
	 * @param name
	 *            存储的名字
	 * @param key
	 *            存储的键值对
	 */

	public static String GetSharedPreferences(Context context, String name,
			String key) {
		String valueString = context.getSharedPreferences(name,
				Context.MODE_PRIVATE).getString(key, "");
		return valueString;
	}

	/**
	 * 
	 * @param context
	 * @param name
	 *            存储名字
	 * @param key
	 *            存储的key
	 * @param value
	 *            存储的内容
	 */

	public static void SetSharedPreferences(Context context, String name,
			String key, String value) {
		SharedPreferences mSharedPreferences = context.getSharedPreferences(
				name, Context.MODE_PRIVATE);
		SharedPreferences.Editor mEditor = mSharedPreferences.edit();
		mEditor.putString(key, value);

		mEditor.commit();

	}

	/**
	 * 
	 * assets安装apk
	 * 
	 * @param ctx
	 */
	public void installApk(Context ctx) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		String type = "application/vnd.android.package-archive";
		try {
			// 从assets读取文件流
			InputStream is = getClass()
					.getResourceAsStream("/assets/zhufu.apk");
			// 将该文件流写入到本应用程序的私有数据区this.getFilesDir().getPath();
			FileOutputStream fos = ctx.openFileOutput("zhufu.apk",
					Context.MODE_PRIVATE + Context.MODE_WORLD_READABLE);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			fos.flush();
			is.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		File f = new File(ctx.getFilesDir().getPath() + "/zhufu.apk");

		intent.setDataAndType(Uri.fromFile(f), type);
		ctx.startActivity(intent);
	}
}
