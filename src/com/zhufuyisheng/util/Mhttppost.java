package com.zhufuyisheng.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

/**
 * http post 请求
 * 
 * @author Administrator
 * 
 */
public class Mhttppost {
	public static String post(String uri, String key, String name) {
		String strResult;
		HttpPost httpRequest = new HttpPost(uri);
		/*
		 * Post运作传送变量必须用NameValuePair[]数组储存
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair(key, name));

		try {
			/* 发出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* 取得HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			/* 若状态码为200 ok */

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 取出响应字符串 */
				StringBuilder sb = new StringBuilder();
				// strResult =
				// EntityUtils.toString(httpResponse.getEntity()).trim();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(httpResponse.getEntity()
								.getContent(), "UTF-8"), 8192);

				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				strResult = sb.toString().trim();
			} else {
				strResult = "error";
			}
		} catch (ClientProtocolException e) {
			strResult = "error";
			e.printStackTrace();
		} catch (IOException e) {
			strResult = "error";
			e.printStackTrace();
		} catch (Exception e) {
			strResult = "error";
			e.printStackTrace();
		}

		return strResult;
	}

	public static String post(String uri, String key, String name, String key2,
			String name2) {
		String strResult;
		HttpPost httpRequest = new HttpPost(uri);
		/*
		 * Post运作传送变量必须用NameValuePair[]数组储存
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(key, name));
		params.add(new BasicNameValuePair(key2, name2));
		try {
			/* 发出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* 取得HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			/* 若状态码为200 ok */

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 取出响应字符串 */
				StringBuilder sb = new StringBuilder();
				// strResult =
				// EntityUtils.toString(httpResponse.getEntity()).trim();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(httpResponse.getEntity()
								.getContent(), "UTF-8"), 8192);

				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				strResult = sb.toString().trim();
			} else {
				strResult = "error";
			}
		} catch (ClientProtocolException e) {
			strResult = "error";
			e.printStackTrace();
		} catch (IOException e) {
			strResult = "error";
			e.printStackTrace();
		} catch (Exception e) {
			strResult = "error";
			e.printStackTrace();
		}
		return strResult;
	}

	public static String post(String uri, String key, String name, String key2,
			String name2, String key3, String name3) {
		String strResult;
		HttpPost httpRequest = new HttpPost(uri);
		/*
		 * Post运作传送变量必须用NameValuePair[]数组储存
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(key, name));
		params.add(new BasicNameValuePair(key2, name2));
		params.add(new BasicNameValuePair(key3, name3));
		try {
			/* 发出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* 取得HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			/* 若状态码为200 ok */

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 取出响应字符串 */
				StringBuilder sb = new StringBuilder();
				// strResult =
				// EntityUtils.toString(httpResponse.getEntity()).trim();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(httpResponse.getEntity()
								.getContent(), "UTF-8"), 8192);

				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				strResult = sb.toString().trim();
			} else {
				strResult = "error";
			}
		} catch (ClientProtocolException e) {
			strResult = "error";
			e.printStackTrace();
		} catch (IOException e) {
			strResult = "error";
			e.printStackTrace();
		} catch (Exception e) {
			strResult = "error";
			e.printStackTrace();
		}
		return strResult;
	}

	public static String post(String uri, String key, String name, String key2,
			String name2, String key3, String name3, String key4, String name4) {
		String strResult;
		HttpPost httpRequest = new HttpPost(uri);
		/*
		 * Post运作传送变量必须用NameValuePair[]数组储存
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(key, name));
		params.add(new BasicNameValuePair(key2, name2));
		params.add(new BasicNameValuePair(key3, name3));
		params.add(new BasicNameValuePair(key4, name4));
		try {
			/* 发出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* 取得HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			/* 若状态码为200 ok */

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 取出响应字符串 */
				StringBuilder sb = new StringBuilder();
				// strResult =
				// EntityUtils.toString(httpResponse.getEntity()).trim();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(httpResponse.getEntity()
								.getContent(), "UTF-8"), 8192);

				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				strResult = sb.toString().trim();
			} else {
				strResult = "error";
			}
		} catch (ClientProtocolException e) {
			strResult = "error";
			e.printStackTrace();
		} catch (IOException e) {
			strResult = "error";
			e.printStackTrace();
		} catch (Exception e) {
			strResult = "error";
			e.printStackTrace();
		}
		return strResult;
	}
}
