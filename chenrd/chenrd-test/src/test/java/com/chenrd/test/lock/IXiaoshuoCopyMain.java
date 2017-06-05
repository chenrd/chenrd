/*
 * 文件名：IXiaoshuoCopyMain.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：chenrd
 * 修改时间：2017年4月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.chenrd.test.lock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

public class IXiaoshuoCopyMain {
	
	public static void main(String[] args) throws IOException {
		File file = new File("C:/Users/xuwenqiang/git/ixiaoshuo-android/src/main/java");
		for (File f : file.listFiles()) {
			listHandleFile(f);
		}
	}
	
	private static void listHandleFile(File file) throws IOException {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				listHandleFile(f);
			}
		} else {
			updateFile(file);
		}
	}
	
	private static void updateFile(File file) throws IOException {
		System.out.println("正在修改：" + file.getPath());
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		StringWriter writer = new StringWriter();
		String input = null;
		File targetFile = null;
		while ((input = bufferedReader.readLine()) != null) {
			if (input.indexOf("package") == 0) {
				String name = input.split("com.vincestyling.ixiaoshuo")[1];
				name = name.replace(".", "/").replace(";", "");
				System.out.println("E:/workFile/newWorkspace/ocean/jyue/src/com/chenrd/jyue" + name);
				File mkdirs = new File("E:/workFile/newWorkspace/ocean/jyue/src/com/chenrd/jyue" + name);
				if (!mkdirs.exists()) {
					mkdirs.mkdirs();
				}
				targetFile = new File("E:/workFile/newWorkspace/ocean/jyue/src/com/chenrd/jyue" + name + "/" + file.getName());
			}
			writer.write(input.replace("com.vincestyling.ixiaoshuo", "com.chenrd.jyue"));
			writer.write("\r\n");
		}
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile)));
		bufferedWriter.write(writer.toString());
		bufferedWriter.close();
		bufferedReader.close();
	}
}
