package com.zhufuyisheng.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class FileDownloader {
	static final String TAG = "FILE DOWNLOADER";
	Thread thread = null;
	Message msg = null;
	Handler handler = null;

	String url;
	String path;
	String fileName;
	int fileSize = 0;
	int downloadPosition = 0;
	public static final int ERROR = 0;
	public static final int START = 1;
	public static final int PROCESS = 2;
	public static final int COMPLETE = 3;

	public FileDownloader(String url, String path, String fileName,
			Handler handler) {
		this.url = url;
		this.path = path;
		this.fileName = fileName;
		this.handler = handler;

		CreateThread(url, path, fileName);
	}

	public void Start() {
		thread.start();
	}

	public void Stop() {
		thread.stop();
	}

	private void CreateThread(final String url, final String path,
			final String fileName) {
		thread = new Thread() {
			public void run() {
				downFile(url, path, fileName);
			}
		};
	}

	private void downFile(String url, String path, String fileName) {
		File pathFile = new File(path);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		URL Url;
		try {
			if (url == null || url == "" || path == null || path == ""
					|| fileName == null || fileName == "")
				throw new RuntimeException("文件路径不完整");
			Url = new URL(url);
			HttpURLConnection urlConn = (HttpURLConnection) Url
					.openConnection();
			// URLConnection urlConn = Url.openConnection();
			urlConn.setRequestMethod("GET");
			int code = urlConn.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				urlConn.connect();
				InputStream is = urlConn.getInputStream();
				fileSize = urlConn.getContentLength();
				if (fileSize < 1)
					throw new RuntimeException("无法获取文件长度");
				downloadPosition = 0;
				sendStartMessage(fileSize);
				File filePath = new File(path);
				if (!filePath.exists())
					filePath.mkdir();
				FileOutputStream FOS = new FileOutputStream(filePath + "/"
						+ fileName);
				byte[] buffer = new byte[1024 * 4];
				int numread = 0;
				while ((numread = is.read(buffer)) != -1) {
					FOS.write(buffer, 0, numread);
					downloadPosition += numread;
					sendProcessMessage(downloadPosition);
				}
				FOS.close();
				is.close();
				sendCompleteMessage();
			} else {
				sendErrorMessage("网络错误，返回值:" + code);
			}
		} catch (MalformedURLException e) {
			sendErrorMessage("URL:" + e.getMessage());
		} catch (IOException e) {
			sendErrorMessage("IO:" + e.getMessage());
		} catch (Exception e) {
			sendErrorMessage("Runtime:" + e.getMessage());
		}
	}

	private void sendStartMessage(int length) {
		Log.d(TAG, "开始，文件长度：" + length);
		if (handler != null) {
			msg = new Message();
			msg.what = FileDownloader.START;
			msg.arg1 = fileSize;
			msg.arg2 = downloadPosition;
			handler.sendMessage(msg);
		}
	}

	private void sendProcessMessage(int postion) {
		Log.d(TAG, "已经下载：" + postion);

		if (handler != null) {
			msg = new Message();
			msg.what = FileDownloader.PROCESS;
			msg.arg1 = fileSize;
			msg.arg2 = downloadPosition;
			handler.sendMessage(msg);
		}

	}

	private void sendCompleteMessage() {
		Log.d(TAG, "文件下载完成");
		if (handler != null) {
			msg = new Message();
			msg.what = FileDownloader.COMPLETE;
			msg.obj = fileName;
			handler.sendMessage(msg);
		}
	}

	private void sendErrorMessage(String errorMsg) {
		Log.d(TAG, "出现错误：" + errorMsg);
		if (handler == null) {
			msg = new Message();
			msg.what = FileDownloader.ERROR;
			msg.obj = errorMsg;
			handler.sendMessage(msg);
		}
	}

	public boolean isAlive() {
		return thread.isAlive();
	}
}
