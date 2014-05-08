package com.zhufuyisheng.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.rarfile.FileHeader;

public class UnRarUtil {

	private static void unrar(String sourceRar, String destDir)
			throws Exception {
		Archive a = null;
		FileOutputStream fos = null;
		try {
			a = new Archive(new File(sourceRar));
			FileHeader fh = a.nextFileHeader();
			while (fh != null) {
				if (!fh.isDirectory()) {
					// 1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
					String compressFileName = fh.getFileNameW().trim();
					if (!existZH(compressFileName)) {
						compressFileName = fh.getFileNameString().trim();
					}
					String destFileName = "";
					String destDirName = "";
					// 非windows系统
					if (File.separator.equals("/")) {
						destFileName = destDir
								+ compressFileName.replaceAll("\\\\", "/");
						destDirName = destFileName.substring(0,
								destFileName.lastIndexOf("/"));
						// windows系统
					} else {
						destFileName = destDir
								+ compressFileName.replaceAll("/", "\\\\");
						destDirName = destFileName.substring(0,
								destFileName.lastIndexOf("\\"));
					}
					// 2创建文件夹
					File dir = new File(destDirName);
					if (!dir.exists() || !dir.isDirectory()) {
						dir.mkdirs();
					}
					// 3解压缩文件
					fos = new FileOutputStream(new File(destFileName));
					a.extractFile(fh, fos);
					fos.close();
					fos = null;
				}
				fh = a.nextFileHeader();
			}
			a.close();
			a = null;
		} catch (Exception e) {
			throw e;
		} finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (a != null) {
				try {
					a.close();
					a = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void deCompress(String sourceFile, String destDir)
			throws Exception {
		// 保证文件夹路径最后是"/"或者"\"
		char lastChar = destDir.charAt(destDir.length() - 1);
		if (lastChar != '/' && lastChar != '\\') {
			destDir += File.separator;
		}
		// 根据类型，进行相应的解压缩
		String type = sourceFile.substring(sourceFile.lastIndexOf(".") + 1);
		if (type.equals("rar")) {
			UnRarUtil.unrar(sourceFile, destDir);
		} else {
			throw new Exception("error");
		}
	}

	/**
	 * 判断是否为中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean existZH(String str) {
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while (m.find()) {
			return true;
		}
		return false;
	}
}