package com.wzf.com.commutil.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件管理类
 * @author wzf
 *20151222
 */
public class FileUtils {
	/**
	 * 检查文件是否存在
	 * @param path
	 * @return Boolean
	 */
	public static boolean checkFileExists(String path) {
		if(StringU.isBlank(path))
			return false;
		File file = new File(path);
		return file.exists();
	}
	/**
	 * 创建一个全新的文件,不管原来是否存在该文件
	 * @param path
	 * @return File
	 */
	public static File createNewFileFromPath(String path) {
		if(StringU.isBlank(path))
			return null;
		File file = new File(path);
		if(file.exists()) {
			file.delete();
		}
		if(! file.getParentFile().exists())
			new File(file.getParentFile().getAbsolutePath()).mkdirs();
		try {
			file.createNewFile();
			return file;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 复制assert文件到另外一个目录中去
	 * @param context
	 * @param newFilePath
	 * @param fromPath
	 */
	public static void createFileFromAssertPath(Context context, String newFilePath, String fromPath) {
		if(NullU.isNull(context) || StringU.isBlank(newFilePath) || StringU.isBlank(fromPath))
			return;
		File file = createNewFileFromPath(newFilePath);
		if(null == file)
			return;
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			is = context.getResources().getAssets().open(fromPath);
			fos = new FileOutputStream(file);
			byte[] buff = new byte[8192];
			while (is.read(buff) > 0) {
				fos.write(buff);
			}
			L.e("数据库文件复制成功！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fos.flush();
				is.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
